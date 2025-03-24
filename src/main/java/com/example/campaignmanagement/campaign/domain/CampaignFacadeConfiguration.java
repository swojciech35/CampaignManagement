package com.example.campaignmanagement.campaign.domain;

import com.example.campaignmanagement.seller.domain.SellerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CampaignFacadeConfiguration {

  CampaignRepository campaignRepository;
  SellerFacade sellerFacade;
  @Autowired
  public CampaignFacadeConfiguration(CampaignRepository campaignRepository, SellerFacade sellerFacade) {
    this.campaignRepository = campaignRepository;
    this.sellerFacade = sellerFacade;
  }

  @Bean
  CampaignFacade campaignFacade() {
    return new CampaignFacade(campaignRepository, sellerFacade);
  }
}
