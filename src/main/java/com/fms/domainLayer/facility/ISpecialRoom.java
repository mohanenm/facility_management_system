package com.fms.domainLayer.facility;

import com.fms.domainLayer.util.RoomVisitor;

public interface ISpecialRoom {

    //used for visitor pattern
    void accept(RoomVisitor roomVisitor);

}
