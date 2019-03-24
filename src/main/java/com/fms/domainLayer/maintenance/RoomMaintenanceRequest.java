package com.fms.domainLayer.maintenance;

import com.fms.domainLayer.common.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;

public class RoomMaintenanceRequest implements IRoomMaintenanceRequest{

  public RoomMaintenanceRequest() {}

  public RoomMaintenanceRequest(int id, MaintenanceRequest maintenanceRequest) {
    this.id = id;
    this.maintenanceRequest = maintenanceRequest;
  }

  public int getId() {
    return id;
  }

  public IMaintenanceRequest getMaintenanceRequest() {
    return maintenanceRequest;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setMaintenanceRequest(IMaintenanceRequest maintenanceRequest) {
    this.maintenanceRequest = maintenanceRequest;
  }

  public static RoomMaintenanceRequest fromJson(String roomMaintenanceRequest) throws IOException {
    Gson gson = new GsonBuilder().serializeNulls()
            .registerTypeAdapter(IMaintenanceRequest.class, new InterfaceAdapter<IMaintenanceRequest>())
            .create();
    return gson.fromJson(roomMaintenanceRequest, RoomMaintenanceRequest.class);
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.registerTypeAdapter(IMaintenanceRequest.class, new InterfaceAdapter<IMaintenanceRequest>())
            .create();
    return gson.toJson(this);
  }

  @Override
  public boolean equals(Object o) {
    final RoomMaintenanceRequest i = (RoomMaintenanceRequest) o;
    if (i == this) {
      return true;
    }
    return id == i.id && maintenanceRequest.equals(i.maintenanceRequest);
  }

  private int id;
  private IMaintenanceRequest maintenanceRequest;
}
