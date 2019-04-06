package com.fms.domainLayer.util;

import com.fms.domainLayer.facility.IRoom;
import com.fms.domainLayer.facility.RoomState;

public class RoomObserver implements IObserver<RoomState> {

    RoomObserver(IRoom room) { this.room = room; }

    public int getUpdateCalls() {
        return updateCalls;
    }

    @Override
    public void update(IObservable<RoomState> object, RoomState roomState) {
        System.out.println("Observing room update: room -> " + room.toString()
                + " Updated State " + roomState);
        updateCalls++;
    }

    private int updateCalls = 0;
    private IRoom room;
}
