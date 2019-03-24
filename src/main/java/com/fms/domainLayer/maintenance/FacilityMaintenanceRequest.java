package com.fms.domainLayer.maintenance;

import com.fms.domainLayer.common.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class FacilityMaintenanceRequest implements IFacilityMaintenanceRequest{

  public FacilityMaintenanceRequest() {}

  public FacilityMaintenanceRequest(int id, MaintenanceRequest maintenanceRequest) {
    this.id = id;
    this.maintenanceRequest = maintenanceRequest;
  }

  public int getId() {
    return id;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder()
            .registerTypeAdapter(IMaintenanceRequest.class, new InterfaceAdapter<IMaintenanceRequest>())
            .setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
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

  public static FacilityMaintenanceRequest fromJson(String facilityMaintenanceRequest)
      throws IOException {
    Gson gson = new GsonBuilder().serializeNulls()
            .registerTypeAdapter(IMaintenanceRequest.class, new InterfaceAdapter<IMaintenanceRequest>())
            .create();
    return gson.fromJson(facilityMaintenanceRequest, FacilityMaintenanceRequest.class);
  }

  @Override
  public boolean equals(Object o) {
    final FacilityMaintenanceRequest i = (FacilityMaintenanceRequest) o;
    if (i == this) {
      return true;
    }
    return id == i.id && maintenanceRequest.equals(i.maintenanceRequest);
  }

  private int id;
  private IMaintenanceRequest maintenanceRequest;
}
