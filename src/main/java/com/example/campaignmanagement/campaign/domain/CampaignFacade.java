package com.example.campaignmanagement.campaign.domain;

import com.example.campaignmanagement.campaign.dto.CampaignDto;
import com.example.campaignmanagement.campaign.dto.CampaignLightDto;
import com.example.campaignmanagement.campaign.dto.CreateCampaignDto;
import com.example.campaignmanagement.campaign.dto.UpdateCampaignDto;
import com.example.campaignmanagement.campaign.exception.CampaignNotFoundException;
import com.example.campaignmanagement.campaign.exception.CampaignUpdateNotAllowedException;
import com.example.campaignmanagement.seller.domain.SellerFacade;
import com.example.campaignmanagement.seller.exception.NotEnoughBalanceException;
import com.example.campaignmanagement.seller.exception.SellerNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CampaignFacade {
  CampaignRepository campaignRepository;
  SellerFacade sellerFacade;

  public CampaignFacade(CampaignRepository campaignRepository, SellerFacade sellerFacade) {
    this.campaignRepository = campaignRepository;
    this.sellerFacade = sellerFacade;
  }

  public List<CampaignLightDto> getAllCampaigns() {
    return campaignRepository.findAll().stream().map(Campaign::toLightDto).toList();
  }

  public CampaignDto createCampaign(CreateCampaignDto createCampaignDto) {
    if (!sellerFacade.isSellerExists(createCampaignDto.getCreatedBy())) {
      throw new SellerNotFoundException(createCampaignDto.getCreatedBy());
    }
    CampaignValidator.validateCreateData(createCampaignDto);
    if (!sellerFacade.hasEnoughBalance(createCampaignDto.getCreatedBy(), createCampaignDto.getCampaignFund())) {
      throw new NotEnoughBalanceException("Seller does not have enough balance to create campaign");
    }
    sellerFacade.withdrawBalance(createCampaignDto.getCreatedBy(), createCampaignDto.getCampaignFund());

    Campaign campaign = Campaign.fromDto(createCampaignDto);
    return campaignRepository.save(campaign).toDto();
  }

  public CampaignDto updateCampaign(UUID campaignId, UpdateCampaignDto updateCampaignDto) {
    Campaign campaign = campaignRepository.findById(campaignId)
            .orElseThrow(() -> new CampaignNotFoundException(campaignId));
    if (!isAuthor(updateCampaignDto, campaign)) {
      throw new CampaignUpdateNotAllowedException();
    }
    CampaignValidator.validateDataToUpdate(updateCampaignDto);
    if (updateCampaignDto.getCampaignFund().compareTo(campaign.getCampaignFund()) < 0) {
      BigDecimal refundAmount = campaign.getCampaignFund().subtract(updateCampaignDto.getCampaignFund());
      sellerFacade.refundBalance(campaign.getCreatedBy(), refundAmount);
    } else if (updateCampaignDto.getCampaignFund().compareTo(campaign.getCampaignFund()) > 0) {
      BigDecimal additionalAmount = updateCampaignDto.getCampaignFund().subtract(campaign.getCampaignFund());
      if (!sellerFacade.hasEnoughBalance(campaign.getCreatedBy(), additionalAmount)) {
        throw new NotEnoughBalanceException("Seller does not have enough balance to update campaign");
      }
      sellerFacade.withdrawBalance(campaign.getCreatedBy(), additionalAmount);
    }
    campaign.update(updateCampaignDto);
    return campaignRepository.save(campaign).toDto();
  }

  private boolean isAuthor(UpdateCampaignDto updateCampaignDto, Campaign campaign) {
    return updateCampaignDto.getUpdater().equals(campaign.getCreatedBy());
  }
}
