package com.example.campaignmanagement.campaign;

import com.example.campaignmanagement.campaign.enums.Town;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/common")
public class CommonController {

  @GetMapping("/towns")
  public Set<Town> getTowns() {
    return Set.of(Town.values());
  }

}
