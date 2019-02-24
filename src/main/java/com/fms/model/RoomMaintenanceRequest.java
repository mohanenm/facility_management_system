package com.fms.model;

public class RoomMaintenanceRequest {

    public RoomMaintenanceRequest(int id, int maintenanceRequestId, int roomId) {
        this.id = id;
        this.maintenanceRequestId = maintenanceRequestId;
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public int getMaintenanceRequestId() {
        return maintenanceRequestId;
    }

    public int getRoomId() {
        return roomId;
    }

    private int id;
    private int maintenanceRequestId;
    private int roomId;
}
