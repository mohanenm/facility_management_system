package com.fms.dal;

import com.fms.model.*;
import com.fms.req_reply_api.GetFacilityDetailResult;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBFacility {

  private PreparedStatement insertBuilding;
  private PreparedStatement insertRoom;
  private PreparedStatement insertFacility;
  private PreparedStatement facilityInformation;
  private PreparedStatement selectFacility;
  private PreparedStatement deleteFacility;
  private ResultSet resultSet;
  private ResultSet facilityResultSet;

  public DBFacility() throws SQLException {

    insertFacility =
        DBConnection.getConnection()
            .prepareStatement("INSERT into facility (name, description) values (?,?) RETURNING id");
    deleteFacility =
        DBConnection.getConnection().prepareStatement("delete from facility where id = ?");

    selectFacility =
        DBConnection.getConnection()
            .prepareStatement("select name, description from facility where id = ?");
    insertBuilding =
        DBConnection.getConnection()
            .prepareStatement(
                "INSERT into building (facility_id, name, street_address, city, state, zip)"
                    + " values (?,?,?,?,?,?) RETURNING id");
    insertRoom =
        DBConnection.getConnection()
            .prepareStatement(
                "INSERT into room (building_id, room_number, capacity) values (?,?,?) RETURNING id");
    facilityInformation =
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
  }

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
    try {
      insertFacility.setString(1, name);
      insertFacility.setString(2, description);
      resultSet = insertFacility.executeQuery();
      resultSet.next();

      return new Facility(resultSet.getInt(1), name, description);
    } catch (SQLException e) {
      System.out.println("caught exception: " + e.toString());
      throw e;
    }
  }

  public void deleteFacility(int facilityId) throws SQLException {
    deleteFacility.setInt(1, facilityId);
    deleteFacility.executeUpdate();
  }

  public Facility addFacilityDetail(int facilityId, IFacilityDetail facilityDetail)
      throws SQLException {

    selectFacility.setInt(1, facilityId);
    resultSet = selectFacility.executeQuery();
    resultSet.next();

    String name = resultSet.getString(1);
    String description = resultSet.getString(2);

    ArrayList<IBuilding> buildings = new ArrayList<>();

    for (IBuilding building : facilityDetail.getBuildings()) {
      buildings.add(createBuilding(facilityId, building));
    }

    IFacilityDetail resultFD = new FacilityDetail();
    resultFD.setBuildings(buildings);
    return new Facility(facilityId, name, description, resultFD);
  }

  private IBuilding createBuilding(int facilityId, IBuilding building) throws SQLException {
    try {

      insertBuilding.setInt(1, facilityId);
      insertBuilding.setString(2, building.getName());
      insertBuilding.setString(3, building.getStreetAddress());
      insertBuilding.setString(4, building.getCity());
      insertBuilding.setString(5, building.getState());
      insertBuilding.setInt(6, building.getZip());
      resultSet = insertBuilding.executeQuery();
      resultSet.next();

      int buildingId = resultSet.getInt(1);
      ArrayList<IRoom> rooms = new ArrayList<>();
      for (IRoom room : building.getRooms()) {
        rooms.add(createRoom(buildingId, room));
      }
      Building result = new Building();
      result.setId(buildingId);
      result.setName(building.getName());
      result.setCity(building.getCity());
      result.setStreetAddress(building.getStreetAddress());
      result.setState(building.getState());
      result.setZip(building.getZip());
      return result;

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
  private IRoom createRoom(int buildingId, IRoom room) throws SQLException {
    try {

      insertRoom.setInt(1, buildingId);
      insertRoom.setInt(2, room.getRoomNumber());
      insertRoom.setInt(3, room.getCapacity());
      resultSet = insertRoom.executeQuery();
      resultSet.next();

      int roomId = resultSet.getInt(1);
      Room result = new Room();
      result.setId(roomId);
      result.setBuildingId(buildingId);
      result.setRoomNumber(room.getRoomNumber());
      result.setCapacity(room.getCapacity());
      return new Room(roomId, room.getBuildingId(), room.getRoomNumber(), room.getCapacity());
    } catch (SQLException e) {
      System.out.println("caught exception: " + e.toString());
      throw e;
    }
  }

  public GetFacilityDetailResult getFacilityInformation(int facilityId) throws SQLException {

    facilityInformation.setInt(1, facilityId);
    resultSet = facilityInformation.executeQuery();

    ArrayList<IBuilding> buildings = new ArrayList<>();
    IBuilding building = null;
    IRoom room = null;
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
          building = new Building();
          building.setId(buildingId);
          building.setName(resultSet.getString("building_name"));
          building.setStreetAddress(resultSet.getString("street_address"));
          building.setCity(resultSet.getString("city"));
          building.setState(resultSet.getString("state"));
          building.setZip(resultSet.getInt("zip"));
          building.setRooms(new ArrayList<>());

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

    IFacilityDetail facilityDetail = new FacilityDetail();
    facilityDetail.setBuildings(buildings);
    Facility facility = new Facility(facilityId, facilityName, facilityDescription, facilityDetail);

    return new GetFacilityDetailResult(null, facility);
  }
}
