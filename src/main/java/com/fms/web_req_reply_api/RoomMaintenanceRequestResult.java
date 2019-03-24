package com.fms.web_req_reply_api;

import com.fms.domainLayer.common.InterfaceAdapter;
import com.fms.domainLayer.maintenance.IRoomMaintenanceRequest;
import com.fms.domainLayer.maintenance.RoomMaintenanceRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class RoomMaintenanceRequestResult {

  public RoomMaintenanceRequestResult() {}

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
    Gson gson = new GsonBuilder().serializeNulls()
            .registerTypeAdapter(IRoomMaintenanceRequest.class, new InterfaceAdapter<IRoomMaintenanceRequest>())
            .create();
    return gson.fromJson(roomMaintenanceRequestResult, RoomMaintenanceRequestResult.class);
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

  IRoomMaintenanceRequest roomMaintenanceRequest;
  String errorMessage;
}
