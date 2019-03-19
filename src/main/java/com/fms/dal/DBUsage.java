package com.fms.dal;

import com.fms.domainLayer.usage.RoomSchedulingConflict;
import com.fms.domainLayer.common.RoomSchedulingConflictException;
import com.fms.domainLayer.inspection.FacilityInspection;
import com.fms.domainLayer.usage.RoomReservation;
import com.google.common.collect.Range;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DBUsage {

  private PreparedStatement insertRoomReservation;

  private PreparedStatement queryRoomRequest;

  private PreparedStatement checkRoomAvailability;

  private PreparedStatement insertFacilityInspection;

  private PreparedStatement listInspections;

  Logger logger = LogManager.getLogger();

  public DBUsage() throws SQLException {

    insertRoomReservation =
        DBConnection.getConnection()
            .prepareStatement(
                "insert into room_reservation\n" +
                        "(room_id, start, finish, maintenance_request_id) \n" +
                        "values (?, ?, ?, ?) RETURNING id");

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
                    + "    start, finish \n"
                    + "from \n"
                    + "    room_reservation as rr\n"
                    + "    where room_id = ? and \n"
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

  private RoomReservation insertRoomReservation(RoomReservation roomRequest) throws SQLException {

    try {

      insertRoomReservation.setInt(1, roomRequest.getRoomId());

      Timestamp start = Timestamp.valueOf(roomRequest.getStart());
      Timestamp finish = Timestamp.valueOf(roomRequest.getFinish());

      insertRoomReservation.setTimestamp(2, start);
      insertRoomReservation.setTimestamp(3, finish);
      Integer maintenanceRequestId = roomRequest.getMaintenanceRequestId();
      if(maintenanceRequestId == null) {
        insertRoomReservation.setNull(4, Types.INTEGER);
      } else {
        insertRoomReservation.setInt(4, maintenanceRequestId.intValue());
      }

      ResultSet resultSet = insertRoomReservation.executeQuery();
      resultSet.next();
      int resId = resultSet.getInt((1));

      // TODO: That "constructor" is bogus?
      return RoomReservation.withId(resId, roomRequest);

    } catch (SQLException e) {
      System.out.println("caught exception: " + e.toString());
      throw e;
    }
  }

  /**
   *
   * @param roomId - Id of room being reserved
   * @param reservationPeriod Start/finish range for reservation
   * @param maintenanceRequestId - If supplied implies reservation is for maintenance
   * @return true if succeeded
   * @throws SQLException
   */
  public RoomReservation scheduleRoomReservation(int roomId, Range<LocalDateTime> reservationPeriod,
                                         Integer maintenanceRequestId)
      throws SQLException, RoomSchedulingConflictException {

    /// Look up RoomMaintenanceRequest

    Timestamp start = Timestamp.valueOf(reservationPeriod.lowerEndpoint());
    Timestamp finish = Timestamp.valueOf(reservationPeriod.upperEndpoint());

    checkRoomAvailability.setInt(1, roomId);
    checkRoomAvailability.setTimestamp(2, start);
    checkRoomAvailability.setTimestamp(3, finish);
    checkRoomAvailability.setTimestamp(4, start);
    checkRoomAvailability.setTimestamp(5, finish);
    ResultSet resultSet = checkRoomAvailability.executeQuery();

    // If our query returns any records, its a conflict.
    // If next is false, no records, no conflict.
    boolean hasConflict = resultSet.next() != false;

    if(hasConflict) {
      LocalDateTime existingStart = resultSet.getTimestamp(1).toLocalDateTime();
      LocalDateTime existingFinish = resultSet.getTimestamp(2).toLocalDateTime();

      RoomSchedulingConflict conflict = new RoomSchedulingConflict(reservationPeriod,
              Range.open(existingStart, existingFinish));

      logger.log(Level.ERROR, "Scheduling conflict encountered: " + conflict);
      throw new RoomSchedulingConflictException(roomId, conflict);
    }

    return insertRoomReservation(new RoomReservation(roomId, reservationPeriod.lowerEndpoint(),
            reservationPeriod.upperEndpoint(), maintenanceRequestId));
  }


  //ToDO: add test for this method.
  public boolean queryUseDuringInterval(int roomId, Range<LocalDateTime> queryPeriod)
    throws SQLException {
    Timestamp start = Timestamp.valueOf(queryPeriod.lowerEndpoint());
    Timestamp finish = Timestamp.valueOf(queryPeriod.upperEndpoint());

    checkRoomAvailability.setInt(1, roomId);
    checkRoomAvailability.setTimestamp(2, start);
    checkRoomAvailability.setTimestamp(3, finish);
    checkRoomAvailability.setTimestamp(4, start);
    checkRoomAvailability.setTimestamp(5, finish);

    ResultSet resultSet = checkRoomAvailability.executeQuery();

    // If our query returns any records, it is in use during the interval.
    // If isInUse is false, no records, not in use.
    boolean isInUse = resultSet.next() != false;

    return isInUse;
  }

  public ArrayList<FacilityInspection> readAllInspections(int facilityId, Range<LocalDateTime> inspectionsPeriod)
    throws SQLException {
    ArrayList<FacilityInspection> inspectionsList = new ArrayList<>();
    Timestamp start = Timestamp.valueOf(inspectionsPeriod.lowerEndpoint());
    Timestamp finish = Timestamp.valueOf(inspectionsPeriod.upperEndpoint());

    listInspections.setTimestamp(1, start);
    listInspections.setTimestamp(2, finish);
    ResultSet resultSet = listInspections.executeQuery();

    System.out.println("Inspections List -> " + resultSet);
    return inspectionsList;
  }
}
