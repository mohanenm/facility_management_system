package com.fms.model;

import com.google.gson.*;
import java.io.IOException;

public class RoomRequestResult {
  public RoomRequestResult(RoomRequest roomRequest, String errorMessage) {
    this.roomRequest = roomRequest;
    this.errorMessage = errorMessage;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static RoomRequestResult fromJson(String roomMaintenanceRequestResult) throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(roomMaintenanceRequestResult);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    JsonElement roomRequestJE = jsonObject.get("roomRequest");

    RoomRequest roomRequest = RoomRequest.fromJson(roomRequestJE.getAsJsonObject().toString());

    return new RoomRequestResult(roomRequest, jsonObject.get("errorMessage").getAsString());
  }

  @Override
  public boolean equals(Object o) {
    final RoomRequestResult g = (RoomRequestResult) o;
    if (g == this) {
      return true;
    }

    return ((roomRequest == null && g.roomRequest == null) || roomRequest.equals(g.roomRequest))
        && ((errorMessage == null && g.errorMessage == null)
            || errorMessage.equals(g.errorMessage));
  }

  RoomRequest roomRequest;
  String errorMessage;
}
