package com.fms.domainLayer.maintenance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class RoomMaintenanceRequest {

  public RoomMaintenanceRequest(int id, MaintenanceRequest maintenanceRequest) {
    this.id = id;
    this.maintenanceRequest = maintenanceRequest;
  }

  public int getId() {
    return id;
  }


  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public MaintenanceRequest getMaintenanceRequest() { return maintenanceRequest; }

  public static RoomMaintenanceRequest fromJson(String roomMaintenanceRequest) throws IOException {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.fromJson(roomMaintenanceRequest, RoomMaintenanceRequest.class);
  }

  @Override
  public boolean equals(Object o) {
    final RoomMaintenanceRequest i = (RoomMaintenanceRequest) o;
    if (i == this) {
      return true;
    }
    return id == i.id
            && maintenanceRequest.equals(i.maintenanceRequest);
  }

  private int id;
  private MaintenanceRequest maintenanceRequest;
}
