package com.fms.client.usage;

import com.fms.dal.DBUsage;
import com.fms.model.RoomRequestResult;
import com.fms.model.RoomReservation;
import com.google.common.collect.Range;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class usageService {

  DBUsage dbUsage;

  usageService() {
    try {
      dbUsage = new DBUsage();
    } catch (SQLException e) {
      System.err.println("Could not initialize `DBUsage` -> " + e.toString());
    }
  }

  public RoomRequestResult roomRequestResult(int roomId, RoomReservation roomReservation)
      throws SQLException {
    try {
      return new RoomRequestResult(dbUsage.makeRoomRequest(roomReservation), null);
    } catch (SQLException e) {
      return new RoomRequestResult(null, "Could not complete room request -> " + e.toString());
    }
  }

  public boolean scheduleRoomReservation(int roomId, Range<LocalDateTime> reservationPeriod) {
    try {
      return dbUsage.scheduleRoomReservation(roomId, reservationPeriod);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
