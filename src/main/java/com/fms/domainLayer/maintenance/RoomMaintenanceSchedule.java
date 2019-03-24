package com.fms.domainLayer.maintenance;

import com.google.gson.*;
import java.io.IOException;
import java.time.LocalDateTime;

public class RoomMaintenanceSchedule {

  public RoomMaintenanceSchedule(
      int id, int roomMaintenanceRequestId, LocalDateTime start, LocalDateTime finish) {
    this.id = id;
    this.roomMaintenanceRequestId = roomMaintenanceRequestId;
    this.start = start;
    this.finish = finish;
  }

  public int getId() {
    return id;
  }

  public int getRoomMaintenanceRequestId() {
    return roomMaintenanceRequestId;
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

  public static RoomMaintenanceSchedule fromJson(String roomMaintenanceSchedule)
      throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(roomMaintenanceSchedule);
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

    return new RoomMaintenanceSchedule(
        jsonObject.get("id").getAsInt(),
        jsonObject.get("roomMaintenanceRequestId").getAsInt(),
        start,
        finish);
  }

  @Override
  public boolean equals(Object o) {
    final RoomMaintenanceSchedule i = (RoomMaintenanceSchedule) o;
    if (i == this) {
      return true;
    }
    return id == i.id
        && roomMaintenanceRequestId == i.roomMaintenanceRequestId
        && start.equals(i.start)
        && finish.equals(i.finish);
  }

  private int id;
  private int roomMaintenanceRequestId;
  private LocalDateTime start;
  private LocalDateTime finish;
}
