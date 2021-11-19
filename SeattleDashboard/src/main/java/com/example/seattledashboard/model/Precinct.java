package com.example.seattledashboard.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Precinct {
  private int precinctId;
  private String precinctName;

}
