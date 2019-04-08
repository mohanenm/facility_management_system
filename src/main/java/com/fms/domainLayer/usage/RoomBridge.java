package com.fms.domainLayer.usage;
import com.fms.domainLayer.facility.Bridge;

public class RoomBridge implements Bridge {

    public RoomBridge(IRoomReservation iRoomReservation) {
        this.iRoomReservation  = iRoomReservation;

    }

    @Override
    public void setReservationType() {
        System.out.println("RoomReservation info -> " + iRoomReservation.toString());
    }

    private IRoomReservation iRoomReservation;

}
