package com.fms.model;

import com.google.gson.*;
import java.io.IOException;

public class RoomRequest {

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

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static RoomRequest fromJson(String roomRequest) throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(roomRequest);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    return new RoomRequest(jsonObject.get("id").getAsInt(), jsonObject.get("roomId").getAsInt());
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
