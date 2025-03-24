package com.example.campaignmanagement.seller.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerNotFoundException extends RuntimeException {
  public SellerNotFoundException(UUID sellerId) {
    super("Seller not found with id: " + sellerId);
  }
}
