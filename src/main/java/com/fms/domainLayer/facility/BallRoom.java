package com.fms.domainLayer.facility;

import com.fms.domainLayer.util.RoomVisitor;

public class BallRoom implements ISpecialRoom {

    public BallRoom() {
    }

    @Override
    public void accept(RoomVisitor roomVisitor) {
        roomVisitor.visit(this);
    }

}
