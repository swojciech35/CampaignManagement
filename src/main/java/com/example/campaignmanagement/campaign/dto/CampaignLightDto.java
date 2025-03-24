package com.example.campaignmanagement.campaign.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode(exclude = "id")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CampaignLightDto {
  UUID id;
  String name;
  Set<String> keywords;
  BigDecimal bidAmount;
  BigDecimal campaignFund;
  boolean status;
  String town;
  int radius;
  UUID createdBy;
}
