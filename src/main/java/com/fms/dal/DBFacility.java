package com.fms.dal;

import com.fms.model.Building;
import com.fms.model.Facility;
import com.fms.model.FacilityDetail;
import com.fms.model.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBFacility {


    public static ArrayList<Facility> readAllFacilities() {
        ArrayList<Facility> result = new ArrayList<>();
        try {

            Statement st = DBConnection.getConnection().createStatement();
            String query = "SELECT id, name, description  FROM facility";
            ResultSet facilityResultSet = st.executeQuery(query);


            while (facilityResultSet.next()) {
                result.add(new Facility(facilityResultSet.getInt("id"),
                        facilityResultSet.getString("name"),
                        facilityResultSet.getString("description"))
                );

            }

            //close to manage resources
            facilityResultSet.close();
        } catch (SQLException e) {
            System.out.println("caught exception: " + e.toString());
        }
        return result;
    }


    public static Facility createFacility(String name, String description) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection
                    .getConnection()
                    .prepareStatement("INSERT into facility (name, description) values (?,?) RETURNING id");


            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return new Facility(resultSet.getInt(1), name, description);
        } catch (SQLException e) {
            System.out.println("caught exception: " + e.toString());
            throw e;
        }
    }

    public static boolean deleteFacility(int facilityId) {

        try {
            PreparedStatement preparedStatement = DBConnection
                    .getConnection()
                    .prepareStatement("delete from facility where id = ?");
            preparedStatement.setInt(1, facilityId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public static void addFacilityDetail(int facilityId, FacilityDetail facilityDetail) throws SQLException {
        PreparedStatement preparedStatement = DBConnection
                .getConnection()
                .prepareStatement("select id from facility where id = ?");
        preparedStatement.setInt(1, facilityId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        for(Building building: facilityDetail.getBuildings()) {
            createBuilding(facilityId, building);
        }
    }

    private static Building createBuilding(int facilityId, Building building) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection
                    .getConnection()
                    .prepareStatement("INSERT into building (facility_id, name, street_address, city, state, zip) values (?,?,?,?,?,?) RETURNING id");


            preparedStatement.setInt(1, facilityId);
            preparedStatement.setString(2, building.getName());
            preparedStatement.setString(3, building.getStreetAddress());
            preparedStatement.setString(4, building.getCity());
            preparedStatement.setString(5, building.getState());
            preparedStatement.setInt(6, building.getZip());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int buildingId = resultSet.getInt(1);

            for(Room room: building.getRooms()) {
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
     * Inserts into database a new room as a record with a buildingId foreign key
     * and a room number
     * @param room without id
     * @return room with id
     * @throws SQLException
     */
    private static Room createRoom(Room room) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection
                    .getConnection()
                    .prepareStatement("INSERT into room (building_id, room_number) values (?,?) RETURNING id");


            preparedStatement.setInt(1, room.getBuildingId());
            preparedStatement.setInt(2, room.getRoomNumber());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int roomId = resultSet.getInt(1);

            // TODO: DB cleanup
            return Room.roomWithId(roomId, room);
        } catch (SQLException e) {
            System.out.println("caught exception: " + e.toString());
            throw e;
        }
    }
}
