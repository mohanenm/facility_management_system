package com.fms.domainLayer.facility;

public class RoomObserver implements Observer {

    public RoomObserver(IRoom iRoom) {
        this.iRoom = iRoom;
        this.iRoom.attach(this);
    }

    @Override
    public void update() {
        System.out.println("Room info -> " + iRoom.toString());
    }

    protected IRoom iRoom;

}
