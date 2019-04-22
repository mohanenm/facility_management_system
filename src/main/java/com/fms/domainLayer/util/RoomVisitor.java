package com.fms.domainLayer.util;

import com.fms.domainLayer.facility.BallRoom;
import com.fms.domainLayer.facility.BoardRoom;
import com.fms.domainLayer.facility.ClassRoom;

public interface RoomVisitor {

    void visit(BallRoom ballRoom);

    void visit(ClassRoom classRoom);

    void visit(BoardRoom boardRoom);

}
