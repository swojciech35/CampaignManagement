package com.example.campaignmanagement.campaign;

import com.example.campaignmanagement.campaign.domain.CampaignFacade;
import com.example.campaignmanagement.campaign.dto.CampaignDto;
import com.example.campaignmanagement.campaign.dto.CampaignLightDto;
import com.example.campaignmanagement.campaign.dto.CreateCampaignDto;
import com.example.campaignmanagement.campaign.dto.UpdateCampaignDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/campaigns")
public class CampaignController {

  final CampaignFacade campaignFacade;

  public CampaignController(CampaignFacade campaignFacade) {
    this.campaignFacade = campaignFacade;
  }

  @GetMapping("/")
  public List<CampaignLightDto> getAllCampaigns() {
    return campaignFacade.getAllCampaigns();
  }

  @PostMapping("/")
  public CampaignDto createCampaign(@RequestBody CreateCampaignDto createCampaignDto) {
    return campaignFacade.createCampaign(createCampaignDto);
  }

  @PutMapping("/{campaignId}")
  public CampaignDto updateCampaign(@PathVariable UUID campaignId, @RequestBody UpdateCampaignDto updateCampaignDto) {
    return campaignFacade.updateCampaign(campaignId, updateCampaignDto);
  }
}
