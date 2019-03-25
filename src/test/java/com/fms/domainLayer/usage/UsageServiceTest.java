package com.fms.domainLayer.usage;

import com.fms.TestData;
import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.common.RoomSchedulingConflictException;
import com.fms.domainLayer.facility.IBuilding;
import com.fms.domainLayer.facility.IFacility;
import com.fms.domainLayer.facility.IRoom;
import com.fms.domainLayer.inspection.FacilityInspection;
import com.fms.domainLayer.services.FacilityService;
import com.fms.domainLayer.services.UsageService;
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
  TestData testData;

  public UsageServiceTest() throws SQLException {
    usageService = new UsageService();
    facilityService = new FacilityService();
    testData = new TestData();
  }

//  @Test
//  public void roomRequest() throws SQLException {
//    RoomRequestResult roomRequestResult =
//        usageService.roomRequestResult(1, TestData.sampleRoomReservation());
//
//    System.out.println("room request -> " + roomRequestResult.toString());
//
//  }

  public IFacility prepFacilityInDb() throws FMSException {
    IFacility facility = facilityService.addNewFacility("Test Facility", "Healthcare Facility");
    return facilityService.addFacilityDetail(facility.getId(), testData.sampleBuildings());
  }

  @Test
  public void scheduleRoom() throws SQLException, RoomSchedulingConflictException, FMSException {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    IFacility facility = null;
    try {
      facility = prepFacilityInDb();
      IBuilding building = facility.getBuildings().get(0);
      IRoom room = building.getRooms().get(0);
      Range<LocalDateTime> sampleRange = sampleRange();

      // Reserve our new room for some sample time range
      RoomReservation roomReservation =
          usageService.scheduleRoomReservation(room.getId(), sampleRange);
    } finally {
      if (facility != null) {
        facilityService.removeFacility(facility.getId());
      }
    }
  }

  @Test // currently just testing listing inspections, will add insert functionality once passes
  public void addInspectionToList() throws SQLException {
    ArrayList<FacilityInspection> listOfInspections =
        usageService.listInspections(
            1,
            Range.closed(
                LocalDateTime.of(2018, 1, 1, 1, 1, 0, 0),
                LocalDateTime.of(2019, 10, 1, 1, 1, 0, 0)));
    System.out.println(
        "Inspections of facility within given range -> " + listOfInspections.toString());
  }

  @Test // currently just testing listing inspections, will add insert functionality once passes
  public void addInspectionResult() throws SQLException {
   FacilityInspection inspection =
            usageService.addCompletedInspection(
                    1,
                    LocalDateTime.of(2019, 10, 1, 1, 1, 0, 0),
                    true);
    System.out.println(
            "InspectionResult -> " + inspection.toString());
  }


  @Test
  public void inUseDuringInterval() throws SQLException, FMSException {

    IFacility facility = null;
    try {
      facility = prepFacilityInDb();
      IBuilding building = facility.getBuildings().get(0);
      IRoom room = building.getRooms().get(0);

      int rId = room.getId();
      Range<LocalDateTime> range = TestData.sampleRange();
      assert (usageService.isInUseDuringInterval(rId, range) == false);
    } finally {
      if (facility != null) {
        facilityService.removeFacility(facility.getId());
      }
    }
  }
}
