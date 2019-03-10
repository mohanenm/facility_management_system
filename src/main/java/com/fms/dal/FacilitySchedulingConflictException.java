package com.fms.dal;

import java.util.ArrayList;

public class FacilitySchedulingConflictException extends Exception {
    public FacilitySchedulingConflictException(int facilityId, ArrayList<RoomSchedulingConflict> roomConflicts) {
        this.facilityId = facilityId;
        this.roomConflicts = roomConflicts;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public ArrayList<RoomSchedulingConflict> getRoomConflicts() {
        return roomConflicts;
    }

    private int facilityId;
    private ArrayList<RoomSchedulingConflict> roomConflicts;
}
