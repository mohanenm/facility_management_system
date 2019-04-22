package com.fms.domainLayer.util;

import com.fms.domainLayer.facility.BallRoom;
import com.fms.domainLayer.facility.BoardRoom;
import com.fms.domainLayer.facility.ClassRoom;

public class DecorateRoomVisitor implements  RoomVisitor {

    @Override
    public void visit(BallRoom ballRoom) {
        System.out.println("Decorating Ballroom -> " + ballRoom.toString());
    }

    @Override
    public void visit(ClassRoom classRoom) {
        System.out.println("Decorating Classroom -> " + classRoom.toString());
    }

    @Override
    public void visit(BoardRoom boardRoom) {
        System.out.println("Decorating Boardroom -> " + boardRoom.toString());
    }
}
