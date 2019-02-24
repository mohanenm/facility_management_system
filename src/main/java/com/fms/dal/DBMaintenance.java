package com.fms.dal;

import com.fms.model.Facility;
import com.fms.model.MaintenanceRequest;
import com.fms.model.MaintenanceType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBMaintenance {


    private PreparedStatement insertMaintenanceRequest;

    private PreparedStatement insertFacilityMaintenanceRequest;

    public DBMaintenance() throws SQLException {
       insertMaintenanceRequest = DBConnection
                .getConnection()
                .prepareStatement("INSERT into maintenance_request " +
                        "(maintenance_type_id, description, is_vacate_required," +
                        "is_routine) values (?,?,?,?) " +
                        "RETURNING id");

    }


    public boolean makeFacilityMaintRequest
            (int facilityId, MaintenanceRequest maintenanceRequest)
            throws SQLException {

        try {

            insertMaintenanceRequest.setInt(1, maintenanceRequest.getMaintenanceTypeId());
            insertMaintenanceRequest.setString(2, maintenanceRequest.getDescription());
            insertMaintenanceRequest.setBoolean(3, maintenanceRequest.isVacateRequired());
            insertMaintenanceRequest.setBoolean(4, maintenanceRequest.isRoutine());

            ResultSet resultSet = insertMaintenanceRequest.executeQuery();
            resultSet.next();
            System.out.println("Insert of maint req id -> " + resultSet.getInt((1)));

            return true;

        } catch (SQLException e) {
            System.out.println("caught exception: " + e.toString());
            throw e;
        }
    }
}
