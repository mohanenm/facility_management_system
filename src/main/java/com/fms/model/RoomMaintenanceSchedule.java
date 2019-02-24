package com.fms.model;

import java.sql.Timestamp;

public class RoomMaintenanceSchedule {

    public RoomMaintenanceSchedule(int id, int roomMaintenaneRequestId, Timestamp start, Timestamp finish) {
        this.id = id;
        this.roomMaintenaneRequestId = roomMaintenaneRequestId;
        this.start = start;
        this.finish = finish;
    }

    public int getId() {
        return id;
    }

    public int getRoomMaintenaneRequestId() {
        return roomMaintenaneRequestId;
    }

    public Timestamp getStart() {
        return start;
    }

    public Timestamp getFinish() {
        return finish;
    }

    private int id;
    private int roomMaintenaneRequestId;
    private Timestamp start;
    private Timestamp finish;
}
