package com.example.campaignmanagement;

import com.example.campaignmanagement.campaign.domain.CampaignFacade;
import com.example.campaignmanagement.campaign.dto.CreateCampaignDto;
import com.example.campaignmanagement.campaign.enums.Town;
import com.example.campaignmanagement.seller.domain.SellerFacade;
import com.example.campaignmanagement.seller.dto.AddBalanceDto;
import com.example.campaignmanagement.seller.dto.CreateSellerDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Configuration
public class DataInitializer {

  @Bean
  CommandLineRunner initData(SellerFacade sellerFacade, CampaignFacade campaignFacade) {
    return args -> {
      CreateSellerDto seller1 = new CreateSellerDto("Seller1");
      CreateSellerDto seller2 = new CreateSellerDto("Seller2");
      sellerFacade.createSeller(seller1);
      sellerFacade.createSeller(seller2);

      UUID seller1Id = sellerFacade.getAllSellers().stream().filter(s -> s.getName().equals("Seller1")).findFirst().get().getId();
      UUID seller2Id = sellerFacade.getAllSellers().stream().filter(s -> s.getName().equals("Seller2")).findFirst().get().getId();

      sellerFacade.addBalance(new AddBalanceDto(seller1Id, BigDecimal.valueOf(1000)));
      sellerFacade.addBalance(new AddBalanceDto(seller2Id, BigDecimal.valueOf(2000)));

      CreateCampaignDto campaign1 = CreateCampaignDto.builder()
              .name("Campaign1")
              .keywords(Set.of("keyword1", "keyword2"))
              .bidAmount(BigDecimal.valueOf(0.5))
              .campaignFund(BigDecimal.valueOf(500))
              .status(true)
              .town(Town.CRACOW)
              .radius(10)
              .createdBy(seller1Id)
              .build();

      CreateCampaignDto campaign2 = CreateCampaignDto.builder()
              .name("Campaign2")
              .keywords(Set.of("keyword3", "keyword4"))
              .bidAmount(BigDecimal.valueOf(2.5))
              .campaignFund(BigDecimal.valueOf(1000))
              .status(true)
              .town(Town.WARSAW)
              .radius(20)
              .createdBy(seller2Id)
              .build();

      campaignFacade.createCampaign(campaign1);
      campaignFacade.createCampaign(campaign2);
    };
  }
}