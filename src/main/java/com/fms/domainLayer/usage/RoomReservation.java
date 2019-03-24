package com.fms.domainLayer.usage;

import com.google.gson.*;
import java.io.IOException;
import java.time.LocalDateTime;

public class RoomReservation implements IRoomReservation{

  public RoomReservation() {}

  public RoomReservation(
      int id, int roomId, LocalDateTime start, LocalDateTime finish, Integer maintenanceRequestId) {
    this.id = id;
    this.roomId = roomId;
    this.start = start;
    this.finish = finish;
    this.maintenanceRequestId = maintenanceRequestId;
  }

  public RoomReservation(
      int roomId, LocalDateTime start, LocalDateTime finish, Integer maintenanceRequestId) {
    this.roomId = roomId;
    this.start = start;
    this.finish = finish;
    this.maintenanceRequestId = maintenanceRequestId;
  }

  // TODO: Is this necessary
  public static RoomReservation withId(int id, RoomReservation roomReservation) {
    return new RoomReservation(
        id,
        roomReservation.roomId,
        roomReservation.start,
        roomReservation.finish,
        roomReservation.maintenanceRequestId);
  }

  public int getId() {
    return id;
  }

  public int getRoomId() {
    return roomId;
  }

  public LocalDateTime getStart() {
    return start;
  }

  public LocalDateTime getFinish() {
    return finish;
  }

  public Integer getMaintenanceRequestId() {
    return maintenanceRequestId;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setRoomId(int roomId) {
    this.roomId = roomId;
  }

  public void setStart(LocalDateTime start) {
    this.start = start;
  }

  public void setFinish(LocalDateTime finish) {
    this.finish = finish;
  }

  public void setMaintenanceRequestId(Integer maintenanceRequestId) {
    this.maintenanceRequestId = maintenanceRequestId;
  }

  public String toString() {
    GsonBuilder builder =
        new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static RoomReservation fromJson(String roomReservation) throws IOException {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.fromJson(roomReservation, RoomReservation.class);
  }

  @Override
  public boolean equals(Object o) {
    final RoomReservation i = (RoomReservation) o;
    if (i == this) {
      return true;
    }
    return id == i.id
        && roomId == i.roomId
        && start.equals(i.start)
        && finish.equals(i.finish)
        && maintenanceRequestId.equals(i.maintenanceRequestId);
  }

  private int id;
  private int roomId;
  private LocalDateTime start;
  private LocalDateTime finish;
  private Integer maintenanceRequestId;
}
