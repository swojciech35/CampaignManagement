package com.example.campaignmanagement.campaign.domain;

import com.example.campaignmanagement.campaign.dto.CampaignDto;
import com.example.campaignmanagement.campaign.dto.CampaignLightDto;
import com.example.campaignmanagement.campaign.dto.CreateCampaignDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
class Campaign {
  @Id
  @Setter
  UUID id;

  @Column(nullable = false)
  String name;

  @OneToMany(mappedBy = "campaignId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  Set<CampaignKeywords> keywords;

  @Column(nullable = false)
  BigDecimal bidAmount;

  @Column(nullable = false)
  BigDecimal campaignFund;

  @Column(nullable = false)
  boolean status;

  @Column(nullable = false)
  String town;

  @Column(nullable = false)
  int radius;


  public CampaignDto toDto() {
    return CampaignDto.builder()
            .id(id)
            .name(name)
            .keywords(keywords.stream().map(CampaignKeywords::toDto).collect(Collectors.toSet()))
            .bidAmount(bidAmount)
            .campaignFund(campaignFund)
            .status(status)
            .town(town)
            .radius(radius)
            .build();
  }

  public CampaignLightDto toLightDto() {
    return CampaignLightDto.builder()
            .id(id)
            .name(name)
            .keywords(keywords.stream().map(CampaignKeywords::getKeyword).collect(Collectors.toSet()))
            .bidAmount(bidAmount)
            .campaignFund(campaignFund)
            .status(status)
            .town(town)
            .radius(radius)
            .build();
  }

  static Campaign fromDto(CreateCampaignDto createCampaignDto) {
    UUID campaignId = UUID.randomUUID();
    return Campaign.builder()
            .id(campaignId)
            .name(createCampaignDto.getName())
            .keywords(toCampaignKeywords(campaignId, createCampaignDto.getKeywords()))
            .bidAmount(createCampaignDto.getBidAmount())
            .campaignFund(createCampaignDto.getCampaignFund())
            .status(createCampaignDto.getStatus())
            .town(createCampaignDto.getTown())
            .radius(createCampaignDto.getRadius())
            .build();
  }

  private static Set<CampaignKeywords> toCampaignKeywords(UUID campaignId, Set<String> keywords) {
    return keywords.stream()
            .map(keyword -> new CampaignKeywords(UUID.randomUUID(), campaignId, keyword))
            .collect(Collectors.toSet());
  }

}
