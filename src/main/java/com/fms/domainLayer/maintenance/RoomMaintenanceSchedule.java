package com.fms.domainLayer.maintenance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.time.LocalDateTime;

public class RoomMaintenanceSchedule implements IRoomMaintenanceSchedule{

  public RoomMaintenanceSchedule() {}

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

  public void setId(int id) {
    this.id = id;
  }

  public void setRoomMaintenanceRequestId(int roomMaintenanceRequestId) {
    this.roomMaintenanceRequestId = roomMaintenanceRequestId;
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

  public static RoomMaintenanceSchedule fromJson(String roomMaintenanceSchedule)
      throws IOException {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.fromJson(roomMaintenanceSchedule, RoomMaintenanceSchedule.class);
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
