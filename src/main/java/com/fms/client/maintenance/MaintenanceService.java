package com.fms.client.maintenance;

import com.fms.client.common.FMSException;
import com.fms.client.common.FacilityErrorCode;
import com.fms.dal.DBMaintenance;
import com.fms.model.FacilityMaintenanceRequest;
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

  public FacilityMaintenanceRequest makeFacilityMaintRequest(
      int facilityId, MaintenanceRequest maintenanceRequest) throws FMSException {
    try {
      return
          dbMaintenance.makeFacilityMaintRequest(facilityId, maintenanceRequest);
    } catch (SQLException e) {
      throw new FMSException (FacilityErrorCode.INVALID_MAINTENANCE_REQUEST,
              "Could not make this Maintenance Request.");
    }
  }

  public void removeFacilityMaintRequest(int facilityMaintenanceRequestId) throws FMSException{
    try {
      dbMaintenance.removeFacilityMaintRequest(facilityMaintenanceRequestId);
    } catch (SQLException e) {
      throw new FMSException(FacilityErrorCode.UNABLE_TO_DELETE_REQUEST,
              "Could not remove this Maintenance Request");
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
