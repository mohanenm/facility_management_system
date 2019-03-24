package com.fms.domainLayer.maintenance;

import java.time.LocalDateTime;

public interface IRoomMaintenanceSchedule {
    int getId();

    int getRoomMaintenanceRequestId();

    LocalDateTime getStart();

    LocalDateTime getFinish();

    void setId(int id);

    void setRoomMaintenanceRequestId(int roomMaintenanceRequestId);

    void setStart(LocalDateTime start);

    void setFinish(LocalDateTime finish);
}
