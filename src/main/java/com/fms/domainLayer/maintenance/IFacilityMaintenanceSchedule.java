package com.fms.domainLayer.maintenance;

import java.time.LocalDateTime;

public interface IFacilityMaintenanceSchedule {
    int getId();

    int getFacilityMaintenanceRequestId();

    LocalDateTime getStart();

    LocalDateTime getFinish();

    void setId(int id);

    void setFacilityMaintenanceRequestId(int facilityMaintenanceRequestId);

    void setStart(LocalDateTime start);

    void setFinish(LocalDateTime finish);
}
