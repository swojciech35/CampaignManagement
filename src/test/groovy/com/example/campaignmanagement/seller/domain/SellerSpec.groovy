package com.example.campaignmanagement.seller.domain

import com.example.campaignmanagement.seller.dto.AddBalanceDto
import com.example.campaignmanagement.seller.dto.SellerDto
import com.example.campaignmanagement.seller.exception.IllegalValueException
import com.example.campaignmanagement.seller.exception.SellerAlreadyExistsException
import spock.lang.Specification
import spock.lang.Unroll

class SellerSpec extends Specification implements SellerSample {

    SellerFacade sellerFacade = new SellerFacade(new InMemorySellerRepository())

    def "should create new seller"() {
        when: "Create a new seller"
          SellerDto seller1 = sellerFacade.createSeller(CREATE_SELLER1)
        then: "Seller is created"
          sellerFacade.getAllSellers() == [new SellerDto(seller1.getId(), SELLER1, BigDecimal.ZERO)] as Set
    }

    def "should not create new seller with the same name"() {
        given: "Create a new seller"
          SellerDto seller1 = sellerFacade.createSeller(CREATE_SELLER1)
        when: "Create a new seller with the same name"
          sellerFacade.createSeller(CREATE_SELLER1)
        then: "Seller with the same name is not created"
          sellerFacade.getAllSellers() == [new SellerDto(seller1.getId(), SELLER1, BigDecimal.ZERO)] as Set
        and: "Exception is thrown"
          thrown(SellerAlreadyExistsException)
    }

    def "should not create new seller without name"() {
        when: "Create a new seller without name"
          sellerFacade.createSeller(CREATE_SELLER_WITH_NULL_NAME)
        then: "Seller without name is not created"
          sellerFacade.getAllSellers().isEmpty()
        and: "Exception is thrown"
          thrown(IllegalValueException)
    }

    def "should get all sellers"() {
        given: "There are two sellers"
          SellerDto seller1 = sellerFacade.createSeller(CREATE_SELLER1)
          SellerDto seller2 = sellerFacade.createSeller(CREATE_SELLER2)
        when: "Get all sellers"
          Set<SellerDto> sellers = sellerFacade.getAllSellers()
        then: "All sellers are returned"
          sellers == [new SellerDto(seller1.getId(), SELLER1, BigDecimal.ZERO), new SellerDto(seller2.getId(), SELLER2, BigDecimal.ZERO)] as Set
    }
}
