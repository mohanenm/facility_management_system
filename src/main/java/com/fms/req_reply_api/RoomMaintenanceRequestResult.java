package com.fms.req_reply_api;

import com.fms.model.RoomMaintenanceRequest;
import com.google.gson.*;
import java.io.IOException;

public class RoomMaintenanceRequestResult {
  public RoomMaintenanceRequestResult(
      RoomMaintenanceRequest roomMaintenanceRequest, String errorMessage) {
    this.roomMaintenanceRequest = roomMaintenanceRequest;
    this.errorMessage = errorMessage;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static RoomMaintenanceRequestResult fromJson(String roomMaintenanceRequestResult)
      throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(roomMaintenanceRequestResult);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    JsonElement roomMaintenanceRequestJE = jsonObject.get("roomMaintenanceRequest");

    RoomMaintenanceRequest roomMaintenanceRequest =
        RoomMaintenanceRequest.fromJson(roomMaintenanceRequestJE.getAsJsonObject().toString());

    return new RoomMaintenanceRequestResult(
        roomMaintenanceRequest, jsonObject.get("errorMessage").getAsString());
  }

  @Override
  public boolean equals(Object o) {
    final RoomMaintenanceRequestResult g = (RoomMaintenanceRequestResult) o;
    if (g == this) {
      return true;
    }

    return ((roomMaintenanceRequest == null && g.roomMaintenanceRequest == null)
            || roomMaintenanceRequest.equals(g.roomMaintenanceRequest))
        && ((errorMessage == null && g.errorMessage == null)
            || errorMessage.equals(g.errorMessage));
  }

  RoomMaintenanceRequest roomMaintenanceRequest;
  String errorMessage;
}
