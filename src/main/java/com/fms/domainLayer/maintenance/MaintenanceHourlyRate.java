package com.fms.domainLayer.maintenance;

import com.google.gson.*;
import java.io.IOException;

public class MaintenanceHourlyRate {

  public MaintenanceHourlyRate(int id, int facilityId, int maintenanceTypeId, double hourlyRate) {
    this.id = id;
    this.facilityId = facilityId;
    this.maintenanceTypeId = maintenanceTypeId;
    this.hourlyRate = hourlyRate;
  }

  public int getId() {
    return id;
  }

  public int getFacilityId() {
    return facilityId;
  }

  public int getMaintenanceTypeId() {
    return maintenanceTypeId;
  }

  public double getRate() {
    return hourlyRate;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static MaintenanceHourlyRate fromJson(String maintenanceRate) throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(maintenanceRate);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    return new MaintenanceHourlyRate(
        jsonObject.get("id").getAsInt(),
        jsonObject.get("facilityId").getAsInt(),
        jsonObject.get("maintenanceTypeId").getAsInt(),
        jsonObject.get("hourlyRate").getAsDouble());
  }

  @Override
  public boolean equals(Object o) {
    final MaintenanceHourlyRate r = (MaintenanceHourlyRate) o;
    if (r == this) {
      return true;
    }
    return id == r.id
        && facilityId == r.facilityId
        && maintenanceTypeId == r.maintenanceTypeId
        && hourlyRate == r.hourlyRate;
  }

  private int id;
  private int facilityId;
  private int maintenanceTypeId;
  private double hourlyRate;
}
