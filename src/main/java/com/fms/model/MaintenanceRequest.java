package com.fms.model;

public class MaintenanceRequest {

    public MaintenanceRequest(int id, int maintenanceTypeId, String description, boolean isVacateRequired, boolean isRoutine) {
        this.id = id;
        this.maintenanceTypeId = maintenanceTypeId;
        this.description = description;
        this.isVacateRequired = isVacateRequired;
        this.isRoutine = isRoutine;
    }

    public int getId() {
        return id;
    }

    public int getMaintenanceTypeId() {
        return maintenanceTypeId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isVacateRequired() {
        return isVacateRequired;
    }

    public boolean isRoutine() {
        return isRoutine;
    }

    private int id;
    private int maintenanceTypeId;
    private String description;
    private boolean isVacateRequired;
    private boolean isRoutine;
}
