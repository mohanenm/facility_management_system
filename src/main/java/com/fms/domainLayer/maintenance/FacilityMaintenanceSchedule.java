package com.fms.domainLayer.maintenance;

import com.fms.domainLayer.common.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.time.LocalDateTime;

public class FacilityMaintenanceSchedule implements IFacilityMaintenanceSchedule{

  public FacilityMaintenanceSchedule() {}

  public FacilityMaintenanceSchedule(
      int id, int facilityMaintenanceRequestId, LocalDateTime start, LocalDateTime finish) {
    this.id = id;
    this.facilityMaintenanceRequestId = facilityMaintenanceRequestId;
    this.start = start;
    this.finish = finish;
  }

  public int getId() {
    return id;
  }

  public int getFacilityMaintenanceRequestId() {
    return facilityMaintenanceRequestId;
  }

  public LocalDateTime getStart() {
    return start;
  }

  public LocalDateTime getFinish() {
    return finish;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setFacilityMaintenanceRequestId(int facilityMaintenanceRequestId) {
    this.facilityMaintenanceRequestId = facilityMaintenanceRequestId;
  }

  public void setStart(LocalDateTime start) {
    this.start = start;
  }

  public void setFinish(LocalDateTime finish) {
    this.finish = finish;
  }

  public String toString() {
    GsonBuilder builder =
        new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static FacilityMaintenanceSchedule fromJson(String facilityMaintenanceSchedule)
      throws IOException {
    Gson gson = new GsonBuilder().serializeNulls()
            .registerTypeAdapter(IFacilityMaintenanceSchedule.class, new InterfaceAdapter<IFacilityMaintenanceSchedule>())
            .create();
    return gson.fromJson(facilityMaintenanceSchedule, FacilityMaintenanceSchedule.class);
  }

  @Override
  public boolean equals(Object o) {
    final FacilityMaintenanceSchedule i = (FacilityMaintenanceSchedule) o;
    if (i == this) {
      return true;
    }
    return id == i.id
        && facilityMaintenanceRequestId == i.facilityMaintenanceRequestId
        && start.equals(i.start)
        && finish.equals(i.finish);
  }

  private int id;
  private int facilityMaintenanceRequestId;
  private LocalDateTime start;
  private LocalDateTime finish;
}
