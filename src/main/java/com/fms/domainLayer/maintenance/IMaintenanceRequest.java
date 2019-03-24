package com.fms.domainLayer.maintenance;

public interface IMaintenanceRequest {
  int getId();

  int getMaintenanceTypeId();

  String getDescription();

  boolean isVacateRequired();

  boolean isRoutine();

  void setId(int id);

  void setMaintenanceTypeId(int maintenanceTypeId);

  void setDescription(String description);

  void setVacateRequired(boolean vacateRequired);

  void setRoutine(boolean routine);
}
