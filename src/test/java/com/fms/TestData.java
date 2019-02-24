package com.fms;

import com.fms.model.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class TestData {

    public static Building sampleBuilding(String name) {
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room(1, 2, 303, 15));
        rooms.add(new Room(2, 2, 304, 20));
        return new Building(name,
                "4 Marshall Ln", "Albequerque", "NM", 66540, rooms);
    }

    public static FacilityDetail sampleFacilityDetail() {
        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(sampleBuilding("B1"));
        buildings.add(sampleBuilding("B2"));
        return new FacilityDetail(buildings);
    }

    public static Facility sampleFacility() {
        return new Facility(3, "F1",
                "sample facility for test", sampleFacilityDetail());
    }

    public static FacilityInspection sampleFacilityInspection() {
        return new FacilityInspection(1,2,
                LocalDateTime.of(1990, Month.JANUARY, 8, 12, 30), false);
    }
}
