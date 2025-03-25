package com.example.campaignmanagement.seller.domain;

import com.example.campaignmanagement.seller.dto.CreateSellerDto;
import com.example.campaignmanagement.seller.dto.SellerDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
class Seller {
  @Id
  @Setter
  UUID id;
  @Column(nullable = false)
  String name;
  @Column(nullable = false)
  BigDecimal balance;


  static Seller fromCreateDto(CreateSellerDto createSellerDto) {
    return Seller.builder()
            .id(UUID.randomUUID())
            .name(createSellerDto.getName())
            .balance(BigDecimal.ZERO)
            .build();
  }

  public SellerDto toDto() {
    return SellerDto.builder()
            .id(id)
            .name(name)
            .balance(balance)
            .build();
  }

  public void addBalance(BigDecimal balanceToAdd) {
    balance = balance.add(balanceToAdd);
  }

  public void withdrawBalance(BigDecimal balanceToWithdraw) {
    balance = balance.subtract(balanceToWithdraw);
  }
}
