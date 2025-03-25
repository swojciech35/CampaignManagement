package com.example.campaignmanagement.campaign.dto;

import com.example.campaignmanagement.campaign.enums.Town;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCampaignDto {
  String name;
  Set<CampaignKeywordsDto> keywords;
  BigDecimal bidAmount;
  BigDecimal campaignFund;
  Boolean status;
  Town town;
  Integer radius;
  UUID updater;
}
