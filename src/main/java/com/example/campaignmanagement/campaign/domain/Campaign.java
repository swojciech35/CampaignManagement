package com.example.campaignmanagement.campaign.domain;

import com.example.campaignmanagement.campaign.dto.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
@Entity
@Builder
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
class Campaign {
  @Id
  @Setter
  UUID id;

  @Column(nullable = false)
  String name;

  @OneToMany(mappedBy = "campaignId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  Set<CampaignKeyword> keywords;

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
  @Column(nullable = false)
  UUID createdBy;


  public CampaignDto toDto() {
    return CampaignDto.builder()
            .id(id)
            .name(name)
            .keywords(keywords.stream().map(CampaignKeyword::toDto).collect(Collectors.toSet()))
            .bidAmount(bidAmount)
            .campaignFund(campaignFund)
            .status(status)
            .town(town)
            .radius(radius)
            .createdBy(createdBy)
            .build();
  }

  public CampaignLightDto toLightDto() {
    return CampaignLightDto.builder()
            .id(id)
            .name(name)
            .keywords(keywords.stream().map(CampaignKeyword::getKeyword).collect(Collectors.toSet()))
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
            .createdBy(createCampaignDto.getCreatedBy())
            .build();
  }

  void update(UpdateCampaignDto updateCampaignDto) {
    this.name = updateCampaignDto.getName();
    this.keywords = updateKeywords(updateCampaignDto.getKeywords());
    this.bidAmount = updateCampaignDto.getBidAmount();
    this.campaignFund = updateCampaignDto.getCampaignFund();
    this.status = updateCampaignDto.getStatus();
    this.town = updateCampaignDto.getTown();
    this.radius = updateCampaignDto.getRadius();
  }

  private static Set<CampaignKeyword> toCampaignKeywords(UUID campaignId, Set<String> keywords) {
    return keywords.stream()
            .map(keyword -> new CampaignKeyword(UUID.randomUUID(), campaignId, keyword))
            .collect(Collectors.toSet());
  }

  private CampaignKeyword toCampaignKeyword(CampaignKeywordsDto campaignKeywordsDto) {
    return new CampaignKeyword(campaignKeywordsDto.getCampaignKeywordsId(), this.id, campaignKeywordsDto.getKeyword());
  }

  private Set<CampaignKeyword> updateKeywords(Set<CampaignKeywordsDto> campaignKeywordsDto) {
    Set<CampaignKeyword> campaignKeywordsToUpdate = campaignKeywordsDto.stream()
            .filter(keyword -> keyword.getCampaignKeywordsId() != null)
            .map(this::toCampaignKeyword)
            .collect(Collectors.toSet());
    Set<CampaignKeyword> newCampaignKeywords = campaignKeywordsDto.stream()
            .filter(keyword -> keyword.getCampaignKeywordsId() == null)
            .map(keyword -> new CampaignKeyword(UUID.randomUUID(), this.id, keyword.getKeyword()))
            .collect(Collectors.toSet());
    return Stream.concat(campaignKeywordsToUpdate.stream(), newCampaignKeywords.stream()).collect(Collectors.toSet());
  }
}
