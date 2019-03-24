package com.fms.domainLayer.usage;

import java.time.LocalDateTime;

public interface IRoomReservation {

    int getId();

    int getRoomId();

    LocalDateTime getStart();

    LocalDateTime getFinish();

    Integer getMaintenanceRequestId();

    void setId(int id);

    void setRoomId(int roomId);

    void setStart(LocalDateTime start);

    void setFinish(LocalDateTime finish);

    void setMaintenanceRequestId(Integer maintenanceRequestId);
}
