package com.fms.domainLayer.facility;

import java.util.List;

public class ApartmentBuilding implements Structure {

    private String name;
    private String streetAddress;
    private String city;
    private String state;
    private List<IRoom> rooms;
    private int id;
    private int zip;

    public ApartmentBuilding(){}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getStreetAddress() {
        return streetAddress;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public List<IRoom> getRooms() {
        return rooms;
    }

    @Override
    public int getZip() {
        return zip;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public void setZip(int zip) {
        this.zip = zip;
    }

    @Override
    public void setRooms(List<IRoom> rooms) {
        this.rooms = rooms;
    }
}
