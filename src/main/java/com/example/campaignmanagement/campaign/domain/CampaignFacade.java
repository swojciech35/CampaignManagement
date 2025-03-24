package com.example.campaignmanagement.campaign.domain;

import com.example.campaignmanagement.campaign.dto.CampaignDto;
import com.example.campaignmanagement.campaign.dto.CampaignLightDto;
import com.example.campaignmanagement.campaign.dto.CreateCampaignDto;
import com.example.campaignmanagement.campaign.exception.InvalidDataException;
import com.example.campaignmanagement.seller.domain.SellerFacade;
import com.example.campaignmanagement.seller.exception.SellerNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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
    if (!isValidData(createCampaignDto)) {
      throw new InvalidDataException("Invalid data in createCampaignDto");
    }
    Campaign campaign = Campaign.fromDto(createCampaignDto);
    return campaignRepository.save(campaign).toDto();
  }

  private boolean isValidData(CreateCampaignDto createCampaignDto) {
    return Objects.nonNull(createCampaignDto.getName()) &&
            Objects.nonNull(createCampaignDto.getKeywords()) &&
            !createCampaignDto.getKeywords().isEmpty() &&
            Objects.nonNull(createCampaignDto.getBidAmount()) &&
            createCampaignDto.getBidAmount().compareTo(BigDecimal.ZERO) > 0 &&
            Objects.nonNull(createCampaignDto.getCampaignFund()) &&
            Objects.nonNull(createCampaignDto.getStatus()) &&
            Objects.nonNull(createCampaignDto.getTown()) &&
            Objects.nonNull(createCampaignDto.getRadius());
  }

}
