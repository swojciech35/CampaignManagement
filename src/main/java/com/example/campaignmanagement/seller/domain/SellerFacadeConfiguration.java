package com.example.campaignmanagement.seller.domain;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class SellerFacadeConfiguration {

  SellerRepository sellerRepository;

  @Autowired
  public SellerFacadeConfiguration(SellerRepository sellerRepository) {
    this.sellerRepository = sellerRepository;
  }

  @Bean
  public SellerFacade createSellerFacade() {
    return new SellerFacade(sellerRepository);
  }
}
