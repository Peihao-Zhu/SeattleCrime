package com.example.seattledashboard.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Group {
  private int groupId;
  private String groupName;

}
