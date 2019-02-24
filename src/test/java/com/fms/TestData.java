package com.fms;

import com.fms.model.Building;
import com.fms.model.FacilityDetail;
import com.fms.model.Room;

import java.util.ArrayList;

public class TestData {

    public static Building sampleBuilding(String name) {
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room(1, 2, 303));
        rooms.add(new Room(2, 2, 304));
        return new Building(name,
                "4 Marshall Ln", "Albequerque", "NM", 66540, rooms);
    }

    public static FacilityDetail sampleFacilityDetail() {
        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(sampleBuilding("B1"));
        buildings.add(sampleBuilding("B2"));
        return new FacilityDetail(buildings);
    }
}
