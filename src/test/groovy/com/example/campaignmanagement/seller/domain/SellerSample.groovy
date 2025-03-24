package com.example.campaignmanagement.seller.domain

import com.example.campaignmanagement.seller.dto.CreateSellerDto

trait SellerSample {
    final static String SELLER1= "Seller1"
    final static String SELLER2= "Seller2"

    CreateSellerDto CREATE_SELLER1 = new CreateSellerDto(SELLER1)
    CreateSellerDto CREATE_SELLER2 = new CreateSellerDto(SELLER2)
    CreateSellerDto CREATE_SELLER_WITH_NULL_NAME = new CreateSellerDto(null)
}