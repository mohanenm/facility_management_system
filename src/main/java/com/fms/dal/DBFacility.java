package com.fms.dal;

import com.fms.model.Facility;

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
}
