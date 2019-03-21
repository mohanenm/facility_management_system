package com.fms.web_req_reply_api;

import com.fms.domainLayer.maintenance.FacilityMaintenanceRequest;
import com.google.gson.*;
import java.io.IOException;

public class FacilityMaintenanceRequestResult {

  public FacilityMaintenanceRequestResult(
      FacilityMaintenanceRequest facilityMaintenanceRequest, String errorMessage) {
    this.facilityMaintenanceRequest = facilityMaintenanceRequest;
    this.errorMessage = errorMessage;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static FacilityMaintenanceRequestResult fromJson(String facilityMaintenanceRequestResult)
      throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(facilityMaintenanceRequestResult);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    JsonElement facilityMaintenanceRequestJE = jsonObject.get("facilityMaintenanceRequest");

    FacilityMaintenanceRequest facilityMaintenanceRequest =
        FacilityMaintenanceRequest.fromJson(
            facilityMaintenanceRequestJE.getAsJsonObject().toString());

    return new FacilityMaintenanceRequestResult(
        facilityMaintenanceRequest, jsonObject.get("errorMessage").getAsString());
  }

  @Override
  public boolean equals(Object o) {
    final FacilityMaintenanceRequestResult g = (FacilityMaintenanceRequestResult) o;
    if (g == this) {
      return true;
    }

    return ((facilityMaintenanceRequest == null && g.facilityMaintenanceRequest == null)
            || facilityMaintenanceRequest.equals(g.facilityMaintenanceRequest))
        && ((errorMessage == null && g.errorMessage == null)
            || errorMessage.equals(g.errorMessage));
  }

  FacilityMaintenanceRequest facilityMaintenanceRequest;
  String errorMessage;
}
