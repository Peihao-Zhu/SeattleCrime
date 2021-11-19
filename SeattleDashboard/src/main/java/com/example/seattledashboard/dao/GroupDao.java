package com.example.seattledashboard.dao;

import com.example.seattledashboard.ConnectionManager;
import com.example.seattledashboard.model.Beat;
import com.example.seattledashboard.model.Group;
import com.example.seattledashboard.model.GroupActionInformation;
import com.example.seattledashboard.model.Precinct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GroupDao {

  protected ConnectionManager connectionManager;
  private static GroupDao instance = null;
  protected GroupDao() {
    connectionManager = new ConnectionManager();
  }
  public static GroupDao getInstance() {
    if (instance == null) {
      instance = new GroupDao();
    }
    return instance;
  }

  public Group create(Group group) throws SQLException {
    String insert =
        "INSERT INTO Precinct(PrecinctName) " +
            "VALUES(?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insert,
          Statement.RETURN_GENERATED_KEYS);
      insertStmt.setString(1, group.getGroupName());

      insertStmt.executeUpdate();

      resultKey = insertStmt.getGeneratedKeys();
      int groupId = -1;
      if(resultKey.next()) {
        groupId = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      group.setGroupId(groupId);
      return group;
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

  public Group getGroupById(int groupId) throws SQLException {

    String selectReviews =
        "SELECT GroupID,GroupName" +
            " FROM Group" +
            " WHERE GroupID=?; ";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReviews);
      selectStmt.setInt(1, groupId);
      results = selectStmt.executeQuery();
      if(results.next()) {
        int resultGroupID = results.getInt("GroupID");
        String groupName = results.getString("GroupName");

        Group group = Group.builder().groupId(resultGroupID).groupName(groupName).build();

        return group;
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


  public Group updateName(Group group, String newName)
      throws SQLException {

    String update = "UPDATE Group SET GroupName =? WHERE GroupID=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(update);
      updateStmt.setString(1, newName);
      updateStmt.setLong(2, group.getGroupId());

      updateStmt.executeUpdate();


      group.setGroupName(newName);
      return group;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(updateStmt != null) {
        updateStmt.close();
      }
    }
  }

  public Group delete(Group group) throws SQLException {

    String delete = "DELETE FROM Group WHERE GroupID = ?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(delete);
      deleteStmt.setInt(1, group.getGroupId());
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
