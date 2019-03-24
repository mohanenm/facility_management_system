package com.fms.domainLayer.maintenance;

public interface IMaintenanceType {
    int getId();

    String getDescription();

    void setDescription(String description);

    void setId(int id);
}
