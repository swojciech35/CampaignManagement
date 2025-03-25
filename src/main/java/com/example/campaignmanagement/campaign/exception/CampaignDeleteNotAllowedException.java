package com.example.campaignmanagement.campaign.exception;

public class CampaignDeleteNotAllowedException extends RuntimeException {
  public CampaignDeleteNotAllowedException() {
    super("Campaign can only be deleted by the creator");
  }
}
