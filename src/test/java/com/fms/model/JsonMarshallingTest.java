package com.fms.model;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class JsonMarshallingTest {
    @Test
    public void BuildingMarshalling() throws IOException {
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room(1, 2, 303));
        rooms.add(new Room(2, 2, 304));
        Building building = new Building(1, "B1",
                "4 Marshall Ln", "Albequerque", "NM", 66540, rooms);
        Building again = Building.fromJson(building.toString());
        boolean areEqual = again.equals(building);
        assert(areEqual);
    }


    @Test
    public void RoomMarshalling() throws IOException {
        Room room = new Room(1, 5, 201);
        Room again = room.fromJson(room.toString());
        boolean areEqual = again.equals(room);
        assert(areEqual);
    }


}
