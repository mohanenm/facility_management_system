package com.fms.model;

public class MaintenanceType {

    public MaintenanceType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }




    private String description;
    private int id;
}
