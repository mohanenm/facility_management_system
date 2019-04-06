package com.fms.domainLayer.inspection;

import com.google.gson.*;
import java.io.IOException;
import java.time.LocalDateTime;

public class FacilityInspection implements IFacilityInspection{

  public FacilityInspection() {}

  public FacilityInspection(int id, int facilityId, LocalDateTime completed, boolean passed) {
    this.id = id;
    this.facilityId = facilityId;
    this.completed = completed;
    this.passed = passed;
  }

  public FacilityInspection(int facilityId, LocalDateTime completed, boolean passed) {
    this.facilityId = facilityId;
    this.completed = completed;
    this.passed = passed;
  }

  public int getId() {
    return id;
  }

  public int getFacilityId() {
    return facilityId;
  }

  public LocalDateTime getCompleted() {
    return completed;
  }

  public boolean isPassed() {
    return passed;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setFacilityId(int facilityId) {
    this.facilityId = facilityId;
  }

  public void setCompleted(LocalDateTime completed) {
    this.completed = completed;
  }

  public void setPassed(boolean passed) {
    this.passed = passed;
  }

  public String toString() {
    GsonBuilder builder =
        new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static FacilityInspection fromJson(String facilityInspection) throws IOException {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.fromJson(facilityInspection, FacilityInspection.class);
  }

  @Override
  public boolean equals(Object o) {
    final FacilityInspection i = (FacilityInspection) o;
    if (i == this) {
      return true;
    }
    return id == i.id
        && facilityId == i.facilityId
        && completed.equals(i.completed)
        && passed == i.passed;
  }

  private int id;
  private int facilityId;
  private LocalDateTime completed;
  private boolean passed;
}
