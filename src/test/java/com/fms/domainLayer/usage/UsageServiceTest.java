package com.fms.domainLayer.usage;

import com.fms.TestData;
import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.facility.FacilityService;
import com.fms.dal.RoomSchedulingConflictException;
import com.fms.model.*;
import com.google.common.collect.Range;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.fms.TestData.sampleRange;

public class UsageServiceTest {

  UsageService usageService;
  FacilityService facilityService;

  public UsageServiceTest() throws SQLException {
    usageService = new UsageService();
    facilityService = new FacilityService();
  }

  @Test
  public void roomRequest() throws SQLException {
    /* TODO
    RoomRequestResult roomRequestResult =
        usageService.roomRequestResult(1, TestData.sampleRoomReservation());

    System.out.println("room request -> " + roomRequestResult.toString());
    */
  }

  public Facility prepFacilityInDb() throws FMSException {
    Facility facility =
            facilityService.addNewFacility("Test Facility", "Healthcare Facility");
    return facilityService.addFacilityDetail(facility.getId(), TestData.sampleFacilityDetail());
  }

  @Test
  public void scheduleRoom() throws SQLException, RoomSchedulingConflictException, FMSException {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    Facility facility = null;
    try {
      facility = prepFacilityInDb();
      Building building = facility.getFacilityDetail().getBuildings().get(0);
      IRoom room = building.getRooms().get(0);
      Range<LocalDateTime> sampleRange = sampleRange();

      // Reserve our new room for some sample time range
      RoomReservation roomReservation = usageService.scheduleRoomReservation(room.getId(), sampleRange);
    } finally {
      if(facility != null) {
        facilityService.removeFacility(facility.getId());
      }
    }
  }

  @Test //currently just testing listing inspections, will add insert functionality once passes
  public void addInspectionToList() throws SQLException {
    ArrayList<FacilityInspection> listOfInspections = usageService.listInspections(1,
            Range.closed(
                    LocalDateTime.of(2018, 1, 1, 1, 1, 0, 0),
                    LocalDateTime.of(2019, 10, 1, 1, 1, 0, 0)));
    System.out.println("Inspections of facility within given range -> " + listOfInspections.toString());
  }

  @Test
  public void inUseDuringInterval() throws SQLException, FMSException {

    Facility facility = null;
    try {
      facility = prepFacilityInDb();
      Building building = facility.getFacilityDetail().getBuildings().get(0);
      IRoom room = building.getRooms().get(0);

      int rId = room.getId();
      Range<LocalDateTime> range = TestData.sampleRange();
      assert (usageService.isInUseDuringInterval(rId, range) == false);
    } finally {
      if(facility != null) {
        facilityService.removeFacility(facility.getId());
      }
    }
  }

}
