package com.fms.dal;

import com.fms.model.FacilityMaintenanceRequest;
import com.fms.model.MaintenanceRequest;
import com.fms.model.RoomMaintenanceRequest;
import com.google.common.collect.Range;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
  private PreparedStatement roomReservationConflict;
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
                    + "    facility_maintenance_schedule as fms\n"
                    + "    where \n"
                    + "    (? between fms.start and fms.finish) or\n"
                    + "    (? between fms.start and fms.finish) or\n"
                    + "    (\n"
                    + "        (? <= fms.start) and \n"
                    + "        (? >= fms.finish)\n"
                    + "    )");

    roomReservationConflict =
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
                    + "        (? <= rr.start) and \n"
                    + "        (? >= rr.finish)\n"
                    + "    )");

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
      int facilityId, MaintenanceRequest maintenanceRequest) throws SQLException {

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

      ///// Insert facility maintenance request
      insertFacilityMaintenanceRequest.setInt(1, maintenanceRequestId);
      insertFacilityMaintenanceRequest.setInt(2, facilityId);
      resultSet = insertFacilityMaintenanceRequest.executeQuery();
      resultSet.next();

      int facilityMaintenanceRequestId = resultSet.getInt((1));
      System.out.println("Insert of facility maint req id -> " + facilityMaintenanceRequestId);

      return new FacilityMaintenanceRequest(facilityMaintenanceRequestId, maintenanceRequest);

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
      int roomId, MaintenanceRequest maintenanceRequest) throws SQLException {

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

      maintenanceRequest = new MaintenanceRequest(maintenanceRequestId, maintenanceRequest);

      return new RoomMaintenanceRequest(roomMaintenanceRequestId, maintenanceRequest);

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

  public int scheduleRoomMaintenance(
      int roomMaintenanceRequestId, Range<LocalDateTime> maintenancePeriod) throws SQLException {

    Timestamp start = Timestamp.valueOf(maintenancePeriod.lowerEndpoint());
    Timestamp finish = Timestamp.valueOf(maintenancePeriod.upperEndpoint());

    /// Look up RoomMaintenanceRequest

    checkRoomAvailability.setTimestamp(1, start);
    checkRoomAvailability.setTimestamp(2, finish);
    checkRoomAvailability.setTimestamp(3, start);
    checkRoomAvailability.setTimestamp(4, finish);
    ResultSet resultSet = checkRoomAvailability.executeQuery();

    // If our query returns any records, its a conflict.
    // If next is false, no records, no conflict.
    boolean hasConflict = resultSet.next() != false;

    System.out.println("Has conflict ->" + hasConflict);
    /// See if requested maintenancePeriod available for room

    /// If so attempt insert of maintenancePeriod
    insertRoomMaintenanceSchedule.setInt(1, roomMaintenanceRequestId);
    insertRoomMaintenanceSchedule.setTimestamp(2, start);
    insertRoomMaintenanceSchedule.setTimestamp(3, finish);
    resultSet = insertRoomMaintenanceSchedule.executeQuery();
    resultSet.next();

    return resultSet.getInt(1);
  }

  public boolean scheduleFacilityMaintenance(
      int facilityRequestId,
      boolean vacateRequired,
      boolean isRoutine,
      Range<LocalDateTime> maintenancePeriod)
      throws SQLException {

    if (vacateRequired) {
      return scheduleFacilityMaintenanceVacateRequired(
          facilityRequestId, isRoutine, maintenancePeriod);
    } else {
      scheduleFacilityMaintenanceVacateNotRequired(facilityRequestId, isRoutine, maintenancePeriod);
      // this method will always succeed, we aren't checking for conflicts
      return true;
    }
  }

  private boolean scheduleFacilityMaintenanceVacateRequired(
      int facilityRequestId, boolean isRoutine, Range<LocalDateTime> maintenancePeriod)
      throws SQLException {

    Connection conn = DBConnection.getConnection();

    try {
      // Begins the complex transaction

      // First see if conflict
      Timestamp start = Timestamp.valueOf(maintenancePeriod.lowerEndpoint());
      Timestamp finish = Timestamp.valueOf(maintenancePeriod.upperEndpoint());

      roomReservationConflict.setTimestamp(1, start);
      roomReservationConflict.setTimestamp(2, finish);
      roomReservationConflict.setTimestamp(3, start);
      roomReservationConflict.setTimestamp(4, finish);

      ResultSet resultSet = roomReservationConflict.executeQuery();

      if (resultSet.next()) {
        return false;
      } else {
        // TODO:
      }

      conn.commit();

    } catch (SQLException e) {
      // TODO Log exception
      conn.rollback();
    }

    Timestamp start = Timestamp.valueOf(maintenancePeriod.lowerEndpoint());
    Timestamp finish = Timestamp.valueOf(maintenancePeriod.upperEndpoint());
    return true;
  }

  private void scheduleFacilityMaintenanceVacateNotRequired(
      int facilityRequestId, boolean isRoutine, Range<LocalDateTime> maintenancePeriod)
      throws SQLException {
    Timestamp start = Timestamp.valueOf(maintenancePeriod.lowerEndpoint());
    Timestamp finish = Timestamp.valueOf(maintenancePeriod.upperEndpoint());

    insertFacilityMaintenanceSchedule.setInt(1, facilityRequestId);
    insertFacilityMaintenanceSchedule.setTimestamp(2, start);
    insertFacilityMaintenanceSchedule.setTimestamp(3, finish);
    insertFacilityMaintenanceSchedule.executeQuery();
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
