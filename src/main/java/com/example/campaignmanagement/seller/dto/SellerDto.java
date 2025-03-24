package com.example.campaignmanagement.seller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerDto {
  UUID id;
  String name;
  BigDecimal balance;
}
