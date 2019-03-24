package com.fms.domainLayer.maintenance;

public interface IFacilityMaintenanceRequest {

  int getId();

  IMaintenanceRequest getMaintenanceRequest();

  void setId(int id);

  void setMaintenanceRequest(IMaintenanceRequest maintenanceRequest);
}
