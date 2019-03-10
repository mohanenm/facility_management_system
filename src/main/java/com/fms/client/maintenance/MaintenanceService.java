package com.fms.client.maintenance;

import com.fms.client.common.FMSException;
import com.fms.client.common.FacilityErrorCode;
import com.fms.dal.DBMaintenance;
import com.fms.model.FacilityMaintenanceRequest;
import com.fms.model.MaintenanceRequest;
import com.fms.model.RoomMaintenanceRequest;
import com.google.common.collect.Range;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class MaintenanceService {

  DBMaintenance dbMaintenance;

  Logger logger = LogManager.getLogger();

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

  public RoomMaintenanceRequest makeRoomMaintRequest(
          int roomId, MaintenanceRequest maintenanceRequest) throws FMSException {
    try {
      return
              dbMaintenance.makeRoomMaintRequest(roomId, maintenanceRequest);
    } catch (SQLException e) {
      throw new FMSException (FacilityErrorCode.INVALID_MAINTENANCE_REQUEST,
              "Could not make this Maintenance Request.");
    }
  }

  public void removeRoomMaintRequest(int roomMaintenanceRequestId) throws FMSException{
    try {
      dbMaintenance.removeRoomMaintRequest(roomMaintenanceRequestId);
    } catch (SQLException e) {
      throw new FMSException(FacilityErrorCode.UNABLE_TO_DELETE_REQUEST,
              "Could not remove this Maintenance Request");
    }
  }

  public int scheduleRoomMaintenance (
          int roomMaintenanceRequestId, Range<LocalDateTime> maintenancePeriod) throws FMSException {
    try {
      return dbMaintenance.scheduleRoomMaintenance(roomMaintenanceRequestId, maintenancePeriod);
    } catch (SQLException e) {
      logger.log(Level.ERROR, "Schedule room maintenance failed, excp: " + e);
      throw new FMSException(FacilityErrorCode.SCHEDULE_ROOM_MAINTENANCE_FAILED,
              "Failed to schedule maintenance on room, excp ->: " + e.toString());
    }
  }

  public boolean scheduleFacilityMaintenance
          (int facilityRequestId, boolean vacancyRequired, boolean isRoutine,
           Range<LocalDateTime> maintenancePeriod) throws FMSException {
    try {
      return dbMaintenance.scheduleFacilityMaintenance(facilityRequestId, vacancyRequired, isRoutine, maintenancePeriod);
    } catch (SQLException e) {
      //ToDo:
      return false;
    }
  }
}
