package com.fms.model;

import com.google.gson.*;
import java.io.IOException;

public class MaintenanceRequest {

  public MaintenanceRequest(
      int id,
      int maintenanceTypeId,
      String description,
      boolean isVacateRequired,
      boolean isRoutine) {
    this.id = id;
    this.maintenanceTypeId = maintenanceTypeId;
    this.description = description;
    this.isVacateRequired = isVacateRequired;
    this.isRoutine = isRoutine;
  }

  public MaintenanceRequest(int id, MaintenanceRequest maintenanceRequest) {
    this.id = id;
    maintenanceTypeId = maintenanceRequest.maintenanceTypeId;
    description = maintenanceRequest.description;
    isVacateRequired = maintenanceRequest.isVacateRequired;
    isRoutine = maintenanceRequest.isRoutine;
  }

  public int getId() {
    return id;
  }

  public int getMaintenanceTypeId() {
    return maintenanceTypeId;
  }

  public String getDescription() {
    return description;
  }

  public boolean isVacateRequired() {
    return isVacateRequired;
  }

  public boolean isRoutine() {
    return isRoutine;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static MaintenanceRequest fromJson(String maintenanceRequest) throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(maintenanceRequest);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    return new MaintenanceRequest(
        jsonObject.get("id").getAsInt(),
        jsonObject.get("maintenanceTypeId").getAsInt(),
        jsonObject.get("description").getAsString(),
        jsonObject.get("isVacateRequired").getAsBoolean(),
        jsonObject.get("isRoutine").getAsBoolean());
  }

  public static MaintenanceRequest maintenanceRequestWithId(
      int id, MaintenanceRequest maintenanceRequest) {
    maintenanceRequest.id = id;
    return maintenanceRequest;
  }

  @Override
  public boolean equals(Object o) {
    final MaintenanceRequest m = (MaintenanceRequest) o;
    if (m == this) {
      return true;
    }
    return id == m.id
        && maintenanceTypeId == m.maintenanceTypeId
        && description.equals(m.description)
        && isVacateRequired == m.isVacateRequired
        && isRoutine == m.isRoutine;
  }

  private int id;
  private int maintenanceTypeId;
  private String description;
  private boolean isVacateRequired;
  private boolean isRoutine;
}
