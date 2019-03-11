package com.fms.client.maintenance;

import com.fms.TestData;
import com.fms.client.common.FMSException;
import com.fms.client.facility.FacilityService;
import com.fms.client.usage.UsageService;
import com.fms.dal.RoomSchedulingConflictException;
import com.fms.model.*;
import com.google.common.collect.Range;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.fms.TestData.sampleRange;

public class MaintenanceServiceTest {

  MaintenanceService maintenanceService;
  FacilityService facilityService;
  UsageService usageService;
  TestData testData;
  MaintenanceCostCalculator maintenanceCostCalculator;

  public MaintenanceServiceTest() throws SQLException {
    maintenanceService = new MaintenanceService();
    facilityService = new FacilityService();
    usageService = new UsageService();
    testData = new TestData();
    maintenanceCostCalculator = new MaintenanceCostCalculator();
  }

  @Rule
  public ExpectedException maintenanceException = ExpectedException.none();

  @Test
  public void CRUDFacilityMaintenanceRequest() throws FMSException {

      Facility facility = null;
      try {
          facility = testData.prepFacilityInDb();
          MaintenanceRequest maintenanceRequest = TestData.sampleMaintenanceRequest();

          FacilityMaintenanceRequest facilityMaintenanceRequest =
                  maintenanceService.makeFacilityMaintRequest(facility.getId(), maintenanceRequest);

          int fmrId = facilityMaintenanceRequest.getId();

          assert (fmrId > 0);

          MaintenanceRequest fromDb = facilityMaintenanceRequest.getMaintenanceRequest();
          MaintenanceRequest patchedId = new MaintenanceRequest(-1, fromDb);

          assert (patchedId.equals(maintenanceRequest));

          assert (facilityMaintenanceRequest.getMaintenanceRequest() == maintenanceRequest);
      } finally {
          if(facility != null) {
              facilityService.removeFacility(facility.getId());
          }
      }
  }

  @Test
  public void CRUDRoomMaintenanceRequest() throws FMSException {

      Facility facility = null;
        try {
            facility = testData.prepFacilityInDb();

            Building building = facility.getFacilityDetail().getBuildings().get(0);
            Room room = building.getRooms().get(0);

            MaintenanceRequest maintenanceRequest = TestData.sampleMaintenanceRequest();

            RoomMaintenanceRequest roomMaintenanceRequest =
                    maintenanceService.makeRoomMaintRequest(room.getId(), maintenanceRequest);

            int rmrId = roomMaintenanceRequest.getId();

            assert (rmrId > 0);

            MaintenanceRequest fromDb = roomMaintenanceRequest.getMaintenanceRequest();
            MaintenanceRequest patchedId = new MaintenanceRequest(-1, fromDb);

            assert (patchedId.equals(maintenanceRequest));
            maintenanceService.removeRoomMaintRequest(rmrId);
        } finally {
            if(facility != null)
                facilityService.removeFacility(facility.getId());
        }
  }

  @Test
  public void scheduleFacilityMaintenance() throws FMSException {

      Facility facility = null;
      try {
          facility = testData.prepFacilityInDb();
          FacilityMaintenanceSchedule facilityMaintenanceSchedule = TestData.sampleFacilityMaintenanceSchedule();
          int fmsId = facilityMaintenanceSchedule.getId();

          maintenanceService.scheduleFacilityMaintenance(fmsId, false, true, sampleRange());
          System.out.println("Facility maintenance schedule ID: " + fmsId);
          maintenanceService.removeFacilityMaintRequest(fmsId);
          assert (fmsId > 0);
      } finally {
          if(facility != null) {
              facilityService.removeFacility(facility.getId());
          }
      }

  }

  @Test
  public void scheduleConflictingFacilityMaintenance() throws SQLException,
          FMSException, RoomSchedulingConflictException {

      Facility facility = null;

      try {
          facility = testData.prepFacilityInDb();
          Building building = facility.getFacilityDetail().getBuildings().get(0);
          Room room = building.getRooms().get(0);
          Range<LocalDateTime> sampleRange = sampleRange();

          // Reserve our new room for some sample time range
          RoomReservation roomReservation = usageService.scheduleRoomReservation(room.getId(), sampleRange);
          assert (roomReservation.getId() > 0);

          // Now with our room scheduled, create the conflict by scheduling maintenance
          // for the same time
          assert (false == maintenanceService.scheduleFacilityMaintenance(facility.getId(),
                  true, true, sampleRange));

          // System.out.println("Facility maintenance schedule ID: " + fmsId);
      } finally {
          facilityService.removeFacility(facility.getId());
      }
  }

  @Test
  public void scheduleRoomMaintenance() throws FMSException, SQLException {

      Facility facility = null;

      try {
          facility = testData.prepFacilityInDb();
          RoomMaintenanceRequest roomMaintenanceRequest = testData.prepRoomMaintenanceRequest(facility);
          int rmsId = maintenanceService.scheduleRoomMaintenance(roomMaintenanceRequest.getId(), sampleRange());
          assert (rmsId > 0);
          System.out.println("Room maintenance schedule ID: " + rmsId);
      } finally {
          if(facility != null) {
              facilityService.removeFacility(facility.getId());
          }
      }
  }

    @Rule
    public ExpectedException schedulingConflictExpected = ExpectedException.none();

    @Test
    public void scheduleConflictingRoomMaintenance() throws SQLException,
            FMSException, RoomSchedulingConflictException {

        schedulingConflictExpected.expect(FMSException.class);

        Facility facility = null;
        try {
          facility = testData.prepFacilityInDb();
          Building building = facility.getFacilityDetail().getBuildings().get(0);
          Room room = building.getRooms().get(0);
          Range<LocalDateTime> sampleRange = sampleRange();

          // Reserve our new room for some sample time range
          RoomReservation roomReservation = usageService.scheduleRoomReservation(room.getId(), sampleRange);
          assert (roomReservation.getId() > 0);

          RoomMaintenanceSchedule roomMaintenanceSchedule = TestData.sampleRoomMaintenanceSchedule();
          int rmsId = roomMaintenanceSchedule.getId();

          int roomMaintenanceRequestId = roomMaintenanceSchedule.getRoomMaintenanceRequestId();

          // Now with our room scheduled, create the conflict by scheduling maintenance
          // for the same time
          maintenanceService.scheduleRoomMaintenance(roomMaintenanceRequestId,
                  TestData.sampleRange());

      } finally {
            if(facility != null) {
                facilityService.removeFacility(facility.getId());
            }
      }
    }

    /*

    @Test
    public void calcMaintenanceCost() throws SQLException, FMSException {
        Facility facility = null;

        try {
            facility = testData.prepFacilityInDb();
            int maintenanceHourlyRateId = testData.prepMaintenanceHourlyRate(facility);
            HashMap<String, Double> costResults = maintenanceCostCalculator.calcMaintenanceCostForFacility(facility.getId(), TestData.sampleRange());
            System.out.println("Grand total maintenance costs: " + costResults);
        } finally {
            if(facility != null) {
                facilityService.removeFacility(facility.getId());
            }
        }
    }
    */


}
