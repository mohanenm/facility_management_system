package com.fms.domainLayer.facility;

public interface IRoom {
    public int getId();
    public int getBuildingId();
    public int getRoomNumber();
    public int getCapacity();
    public void setId(int id);
    public void setBuildingId(int buildingId);
    public void setRoomNumber(int roomNumber);
    public void setCapacity(int capacity);
    //public static Room fromJson(String room) throws IOException;
}
