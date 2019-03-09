package com.fms.client.maintenance;

import com.fms.TestData;
import com.fms.client.common.FMSException;
import com.fms.client.facility.FacilityService;
import com.fms.client.usage.UsageService;
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

  public MaintenanceServiceTest() throws SQLException {
    maintenanceService = new MaintenanceService();
    facilityService = new FacilityService();
    usageService = new UsageService();
  }

  @Rule
  public ExpectedException maintenanceException = ExpectedException.none();

  @Test
  public void CRUDFacilityMaintenanceRequest() throws FMSException {

    MaintenanceRequest maintenanceRequest = TestData.sampleMaintenanceRequest();

    FacilityMaintenanceRequest facilityMaintenanceRequest =
            maintenanceService.makeFacilityMaintRequest(1, maintenanceRequest);

    int fmrId = facilityMaintenanceRequest.getId();

    assert(fmrId > 0);

    assert(facilityMaintenanceRequest.getMaintenanceRequest() == maintenanceRequest);

    maintenanceService.removeFacilityMaintRequest(fmrId);
  }

  @Test
  public void CRUDRoomMaintenanceRequest() throws FMSException {

    MaintenanceRequest maintenanceRequest = TestData.sampleMaintenanceRequest();

    RoomMaintenanceRequest roomMaintenanceRequest =
            maintenanceService.makeRoomMaintRequest(1, maintenanceRequest);

    int rmrId = roomMaintenanceRequest.getId();

    assert(rmrId > 0);

    assert(roomMaintenanceRequest.getMaintenanceRequest() == maintenanceRequest);

    maintenanceService.removeRoomMaintRequest(rmrId);
  }

  @Test
  public void scheduleFacilityMaintenance() throws FMSException {

    FacilityMaintenanceSchedule facilityMaintenanceSchedule = TestData.sampleFacilityMaintenanceSchedule();

    int fmsId = facilityMaintenanceSchedule.getId();

    maintenanceService.scheduleFacilityMaintenance(fmsId, false, true, sampleRange());

    System.out.println("Facility maintenance schedule ID: " + fmsId);

    maintenanceService.removeFacilityMaintRequest(fmsId);

    assert(fmsId > 0);

  }

   public Facility prepFacilityInDb() throws FMSException {
    Facility facility =
            facilityService.addNewFacility("Test Facility", "Healthcare Facility");
    return facilityService.addFacilityDetail(facility.getId(), TestData.sampleFacilityDetail());
  }

  @Test
  public void scheduleConflictingFacilityMaintenance() throws FMSException {

    Facility facility = prepFacilityInDb();
    Building building = facility.getFacilityDetail().getBuildings().get(0);
    Room room = building.getRooms().get(0);
    Range<LocalDateTime> sampleRange = sampleRange();

    // Reserve our new room for some sample time range
    assert(true == usageService.scheduleRoomReservation(room.getId(), sampleRange));

    // Now with our room scheduled, create the conflict by scheduling maintenance
    // for the same time
    assert(false == maintenanceService.scheduleFacilityMaintenance(facility.getId(),
            true, true, sampleRange));

   // System.out.println("Facility maintenance schedule ID: " + fmsId);
    facilityService.removeFacility(facility.getId());
  }


}
