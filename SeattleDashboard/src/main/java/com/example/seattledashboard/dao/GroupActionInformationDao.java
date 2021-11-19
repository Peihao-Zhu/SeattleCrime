package com.example.seattledashboard.dao;

import com.example.seattledashboard.ConnectionManager;
import com.example.seattledashboard.model.Beat;
import com.example.seattledashboard.model.GroupActionInformation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class GroupActionInformationDao {

  protected ConnectionManager connectionManager;
  private static GroupActionInformationDao instance = null;
  protected GroupActionInformationDao() {
    connectionManager = new ConnectionManager();
  }
  public static GroupActionInformationDao getInstance() {
    if (instance == null) {
      instance = new GroupActionInformationDao();
    }
    return instance;
  }

  public GroupActionInformation create(GroupActionInformation action) throws SQLException {

    String insertReviews =
        "INSERT INTO GroupActionInformation(GroupID, SendTime, EndTime)" +
            "VALUES(?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      // BlogPosts has an auto-generated key. So we want to retrieve that key.
      insertStmt = connection.prepareStatement(insertReviews,
          Statement.RETURN_GENERATED_KEYS);
      insertStmt.setInt(1, action.getGroupId());
      insertStmt.setTimestamp(2, action.getSendTime());
      insertStmt.setTimestamp(3, action.getEndTime());
      // Note: for the sake of simplicity, just set Picture to null for now.
      insertStmt.executeUpdate();

      // Retrieve the auto-generated key and set it, so it can be used by the caller.
      // For more details, see:
      // http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
      resultKey = insertStmt.getGeneratedKeys();
      int actionId = -1;
      if(resultKey.next()) {
        actionId = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      action.setActionId(actionId);
      return action;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(insertStmt != null) {
        insertStmt.close();
      }
      if(resultKey != null) {
        resultKey.close();
      }
    }
  }



  public GroupActionInformation getGroupActionInformationById(int actionId) throws SQLException {

    String selectReviews =
        "SELECT ActionID, GroupID, SendTime, EndTime " +
            " FROM GroupActionInformation" +
            " WHERE ActionID=?; ";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReviews);
      selectStmt.setInt(1, actionId);
      results = selectStmt.executeQuery();
      if(results.next()) {
        int resultActionID = results.getInt("ActionID");
        int groupID = results.getInt("GroupID");
        Timestamp sendTime = results.getTimestamp("SendTime");
        Timestamp endTime = results.getTimestamp("EndTime");

        GroupActionInformation groupActionInformation = GroupActionInformation.builder().actionId(resultActionID)
            .groupId(groupID).sendTime(sendTime).endTime(endTime).build();

        return groupActionInformation;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStmt != null) {
        selectStmt.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return null;
  }

  public GroupActionInformation delete(GroupActionInformation action) throws SQLException {

    String delete = "DELETE FROM GroupActionInformation WHERE ActionID=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(delete);
      deleteStmt.setInt(1, action.getActionId());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the BlogPosts instance.
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(deleteStmt != null) {
        deleteStmt.close();
      }
    }
  }




}
