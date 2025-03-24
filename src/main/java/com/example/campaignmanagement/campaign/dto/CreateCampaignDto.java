package com.example.campaignmanagement.campaign.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

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
}
