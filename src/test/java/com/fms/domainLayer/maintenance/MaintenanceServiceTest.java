package com.fms.domainLayer.maintenance;

import com.fms.TestData;
import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.common.RoomSchedulingConflictException;
import com.fms.domainLayer.facility.IBuilding;
import com.fms.domainLayer.facility.IFacility;
import com.fms.domainLayer.facility.IRoom;
import com.fms.domainLayer.services.FacilityService;
import com.fms.domainLayer.services.MaintenanceService;
import com.fms.domainLayer.services.UsageService;
import com.fms.domainLayer.usage.RoomReservation;
import com.google.common.collect.Range;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;

import static com.fms.TestData.sampleRange;
import static junit.framework.TestCase.assertEquals;

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

  @Rule
  public ExpectedException scheduleConflictException = ExpectedException.none();

  @Test
  public void CRUDFacilityMaintenanceRequest() throws FMSException {

      IFacility facility = null;
      try {
          facility = testData.prepFacilityInDb();
          IMaintenanceRequest maintenanceRequest = testData.sampleMaintenanceRequest();

          IFacilityMaintenanceRequest facilityMaintenanceRequest =
                  maintenanceService.makeFacilityMaintRequest(facility.getId(), maintenanceRequest);

          IMaintenanceRequest returned = facilityMaintenanceRequest.getMaintenanceRequest();
          assertEquals(maintenanceRequest.getMaintenanceTypeId(),
                  returned.getMaintenanceTypeId());
          assertEquals(maintenanceRequest.getDescription(), returned.getDescription());
      } finally {
          if(facility != null) {
              facilityService.removeFacility(facility.getId());
          }
      }
  }

  @Test
  public void CRUDRoomMaintenanceRequest() throws FMSException {

      IFacility facility = null;
        try {
            facility = testData.prepFacilityInDb();

            IBuilding building = facility.getBuildings().get(0);
            IRoom room = building.getRooms().get(0);

            IMaintenanceRequest maintenanceRequest = testData.sampleMaintenanceRequest();

            RoomMaintenanceRequest roomMaintenanceRequest =
                    maintenanceService.makeRoomMaintRequest(room.getId(), maintenanceRequest);

            int rmrId = roomMaintenanceRequest.getId();

            assert (rmrId > 0);

            IMaintenanceRequest fromDb = roomMaintenanceRequest.getMaintenanceRequest();
            MaintenanceRequest patchedId = new MaintenanceRequest();
            patchedId.setDescription(fromDb.getDescription());
            patchedId.setMaintenanceTypeId(fromDb.getMaintenanceTypeId());
            patchedId.setVacateRequired(fromDb.isVacateRequired());
            patchedId.setRoutine(fromDb.isRoutine());
            patchedId.setId(-1);

            assert (patchedId.equals(maintenanceRequest));
            maintenanceService.removeRoomMaintRequest(rmrId);
        } finally {
            if(facility != null)
                facilityService.removeFacility(facility.getId());
        }
  }

  @Test
  public void scheduleFacilityMaintenance() throws FMSException {
//ToDo:
//      IFacility facility = null;
//      try {
//          facility = testData.prepFacilityInDb();
//          FacilityMaintenanceSchedule facilityMaintenanceSchedule = testData.sampleFacilityMaintenanceSchedule();
//          int fmsId = facilityMaintenanceSchedule.getId();
//
//          maintenanceService.scheduleFacilityMaintenance(fmsId, false, true, sampleRange());
//          System.out.println("Facility maintenance schedule ID: " + fmsId);
//          maintenanceService.removeFacilityMaintRequest(fmsId);
//          assert (fmsId > 0);
//      } finally {
//          if(facility != null) {
//              facilityService.removeFacility(facility.getId());
//          }
//      }

  }

  @Test
  public void scheduleConflictingFacilityMaintenance() throws SQLException,
          FMSException, RoomSchedulingConflictException {

      IFacility facility = null;

      schedulingConflictExpected.expect(FMSException.class);

      try {
          facility = testData.prepFacilityInDb();
          IBuilding building = facility.getBuildings().get(0);
          IRoom room = building.getRooms().get(0);
          Range<LocalDateTime> sampleRange = sampleRange();

          // Reserve our new room for some sample time range
          RoomReservation roomReservation = usageService.scheduleRoomReservation(room.getId(), sampleRange);
          assert roomReservation.getId() > 0;


          IFacilityMaintenanceRequest facilityMaintenanceRequest = TestData.sampleFacilityMaintenanceRequest();

          facilityMaintenanceRequest = maintenanceService
                  .makeFacilityMaintRequest(facility.getId(),
                          facilityMaintenanceRequest.getMaintenanceRequest());

          assert(facilityMaintenanceRequest.getId() > 0);


          // Now with our room scheduled, create the conflict by scheduling maintenance
          // for the same time
          maintenanceService.scheduleFacilityMaintenance(facilityMaintenanceRequest.getId(),
                  true, true, sampleRange);
      } finally {
          facilityService.removeFacility(facility.getId());
      }
  }

  @Test
  public void scheduleRoomMaintenance() throws FMSException, SQLException {

      IFacility facility = null;

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

        IFacility facility = null;
        try {
          facility = testData.prepFacilityInDb();
          IBuilding building = facility.getBuildings().get(0);
          IRoom room = building.getRooms().get(0);
          Range<LocalDateTime> sampleRange = sampleRange();

          // Reserve our new room for some sample time range
          RoomReservation roomReservation = usageService.scheduleRoomReservation(room.getId(), sampleRange);
          assert (roomReservation.getId() > 0);

          IRoomMaintenanceRequest roomMaintenanceRequest = TestData.sampleRoomMaintenanceRequest();

          roomMaintenanceRequest = maintenanceService
                  .makeRoomMaintRequest(room.getId(), roomMaintenanceRequest.getMaintenanceRequest());

          assert(roomMaintenanceRequest.getId() > 0);

          maintenanceService
                  .scheduleRoomMaintenance(roomMaintenanceRequest.getId(), sampleRange);

      } finally {
            if(facility != null) {
                facilityService.removeFacility(facility.getId());
            }
      }
    }

    @Test
    public void calcMaintenanceCost() throws SQLException, FMSException {
        IFacility facility = null;

        try {
            facility = testData.prepFacilityInDb();
            testData.prepMaintenanceHourlyRate(facility);
            testData.prepRoomMaintenanceRequest(facility);
            testData.prepRoomMaintenanceSchedule(facility);
            HashMap<String, Double> costResults = maintenanceCostCalculator
                    .calcMaintenanceCostForFacility(facility.getId(), testData.sampleRange());
            System.out.println("Grand total maintenance costs: " + costResults);
        } finally {
            if(facility != null) {
                facilityService.removeFacility(facility.getId());
            }
        }
    }



}
