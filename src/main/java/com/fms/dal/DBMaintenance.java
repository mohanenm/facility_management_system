package com.fms.dal;

import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.common.FacilityErrorCode;
import com.fms.domainLayer.maintenance.FacilityMaintenanceRequest;
import com.fms.domainLayer.maintenance.IMaintenanceRequest;
import com.fms.domainLayer.maintenance.MaintenanceRequest;
import com.fms.domainLayer.maintenance.RoomMaintenanceRequest;
import com.fms.domainLayer.usage.RoomReservation;
import com.google.common.collect.Range;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBMaintenance {

  Logger logger = LogManager.getLogger();

  private PreparedStatement insertMaintenanceRequest;
  private PreparedStatement deleteMaintenanceRequest;
  private PreparedStatement insertFacilityMaintenanceRequest;
  private PreparedStatement deleteFacilityMaintenanceRequest;
  private PreparedStatement insertRoomMaintenanceRequest;
  private PreparedStatement deleteRoomMaintenanceRequest;
  private PreparedStatement deleteFromFacilityMaintenanceSchedule;
  private PreparedStatement deleteFromRoomMaintenanceSchedule;
  private PreparedStatement queryMaintenanceIdFromFacilityId;
  private PreparedStatement queryMaintenanceIdFromRoomId;
  private PreparedStatement queryRoomMaintenanceRequest;
  private PreparedStatement checkRoomAvailability;
  private PreparedStatement roomIdFromRoomMaintenanceRequestId;
  private PreparedStatement facilityIdFromFacilityMaintenanceRequestId;
  private PreparedStatement facilityAnyRoomReservationConflict;
  private PreparedStatement queryFacilityMaintenanceRequest;
  private PreparedStatement insertFacilityMaintenanceSchedule;
  private PreparedStatement insertRoomMaintenanceSchedule;
  private PreparedStatement insertMaintenanceHourlyRate;
  private PreparedStatement deleteMaintenanceHourlyRate;
  private PreparedStatement calcMaintenanceCostForFacility;

  public DBMaintenance() throws SQLException {
    insertMaintenanceRequest =
        DBConnection.getConnection()
            .prepareStatement(
                "INSERT into maintenance_request "
                    + "(maintenance_type_id, description, is_vacate_required,"
                    + "is_routine) values (?,?,?,?) "
                    + "RETURNING id");

    deleteMaintenanceRequest =
        DBConnection.getConnection()
            .prepareStatement("" + "delete from maintenance_request\n" + "where \n" + "    id = ?");

    insertFacilityMaintenanceRequest =
        DBConnection.getConnection()
            .prepareStatement(
                "INSERT into facility_maintenance_request "
                    + "(maintenance_request_id, facility_id) "
                    + " values (?,?) "
                    + "RETURNING id");

    deleteFacilityMaintenanceRequest =
        DBConnection.getConnection()
            .prepareStatement(
                "delete from facility_maintenance_request\n" + "where \n" + "    id = ?");

    deleteFromFacilityMaintenanceSchedule =
        DBConnection.getConnection()
            .prepareStatement(
                "delete from facility_maintenance_schedule\n"
                    + "where \n"
                    + "    facility_maintenance_request_id = ?");

    insertRoomMaintenanceRequest =
        DBConnection.getConnection()
            .prepareStatement(
                "INSERT into room_maintenance_request \n"
                    + " (maintenance_request_id, room_id)\n"
                    + "  values (?,?) "
                    + " RETURNING id");

    deleteRoomMaintenanceRequest =
        DBConnection.getConnection()
            .prepareStatement("delete from room_maintenance_request\n" + "where\n" + "    id = ?");

    deleteFromRoomMaintenanceSchedule =
        DBConnection.getConnection()
            .prepareStatement(
                "delete from room_maintenance_schedule\n"
                    + "where\n"
                    + "    room_maintenance_request_id = ?");

    // ToDo: queryRoomMaintenanceRequest = DBConnection.getConnection().prepareStatement("SELECT ");

    queryMaintenanceIdFromFacilityId =
        DBConnection.getConnection()
            .prepareStatement(
                "select\n"
                    + "    maintenance_request_id\n"
                    + "from\n"
                    + "    facility_maintenance_request\n"
                    + "where\n"
                    + "    id = ?");

    queryMaintenanceIdFromRoomId =
        DBConnection.getConnection()
            .prepareStatement(
                "select\n"
                    + "    maintenance_request_id\n"
                    + "from\n"
                    + "    room_maintenance_request\n"
                    + "where\n"
                    + "    id = ?;");

    queryFacilityMaintenanceRequest =
        DBConnection.getConnection()
            .prepareStatement(
                ""
                    + "select \n"
                    + "    fmr.id as facility_maintenance_request_id,\n"
                    + "    mr.id as maintenance_request_id,\n"
                    + "    mr.maintenance_type_id,\n"
                    + "    mr.description as description,\n"
                    + "    mr.is_vacate_required as is_vacate_required,\n"
                    + "    mr.is_routine as is_routine \n"
                    + "from \n"
                    + "    facility_maintenance_request fmr\n"
                    + "    join maintenance_request mr on (fmr.maintenance_request_id = mr.id)\n"
                    + "where\n"
                    + "    fmr.id = ?");

    checkRoomAvailability =
        DBConnection.getConnection()
            .prepareStatement(
                ""
                    + "select\n"
                    + "    *\n"
                    + "from \n"
                    + "    room_reservation as fms\n"
                    + "    where \n"
                    + "       room_id = ? and ("
                    + "    (? between fms.start and fms.finish) or\n"
                    + "    (? between fms.start and fms.finish) or\n"
                    + "    (\n"
                    + "        (? <= fms.start) and \n"
                    + "        (? >= fms.finish)\n"
                    + "    ))");

    roomIdFromRoomMaintenanceRequestId =
            DBConnection.getConnection()
                    .prepareStatement(
                            ""
                                    + "select\n"
                                    + "    room_id\n"
                                    + "from \n"
                                    + "    room_maintenance_request\n"
                                    + "where\n"
                                    + " id = ?");

    facilityIdFromFacilityMaintenanceRequestId =
            DBConnection.getConnection()
                    .prepareStatement(
                            ""
                                    + "select\n"
                                    + "    facility_id\n"
                                    + "from \n"
                                    + "    facility_maintenance_request\n"
                                    + "where\n"
                                    + " id = ?");

    facilityAnyRoomReservationConflict =
        DBConnection.getConnection()
            .prepareStatement(
                ""
                    + "select\n" +
                    "  r.id as room_id,\n" +
                    "  rr.start as start,\n" +
                    "  rr.finish as finish,\n" +
                    "  rr.maintenance_request_id as maintenance_request_id\n" +
                    "from\n" +
                    "  room_reservation as rr\n" +
                    "  inner join building b on (b.facility_id = ?)\n" +
                    "  inner join room r on (r.building_id = b.id)\n" +
                    "where\n" +
                    "  facility_id = ? and\n" +
                    "  (\n" +
                    "  (? between rr.start and rr.finish) or\n" +
                    "  (? between rr.start and rr.finish) or\n" +
                    "  (\n" +
                    "    (? <= rr.start) and\n" +
                    "    (? >= rr.finish)\n" +
                    "  ))");

    insertFacilityMaintenanceSchedule =
        DBConnection.getConnection()
            .prepareStatement(
                "insert into \n"
                    + "    facility_maintenance_schedule \n"
                    + "    (facility_maintenance_request_id, start, finish)\n"
                    + "values\n"
                    + "    (?, ?, ?)\n"
                    + "returning id");

    insertRoomMaintenanceSchedule =
        DBConnection.getConnection()
            .prepareStatement(
                "insert into \n"
                    + "    room_maintenance_schedule \n"
                    + "    (room_maintenance_request_id, start, finish)\n"
                    + "values\n"
                    + "    (?, ?, ?)\n"
                    + "returning id");

    insertMaintenanceHourlyRate =
        DBConnection.getConnection()
            .prepareStatement(
                "insert into \n"
                    + "    maintenance_hourly_rate\n"
                    + "    (facility_id, maintenance_type_id, hourly_rate)\n"
                    + "values(?, ?, ?)\n"
                    + "returning id");

    deleteMaintenanceHourlyRate =
        DBConnection.getConnection()
            .prepareStatement("delete from \n" + "    maintenance_hourly_rate\n" + "where id = ?");

    // This *extremely* nasty join is responsible for calculating the maintenance cost across an
    // entire facility
    // within a specified time range

    calcMaintenanceCostForFacility =
        DBConnection.getConnection()
            .prepareStatement(
                "select \n"
                    + "   F.id facility_id,\n"
                    + "   MT.description,\n"
                    +

                    // Hourly rate is whatever is in the record, or 15 if NULL

                    "   COALESCE(MHR.hourly_rate, 15) hourly_rate,\n"
                    + "   \n"
                    + "   SUM(EXTRACT(EPOCH FROM (\n"
                    +
                    // parm_finish
                    "        least(?, RMS.finish) - \n"
                    +
                    // parm_start
                    "        greatest(?, RMS.start)\n"
                    + "        )))/3600 \n"
                    + "        \n"
                    + "        total_hours,\n"
                    + "        \n"
                    +

                    //  Total costs is hourly rate *
                    //  sum(min(parm_finish, finish) - max(parm_start, start)) of all records
                    //  grouped by maintenance request type

                    "   COALESCE(MHR.hourly_rate, 15) * \n"
                    + "       SUM(EXTRACT(EPOCH FROM (\n"
                    +
                    // parm_finish
                    "            least(?, RMS.finish) - \n"
                    +
                    // parm_start
                    "            greatest(?, RMS.start)\n"
                    + "            )))/3600 \n"
                    + "\n"
                    + "        partial_cost_of_type\n"
                    + "from \n"
                    + "    room R \n"
                    + "    inner join building B on (R.building_id = B.id)\n"
                    + "    full join facility F on (B.facility_id = F.id)\n"
                    + "    inner join room_maintenance_request RMR on (R.id = RMR.room_id)\n"
                    + "    full join maintenance_request MR on (MR.id = RMR.maintenance_request_id)\n"
                    + "    full join maintenance_type MT on (MR.maintenance_type_id = MT.id)\n"
                    + "    inner join room_maintenance_schedule RMS on (RMS.room_maintenance_request_id = RMR.id)\n"
                    + "    full join maintenance_hourly_rate MHR on (F.id = MHR.facility_id and MT.id = MHR.maintenance_type_id)\n"
                    + "where \n"
                    +
                    // parm_facility_id
                    "    F.id = ? and\n"
                    +
                    // parm_start
                    "    ? between RMS.start and RMS.finish or\n"
                    +
                    // parm_finish
                    "    ? between RMS.start and RMS.finish or\n"
                    +
                    // parm_start
                    "    (? <= RMS.start and \n"
                    +
                    // parm_finish
                    "    ? >= RMS.finish)\n"
                    + "group by\n"
                    + "    F.id, \n"
                    + "    MT.id,\n"
                    + "    MHR.hourly_rate");
  }

  // Returns the maintenance request with the associated database id for the request
  public FacilityMaintenanceRequest makeFacilityMaintRequest(
      int facilityId, IMaintenanceRequest maintenanceRequest) throws SQLException {

    try {

      ///// Insert base maintenance request
      insertMaintenanceRequest.setInt(1, maintenanceRequest.getMaintenanceTypeId());
      insertMaintenanceRequest.setString(2, maintenanceRequest.getDescription());
      insertMaintenanceRequest.setBoolean(3, maintenanceRequest.isVacateRequired());
      insertMaintenanceRequest.setBoolean(4, maintenanceRequest.isRoutine());

      ResultSet resultSet = insertMaintenanceRequest.executeQuery();
      resultSet.next();

      int maintenanceRequestId = resultSet.getInt(1);
      System.out.println("Insert of maint req id -> " + maintenanceRequestId);

      MaintenanceRequest resultMaintenanceRequest = new MaintenanceRequest(maintenanceRequestId,
              maintenanceRequest.getMaintenanceTypeId(), maintenanceRequest.getDescription(),
              maintenanceRequest.isVacateRequired(), maintenanceRequest.isRoutine());


      ///// Insert facility maintenance request
      insertFacilityMaintenanceRequest.setInt(1, maintenanceRequestId);
      insertFacilityMaintenanceRequest.setInt(2, facilityId);
      resultSet = insertFacilityMaintenanceRequest.executeQuery();
      resultSet.next();

      int facilityMaintenanceRequestId = resultSet.getInt(1);
      System.out.println("Insert of facility maint req id -> " + facilityMaintenanceRequestId);

      FacilityMaintenanceRequest result = new FacilityMaintenanceRequest();
      result.setId(facilityMaintenanceRequestId);
      result.setMaintenanceRequest(resultMaintenanceRequest);
      return result;

    } catch (SQLException e) {
      System.out.println("caught exception: " + e.toString());
      throw e;
    }
  }

  public void removeFacilityMaintRequest(int facilityMaintenanceRequestId) throws SQLException {
    try {

      deleteFromFacilityMaintenanceSchedule.setInt(1, facilityMaintenanceRequestId);
      deleteFromFacilityMaintenanceSchedule.executeUpdate();

      queryMaintenanceIdFromFacilityId.setInt(1, facilityMaintenanceRequestId);
      ResultSet resultSet = queryMaintenanceIdFromFacilityId.executeQuery();
      resultSet.next();

      int maintenanceId = resultSet.getInt(1);

      deleteFacilityMaintenanceRequest.setInt(1, facilityMaintenanceRequestId);
      deleteMaintenanceRequest.setInt(1, maintenanceId);
    } catch (SQLException e) {
      logger.log(Level.ERROR, "Caught exception: " + e);
    }
  }

  public RoomMaintenanceRequest makeRoomMaintRequest(
      int roomId, IMaintenanceRequest maintenanceRequest) throws SQLException {

    try {

      ///// Insert base maintenance request
      insertMaintenanceRequest.setInt(1, maintenanceRequest.getMaintenanceTypeId());
      insertMaintenanceRequest.setString(2, maintenanceRequest.getDescription());
      insertMaintenanceRequest.setBoolean(3, maintenanceRequest.isVacateRequired());
      insertMaintenanceRequest.setBoolean(4, maintenanceRequest.isRoutine());

      ResultSet resultSet = insertMaintenanceRequest.executeQuery();
      resultSet.next();

      int maintenanceRequestId = resultSet.getInt((1));
      System.out.println("Insert of maint req id -> " + maintenanceRequestId);

      ///// Insert room maintenance request
      insertRoomMaintenanceRequest.setInt(1, maintenanceRequestId);
      insertRoomMaintenanceRequest.setInt(2, roomId);
      resultSet = insertRoomMaintenanceRequest.executeQuery();
      resultSet.next();

      int roomMaintenanceRequestId = resultSet.getInt((1));
      System.out.println("Insert of room maint req id -> " + roomMaintenanceRequestId);

      MaintenanceRequest maintenanceRequestResult = new MaintenanceRequest();
      maintenanceRequestResult.setId(maintenanceRequestId);
      maintenanceRequestResult.setRoutine(maintenanceRequest.isRoutine());
      maintenanceRequestResult.setVacateRequired(maintenanceRequest.isVacateRequired());
      maintenanceRequestResult.setMaintenanceTypeId(maintenanceRequest.getMaintenanceTypeId());
      maintenanceRequestResult.setDescription(maintenanceRequest.getDescription());

      RoomMaintenanceRequest roomMaintenanceRequest = new RoomMaintenanceRequest();
      roomMaintenanceRequest.setId(roomMaintenanceRequestId);
      roomMaintenanceRequest.setMaintenanceRequest(maintenanceRequestResult);
      return roomMaintenanceRequest;

    } catch (SQLException e) {
      System.out.println("caught exception: " + e.toString());
      throw e;
    }
  }

  public void removeRoomMaintRequest(int roomMaintenanceRequestId) throws SQLException {
    try {

      deleteFromRoomMaintenanceSchedule.setInt(1, roomMaintenanceRequestId);
      deleteFromRoomMaintenanceSchedule.executeUpdate();

      queryMaintenanceIdFromRoomId.setInt(1, roomMaintenanceRequestId);
      ResultSet resultSet = queryMaintenanceIdFromRoomId.executeQuery();
      resultSet.next();

      int maintenanceId = resultSet.getInt(1);

      deleteRoomMaintenanceRequest.setInt(1, roomMaintenanceRequestId);
      deleteMaintenanceRequest.setInt(1, maintenanceId);
    } catch (SQLException e) {
      Logger logger = LogManager.getLogger();
      logger.log(Level.ERROR, "Caught exception: " + e);
    }
  }

  public List<RoomReservation> checkForRoomScheduleConflict(int roomId, Timestamp start, Timestamp finish)
          throws SQLException {
    checkRoomAvailability.setInt(1, roomId);
    checkRoomAvailability.setTimestamp(2, start);
    checkRoomAvailability.setTimestamp(3, finish);
    checkRoomAvailability.setTimestamp(4, start);
    checkRoomAvailability.setTimestamp(5, finish);
    ResultSet resultSet = checkRoomAvailability.executeQuery();

    List<RoomReservation> result = new ArrayList<>();
    while(resultSet.next()) {
      result.add(new RoomReservation(resultSet.getInt("id"),
              resultSet.getTimestamp("start").toLocalDateTime(),
              resultSet.getTimestamp("finish").toLocalDateTime(),
              resultSet.getInt("room_id")));
    }

    return result;
  }

  public int scheduleRoomMaintenance(
      int roomMaintenanceRequestId, Range<LocalDateTime> maintenancePeriod) throws FMSException, SQLException {

    roomIdFromRoomMaintenanceRequestId.setInt(1, roomMaintenanceRequestId);
    ResultSet resultSet = roomIdFromRoomMaintenanceRequestId.executeQuery();
    int roomId = -1;
    if(resultSet.next()) {
      roomId = resultSet.getInt(1);
    } else {
      throw new FMSException(FacilityErrorCode.UNABLE_TO_DELETE_REQUEST,
              "No room for room maintenance request " + roomMaintenanceRequestId);
    }

    Timestamp start = Timestamp.valueOf(maintenancePeriod.lowerEndpoint());
    Timestamp finish = Timestamp.valueOf(maintenancePeriod.upperEndpoint());
    List<RoomReservation> conflicts = checkForRoomScheduleConflict(roomId, start, finish);
    if(conflicts.size() > 0) {
      System.out.println("Requested schedule found conflicts: " + conflicts.toString());

      String message = "Found room conflicts on " + roomMaintenanceRequestId
              + "\nFor maintenance period -> " +
              maintenancePeriod.toString() +
              "\nConflicts -> " + conflicts.toString();

      throw new FMSException(FacilityErrorCode.ROOM_CONFLICTS_EXIST, message);
    }

    /// If so attempt insert of maintenancePeriod
    insertRoomMaintenanceSchedule.setInt(1, roomMaintenanceRequestId);
    insertRoomMaintenanceSchedule.setTimestamp(2, start);
    insertRoomMaintenanceSchedule.setTimestamp(3, finish);
    resultSet = insertRoomMaintenanceSchedule.executeQuery();
    resultSet.next();

    return resultSet.getInt(1);
  }

  public int scheduleFacilityMaintenance(
      int facilityMaintenanceRequestId,
      boolean vacateRequired,
      boolean isRoutine,
      Range<LocalDateTime> maintenancePeriod)
      throws SQLException, FMSException {

    if (vacateRequired) {
      return scheduleFacilityMaintenanceVacateRequired(
          facilityMaintenanceRequestId, isRoutine, maintenancePeriod);
    } else {
      return scheduleFacilityMaintenanceVacateNotRequired(
              facilityMaintenanceRequestId, isRoutine, maintenancePeriod);
    }
  }

  public List<RoomReservation> checkFacilityWideRoomScheduleConflict(int facilityId, Timestamp start, Timestamp finish)
          throws SQLException {
    facilityAnyRoomReservationConflict.setInt(1, facilityId);
    facilityAnyRoomReservationConflict.setInt(2, facilityId);
    facilityAnyRoomReservationConflict.setTimestamp(3, start);
    facilityAnyRoomReservationConflict.setTimestamp(4, finish);
    facilityAnyRoomReservationConflict.setTimestamp(5, start);
    facilityAnyRoomReservationConflict.setTimestamp(6, finish);
    ResultSet resultSet = facilityAnyRoomReservationConflict.executeQuery();

    List<RoomReservation> result = new ArrayList<>();
    while(resultSet.next()) {
      result.add(new RoomReservation(resultSet.getInt("room_id"),
              resultSet.getTimestamp("start").toLocalDateTime(),
              resultSet.getTimestamp("finish").toLocalDateTime(),
              resultSet.getInt("maintenance_request_id")));
    }

    return result;
  }

  private int scheduleFacilityMaintenanceVacateRequired (
      int facilityMaintenanceRequestId, boolean isRoutine, Range<LocalDateTime> maintenancePeriod)
      throws SQLException, FMSException {


    facilityIdFromFacilityMaintenanceRequestId.setInt(1, facilityMaintenanceRequestId);
    ResultSet resultSet =facilityIdFromFacilityMaintenanceRequestId.executeQuery();
    int facilityId = -1;

    if(resultSet.next()) {
      facilityId = resultSet.getInt(1);
      Timestamp start = Timestamp.valueOf(maintenancePeriod.lowerEndpoint());
      Timestamp finish = Timestamp.valueOf(maintenancePeriod.upperEndpoint());
      List<RoomReservation> conflicts = checkFacilityWideRoomScheduleConflict(facilityId, start, finish);

      if(conflicts.size() > 0) {
        String message =  "Room conflicts exist on facility " + facilityId +
                " conflicts: " + conflicts.toString();
        logger.log(Level.ERROR, message);
        throw new FMSException(FacilityErrorCode.ROOM_CONFLICTS_EXIST,
               message);
      }

      insertFacilityMaintenanceSchedule.setInt(1, facilityMaintenanceRequestId);
      insertFacilityMaintenanceSchedule.setTimestamp(2, start);
      insertFacilityMaintenanceSchedule.setTimestamp(3, finish);
      resultSet = insertFacilityMaintenanceSchedule.executeQuery();
      resultSet.next();
      return resultSet.getInt(1);

    } else {
      String message = "Unable to get facility ID from facility maintenance request ID -> " +
              facilityMaintenanceRequestId;
      logger.log(Level.ERROR, message);

      throw new FMSException(FacilityErrorCode.COULD_NOT_GET_FACILITY_ID_FROM_FACILITY_MAINTENANCE_REQUEST_ID,
              message);
    }
  }

  private int scheduleFacilityMaintenanceVacateNotRequired(
      int facilityRequestId, boolean isRoutine, Range<LocalDateTime> maintenancePeriod)
      throws SQLException {
    Timestamp start = Timestamp.valueOf(maintenancePeriod.lowerEndpoint());
    Timestamp finish = Timestamp.valueOf(maintenancePeriod.upperEndpoint());

    insertFacilityMaintenanceSchedule.setInt(1, facilityRequestId);
    insertFacilityMaintenanceSchedule.setTimestamp(2, start);
    insertFacilityMaintenanceSchedule.setTimestamp(3, finish);
    ResultSet resultSet = insertFacilityMaintenanceSchedule.executeQuery();
    resultSet.next();
    return resultSet.getInt(1);
  }

  public int setMaintenanceHourlyRate(int facilityId, int maintenanceTypeId, double hourlyRate)
      throws SQLException {
    try {
      insertMaintenanceHourlyRate.setInt(1, facilityId);
      insertMaintenanceHourlyRate.setInt(2, maintenanceTypeId);
      insertMaintenanceHourlyRate.setDouble(3, hourlyRate);
      ResultSet resultSet = insertMaintenanceHourlyRate.executeQuery();
      resultSet.next();
      return resultSet.getInt(1);
    } catch (SQLException e) {
      System.out.println("caught exception: " + e.toString());
      throw e;
    }
  }

  public void removeMaintenanceHourlyRate(int maintenanceHourlyRateId) throws SQLException {
    try {
      deleteMaintenanceHourlyRate.setInt(1, maintenanceHourlyRateId);
    } catch (SQLException e) {
      logger.log(Level.ERROR, "Unable to remove maintenance hourly rate" + e);
    }
  }

  public HashMap<String, Double> calcMaintenanceCostForFacility(
      int facilityId, Range<LocalDateTime> calculationPeriod) throws SQLException {

    Timestamp start = Timestamp.valueOf(calculationPeriod.lowerEndpoint());
    Timestamp finish = Timestamp.valueOf(calculationPeriod.upperEndpoint());

    try {
      calcMaintenanceCostForFacility.setTimestamp(1, finish);
      calcMaintenanceCostForFacility.setTimestamp(2, start);
      calcMaintenanceCostForFacility.setTimestamp(3, finish);
      calcMaintenanceCostForFacility.setTimestamp(4, start);
      calcMaintenanceCostForFacility.setInt(5, facilityId);
      calcMaintenanceCostForFacility.setTimestamp(6, start);
      calcMaintenanceCostForFacility.setTimestamp(7, finish);
      calcMaintenanceCostForFacility.setTimestamp(8, start);
      calcMaintenanceCostForFacility.setTimestamp(9, finish);
      ResultSet resultSet = calcMaintenanceCostForFacility.executeQuery();
      HashMap<String, Double> totals = new HashMap<>();
      while (resultSet.next()) {
        totals.put(resultSet.getString(2), resultSet.getDouble(5));
      }
      return totals;
    } catch (SQLException e) {
      logger.log(Level.ERROR, "Caught exception: " + e);
      throw e;
    }
  }
}
