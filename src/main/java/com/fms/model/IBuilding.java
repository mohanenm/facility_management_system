package com.fms.model;

import java.util.List;

public interface IBuilding {
    public String getName();
    public String getStreetAddress();
    public String getCity();
    public String getState();
    public List<IRoom> getRooms();
    public int getZip();
    public int getId();
    public void setId(int id);
    public void setName(String name);
    public void setStreetAddress(String streetAddress);
    public void setCity(String city);
    public void setState(String state);
    public void setZip(int zip);
    public void setRooms(List<IRoom> rooms);
    //public static Building fromJson(String building) throws IOException;
}
