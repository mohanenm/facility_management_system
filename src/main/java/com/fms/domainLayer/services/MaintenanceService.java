package com.fms.domainLayer.services;

import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.common.FacilityErrorCode;
import com.fms.dal.DBMaintenance;
import com.fms.domainLayer.maintenance.FacilityMaintenanceRequest;
import com.fms.domainLayer.maintenance.MaintenanceRequest;
import com.fms.domainLayer.maintenance.RoomMaintenanceRequest;
import com.google.common.collect.Range;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class MaintenanceService {

  DBMaintenance dbMaintenance;

  Logger logger = LogManager.getLogger();

  public MaintenanceService() {
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

  public int insertMaintenanceHourlyRate (int facilityId, int maintenanceTypeId, double hourlyRate) throws FMSException {
    try {
      return dbMaintenance.setMaintenanceHourlyRate(facilityId, maintenanceTypeId, hourlyRate);
    } catch (SQLException e) {
      logger.log(Level.ERROR, "Hourly rate insertion failed, excp: " + e);
      throw new FMSException(FacilityErrorCode.UNABLE_TO_INSERT_HOURLY_RATE,
              "Failed to insert hourly rate on room, excp ->: " + e.toString());
    }
  }

  public void removeMaintenanceHourlyRate (int maintenanceHourlyRateId) throws FMSException {
    try {
      dbMaintenance.removeFacilityMaintRequest(maintenanceHourlyRateId);
    } catch (SQLException e) {
      logger.log(Level.ERROR, "Removal of maintenance hourly rate failed, excp: " + e);
      throw new FMSException(FacilityErrorCode.UNABLE_TO_DELETE_REQUEST,
              "Unable to remove Maintenance Hourly Rate");
    }
  }

}
