package com.example.campaignmanagement.campaign.domain


import com.example.campaignmanagement.campaign.dto.CreateCampaignDto
import com.example.campaignmanagement.campaign.exception.InvalidDataException
import com.example.campaignmanagement.seller.domain.SellerFacade
import spock.lang.Specification
import spock.lang.Unroll

class CreateCampaignSpec extends Specification implements CampaignSample {

    CampaignRepository campaignRepository = new InMemoryCampaignRepository()
    SellerFacade sellerFacade = Stub(SellerFacade)

    CampaignFacade campaignFacade = new CampaignFacade(campaignRepository, sellerFacade)

    def setup() {
        given: "Seller with id #SELLER_ID is exists"
          sellerFacade.isSellerExists(SELLER_ID) >> true
    }

    def "Should create a new campaign"() {
        when: "User create a new campaign"
          campaignFacade.createCampaign(TV_CAMPAIGN)
        then: "Campaign is created"
          campaignFacade.getAllCampaigns() == [TV_CAMPAIGN_DTO]
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
          scenario                        | campaignName | campaignKeywords | campaignFund         | campaignTown | campaignRadius | campaignBidAmount | campaignStatus
          "without set NAME"              | null         | KEYWORDS         | new BigDecimal(1000) | TOWN         | 10             | BigDecimal.ONE    | true
          "without set KEYWORDS"          | NAME         | null             | new BigDecimal(1000) | TOWN         | 10             | BigDecimal.ONE    | true
          "without set campaignFund"      | NAME         | KEYWORDS         | null                 | TOWN         | 10             | BigDecimal.ONE    | false
          "without set campaignTown"      | NAME         | KEYWORDS         | new BigDecimal(1000) | null         | 10             | BigDecimal.ONE    | true
          "without set campaignRadius"    | NAME         | KEYWORDS         | new BigDecimal(1000) | TOWN         | null           | BigDecimal.ONE    | true
          "without set campaignBidAmount" | NAME         | KEYWORDS         | new BigDecimal(1000) | TOWN         | 10             | null              | true
          "without set campaignStatus"    | NAME         | KEYWORDS         | new BigDecimal(1000) | TOWN         | 10             | BigDecimal.ONE    | null
          "with set empty list KEYWORDS"  | NAME         | [] as Set        | new BigDecimal(1000) | TOWN         | 10             | BigDecimal.ONE    | true
          "with campaignBidAmount is <0 " | NAME         | KEYWORDS         | new BigDecimal(1000) | TOWN         | 10             | BigDecimal.ZERO   | true
    }
}
