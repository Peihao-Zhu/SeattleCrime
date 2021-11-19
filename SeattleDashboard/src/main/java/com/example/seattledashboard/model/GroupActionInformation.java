package com.example.seattledashboard.model;

import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupActionInformation {

  private int actionId;
  private int groupId;
  private Timestamp sendTime;
  private Timestamp endTime;
}
