package com.fms.domainLayer.services;

import com.fms.domainLayer.common.RoomSchedulingConflictException;
import com.fms.domainLayer.inspection.FacilityInspection;
import com.fms.domainLayer.inspection.IFacilityInspection;
import com.fms.domainLayer.usage.RoomReservation;
import com.google.common.collect.Range;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface IUsageService {

    RoomReservation scheduleRoomReservation(int roomId, Range<LocalDateTime> duration)
            throws SQLException, RoomSchedulingConflictException;

    RoomReservation scheduleRoomReservationForMaintenance(
            int roomId, Range<LocalDateTime> reservationPeriod, int maintenanceId)
            throws SQLException, RoomSchedulingConflictException;

    boolean isInUseDuringInterval(int roomId, Range<LocalDateTime> queryPeriod);

    List<IFacilityInspection> listInspections(
            int facilityId, Range<LocalDateTime> inspectionsPeriod);

    FacilityInspection addCompletedInspection(FacilityInspection facilityInspection) throws SQLException;

}
