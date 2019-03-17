package com.fms.req_reply_api;

import com.fms.model.MaintenanceRequest;
import com.google.gson.*;
import java.io.IOException;

public class FacilityMaintenanceWebRequest {

  public FacilityMaintenanceWebRequest(int facilityId, MaintenanceRequest maintenanceRequest) {
    this.id = -1;
    this.maintenanceRequest = maintenanceRequest;
    this.facilityId = facilityId;
  }

  public FacilityMaintenanceWebRequest(
      int id, MaintenanceRequest maintenanceRequest, int facilityId) {
    this.id = id;
    this.maintenanceRequest = maintenanceRequest;
    this.facilityId = facilityId;
  }

  public int getId() {
    return id;
  }

  public int getFacilityId() {
    return facilityId;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static FacilityMaintenanceWebRequest fromJson(String facilityMaintenanceRequest)
      throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(facilityMaintenanceRequest);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    MaintenanceRequest maintenanceRequest =
        MaintenanceRequest.fromJson(
            jsonObject.get("maintenanceRequest").getAsJsonObject().toString());

    return new FacilityMaintenanceWebRequest(
        jsonObject.get("id").getAsInt(),
        maintenanceRequest,
        jsonObject.get("facilityId").getAsInt());
  }

  @Override
  public boolean equals(Object o) {
    final FacilityMaintenanceWebRequest r = (FacilityMaintenanceWebRequest) o;
    if (r == this) {
      return true;
    }
    return id == r.id
        && maintenanceRequest.equals(r.maintenanceRequest)
        && facilityId == r.facilityId;
  }

  private int id;
  private MaintenanceRequest maintenanceRequest;
  private int facilityId;
}
