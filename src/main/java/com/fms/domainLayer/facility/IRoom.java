package com.fms.domainLayer.facility;

import com.fms.domainLayer.util.IObservable;

public interface IRoom extends IObservable <RoomState> {
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

}
