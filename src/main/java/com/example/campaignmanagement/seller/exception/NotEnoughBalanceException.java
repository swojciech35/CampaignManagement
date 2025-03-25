package com.example.campaignmanagement.seller.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotEnoughBalanceException extends RuntimeException {

  public NotEnoughBalanceException(String message) {
    super(message);
  }
}
