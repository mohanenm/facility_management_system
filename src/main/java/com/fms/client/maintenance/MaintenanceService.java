package com.fms.client.maintenance;

import com.fms.dal.DBMaintenance;
import com.fms.model.FacilityMaintenanceRequestResult;
import com.fms.model.MaintenanceRequest;
import com.google.common.collect.Range;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MaintenanceService {

  DBMaintenance dbMaintenance;

  MaintenanceService() {
    try {
      dbMaintenance = new DBMaintenance();
    } catch (SQLException e) {
      System.err.println("Could not initialize `DBMaintenance` -> " + e.toString());
    }
  }

  public FacilityMaintenanceRequestResult makeFacilityMaintRequest(
      int facilityId, MaintenanceRequest maintenanceRequest) throws SQLException {
    try {
      return new FacilityMaintenanceRequestResult(
          dbMaintenance.makeFacilityMaintRequest(facilityId, maintenanceRequest), null);
    } catch (SQLException e) {
      return new FacilityMaintenanceRequestResult(
          null, "Could not complete facility request -> " + e.toString());
    }
  }

  public boolean scheduleRoomMaintenance(
      int roomRequestId, Range<LocalDateTime> maintenancePeriod) {
    return dbMaintenance.scheduleRoomMaintenance(roomRequestId, maintenancePeriod);
  }

  public boolean scheduleFacilityMaintenance(
      int facilityRequestId, Range<LocalDateTime> maintenancePeriod) {
    try {
      return dbMaintenance.scheduleFacilityMaintenance(facilityRequestId, maintenancePeriod);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
