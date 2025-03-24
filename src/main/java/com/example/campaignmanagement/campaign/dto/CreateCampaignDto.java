package com.example.campaignmanagement.campaign.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCampaignDto {
  String name;
  Set<String> keywords;
  BigDecimal bidAmount;
  BigDecimal campaignFund;
  Boolean status;
  String town;
  Integer radius;
  UUID createdBy;
}
