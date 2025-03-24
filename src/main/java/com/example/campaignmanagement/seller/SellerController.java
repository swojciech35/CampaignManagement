package com.example.campaignmanagement.seller;

import com.example.campaignmanagement.seller.domain.SellerFacade;
import com.example.campaignmanagement.seller.dto.CreateSellerDto;
import com.example.campaignmanagement.seller.dto.SellerDto;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;


@RestController
@RequestMapping(value = "/api/sellers")
public class SellerController {

  final SellerFacade sellerFacade;

  public SellerController(SellerFacade sellerFacade) {
    this.sellerFacade = sellerFacade;
  }

  @GetMapping("/")
  public Set<SellerDto> getAllSellers() {
    return sellerFacade.getAllSellers();
  }

  @GetMapping("/{sellerId}")
  public SellerDto getSellerById(@PathVariable("sellerId") UUID sellerId) {
    return sellerFacade.getSellerById(sellerId);
  }

  @PostMapping("/")
  public void createSeller(@RequestBody CreateSellerDto createSellerDto) {
    sellerFacade.createSeller(createSellerDto);
  }
}
