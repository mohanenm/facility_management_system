package com.fms.domainLayer.maintenance;

public interface IRoomMaintenanceRequest {
    int getId();

    IMaintenanceRequest getMaintenanceRequest();

    void setId(int id);

    void setMaintenanceRequest(IMaintenanceRequest maintenanceRequest);
}
