package com.example.campaignmanagement.campaign.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

public class CampaignUpdateNotAllowedException extends RuntimeException {
  public CampaignUpdateNotAllowedException() {
    super("Campaign can only be updated by the creator");
  }
}
