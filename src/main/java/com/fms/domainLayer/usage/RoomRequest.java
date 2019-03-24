package com.fms.domainLayer.usage;

import com.google.gson.*;
import java.io.IOException;

public class RoomRequest implements IRoomRequest{

  public RoomRequest() {}

  public RoomRequest(int id, int roomId) {
    this.id = id;
    this.roomId = roomId;
  }

  public int getId() {
    return id;
  }

  public int getRoomId() {
    return roomId;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setRoomId(int roomId) {
    this.roomId = roomId;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static RoomRequest fromJson(String roomRequest) throws IOException {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.fromJson(roomRequest, RoomRequest.class);
  }

  @Override
  public boolean equals(Object o) {
    final RoomRequest m = (RoomRequest) o;
    if (m == this) {
      return true;
    }
    return id == m.id && roomId == m.roomId;
  }

  private int id;
  private int roomId;
}
