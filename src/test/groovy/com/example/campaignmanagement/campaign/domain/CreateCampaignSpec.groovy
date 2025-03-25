package com.example.campaignmanagement.campaign.domain


import com.example.campaignmanagement.campaign.dto.CreateCampaignDto
import com.example.campaignmanagement.campaign.exception.InvalidDataException
import com.example.campaignmanagement.seller.domain.SellerFacade
import com.example.campaignmanagement.seller.exception.NotEnoughBalanceException
import com.example.campaignmanagement.seller.exception.SellerNotFoundException
import spock.lang.Specification
import spock.lang.Unroll

class CreateCampaignSpec extends Specification implements CampaignSample {

    CampaignRepository campaignRepository = new InMemoryCampaignRepository()
    SellerFacade sellerFacade = Mock(SellerFacade)

    CampaignFacade campaignFacade = new CampaignFacade(campaignRepository, sellerFacade)

    def setup() {
        given: "Seller with id #SELLER_ID  exists"
          sellerFacade.isSellerExists(SELLER_ID) >> true
        and: "Seller with id has enough balance"
          sellerFacade.hasEnoughBalance(SELLER_ID, _) >> true
    }

    def "Should create a new campaign"() {
        when: "User create a new campaign"
          campaignFacade.createCampaign(TV_CAMPAIGN)
        then: "Campaign is created"
          campaignFacade.getAllCampaigns() == [TV_CAMPAIGN_DTO]
        and: "Seller balance is deduced"
          1 * sellerFacade.withdrawBalance(SELLER_ID, TV_CAMPAIGN.getCampaignFund())
    }

    @Unroll
    def "Shouldn't create new campaign #scenario"() {
        when: "User create a new campaign"
          CreateCampaignDto newCampaign = CreateCampaignDto.builder()
                  .name(campaignName)
                  .keywords(campaignKeywords)
                  .campaignFund(campaignFund)
                  .status(campaignStatus)
                  .town(campaignTown)
                  .bidAmount(campaignBidAmount)
                  .radius(campaignRadius)
                  .createdBy(SELLER_ID)
                  .build()
          campaignFacade.createCampaign(newCampaign)
        then: "Campaign is not created"
          campaignFacade.getAllCampaigns().isEmpty()
        and: "Exception is thrown"
          thrown(InvalidDataException)
        where:
          scenario                                   | campaignName | campaignKeywords | campaignFund         | campaignTown | campaignRadius | campaignBidAmount  | campaignStatus
          "without set NAME"                         | null         | KEYWORDS         | new BigDecimal(1000) | WARSAW       | 10             | BigDecimal.ONE     | true
          "without set KEYWORDS"                     | NAME         | null             | new BigDecimal(1000) | WARSAW       | 10             | BigDecimal.ONE     | true
          "without set campaignFund"                 | NAME         | KEYWORDS         | null                 | WARSAW       | 10             | BigDecimal.ONE     | false
          "without set campaignTown"                 | NAME         | KEYWORDS         | new BigDecimal(1000) | null         | 10             | BigDecimal.ONE     | true
          "without set campaignRadius"               | NAME         | KEYWORDS         | new BigDecimal(1000) | WARSAW       | null           | BigDecimal.ONE     | true
          "without set campaignBidAmount"            | NAME         | KEYWORDS         | new BigDecimal(1000) | WARSAW       | 10             | null               | true
          "without set campaignStatus"               | NAME         | KEYWORDS         | new BigDecimal(1000) | WARSAW       | 10             | BigDecimal.ONE     | null
          "with set empty list KEYWORDS"             | NAME         | [] as Set        | new BigDecimal(1000) | WARSAW       | 10             | BigDecimal.ONE     | true
          "with campaignBidAmount is <0 "            | NAME         | KEYWORDS         | new BigDecimal(1000) | WARSAW       | 10             | BigDecimal.ZERO    | true
          "with bidAmount greater than campaignFund" | NAME         | KEYWORDS         | BigDecimal.TEN       | WARSAW       | 10             | new BigDecimal(11) | true
    }

    def "Shouldn't create new campaign if Seller not exists"() {
        when: "User creates a new campaign"
          campaignFacade.createCampaign(TV_CAMPAIGN_CREATED_BY_SELLER2)
        then: "Campaign is not created"
          campaignFacade.getAllCampaigns().isEmpty()
        and: "Exception is thrown"
          thrown(SellerNotFoundException)
    }

    def "Shouldn't create new campaign if Seller has not enough balance"() {
        given: "Seller2 exists"
          sellerFacade.isSellerExists(SELLER2_ID) >> true
        and: "Seller2 has not enough balance"
          sellerFacade.hasEnoughBalance(SELLER2_ID, _) >> false
        when: "Seller2 creates a new campaign"
          campaignFacade.createCampaign(TV_CAMPAIGN_CREATED_BY_SELLER2)
        then: "Campaign is not created"
          campaignFacade.getAllCampaigns().isEmpty()
        and: "Exception is thrown"
          thrown(NotEnoughBalanceException)
    }
}