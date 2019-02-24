package com.fms.model;
import com.fms.Utility;
import com.google.gson.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class Building {

    /// Client constructor - no id specified
    public Building(String name, String streetAddress, String city, String state, int zip, ArrayList<Room> rooms) {
        this.id = -1;
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.rooms = rooms;
    }

    /// Constructor for database, id required

    public Building(int id, String name, String streetAddress, String city, String state, int zip, ArrayList<Room> rooms) {
        this.id = id;
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.rooms = rooms;
    }

    // In terms of encapsulation, this is a backdoor to setting id.
    // Giving it a specific name that is not `setId` makes clear what is happening.
    // This is to be used only by DB related code.
    public static Building buildingWithId(int id, Building building) {
        building.id = id;
        return building;
    }


    public String getName() {
        return name;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public int getZip() {
        return zip;
    }



    public int getId() {
        return id;
    }

    public static Building fromJson(String building) throws IOException {
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(building);
        JsonObject jsonObject = jsonTree.getAsJsonObject();

        ArrayList<Room> rooms = new ArrayList<>();

        final JsonArray rooms1 = jsonObject.get("rooms").getAsJsonArray();

        for(JsonElement room : rooms1) {
            String roomJson = room.getAsJsonObject().toString();
            rooms.add(Room.fromJson(roomJson));
        }

        return new Building(jsonObject.get("id").getAsInt(),
                jsonObject.get("name").getAsString(),
                jsonObject.get("streetAddress").getAsString(),
                jsonObject.get("city").getAsString(),
                jsonObject.get("state").getAsString(),
                jsonObject.get("zip").getAsInt(),
                rooms);
    }

    public String toString() {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        final Building b = (Building) o;
        if(b == this) {
            return true;
        }

        return id == b.id &&
                name.equals(b.name) &&
                streetAddress.equals(b.streetAddress) &&
                city.equals(b.city) &&
                state.equals(b.state) &&
                zip == b.zip;
    }


    private int id;
    private String name;
    private String streetAddress;
    private String city;
    private String state;
    private int zip;
    private ArrayList<Room> rooms;
}
