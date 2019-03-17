package com.fms.model;

public interface IRoom {
  int getId();

  int getBuildingId();

  int getRoomNumber();

  int getCapacity();

  void setId(int id);

  void setBuildingId(int buildingId);

  void setRoomNumber(int roomNumber);

  void setCapacity(int capacity);
  // public static Room fromJson(String room) throws IOException;
}
