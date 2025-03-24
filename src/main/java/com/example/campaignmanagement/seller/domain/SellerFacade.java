package com.example.campaignmanagement.seller.domain;

import com.example.campaignmanagement.seller.dto.AddBalanceDto;
import com.example.campaignmanagement.seller.dto.CreateSellerDto;
import com.example.campaignmanagement.seller.dto.SellerDto;
import com.example.campaignmanagement.seller.exception.IllegalValueException;
import com.example.campaignmanagement.seller.exception.SellerAlreadyExistsException;
import com.example.campaignmanagement.seller.exception.SellerNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerFacade {
  SellerRepository sellerRepository;

  public SellerFacade(SellerRepository sellerRepository) {
    this.sellerRepository = sellerRepository;
  }

  public SellerDto createSeller(CreateSellerDto createSeller) {
    if (sellerRepository.existsSellerByName(createSeller.getName())) {
      throw new SellerAlreadyExistsException(createSeller.getName());
    }
    if (createSeller.getName() == null) {
      throw new IllegalValueException("Seller name cannot be null");
    }
    Seller seller = Seller.fromCreateDto(createSeller);
    return sellerRepository.save(seller).toDto();
  }

  public void addBalance(AddBalanceDto addBalanceDto) {
    Seller seller = sellerRepository.findById(addBalanceDto.getSellerId())
            .orElseThrow(() -> new SellerNotFoundException(addBalanceDto.getSellerId()));
    if (!isBalanceToAddIsCorrectValue(addBalanceDto.getBalanceToAdd())) {
      throw new IllegalValueException("Balance to add must be greater than 0");
    }
    seller.addBalance(addBalanceDto.getBalanceToAdd());
    sellerRepository.save(seller);
  }

  public Set<SellerDto> getAllSellers() {
    return sellerRepository.findAll()
            .stream()
            .map(Seller::toDto)
            .collect(Collectors.toSet());
  }

  public SellerDto getSellerById(UUID sellerId) {
    return sellerRepository.findById(sellerId)
            .orElseThrow(() -> new SellerNotFoundException(sellerId))
            .toDto();
  }

  public boolean isSellerExists(UUID sellerId) {
    return sellerRepository.existsById(sellerId);
  }

  private boolean isBalanceToAddIsCorrectValue(BigDecimal balanceToAdd) {
    return balanceToAdd != null && balanceToAdd.compareTo(BigDecimal.ZERO) > 0;
  }
}
