package com.fms.dal;

import com.fms.model.*;
import com.google.common.collect.Range;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DBUsage {

  private PreparedStatement insertRoomRequest;

  private PreparedStatement queryRoomRequest;

  private PreparedStatement checkRoomAvailability;

  private PreparedStatement insertFacilityInspection;

  private PreparedStatement listInspections;

  public DBUsage() throws SQLException {
    insertRoomRequest =
        DBConnection.getConnection()
            .prepareStatement(
                "INSERT into room_reservation " + "(room_id values (?) " + "RETURNING id");

    //Not necessarily a need to query a room request, if the request is successful it
    //becomes a reservation which can be queried with checkRoomAvailability, if not
    //it is thrown out
    queryRoomRequest =
        DBConnection.getConnection()
            .prepareStatement(
                ""
                    + "select \n"
                    + "    rr.id as room_reservation,\n"
                    + "    (rr.room_id as room_id),\n"
                    + "from \n"
                    + "    (room_reservation) rr\n"
                    + "where\n"
                    + "    (rr.id = ?)");

    checkRoomAvailability =
        DBConnection.getConnection()
            .prepareStatement(
                ""
                    + "select\n"
                    + "    *\n"
                    + "from \n"
                    + "    room_reservation as rr\n"
                    + "    where \n"
                    + "    (? between rr.start and rr.finish) or\n"
                    + "    (? between rr.start and rr.finish) or\n"
                    + "    (\n"
                    + "        (? < rr.start) and \n"
                    + "        (? > rr.finish)\n"
                    + "    )");

    listInspections =
            DBConnection.getConnection().prepareStatement("select\n" +
                    "*\n" +
                    "from \n" +
                    "facility_inspection as fi\n" +
                    "where (? < fi.completed) and \n" +
                    "(? > fi.completed)");
  }

  public RoomRequest makeRoomRequest(RoomReservation roomRequest) throws SQLException {

    try {

      insertRoomRequest.setInt(1, roomRequest.getRoomId());
      insertRoomRequest.setString(2, roomRequest.getStart().toString());
      insertRoomRequest.setString(3, roomRequest.getFinish().toString());
      insertRoomRequest.setInt(4, roomRequest.getMaintenanceRequestId());

      ResultSet resultSet = insertRoomRequest.executeQuery();
      resultSet.next();

      int resId = resultSet.getInt((1));
      System.out.println("Insert of res id -> " + resId);
      String startDate = resultSet.getString((2));
      System.out.println("Insert of start -> " + startDate);
      String endDate = resultSet.getString((3));
      System.out.println("Insert of re req id -> " + endDate);
      int maint = resultSet.getInt((4));
      System.out.println("Insert of maint id -> " + maint);

      return new RoomRequest(resId, maint);

    } catch (SQLException e) {
      System.out.println("caught exception: " + e.toString());
      throw e;
    }
  }

  public boolean scheduleRoomReservation(int roomId, Range<LocalDateTime> reservationPeriod)
      throws SQLException {

    /// Look up RoomMaintenanceRequest

    Timestamp start = Timestamp.valueOf(reservationPeriod.lowerEndpoint());
    Timestamp finish = Timestamp.valueOf(reservationPeriod.upperEndpoint());

    checkRoomAvailability.setTimestamp(1, start);
    checkRoomAvailability.setTimestamp(2, finish);
    checkRoomAvailability.setTimestamp(3, start);
    checkRoomAvailability.setTimestamp(4, finish);
    ResultSet resultSet = checkRoomAvailability.executeQuery();

    // If our query returns any records, its a conflict.
    // If next is false, no records, no conflict.
    boolean hasConflict = resultSet.next() != false;

    System.out.println("Has conflict ->" + hasConflict);

    return hasConflict;

    /// See if requested maintenancePeriod available for room

    /// If so attempt insert of maintenancePeriod

  }
  //ToDO: add test for this method.
  public boolean queryUseDuringInterval(int roomId, Range<LocalDateTime> queryPeriod)
    throws SQLException {
    Timestamp start = Timestamp.valueOf(queryPeriod.lowerEndpoint());
    Timestamp finish = Timestamp.valueOf(queryPeriod.upperEndpoint());

    checkRoomAvailability.setTimestamp(1, start);
    checkRoomAvailability.setTimestamp(2, finish);
    checkRoomAvailability.setTimestamp(3, start);
    checkRoomAvailability.setTimestamp(4, finish);
    ResultSet resultSet = checkRoomAvailability.executeQuery();

    // If our query returns any records, it is in use during the interval.
    // If isInUse is false, no records, not in use.
    boolean isInUse = resultSet.next() != false;

    return isInUse;
  }


}
