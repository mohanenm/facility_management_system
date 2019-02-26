package com.fms.model;

import com.google.gson.*;
import java.io.IOException;

public class MaintenanceRate {

  public MaintenanceRate(int id, int facilityId, int maintenanceTypeId, double rate) {
    this.id = id;
    this.facilityId = facilityId;
    this.maintenanceTypeId = maintenanceTypeId;
    this.rate = rate;
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
    return rate;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static MaintenanceRate fromJson(String maintenanceRate) throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(maintenanceRate);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    return new MaintenanceRate(
        jsonObject.get("id").getAsInt(),
        jsonObject.get("facilityId").getAsInt(),
        jsonObject.get("maintenanceTypeId").getAsInt(),
        jsonObject.get("rate").getAsDouble());
  }

  @Override
  public boolean equals(Object o) {
    final MaintenanceRate r = (MaintenanceRate) o;
    if (r == this) {
      return true;
    }
    return id == r.id
        && facilityId == r.facilityId
        && maintenanceTypeId == r.maintenanceTypeId
        && rate == r.rate;
  }

  private int id;
  private int facilityId;
  private int maintenanceTypeId;
  private double rate;
}
