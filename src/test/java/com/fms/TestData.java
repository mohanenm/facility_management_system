package com.fms;

import com.fms.service.common.FMSException;
import com.fms.service.facility.FacilityService;
import com.fms.service.maintenance.MaintenanceService;
import com.fms.service.usage.UsageService;
import com.fms.model.*;
import com.fms.req_reply_api.FacilityMaintenanceRequestResult;
import com.fms.req_reply_api.RoomMaintenanceRequestResult;
import com.google.common.collect.Range;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class TestData {

  MaintenanceService maintenanceService;
  FacilityService facilityService;
  UsageService usageService;

  public TestData() throws SQLException {
    try {
      maintenanceService = new MaintenanceService();
      facilityService = new FacilityService();
      usageService = new UsageService();
    } catch(SQLException e) {
      System.out.println("SQL exception: " + e.toString());
    }
  }

  public static Building sampleBuilding(String name) {
    ArrayList<Room> rooms = new ArrayList<>();
    rooms.add(new Room(-1, 303, 15));
    rooms.add(new Room(-1, 304, 20));
    return new Building(name, "4 Marshall Ln", "Albequerque", "NM", 66540, rooms);
  }

  public static FacilityDetail sampleFacilityDetail() {
    ArrayList<Building> buildings = new ArrayList<>();
    buildings.add(sampleBuilding("Big1"));
    buildings.add(sampleBuilding("B2"));
    return new FacilityDetail(buildings);
  }

  public static FacilityDetail sampleFacilityDetailDuplicateBuildings() {
    ArrayList<Building> buildings = new ArrayList<>();
    buildings.add(sampleBuilding("B"));
    buildings.add(sampleBuilding("B"));
    return new FacilityDetail(buildings);
  }

  public static Facility sampleFacility() {
    return new Facility(-1, "F1", "sample facility for test", sampleFacilityDetail());
  }

  public static FacilityMaintenanceRequest sampleFacilityMaintenanceRequest() {
    return new FacilityMaintenanceRequest(-1, sampleMaintenanceRequest());
  }

  public static FacilityInspection sampleFacilityInspection() {
    return new FacilityInspection(-1, -1, LocalDateTime.of(1990, Month.JANUARY, 8, 12, 30), false);
  }

  public static MaintenanceType sampleMaintenanceType() {
    return new MaintenanceType(-1, "Sample maintenance type");
  }

  public static MaintenanceHourlyRate sampleMaintenanceHourlyRate() {
    return new MaintenanceHourlyRate(-1, -1, 1, 20.00);
  }

  public static MaintenanceRequest sampleMaintenanceRequest() {
    return new MaintenanceRequest(-1, 1, "The sink is broken, plz fix.", false, false);
  }

  public static Range<LocalDateTime> sampleRange() {
    LocalDateTime lower = LocalDateTime.of(1984, Month.DECEMBER, 17, 15, 30);
    LocalDateTime upper = LocalDateTime.of(2010, Month.SEPTEMBER, 17, 4, 10);
    return Range.open(lower, upper);
  }

  public static Range<LocalDateTime> sampleRangeConflicting() {
    LocalDateTime lower = LocalDateTime.of(1984, Month.DECEMBER, 17, 15, 30);
    LocalDateTime upper = LocalDateTime.of(2010, Month.SEPTEMBER, 17, 4, 10);
    return Range.open(lower, upper);
  }

  public static FacilityMaintenanceSchedule sampleFacilityMaintenanceSchedule() {
    return new FacilityMaintenanceSchedule(
        -1,
        -1,
        LocalDateTime.of(1984, Month.DECEMBER, 17, 15, 30),
        LocalDateTime.of(2010, Month.SEPTEMBER, 17, 4, 10));
  }

  public static RoomMaintenanceRequest sampleRoomMaintenanceRequest() {
    return new RoomMaintenanceRequest(-1, sampleMaintenanceRequest());
  }

  public static RoomReservation sampleRoomReservation() {
    return new RoomReservation(
        -1,
        -1,
        LocalDateTime.of(1984, Month.DECEMBER, 17, 15, 30),
        LocalDateTime.of(2010, Month.SEPTEMBER, 17, 4, 10),
        -1);
  }

  public static RoomMaintenanceSchedule sampleRoomMaintenanceSchedule() {
    return new RoomMaintenanceSchedule(
        -1,
        -1,
        LocalDateTime.of(2019, Month.JANUARY, 13, 20, 8),
        LocalDateTime.of(2019, Month.JANUARY, 13, 21, 8));
  }

  public static FacilityMaintenanceRequestResult sampleFacilityMaintenanceRequestResult() {
    return new FacilityMaintenanceRequestResult(
        sampleFacilityMaintenanceRequest(),
        "The Facility Maintenance Request you have submitted does not correctly reference an existing facility ");
  }

  public static RoomMaintenanceRequestResult sampleRoomMaintenanceRequestResult() {
    return new RoomMaintenanceRequestResult(
        sampleRoomMaintenanceRequest(),
        "The Room Maintenance Request does not correctly reference an existing room ");
  }

  public Facility prepFacilityInDb() throws FMSException {
    Facility facility =
            facilityService.addNewFacility("Test Facility", "Healthcare Facility");
    return facilityService.addFacilityDetail(facility.getId(), TestData.sampleFacilityDetail());
  }

  public RoomMaintenanceRequest prepRoomMaintenanceRequest(Facility facility) throws FMSException {
    Building building = facility.getFacilityDetail().getBuildings().get(0);
    Room room = building.getRooms().get(0);
    return maintenanceService.makeRoomMaintRequest(room.getId(), TestData.sampleMaintenanceRequest());
  }

  public FacilityMaintenanceRequest prepFacilityMaintenanceRequest(Facility facility) throws FMSException {
    return maintenanceService.makeFacilityMaintRequest(facility.getId(), TestData.sampleMaintenanceRequest());
  }

  public int prepRoomMaintenanceSchedule(Facility facility) throws FMSException {
    Building building = facility.getFacilityDetail().getBuildings().get(0);
    Room room = building.getRooms().get(0);
    RoomMaintenanceRequest rmr = maintenanceService.makeRoomMaintRequest(room.getId(),sampleMaintenanceRequest());
    return maintenanceService.scheduleRoomMaintenance(rmr.getId(), sampleRange());
  }

  public int prepMaintenanceHourlyRate(Facility facility) throws FMSException {
    Building building = facility.getFacilityDetail().getBuildings().get(0);
    return maintenanceService.insertMaintenanceHourlyRate(facility.getId(),
            TestData.sampleMaintenanceRequest().getMaintenanceTypeId(),
            TestData.sampleMaintenanceHourlyRate().getRate());
  }
}
