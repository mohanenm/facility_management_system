package com.fms.domainLayer.facility;

import com.fms.domainLayer.util.RoomVisitor;

public class BoardRoom implements ISpecialRoom {

    @Override
    public void accept(RoomVisitor roomVisitor) {
        roomVisitor.visit(this);
    }
}
