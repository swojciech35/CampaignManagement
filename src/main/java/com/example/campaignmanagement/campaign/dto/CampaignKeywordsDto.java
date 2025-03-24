package com.example.campaignmanagement.campaign.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(exclude = "campaignKeywordsId")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CampaignKeywordsDto {
  UUID campaignKeywordsId;
  UUID campaignId;
  String keyword;
}
