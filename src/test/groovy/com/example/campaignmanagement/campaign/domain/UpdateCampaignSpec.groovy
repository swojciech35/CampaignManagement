package com.example.campaignmanagement.campaign.domain

import com.example.campaignmanagement.campaign.dto.CampaignDto
import com.example.campaignmanagement.campaign.dto.CampaignKeywordsDto
import com.example.campaignmanagement.campaign.dto.CampaignLightDto
import com.example.campaignmanagement.campaign.dto.UpdateCampaignDto
import com.example.campaignmanagement.campaign.enums.Town
import com.example.campaignmanagement.campaign.exception.CampaignNotFoundException
import com.example.campaignmanagement.campaign.exception.CampaignUpdateNotAllowedException
import com.example.campaignmanagement.seller.domain.SellerFacade
import com.example.campaignmanagement.seller.exception.NotEnoughBalanceException
import spock.lang.Specification

class UpdateCampaignSpec extends Specification implements CampaignSample {
    CampaignRepository campaignRepository = new InMemoryCampaignRepository()
    SellerFacade sellerFacade = Mock(SellerFacade)

    CampaignFacade campaignFacade = new CampaignFacade(campaignRepository, sellerFacade)
    CampaignDto campaign

    def setup() {
        given: "Seller with id #SELLER_ID exists"
          sellerFacade.isSellerExists(SELLER_ID) >> true
        and: "SELLER_ID has enough balance"
          sellerFacade.hasEnoughBalance(SELLER_ID, new BigDecimal(1000)) >> true
        and: "Campaign exists"
          campaign = campaignFacade.createCampaign(TV_CAMPAIGN)
    }

    def "Should update a campaign"() {
        when: "User update a campaign"
          sellerFacade.hasEnoughBalance(SELLER_ID, new BigDecimal(9000)) >> true
          CampaignKeywordsDto existsKeyword = campaign.getKeywords()[0]
          campaignFacade.updateCampaign(campaign.getId(), UpdateCampaignDto.builder()
                  .name("New name")
                  .campaignFund(new BigDecimal(10000))
                  .status(true)
                  .town(Town.CRACOW)
                  .bidAmount(BigDecimal.TEN)
                  .radius(100)
                  .keywords([new CampaignKeywordsDto(existsKeyword.getCampaignKeywordsId(), existsKeyword.getCampaignId(), "newKeyword")] as Set)
                  .updater(SELLER_ID)
                  .build())
        then: "Campaign is updated"
          campaignFacade.getAllCampaigns() == [CampaignLightDto.builder()
                                                       .name("New name")
                                                       .campaignFund(new BigDecimal(10000))
                                                       .status(true)
                                                       .town(Town.CRACOW)
                                                       .bidAmount(BigDecimal.TEN)
                                                       .radius(100)
                                                       .keywords(Set.of("newKeyword"))
                                                       .createdBy(SELLER_ID)
                                                       .build()]
    }

    def "Shouldn't update a campaign when updater is not author"() {
        when: "Seller update a campaign"
          campaignFacade.updateCampaign(campaign.getId(), UpdateCampaignDto.builder()
                  .name("New name")
                  .campaignFund(new BigDecimal(10000))
                  .status(true)
                  .town(Town.CRACOW)
                  .bidAmount(BigDecimal.TEN)
                  .radius(100)
                  .keywords(campaign.getKeywords())
                  .updater(UUID.randomUUID())
                  .build())
        then: "Campaign is not updated"
          campaignFacade.getAllCampaigns() == [TV_CAMPAIGN_DTO]
        and: "Exception is thrown"
          thrown(CampaignUpdateNotAllowedException)
    }

    def "Should throw exception when trying to update a non-existent campaign"() {
        when: "Seller update a campaign"
          campaignFacade.updateCampaign(UUID.randomUUID(), UpdateCampaignDto.builder()
                  .name("New name")
                  .campaignFund(new BigDecimal(10000))
                  .status(true)
                  .town(Town.CRACOW)
                  .bidAmount(BigDecimal.TEN)
                  .radius(100)
                  .keywords(campaign.getKeywords())
                  .updater(UUID.randomUUID())
                  .build())
        then: "Exception is thrown"
          thrown(CampaignNotFoundException)
    }

    def "Should refund balance when campaign fund is decreased"() {
        when: "Seller updates a campaign"
          campaignFacade.updateCampaign(campaign.getId(), UpdateCampaignDto.builder()
                  .name(NAME)
                  .campaignFund(new BigDecimal(100))
                  .status(true)
                  .town(WARSAW)
                  .bidAmount(BigDecimal.TEN)
                  .radius(10)
                  .keywords(campaign.getKeywords())
                  .updater(SELLER_ID)
                  .build())
        then: "Seller balance is refunded"
          1 * sellerFacade.refundBalance(SELLER_ID, new BigDecimal(900))
    }

    def "Should not update campaign when seller has not enough balance"() {
        given: "Seller has not enough balance"
          sellerFacade.hasEnoughBalance(SELLER_ID, new BigDecimal(9000)) >> false
        when: "Seller updates a campaign"
          campaignFacade.updateCampaign(campaign.getId(), UpdateCampaignDto.builder()
                  .name("New name")
                  .campaignFund(new BigDecimal(10000))
                  .status(true)
                  .town(Town.CRACOW)
                  .bidAmount(BigDecimal.TEN)
                  .radius(100)
                  .keywords(campaign.getKeywords())
                  .updater(SELLER_ID)
                  .build())
        then: "Exception is thrown"
          thrown(NotEnoughBalanceException)
    }
}
