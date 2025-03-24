package com.example.campaignmanagement.seller.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IllegalValueException extends RuntimeException {
  public IllegalValueException(String message) {
    super(message);
  }
}
