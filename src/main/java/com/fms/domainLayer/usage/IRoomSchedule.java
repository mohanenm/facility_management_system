package com.fms.domainLayer.usage;

import java.time.LocalDateTime;

public interface IRoomSchedule {

    int getId();

    int getRoomId();

    LocalDateTime getStart();

    LocalDateTime getFinish();

    void setId(int id);

    void setRoomId(int roomId);

    void setStart(LocalDateTime start);

    void setFinish(LocalDateTime finish);
}
