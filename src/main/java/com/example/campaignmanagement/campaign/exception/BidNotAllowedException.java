package com.example.campaignmanagement.campaign.exception;

public class BidNotAllowedException extends RuntimeException {
  public BidNotAllowedException(String message) {
    super(message);
  }
}
