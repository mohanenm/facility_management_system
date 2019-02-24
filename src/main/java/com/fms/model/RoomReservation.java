package com.fms.model;

import com.google.gson.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RoomReservation {

    public RoomReservation(int id, int roomId, LocalDateTime start, LocalDateTime finish, int maintenanceRequestId) {
        this.id = id;
        this.roomId = roomId;
        this.start = start;
        this.finish = finish;
        this.maintenanceRequestId = maintenanceRequestId;
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

    public int getMaintenanceRequestId() {
        return maintenanceRequestId;
    }

    public String toString() {
        GsonBuilder builder = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Gson gson = builder.create();
        return gson.toJson(this);
    }


    public static RoomReservation fromJson(String roomReservation) throws IOException {
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(roomReservation);
        JsonObject jsonObject = jsonTree.getAsJsonObject();

        JsonObject startAsJsonObject = jsonObject.get("start").getAsJsonObject();
        JsonObject startDateObject = startAsJsonObject.get("date").getAsJsonObject();
        JsonObject startTimeObject = startAsJsonObject.get("time").getAsJsonObject();

        JsonObject finishAsJsonObject = jsonObject.get("finish").getAsJsonObject();
        JsonObject finishDateObject = finishAsJsonObject.get("date").getAsJsonObject();
        JsonObject finishTimeObject = finishAsJsonObject.get("time").getAsJsonObject();

        LocalDateTime start = LocalDateTime.of(
                startDateObject.get("year").getAsInt(),
                startDateObject.get("month").getAsInt(),
                startDateObject.get("day").getAsInt(),
                startTimeObject.get("hour").getAsInt(),
                startTimeObject.get("minute").getAsInt(),
                startTimeObject.get("second").getAsInt(),
                startTimeObject.get("nano").getAsInt());

        LocalDateTime finish = LocalDateTime.of(
                finishDateObject.get("year").getAsInt(),
                finishDateObject.get("month").getAsInt(),
                finishDateObject.get("day").getAsInt(),
                finishTimeObject.get("hour").getAsInt(),
                finishTimeObject.get("minute").getAsInt(),
                finishTimeObject.get("second").getAsInt(),
                finishTimeObject.get("nano").getAsInt());

        return new RoomReservation(jsonObject.get("id").getAsInt(),
                jsonObject.get("roomId").getAsInt(),
                start,
                finish,
                jsonObject.get("maintenanceRequestId").getAsInt());
    }

    @Override
    public boolean equals(Object o) {
        final RoomReservation i = (RoomReservation) o;
        if (i == this) {
            return true;
        }
        return id == i.id &&
                roomId == i.roomId &&
                start.equals(i.start) &&
                finish.equals(i.finish) &&
                maintenanceRequestId == i.maintenanceRequestId;
    }

    private int id;
    private int roomId;
    private LocalDateTime start;
    private LocalDateTime finish;
    private int maintenanceRequestId;
}
