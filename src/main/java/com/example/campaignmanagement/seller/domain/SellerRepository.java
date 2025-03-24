package com.example.campaignmanagement.seller.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SellerRepository extends JpaRepository<Seller, UUID> {
  boolean existsSellerByName(String name);
}
