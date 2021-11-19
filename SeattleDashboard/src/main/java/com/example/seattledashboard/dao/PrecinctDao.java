package com.example.seattledashboard.dao;

import com.example.seattledashboard.ConnectionManager;
import com.example.seattledashboard.model.Group;
import com.example.seattledashboard.model.GroupActionInformation;
import com.example.seattledashboard.model.Mcpp;
import com.example.seattledashboard.model.Precinct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PrecinctDao {

  protected ConnectionManager connectionManager;
  private static PrecinctDao instance = null;
  protected  PrecinctDao() {
    connectionManager = new ConnectionManager();
  }
  public static PrecinctDao getInstance() {
    if (instance == null) {
      instance = new PrecinctDao();
    }
    return instance;
  }

  public Precinct create(Precinct precinct) throws SQLException {
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
      insertStmt.setString(1, precinct.getPrecinctName());

      insertStmt.executeUpdate();

      resultKey = insertStmt.getGeneratedKeys();
      int precinctId = -1;
      if(resultKey.next()) {
        precinctId = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      precinct.setPrecinctId(precinctId);
      return precinct;
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

  public Precinct getPrecinctById(int precinciId) throws SQLException {

    String selectReviews =
        "SELECT PrecinctID, PrecinctName" +
            " FROM Precinct" +
            " WHERE PrecinctID=?; ";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReviews);
      selectStmt.setInt(1, precinciId);
      results = selectStmt.executeQuery();
      if(results.next()) {
        int resultPrecinctID = results.getInt("PrecinctID");
        String precinctName = results.getString("PrecinctName");

        Precinct precinct = Precinct.builder().precinctId(resultPrecinctID).precinctName(precinctName).build();

        return precinct;
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

  public Precinct updateName(Precinct precinct, String newName)
      throws SQLException {

    String update = "UPDATE Precinct SET PrecinctName =? WHERE PrecinctID =?; ";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(update);
      updateStmt.setString(1, newName);
      updateStmt.setLong(2, precinct.getPrecinctId());

      updateStmt.executeUpdate();


      precinct.setPrecinctName(newName);
      return precinct;
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

  public Precinct delete(Precinct precinct) throws SQLException {

    String delete = "DELETE FROM Precinct WHERE PrecinctID = ?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(delete);
      deleteStmt.setInt(1, precinct.getPrecinctId());
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
