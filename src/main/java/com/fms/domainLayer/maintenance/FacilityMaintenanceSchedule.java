package com.fms.domainLayer.maintenance;

import com.google.gson.*;
import java.io.IOException;
import java.time.LocalDateTime;

public class FacilityMaintenanceSchedule {

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

  public String toString() {
    GsonBuilder builder =
        new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static FacilityMaintenanceSchedule fromJson(String facilityMaintenanceSchedule)
      throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(facilityMaintenanceSchedule);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    JsonObject startAsJsonObject = jsonObject.get("start").getAsJsonObject();
    JsonObject startDateObject = startAsJsonObject.get("date").getAsJsonObject();
    JsonObject startTimeObject = startAsJsonObject.get("time").getAsJsonObject();

    JsonObject finishAsJsonObject = jsonObject.get("finish").getAsJsonObject();
    JsonObject finishDateObject = finishAsJsonObject.get("date").getAsJsonObject();
    JsonObject finishTimeObject = finishAsJsonObject.get("time").getAsJsonObject();

    LocalDateTime start =
        LocalDateTime.of(
            startDateObject.get("year").getAsInt(),
            startDateObject.get("month").getAsInt(),
            startDateObject.get("day").getAsInt(),
            startTimeObject.get("hour").getAsInt(),
            startTimeObject.get("minute").getAsInt(),
            startTimeObject.get("second").getAsInt(),
            startTimeObject.get("nano").getAsInt());

    LocalDateTime finish =
        LocalDateTime.of(
            finishDateObject.get("year").getAsInt(),
            finishDateObject.get("month").getAsInt(),
            finishDateObject.get("day").getAsInt(),
            finishTimeObject.get("hour").getAsInt(),
            finishTimeObject.get("minute").getAsInt(),
            finishTimeObject.get("second").getAsInt(),
            finishTimeObject.get("nano").getAsInt());

    return new FacilityMaintenanceSchedule(
        jsonObject.get("id").getAsInt(),
        jsonObject.get("facilityMaintenanceRequestId").getAsInt(),
        start,
        finish);
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
