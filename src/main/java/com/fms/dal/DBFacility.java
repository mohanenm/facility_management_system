package com.fms.dal;

import com.fms.model.Building;
import com.fms.model.Facility;
import com.fms.model.FacilityDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBFacility {


    public static ArrayList<Facility> readAllFacilities() {
        ArrayList<Facility> result = new ArrayList<>();
        try {

            Statement st = DBConnection.getConnection().createStatement();
            String query = "SELECT id, name, description  FROM facility";
            ResultSet facilityResultSet = st.executeQuery(query);


            while (facilityResultSet.next()) {
                result.add(new Facility(facilityResultSet.getInt("id"),
                        facilityResultSet.getString("name"),
                        facilityResultSet.getString("description"))
                );

            }

            //close to manage resources
            facilityResultSet.close();
        } catch (SQLException e) {
            System.out.println("caught exception: " + e.toString());
        }
        return result;
    }


    public static Facility createFacility(String name, String description) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection
                    .getConnection()
                    .prepareStatement("INSERT into facility (name, description) values (?,?) RETURNING id");


            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return new Facility(resultSet.getInt(1), name, description);
        } catch (SQLException e) {
            System.out.println("caught exception: " + e.toString());
            throw e;
        }
    }

    public static boolean deleteFacility(int facilityId) {

        try {
            PreparedStatement preparedStatement = DBConnection
                    .getConnection()
                    .prepareStatement("delete from facility where id = ?");
            preparedStatement.setInt(1, facilityId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public static void addFacilityDetail(int facilityId, FacilityDetail facilityDetail) throws SQLException {
        PreparedStatement preparedStatement = DBConnection
                .getConnection()
                .prepareStatement("select id from facility where id = ?");
        preparedStatement.setInt(1, facilityId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        for(Building building: facilityDetail.getBuildings()) {

        }
    }
}
