package com.fms.service.usage;

import com.fms.dal.DBUsage;
import com.fms.dal.RoomSchedulingConflictException;
import com.fms.model.FacilityInspection;
import com.fms.model.RoomReservation;
import com.google.common.collect.Range;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UsageService {

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

  public RoomReservation scheduleRoomReservationForMaintenance
  (int roomId, Range<LocalDateTime> reservationPeriod, int maintenanceId)
          throws SQLException, RoomSchedulingConflictException {
      return dbUsage.scheduleRoomReservation(roomId, reservationPeriod, maintenanceId);
  }

  //Should this be a part of scheduleRoomReservation or left as a separate but mostly identical method
  //possible refactor: call isInUseDuringInterval within scheduleRoomReservation method instead of duplicate logic
  public boolean isInUseDuringInterval(int roomId, Range<LocalDateTime> queryPeriod) {
    try {
      return dbUsage.queryUseDuringInterval(roomId, queryPeriod);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public ArrayList<FacilityInspection> listInspections(int facilityId, Range<LocalDateTime> inspectionsPeriod) {
    try {
      return dbUsage.readAllInspections(facilityId, inspectionsPeriod);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
