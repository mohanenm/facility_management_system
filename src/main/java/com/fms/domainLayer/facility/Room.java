package com.fms.domainLayer.facility;

import com.fms.domainLayer.common.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;

public class Room implements IRoom {

  public Room() {}

  public Room(int id, int buildingId, int roomNumber, int capacity) {
    this.id = id;
    this.buildingId = buildingId;
    this.roomNumber = roomNumber;
    this.capacity = capacity;
  }

  public Room(int buildingId, int roomNumber, int capacity) {
    this.id = -1;
    this.buildingId = buildingId;
    this.roomNumber = roomNumber;
    this.capacity = capacity;
  }

  public static Room roomWithId(int id, Room room) {
    room.id = id;
    return room;
  }

  public int getId() {
    return id;
  }

  public int getBuildingId() {
    return buildingId;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setBuildingId(int buildingId) {
    this.buildingId = buildingId;
  }

  public void setRoomNumber(int roomNumber) {
    this.roomNumber = roomNumber;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static Room fromJson(String room) throws IOException {
    Gson gson = new GsonBuilder().serializeNulls()
            .registerTypeAdapter(IRoom.class, new InterfaceAdapter<IRoom>()).create();
    return gson.fromJson(room, Room.class);
  }

  @Override
  public boolean equals(Object o) {
    final Room room = (Room) o;
    if (room == this) {
      return true;
    }

    return id == room.id
        && buildingId == room.buildingId
        && roomNumber == room.roomNumber
        && capacity == room.capacity;
  }

  private int id;
  private int buildingId;
  private int roomNumber;
  private int capacity;
}
