package com.fms.dal;

import com.fms.model.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBFacility {

  private PreparedStatement preparedStatement;
  private ResultSet resultSet;
  private ResultSet facilityResultSet;

  // private PreparedStatement insertFacilityMaintenanceRequest;

  public ArrayList<Facility> readAllFacilities() {
    ArrayList<Facility> result = new ArrayList<>();
    try {

      Statement st = DBConnection.getConnection().createStatement();
      String query = "SELECT id, name, description  FROM facility";
      facilityResultSet = st.executeQuery(query);

      while (facilityResultSet.next()) {
        result.add(
            new Facility(
                facilityResultSet.getInt("id"),
                facilityResultSet.getString("name"),
                facilityResultSet.getString("description")));
      }

      // close to manage resources
      facilityResultSet.close();
    } catch (SQLException e) {
      System.out.println("caught exception: " + e.toString());
    }
    return result;
  }

  public Facility createFacility(String name, String description) throws SQLException {
    preparedStatement = null;
    try {
      preparedStatement =
          DBConnection.getConnection()
              .prepareStatement(
                  "INSERT into facility (name, description) values (?,?) RETURNING id");

      preparedStatement.setString(1, name);
      preparedStatement.setString(2, description);
      resultSet = preparedStatement.executeQuery();
      resultSet.next();

      return new Facility(resultSet.getInt(1), name, description);
    } catch (SQLException e) {
      System.out.println("caught exception: " + e.toString());
      throw e;
    }
  }

  public boolean deleteFacility(int facilityId) {

    try {
      preparedStatement =
          DBConnection.getConnection().prepareStatement("delete from facility where id = ?");
      preparedStatement.setInt(1, facilityId);
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  public void addFacilityDetail(int facilityId, FacilityDetail facilityDetail) throws SQLException {
    preparedStatement =
        DBConnection.getConnection().prepareStatement("select id from facility where id = ?");
    preparedStatement.setInt(1, facilityId);
    resultSet = preparedStatement.executeQuery();
    resultSet.next();

    for (Building building : facilityDetail.getBuildings()) {
      createBuilding(facilityId, building);
    }
  }

  private Building createBuilding(int facilityId, Building building) throws SQLException {
    preparedStatement = null;
    try {
      preparedStatement =
          DBConnection.getConnection()
              .prepareStatement(
                  "INSERT into building (facility_id, name, street_address, city, state, zip) values (?,?,?,?,?,?) RETURNING id");

      preparedStatement.setInt(1, facilityId);
      preparedStatement.setString(2, building.getName());
      preparedStatement.setString(3, building.getStreetAddress());
      preparedStatement.setString(4, building.getCity());
      preparedStatement.setString(5, building.getState());
      preparedStatement.setInt(6, building.getZip());
      resultSet = preparedStatement.executeQuery();
      resultSet.next();

      int buildingId = resultSet.getInt(1);

      for (Room room : building.getRooms()) {
        createRoom(room);
      }

      // TODO: DB cleanup

      return Building.buildingWithId(buildingId, building);
    } catch (SQLException e) {
      System.out.println("caught exception: " + e.toString());
      throw e;
    }
  }

  /**
   * Inserts into database a new room as a record with a buildingId foreign key and a room number
   *
   * @param room without id
   * @return room with id
   * @throws SQLException
   */
  private Room createRoom(Room room) throws SQLException {
    preparedStatement = null;
    try {
      preparedStatement =
          DBConnection.getConnection()
              .prepareStatement(
                  "INSERT into room (building_id, room_number, capacity) values (?,?,?) RETURNING id");

      preparedStatement.setInt(1, room.getBuildingId());
      preparedStatement.setInt(2, room.getRoomNumber());
      preparedStatement.setInt(3, room.getCapacity());
      resultSet = preparedStatement.executeQuery();
      resultSet.next();

      int roomId = resultSet.getInt(1);

      // TODO: DB cleanup
      return Room.roomWithId(roomId, room);
    } catch (SQLException e) {
      System.out.println("caught exception: " + e.toString());
      throw e;
    }
  }

  public GetFacilityDetailResult getFacilityInformation(int facilityId) throws SQLException {
    preparedStatement =
        DBConnection.getConnection()
            .prepareStatement(
                ""
                    + "select \n"
                    + "    f.id as facility_id,\n"
                    + "    f.name as facility_name, \n"
                    + "    f.description as description,\n"
                    + "    b.id as building_id,\n"
                    + "    b.name as building_name,\n"
                    + "    b.street_address as street_address,\n"
                    + "    b.city as city,\n"
                    + "    b.state as state,\n"
                    + "    b.zip as zip,\n"
                    + "    r.id as room_id,\n"
                    + "    r.room_number as room_number,\n"
                    + "    r.capacity as capacity\n"
                    + "from\n"
                    + "    facility as f \n"
                    + "    left join building as b on (b.facility_id = f.id)\n"
                    + "    left join room as r on (r.building_id = b.id)"
                    + "where f.id = ?");
    preparedStatement.setInt(1, facilityId);
    resultSet = preparedStatement.executeQuery();

    ArrayList<Building> buildings = new ArrayList<>();
    Building building = null;
    Room room = null;
    String facilityName = null;
    String facilityDescription = null;

    while (resultSet.next()) {

      if (facilityName == null) {
        facilityName = resultSet.getString("facility_name");
        facilityDescription = resultSet.getString("description");
      }

      Integer buildingId = resultSet.getInt("building_id");
      boolean hasBuilding = !resultSet.wasNull();
      Integer roomId = resultSet.getInt("room_id");
      boolean hasRoom = !resultSet.wasNull();

      if (building == null || building.getId() != buildingId) {
        if (hasBuilding) {
          building =
              new Building(
                  buildingId,
                  resultSet.getString("building_name"),
                  resultSet.getString("street_address"),
                  resultSet.getString("city"),
                  resultSet.getString("state"),
                  resultSet.getInt("zip"),
                  new ArrayList<>());

          buildings.add(building);
        }
      }

      if (room == null || room.getId() != roomId) {
        if (hasRoom) {
          room =
              new Room(
                  roomId,
                  resultSet.getInt("building_id"),
                  resultSet.getInt("room_number"),
                  resultSet.getInt("capacity"));
          building.getRooms().add(room);
        }
      }
    }

    FacilityDetail facilityDetail = new FacilityDetail(buildings);
    Facility facility = new Facility(facilityId, facilityName, facilityDescription, facilityDetail);

    return new GetFacilityDetailResult(null, facility);
  }
}
