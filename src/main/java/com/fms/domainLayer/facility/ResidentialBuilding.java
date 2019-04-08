package com.fms.domainLayer.facility;

import com.fms.domainLayer.common.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResidentialBuilding extends Building {

    public ResidentialBuilding(
            String name, String streetAddress, String city, String state, int zip, List<IRoom> rooms, Structure structure) {
        super(structure);
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.rooms = rooms;
    }

    /// Constructor for database, id required

    public ResidentialBuilding(
            int id,
            String name,
            String streetAddress,
            String city,
            String state,
            int zip,
            List<IRoom> rooms,
            Structure structure){
        super(structure);
        this.id = id;
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.rooms = rooms;
    }

    public ResidentialBuilding(int id, String name, String streetAddress, String city, String state, int zip, Structure structure) {
        super(structure);
        this.id = id;
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.rooms = new ArrayList<IRoom>();
    }

    public String getName() {
        return structure.getName();
    }

    public String getStreetAddress() {
        return structure.getStreetAddress();
    }

    public String getCity() {
        return structure.getCity();
    }

    public String getState() {
        return structure.getState();
    }

    public List<IRoom> getRooms() {
        return structure.getRooms();
    }

    public int getZip() {
        return structure.getZip();
    }

    public int getId() {
        return structure.getId();
    }

    public void setId(int id) {
        structure.setId(id);
    }

    public void setName(String name) {
        structure.setName(name);
    }

    public void setStreetAddress(String streetAddress) {
        structure.setStreetAddress(streetAddress);
    }

    public void setCity(String city) {
        structure.setCity(city);
    }

    public void setState(String state) {
        structure.setState(state);
    }

    public void setZip(int zip) {
        structure.setZip(zip);
    }

    public void setRooms(List<IRoom> rooms) {
        structure.setRooms(rooms);
    }

    public static Building fromJson(String building) throws IOException {
        Gson gson =
                new GsonBuilder()
                        .serializeNulls()
                        .registerTypeAdapter(IRoom.class, new InterfaceAdapter<IRoom>())
                        .create();
        return gson.fromJson(building, Building.class);
    }

    public String toString() {
        GsonBuilder builder =
                new GsonBuilder()
                        .registerTypeAdapter(IRoom.class, new InterfaceAdapter<IRoom>())
                        .setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        final ResidentialBuilding b = (ResidentialBuilding) o;
        if (b == this) {
            return true;
        }

        return id == b.id
                && name.equals(b.name)
                && streetAddress.equals(b.streetAddress)
                && city.equals(b.city)
                && state.equals(b.state)
                && zip == b.zip;
    }

    private int id;
    private String name;
    private String streetAddress;
    private String city;
    private String state;
    private int zip;
    private List<IRoom> rooms;
}
