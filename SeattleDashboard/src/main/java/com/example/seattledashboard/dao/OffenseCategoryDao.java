package com.example.seattledashboard.dao;

import com.example.seattledashboard.ConnectionManager;
import com.example.seattledashboard.model.Beat;
import com.example.seattledashboard.model.OffenseCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OffenseCategoryDao {

  protected ConnectionManager connectionManager;
  private static OffenseCategoryDao instance = null;
  protected OffenseCategoryDao() {
    connectionManager = new ConnectionManager();
  }
  public static OffenseCategoryDao getInstance() {
    if (instance == null) {
      instance = new OffenseCategoryDao();
    }
    return instance;
  }

  public OffenseCategory getOffenseCategoryById(int offenseCategoryId) throws SQLException {

    String selectReviews =
        "SELECT OffenseCategoryID, OffenseCategoryName, OffenseContent, OffenseCode " +
            " FROM OffenseCategory " +
            " WHERE OffenseCategoryID=?; ";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReviews);
      selectStmt.setInt(1, offenseCategoryId);
      results = selectStmt.executeQuery();
      if(results.next()) {
        int resultOffenseCategoryID = results.getInt("OffenseCategoryID");
        String offenseCategoryName = results.getString("OffenseCategoryName");
        String offenseContent = results.getString("OffenseContent");
        String offenseCode = results.getString("OffenseCode");

        OffenseCategory offenseCategory = OffenseCategory.builder().offenseCategoryId(resultOffenseCategoryID)
            .offenseCategoryName(offenseCategoryName).offenseContent(offenseContent).offenseCode(offenseCode).build();

        return offenseCategory;
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

  public OffenseCategory delete(OffenseCategory offenseCategory) throws SQLException {

    String delete = "DELETE FROM OffenseCategory WHERE OffenseCategoryID = ?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(delete);
      deleteStmt.setInt(1, offenseCategory.getOffenseCategoryId());
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
