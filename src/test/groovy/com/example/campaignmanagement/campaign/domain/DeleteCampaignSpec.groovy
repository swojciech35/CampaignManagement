package com.example.campaignmanagement.campaign.domain

import com.example.campaignmanagement.campaign.dto.CampaignDto
import com.example.campaignmanagement.campaign.dto.DeleteCampaignDto
import com.example.campaignmanagement.campaign.exception.CampaignDeleteNotAllowedException
import com.example.campaignmanagement.seller.domain.SellerFacade
import spock.lang.Specification

class DeleteCampaignSpec extends Specification implements CampaignSample {

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

    def "Should delete a campaign"() {
        when: "User delete a campaign"
          campaignFacade.deleteCampaign(new DeleteCampaignDto(campaign.getId(), SELLER_ID))
        then: "Campaign is deleted"
          campaignFacade.getAllCampaigns().isEmpty()
        and: "Money is refund"
          1 * sellerFacade.refundBalance(SELLER_ID, campaign.getCampaignFund())
    }

    def "Shouldn't delete a campaign if deleter is not campaign author"() {
        when: "User delete a campaign"
          campaignFacade.deleteCampaign(new DeleteCampaignDto(campaign.getId(), SELLER2_ID))
        then: "Campaign is not deleted"
          !campaignFacade.getAllCampaigns().isEmpty()
        and: "Money is not refund"
          0 * sellerFacade.refundBalance(SELLER_ID, campaign.getCampaignFund())
        and: "Exception is thrown"
          thrown(CampaignDeleteNotAllowedException)
    }
}
