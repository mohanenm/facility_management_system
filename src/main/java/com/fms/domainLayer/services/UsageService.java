package com.fms.domainLayer.services;

import com.fms.dal.DBUsage;
import com.fms.domainLayer.common.RoomSchedulingConflictException;
import com.fms.domainLayer.inspection.FacilityInspection;
import com.fms.domainLayer.inspection.IFacilityInspection;
import com.fms.domainLayer.usage.RoomReservation;
import com.google.common.collect.Range;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class UsageService implements IUsageService {

  DBUsage dbUsage;

  public UsageService() {
    try {
      dbUsage = new DBUsage();
    } catch (SQLException e) {
      System.err.println("Could not initialize `DBUsage` -> " + e.toString());
    }
  }

  public RoomReservation scheduleRoomReservation(int roomId, Range<LocalDateTime> duration)
          throws SQLException, RoomSchedulingConflictException {
    return dbUsage.scheduleRoomReservation(roomId, duration, null);
  }

  public RoomReservation scheduleRoomReservationForMaintenance(
          int roomId, Range<LocalDateTime> reservationPeriod, int maintenanceId)
          throws SQLException, RoomSchedulingConflictException {
    return dbUsage.scheduleRoomReservation(roomId, reservationPeriod, maintenanceId);
  }

  // Should this be a part of scheduleRoomReservation or left as a separate but mostly identical
  // method
  // possible refactor: call isInUseDuringInterval within scheduleRoomReservation method instead of
  // duplicate logic
  public boolean isInUseDuringInterval(int roomId, Range<LocalDateTime> queryPeriod) {
    try {
      return dbUsage.queryUseDuringInterval(roomId, queryPeriod);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

    /*
  Corresponds to addInspectionResults
  Given the facility_id, time_completed,
  and passed indicating whether it passed
  the inspection, saves the inspection results.
      */


  public FacilityInspection addCompletedInspection(FacilityInspection facilityInspection) {
    try {
      return dbUsage.addInspection(facilityInspection);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<IFacilityInspection> listInspections(
          int facilityId, Range<LocalDateTime> inspectionsPeriod) {
    try {
      return dbUsage.readAllInspections(facilityId, inspectionsPeriod);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

}


