package com.fms.client.maintenance;

import com.fms.dal.DBMaintenance;
import com.fms.model.MaintenanceRequest;
import com.google.common.collect.Range;

import java.sql.SQLException;


public class MaintenanceService {

    DBMaintenance dbMaintenance;

    MaintenanceService() {
        try {
            dbMaintenance = new DBMaintenance();
        } catch(SQLException e) {
            System.err.println("Could not initialize `DBMaintenance` -> " + e.toString());
        }
    }

    public boolean makeFacilityMaintRequest
            (int facilityId, MaintenanceRequest maintenanceRequest) {
        try {
            return dbMaintenance.makeFacilityMaintRequest(facilityId, maintenanceRequest);
        } catch(SQLException e) {
            // TODO: return error
            return false;
        }
    }
}
