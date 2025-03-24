package com.example.campaignmanagement.campaign.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CampaignFacadeConfiguration {

  CampaignRepository campaignRepository;

  @Autowired
  public CampaignFacadeConfiguration(CampaignRepository campaignRepository) {
    this.campaignRepository = campaignRepository;
  }

  @Bean
  CampaignFacade campaignFacade() {
    return new CampaignFacade(campaignRepository);
  }
}
