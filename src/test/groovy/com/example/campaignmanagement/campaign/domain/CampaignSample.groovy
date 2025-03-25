package com.example.campaignmanagement.campaign.domain

import com.example.campaignmanagement.campaign.dto.CampaignLightDto
import com.example.campaignmanagement.campaign.dto.CreateCampaignDto
import com.example.campaignmanagement.campaign.enums.Town
import spock.lang.Shared

trait CampaignSample {

    final static String NAME = "Campaign for 4K TV"
    @Shared
    final static Town WARSAW = Town.WARSAW
    final static Set<String> KEYWORDS = ["TV", "4k", "UHD"] as Set
    final static UUID SELLER_ID = UUID.randomUUID()
    final static UUID SELLER2_ID = UUID.randomUUID()

    CreateCampaignDto TV_CAMPAIGN = CreateCampaignDto.builder()
            .name(NAME)
            .keywords(KEYWORDS)
            .campaignFund(new BigDecimal(1000))
            .status(true)
            .town(WARSAW)
            .bidAmount(BigDecimal.ONE)
            .radius(10)
            .createdBy(SELLER_ID)
            .build()

    CreateCampaignDto TV_CAMPAIGN_CREATED_BY_SELLER2 = CreateCampaignDto.builder()
            .name(NAME)
            .keywords(KEYWORDS)
            .campaignFund(new BigDecimal(1000))
            .status(true)
            .town(WARSAW)
            .bidAmount(BigDecimal.ONE)
            .radius(10)
            .createdBy(SELLER2_ID)
            .build()

    CampaignLightDto TV_CAMPAIGN_DTO = CampaignLightDto.builder()
            .name(NAME)
            .keywords(KEYWORDS)
            .campaignFund(new BigDecimal(1000))
            .status(true)
            .town(WARSAW)
            .bidAmount(BigDecimal.ONE)
            .radius(10)
            .build()
}