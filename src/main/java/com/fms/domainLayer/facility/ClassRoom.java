package com.fms.domainLayer.facility;

import com.fms.domainLayer.util.RoomVisitor;

public class ClassRoom implements ISpecialRoom {

    @Override
    public void accept(RoomVisitor roomVisitor) {
        roomVisitor.visit(this);
    }

}
