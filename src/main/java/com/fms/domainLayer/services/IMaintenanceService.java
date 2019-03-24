package com.fms.domainLayer.services;

import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.maintenance.IFacilityMaintenanceRequest;
import com.fms.domainLayer.maintenance.IMaintenanceRequest;
import com.fms.domainLayer.maintenance.RoomMaintenanceRequest;
import com.google.common.collect.Range;

import java.time.LocalDateTime;

public interface IMaintenanceService {

    IFacilityMaintenanceRequest makeFacilityMaintRequest(
            int facilityId, IMaintenanceRequest maintenanceRequest) throws FMSException;

    void removeFacilityMaintRequest(int facilityMaintenanceRequestId) throws FMSException;

    RoomMaintenanceRequest makeRoomMaintRequest(
            int roomId, IMaintenanceRequest maintenanceRequest) throws FMSException;

    void removeRoomMaintRequest(int roomMaintenanceRequestId) throws FMSException;

    int scheduleRoomMaintenance (
            int roomMaintenanceRequestId, Range<LocalDateTime> maintenancePeriod) throws FMSException;

    int scheduleFacilityMaintenance
            (int facilityRequestId, boolean vacancyRequired, boolean isRoutine,
             Range<LocalDateTime> maintenancePeriod) throws FMSException;

    int insertMaintenanceHourlyRate (int facilityId, int maintenanceTypeId, double hourlyRate) throws FMSException;

    void removeMaintenanceHourlyRate (int maintenanceHourlyRateId) throws FMSException;

}
