package com.example.seattledashboard.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OffenseCategory {

  private int offenseCategoryId;
  private String offenseCategoryName;
  private String offenseContent;
  private String offenseCode;
}
