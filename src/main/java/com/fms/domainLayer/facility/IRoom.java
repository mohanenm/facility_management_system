package com.fms.domainLayer.facility;

public interface IRoom {
  int getId();

  int getBuildingId();

  int getRoomNumber();

  int getCapacity();

  RoomState getRoomState();

  void setId(int id);

  void setBuildingId(int buildingId);

  void setRoomNumber(int roomNumber);

  void setCapacity(int capacity);

  void setRoomState(RoomState roomState);

  void attach(Observer roomObserver);

  void notifyAllRoomObservers();

}
