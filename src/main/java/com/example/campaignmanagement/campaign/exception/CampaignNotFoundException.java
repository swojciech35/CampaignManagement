package com.example.campaignmanagement.campaign.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

public class CampaignNotFoundException extends RuntimeException {
  public CampaignNotFoundException(UUID campaignId) {
    super("Campaign not found with id: " + campaignId);
  }
}
