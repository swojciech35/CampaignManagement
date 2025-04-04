package com.example.campaignmanagement.campaign.dto;


import com.example.campaignmanagement.campaign.enums.Town;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CampaignDto {
  UUID id;
  String name;
  Set<CampaignKeywordsDto> keywords;
  BigDecimal bidAmount;
  BigDecimal campaignFund;
  boolean status;
  Town town;
  int radius;
  UUID createdBy;
}
