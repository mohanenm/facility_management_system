package com.fms.domainLayer.usage;

import com.google.gson.*;
import java.io.IOException;
import java.time.LocalDateTime;

public class RoomSchedule implements IRoomSchedule{

  public RoomSchedule() {}

  public RoomSchedule(int id, int roomId, LocalDateTime start, LocalDateTime finish) {
    this.id = id;
    this.roomId = roomId;
    this.start = start;
    this.finish = finish;
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

  public String toString() {
    GsonBuilder builder =
        new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static RoomSchedule fromJson(String roomSchedule) throws IOException {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.fromJson(roomSchedule, RoomSchedule.class);
  }

  @Override
  public boolean equals(Object o) {
    final RoomSchedule i = (RoomSchedule) o;
    if (i == this) {
      return true;
    }
    return id == i.id && roomId == i.roomId && start.equals(i.start) && finish.equals(i.finish);
  }

  private int id;
  private int roomId;
  private LocalDateTime start;
  private LocalDateTime finish;
}
