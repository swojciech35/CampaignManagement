package com.example.campaignmanagement.seller.domain

import com.example.campaignmanagement.seller.dto.AddBalanceDto
import com.example.campaignmanagement.seller.dto.SellerDto
import com.example.campaignmanagement.seller.exception.IllegalValueException
import com.example.campaignmanagement.seller.exception.NotEnoughBalanceException
import com.example.campaignmanagement.seller.exception.SellerNotFoundException
import spock.lang.Specification
import spock.lang.Unroll

class SellerBalanceSpec extends Specification implements SellerSample {

    SellerFacade sellerFacade = new SellerFacade(new InMemorySellerRepository())
    SellerDto seller1

    def setup() {
        given: "There is a seller1"
          seller1 = sellerFacade.createSeller(CREATE_SELLER1)
    }

    def "Should add balance to an existing seller"() {
        when: "Add balance to seller1"
          AddBalanceDto addBalanceDto = new AddBalanceDto(seller1.getId(), BigDecimal.valueOf(1000l))
          sellerFacade.addBalance(addBalanceDto)
        then: "Balance is added to seller1"
          sellerFacade.getSellerById(seller1.getId()) == new SellerDto(seller1.getId(), SELLER1, BigDecimal.valueOf(1000l))
    }

    @Unroll
    def "Shouldn't add balance with incorrect value"() {
        when: "Try to add incorrect balance value"
          AddBalanceDto addBalanceDto = new AddBalanceDto(seller1.getId(), balanceToAdd)
          sellerFacade.addBalance(addBalanceDto)
        then: "Exception is thrown"
          thrown(IllegalValueException)
        where:
          balanceToAdd << [BigDecimal.valueOf(0l), BigDecimal.valueOf(-1000l), null]

    }

    @Unroll
    def "Should check if the seller has enough balance"() {
        given: "Add balance to seller1"
          AddBalanceDto addBalanceDto = new AddBalanceDto(seller1.getId(), BigDecimal.valueOf(500l))
          sellerFacade.addBalance(addBalanceDto)
        when: "Check if seller has enough balance"
          boolean hasEnoughBalance = sellerFacade.hasEnoughBalance(seller1.getId(), balance)
        then: "Seller has the expected balance status"
          hasEnoughBalance == expected
        where:
          balance                   | expected
          BigDecimal.valueOf(1000l) | false
          BigDecimal.valueOf(200l)  | true
          BigDecimal.valueOf(500l)  | true
    }

    def "Should not check if seller has enough balance for not existing seller"() {
        when: "Check enough balance on a non-existing seller"
          sellerFacade.hasEnoughBalance(UUID.randomUUID(), BigDecimal.valueOf(1000l))
        then: "Exception is thrown"
          thrown(SellerNotFoundException)
    }

    def "Should withdraw money from seller1 balance "() {
        given: "Add balance to seller1"
          AddBalanceDto addBalanceDto = new AddBalanceDto(seller1.getId(), BigDecimal.valueOf(500l))
          sellerFacade.addBalance(addBalanceDto)
        when: "campaign withdraws money from seller1"
          sellerFacade.withdrawBalance(seller1.getId(), BigDecimal.valueOf(200l))
        then: "Money is withdrawn from seller1"
          sellerFacade.getSellerById(seller1.getId()) == new SellerDto(seller1.getId(), SELLER1, BigDecimal.valueOf(300l))
    }

    def "Shouldn't withdraw money from seller1 if seller1 has not enough balance"() {
        given: "Add balance to seller1"
          AddBalanceDto addBalanceDto = new AddBalanceDto(seller1.getId(), BigDecimal.valueOf(500l))
          sellerFacade.addBalance(addBalanceDto)
        when: "campaign withdraws money from seller1"
          sellerFacade.withdrawBalance(seller1.getId(), BigDecimal.valueOf(600l))
        then: "Money is not withdrawn from seller1"
          sellerFacade.getSellerById(seller1.getId()) == new SellerDto(seller1.getId(), SELLER1, BigDecimal.valueOf(500l))
        and: "Exception is thrown"
          thrown(NotEnoughBalanceException)
    }

    def "Should not withdraw money from non-existing seller"() {
        when: "campaign withdraws money from non-existing seller"
          sellerFacade.withdrawBalance(UUID.randomUUID(), BigDecimal.valueOf(1000l))
        then: "Exception is thrown"
          thrown(SellerNotFoundException)
    }


    def "should refund money to an existing seller"() {
        given: "Add balance to seller1"
          AddBalanceDto addBalanceDto = new AddBalanceDto(seller1.getId(), BigDecimal.valueOf(500l))
          sellerFacade.addBalance(addBalanceDto)
        when: "Refund money to seller1"
          sellerFacade.refundBalance(seller1.getId(), BigDecimal.valueOf(200l))
        then: "Money is refunded to seller1"
          sellerFacade.getSellerById(seller1.getId()) == new SellerDto(seller1.getId(), SELLER1, BigDecimal.valueOf(700l))
    }

    @Unroll
    def "shouldn't refund money with incorrect value"() {
        when: "Try to refund incorrect money value"
          sellerFacade.refundBalance(seller1.getId(), balanceToRefund)
        then: "Exception is thrown"
          thrown(IllegalValueException)
        where:
          balanceToRefund << [BigDecimal.valueOf(0l), BigDecimal.valueOf(-1000l), null]
    }

    def "should not refund money to a non-existing seller"() {
        when: "Refund money to a non-existing seller"
          sellerFacade.refundBalance(UUID.randomUUID(), BigDecimal.valueOf(1000l))
        then: "Exception is thrown"
          thrown(SellerNotFoundException)
    }
}
