package com.example.campaignmanagement.campaign.domain;

import com.example.campaignmanagement.campaign.dto.CampaignKeywordsDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "campaign_keywords")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CampaignKeyword {
  @Id
  UUID campaignKeywordsId;
  UUID campaignId;
  String keyword;

  CampaignKeywordsDto toDto() {
    return new CampaignKeywordsDto(campaignKeywordsId, campaignId, keyword);
  }
}
