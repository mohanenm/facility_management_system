package com.fms.dal;

import com.fms.model.*;

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

        insertFacilityMaintenanceRequest = DBConnection
                .getConnection()
                .prepareStatement("INSERT into facility_maintenance_request " +
                        "(maintenance_request_id, facility_id) " +
                        " values (?,?) " +
                        "RETURNING id");

    }


    public FacilityMaintenanceRequest makeFacilityMaintRequest
            (int facilityId, MaintenanceRequest maintenanceRequest)
            throws SQLException {

        try {

            ///// Insert base maintenance request
            insertMaintenanceRequest.setInt(1, maintenanceRequest.getMaintenanceTypeId());
            insertMaintenanceRequest.setString(2, maintenanceRequest.getDescription());
            insertMaintenanceRequest.setBoolean(3, maintenanceRequest.isVacateRequired());
            insertMaintenanceRequest.setBoolean(4, maintenanceRequest.isRoutine());

            ResultSet resultSet = insertMaintenanceRequest.executeQuery();
            resultSet.next();

            int maintenanceRequestId = resultSet.getInt((1));
            System.out.println("Insert of maint req id -> " + maintenanceRequestId);

            ///// Insert facility maintenance request
            insertFacilityMaintenanceRequest.setInt(1, maintenanceRequestId);
            insertFacilityMaintenanceRequest.setInt(2, facilityId);
            resultSet = insertFacilityMaintenanceRequest.executeQuery();
            resultSet.next();

            int facilityMaintenanceRequestId = resultSet.getInt((1));
            System.out.println("Insert of facility maint req id -> " + facilityMaintenanceRequestId);

            return new FacilityMaintenanceRequest(facilityMaintenanceRequestId, maintenanceRequest, facilityId);

        } catch (SQLException e) {
            System.out.println("caught exception: " + e.toString());
            throw e;
        }
    }
}
