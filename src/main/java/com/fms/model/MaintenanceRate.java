package com.fms.model;

public class MaintenanceRate {

    public MaintenanceRate(int id, int facilityId, int maintenanceTypeId, double rate) {
        this.id = id;
        this.facilityId = facilityId;
        MaintenanceTypeId = maintenanceTypeId;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public int getMaintenanceTypeId() {
        return MaintenanceTypeId;
    }

    public double getRate() {
        return rate;
    }

    private int id;
    private int facilityId;
    private int MaintenanceTypeId;
    private double rate;
}
