package database;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dbDetails {

    @SuppressWarnings("unused")
    public static Connection getConnection() {

        System.out.println("\ndbDetails: -------- PostgreSQL " + "JDBC Connection  ------------");

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println("dbDetails: Check Where  your PostgreSQL JDBC Driver exist and " + "Include in your library path!");
            e.printStackTrace();
            return null;

        }

        System.out.println("dbDetails: PostgreSQL JDBC Driver Registered!");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection("", "", "");
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT VERSION()");

            if (rs.next()) {
                System.out.println("dbDetails: The Database Version is " + rs.getString(1));
            }

        } catch (SQLException e) {

            System.out.println("dbDetails: Connection Failed! ");
            e.printStackTrace();
            return null;

        }

        if (connection != null) {
            System.out.println("dbDetails: You have a database connection!");
        } else {
            System.out.println("dbDetails: Failed to make connection!");
        }

        return connection;
    }
}