package com.fms.model;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.StringReader;

public interface IRoom {
    public int getId();
    public int getBuildingId();
    public int getRoomNumber();
    public int getCapacity();
    public void setId(int id);
    public void setBuildingId(int buildingId);
    public void setRoomNumber(int roomNumber);
    public void setCapacity(int capacity);

    @Contract(value = "null -> false", pure = true)
    public boolean equals(Object o);
    @NotNull
    @Contract("_ -> new")
    public static Room fromJson(String room) throws IOException {
        JsonReader reader = new JsonReader(new StringReader(room));
        reader.beginObject();

        int id = 0;
        int buildingId = 0;
        int roomNumber = 0;
        int capacity = 0;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.NAME)) {
                String key = reader.nextName();

                switch (key) {
                    case "id":
                    {
                        id = reader.nextInt();
                        break;
                    }
                    case "buildingId":
                    {
                        buildingId = reader.nextInt();
                        break;
                    }
                    case "roomNumber":
                    {
                        roomNumber = reader.nextInt();
                        break;
                    }
                    case "capacity":
                    {
                        capacity = reader.nextInt();
                        break;
                    }
                    default:
                        System.out.println("no match");
                }
            }
        }

        return new Room(id, buildingId, roomNumber, capacity);
    };
}
