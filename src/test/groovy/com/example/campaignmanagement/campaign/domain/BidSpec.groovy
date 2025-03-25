package com.example.campaignmanagement.campaign.domain

import com.example.campaignmanagement.campaign.dto.BidDto
import com.example.campaignmanagement.campaign.dto.CampaignDto
import com.example.campaignmanagement.campaign.exception.BidNotAllowedException
import com.example.campaignmanagement.seller.domain.SellerFacade
import spock.lang.Specification

class BidSpec extends Specification implements CampaignSample {

    CampaignRepository campaignRepository = new InMemoryCampaignRepository()
    SellerFacade sellerFacade = Mock(SellerFacade)

    CampaignFacade campaignFacade = new CampaignFacade(campaignRepository, sellerFacade)

    def setup() {
        given: "Seller with id #SELLER_ID  exists"
          sellerFacade.isSellerExists(SELLER_ID) >> true
          sellerFacade.isSellerExists(SELLER2_ID) >> true
        and: "Seller with id has enough balance"
          sellerFacade.hasEnoughBalance(SELLER_ID, _) >> true
    }

    def "Should bid on a campaign"() {
        given: "Campaign exists"
          CampaignDto campaignDto = campaignFacade.createCampaign(TV_CAMPAIGN)
        when: "User bid on a campaign"
          campaignFacade.placeBid(new BidDto(campaignDto.getId(), SELLER2_ID))
        then: "Campaign is bidded"
          campaignFacade.getCampaignFullInfo(campaignDto.getId()).getCampaignFund() == TV_CAMPAIGN.campaignFund.subtract(TV_CAMPAIGN.bidAmount)
    }

    def "Shouldn't bid on a campaign if seller is campaign author"() {
        given: "Campaign exists"
          CampaignDto campaignDto = campaignFacade.createCampaign(TV_CAMPAIGN)
        when: "User bid on a campaign"
          campaignFacade.placeBid(new BidDto(campaignDto.getId(), SELLER_ID))
        then: "Campaign is not bidded"
          campaignFacade.getCampaignFullInfo(campaignDto.getId()).getCampaignFund() == TV_CAMPAIGN.campaignFund
        and: "Exception is thrown"
          thrown(BidNotAllowedException)
    }

    def "Shouldn't bid on a campaign if campaign is not active"() {
        given: "Campaign exists with not active status"
          CampaignDto campaignDto = campaignFacade.createCampaign(NOT_ACTIVE_TV_CAMPAIGN)
        when: "User bid on a campaign"
          campaignFacade.placeBid(new BidDto(campaignDto.getId(), SELLER2_ID))
        then: "Campaign is not bidded"
          campaignFacade.getCampaignFullInfo(campaignDto.getId()).getCampaignFund() == TV_CAMPAIGN.campaignFund
        and: "Exception is thrown"
          thrown(BidNotAllowedException)
    }

    def "should automatically deactivate campaign if campaign fund is less than bid amount"() {
        given: "Campaign exists"
          CampaignDto campaignDto = campaignFacade.createCampaign(TV_CAMPAIGN_WITH_SMALL_CAMPAIGN_FUND)
        when: "User bid on a campaign"
          campaignFacade.placeBid(new BidDto(campaignDto.getId(), SELLER2_ID))
        and: "user bid a second time"
          campaignFacade.placeBid(new BidDto(campaignDto.getId(), SELLER2_ID))
        then: "Campaign is not bidded"
          campaignFacade.getCampaignFullInfo(campaignDto.getId()).getCampaignFund() == BigDecimal.ZERO
        and: "Campaign is deactivated"
          !campaignFacade.getCampaignFullInfo(campaignDto.getId()).isStatus()
    }
}
