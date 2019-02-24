package com.fms.model;

import java.sql.Timestamp;

public class FacilityMaintenanceSchedule {

    public FacilityMaintenanceSchedule(int id, int facilityMaintenanceRequestId, Timestamp start, Timestamp finish) {
        this.id = id;
        this.facilityMaintenanceRequestId = facilityMaintenanceRequestId;
        this.start = start;
        this.finish = finish;
    }

    public int getId() {
        return id;
    }

    public int getFacilityMaintenanceRequestId() {
        return facilityMaintenanceRequestId;
    }

    public Timestamp getStart() {
        return start;
    }

    public Timestamp getFinish() {
        return finish;
    }

    private int id;
    private int facilityMaintenanceRequestId;
    private Timestamp start;
    private Timestamp finish;
}
