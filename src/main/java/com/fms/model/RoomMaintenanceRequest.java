package com.fms.model;

import com.google.gson.*;
import java.io.IOException;

public class RoomMaintenanceRequest {

  public RoomMaintenanceRequest(int id, int maintenanceRequestId, int roomId) {
    this.id = id;
    this.maintenanceRequestId = maintenanceRequestId;
    this.roomId = roomId;
  }

  public int getId() {
    return id;
  }

  public int getMaintenanceRequestId() {
    return maintenanceRequestId;
  }

  public int getRoomId() {
    return roomId;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static RoomMaintenanceRequest fromJson(String roomMaintenanceRequest) throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(roomMaintenanceRequest);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    return new RoomMaintenanceRequest(
        jsonObject.get("id").getAsInt(),
        jsonObject.get("maintenanceRequestId").getAsInt(),
        jsonObject.get("roomId").getAsInt());
  }

  @Override
  public boolean equals(Object o) {
    final RoomMaintenanceRequest m = (RoomMaintenanceRequest) o;
    if (m == this) {
      return true;
    }
    return id == m.id && maintenanceRequestId == m.maintenanceRequestId && roomId == m.roomId;
  }

  private int id;
  private int maintenanceRequestId;
  private int roomId;
}
