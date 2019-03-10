package com.fms.dal;

public class RoomSchedulingConflictException extends Exception {
    int roomId;

    public RoomSchedulingConflictException(int roomId, RoomSchedulingConflict roomSchedulingConflict) {
        this.roomId = roomId;
        this.roomSchedulingConflict = roomSchedulingConflict;
    }

    public int getRoomId() {
        return roomId;
    }

    public RoomSchedulingConflict getRoomSchedulingConflict() {
        return roomSchedulingConflict;
    }

    RoomSchedulingConflict roomSchedulingConflict;
}
