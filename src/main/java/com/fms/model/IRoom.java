package com.fms.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public interface IRoom {
    public int getId();
    public int getBuildingId();
    public int getRoomNumber();
    public int getCapacity();
    public void setId(int id);
    public void setBuildingId(int buildingId);
    public void setRoomNumber(int roomNumber);
    public void setCapacity(int capacity);
    public static Room fromJson(String room) throws IOException{
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(room, Room.class);
    };
}
