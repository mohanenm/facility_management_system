package com.fms.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {

  private static Connection connection;

  public static Connection getConnection() throws java.sql.SQLException {

    if (connection == null) {
      connection =
          DriverManager.getConnection(
              "jdbc:postgresql://127.0.0.1:5432/facility", "postgres", "postgres");
      Statement st = connection.createStatement();
      ResultSet rs = st.executeQuery("SELECT VERSION()");

      if (rs.next()) {
        System.out.println("Connection created: The Database Version is -> " + rs.getString(1));
      }
    }

    return connection;
  }
}
