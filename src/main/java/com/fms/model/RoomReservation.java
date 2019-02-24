package com.fms.model;

import java.sql.Timestamp;

public class RoomReservation {

    public RoomReservation(int id, int roomId, Timestamp start, Timestamp finish, int maintenanceRequestId) {
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

    public Timestamp getStart() {
        return start;
    }

    public Timestamp getFinish() {
        return finish;
    }

    public int getMaintenanceRequestId() {
        return maintenanceRequestId;
    }

    private int id;
    private int roomId;
    private Timestamp start;
    private Timestamp finish;
    private int maintenanceRequestId;
}
