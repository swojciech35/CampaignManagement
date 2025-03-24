package com.example.campaignmanagement.campaign.domain

import com.example.campaignmanagement.campaign.dto.CampaignLightDto
import com.example.campaignmanagement.campaign.dto.CreateCampaignDto

trait CampaignSample {

    final static String NAME = "Campaign for 4K TV"
    final static String TOWN = "WARSAW"
    final static Set<String> KEYWORDS = ["TV", "4k", "UHD"] as Set


    CreateCampaignDto TV_CAMPAIGN = CreateCampaignDto.builder()
            .name(NAME)
            .keywords(KEYWORDS)
            .campaignFund(new BigDecimal(1000))
            .status(true)
            .town(TOWN)
            .bidAmount(BigDecimal.ONE)
            .radius(10)
            .build()

    CampaignLightDto TV_CAMPAIGN_DTO = CampaignLightDto.builder()
            .name(NAME)
            .keywords(KEYWORDS)
            .campaignFund(new BigDecimal(1000))
            .status(true)
            .town(TOWN)
            .bidAmount(BigDecimal.ONE)
            .radius(10)
            .build()

}