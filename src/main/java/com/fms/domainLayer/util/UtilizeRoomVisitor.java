package com.fms.domainLayer.util;

import com.fms.domainLayer.facility.BallRoom;
import com.fms.domainLayer.facility.BoardRoom;
import com.fms.domainLayer.facility.ClassRoom;

public class UtilizeRoomVisitor implements RoomVisitor {

    @Override
    public void visit(BallRoom ballRoom) {
        System.out.println("Hosting a Ball! -> " + ballRoom.toString());
    }

    @Override
    public void visit(ClassRoom classRoom) {
        System.out.println("Teaching a Class! -> " + classRoom.toString());
    }

    @Override
    public void visit(BoardRoom boardRoom) {
        System.out.println("Holding a Meeting! -> " + boardRoom.toString());
    }

}
