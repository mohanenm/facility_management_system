package com.fms.domainLayer.maintenance;

public interface IMaintenanceHourlyRate {
    int getId();

    int getFacilityId();

    int getMaintenanceTypeId();

    double getRate();

    void setId(int id);

    void setFacilityId(int facilityId);

    void setMaintenanceTypeId(int maintenanceTypeId);

    void setHourlyRate(double hourlyRate);

}
