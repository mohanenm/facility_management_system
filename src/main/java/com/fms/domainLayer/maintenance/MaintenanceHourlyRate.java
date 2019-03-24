package com.fms.domainLayer.maintenance;

import com.fms.domainLayer.common.InterfaceAdapter;
import com.google.gson.*;
import java.io.IOException;

public class MaintenanceHourlyRate implements IMaintenanceHourlyRate{

  public MaintenanceHourlyRate() {}

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

  public void setId(int id) {
    this.id = id;
  }

  public void setFacilityId(int facilityId) {
    this.facilityId = facilityId;
  }

  public void setMaintenanceTypeId(int maintenanceTypeId) {
    this.maintenanceTypeId = maintenanceTypeId;
  }

  public void setHourlyRate(double hourlyRate) {
    this.hourlyRate = hourlyRate;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static MaintenanceHourlyRate fromJson(String maintenanceRate) throws IOException {
    Gson gson = new GsonBuilder().serializeNulls()
            .registerTypeAdapter(IMaintenanceHourlyRate.class, new InterfaceAdapter<IMaintenanceHourlyRate>())
            .create();
    return gson.fromJson(maintenanceRate, MaintenanceHourlyRate.class);
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
