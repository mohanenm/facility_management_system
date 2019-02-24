package com.fms.model;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class JsonMarshallingTest {

    Building sampleBuilding(String name) {
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room(1, 2, 303));
        rooms.add(new Room(2, 2, 304));
        return new Building(1, name,
                "4 Marshall Ln", "Albequerque", "NM", 66540, rooms);

    }

    @Test
    public void FacilityDetailMarshalling() throws IOException {
        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(sampleBuilding("B1"));
        buildings.add(sampleBuilding("B2"));
        FacilityDetail facilityDetail = new FacilityDetail(buildings);

        FacilityDetail again = FacilityDetail.fromJson(facilityDetail.toString());
        boolean areEqual = again.equals(facilityDetail);
        assert (areEqual);
        System.out.println("FD ->" + again.toString());
    }

    @Test
    public void BuildingMarshalling() throws IOException {
        Building building = sampleBuilding("B1");
        Building again = Building.fromJson(building.toString());
        boolean areEqual = again.equals(building);
        assert (areEqual);
    }


    @Test
    public void RoomMarshalling() throws IOException {
        Room room = new Room(1, 5, 201);
        Room again = room.fromJson(room.toString());
        boolean areEqual = again.equals(room);
        assert (areEqual);
    }


}
