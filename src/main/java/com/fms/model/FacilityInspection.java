package com.fms.model;

import java.sql.Timestamp;

public class FacilityInspection {

    public FacilityInspection(int id, int facilityId, Timestamp completed, boolean passed) {
        this.id = id;
        this.facilityId = facilityId;
        this.completed = completed;
        this.passed = passed;
    }


    public int getId() {
        return id;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public Timestamp getCompleted() {
        return completed;
    }

    public boolean isPassed() {
        return passed;
    }

    private int id;
    private int facilityId;
    private Timestamp completed;
    private boolean passed;
}
