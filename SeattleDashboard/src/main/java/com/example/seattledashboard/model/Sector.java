package com.example.seattledashboard.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Sector {
  private int sectorId;
  private String sectorName;

}
