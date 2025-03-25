package com.example.campaignmanagement.campaign.domain;

import com.example.campaignmanagement.campaign.dto.CampaignKeywordsDto;
import com.example.campaignmanagement.campaign.dto.CreateCampaignDto;
import com.example.campaignmanagement.campaign.dto.UpdateCampaignDto;
import com.example.campaignmanagement.campaign.enums.Town;
import com.example.campaignmanagement.campaign.exception.InvalidDataException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

class CampaignValidator {
  private static final BigDecimal MIN_BID_AMOUNT = BigDecimal.valueOf(0.1);

  public static void validateCreateData(CreateCampaignDto dto) {
    validateName(dto.getName());
    validateKeywords(dto.getKeywords());
    validateBidAmountAndCampaignFund(dto.getBidAmount(), dto.getCampaignFund());
    validateStatus(dto.getStatus());
    validateTown(dto.getTown());
    validateRadius(dto.getRadius());
  }

  public static void validateDataToUpdate(UpdateCampaignDto dto) {
    validateName(dto.getName());
    validateKeywordsToUpdate(dto.getKeywords());
    validateBidAmountAndCampaignFund(dto.getBidAmount(), dto.getCampaignFund());
    validateStatus(dto.getStatus());
    validateTown(dto.getTown());
    validateRadius(dto.getRadius());
  }

  private static void validateKeywordsToUpdate(Set<CampaignKeywordsDto> keywords) {
    if (Objects.isNull(keywords) || keywords.isEmpty()) {
      throw new InvalidDataException("Keywords cannot be null or empty");
    }
  }

  private static void validateName(String name) {
    if (Objects.isNull(name)) {
      throw new InvalidDataException("Campaign name cannot be null");
    }
  }

  private static void validateKeywords(Set<String> keywords) {
    if (Objects.isNull(keywords) || keywords.isEmpty()) {
      throw new InvalidDataException("Keywords cannot be null or empty");
    }
  }

  private static void validateBidAmountAndCampaignFund(BigDecimal bidAmount, BigDecimal campaignFund) {
    if (Objects.isNull(bidAmount) || bidAmount.compareTo(MIN_BID_AMOUNT) < 0) {
      throw new InvalidDataException("Bid amount must be at least " + MIN_BID_AMOUNT);
    }
    if (Objects.isNull(campaignFund)) {
      throw new InvalidDataException("Campaign fund cannot be null");
    }
    if (campaignFund.compareTo(BigDecimal.ZERO) < 0) {
      throw new InvalidDataException("Campaign fund cannot be negative");
    }
    if (bidAmount.compareTo(campaignFund) > 0) {
      throw new InvalidDataException("Bid amount cannot be greater than campaign fund");
    }
  }


  private static void validateStatus(Object status) {
    if (Objects.isNull(status)) {
      throw new InvalidDataException("Campaign status cannot be null");
    }
  }

  private static void validateTown(Town town) {
    if (Objects.isNull(town)) {
      throw new InvalidDataException("Town cannot be null");
    }
  }

  private static void validateRadius(Integer radius) {
    if (Objects.isNull(radius)) {
      throw new InvalidDataException("Radius cannot be null");
    }
  }
}
