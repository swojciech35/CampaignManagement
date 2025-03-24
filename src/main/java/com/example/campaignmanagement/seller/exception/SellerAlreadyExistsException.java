package com.example.campaignmanagement.seller.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerAlreadyExistsException extends RuntimeException {
  public SellerAlreadyExistsException(String name) {
    super("Seller " + name + " already exists");
  }
}
