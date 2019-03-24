package com.fms;

import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.facility.*;
import com.fms.domainLayer.inspection.FacilityInspection;
import com.fms.domainLayer.maintenance.*;
import com.fms.domainLayer.services.FacilityService;
import com.fms.domainLayer.services.MaintenanceService;
import com.fms.domainLayer.services.UsageService;
import com.fms.domainLayer.usage.RoomReservation;
import com.fms.web_req_reply_api.FacilityMaintenanceRequestResult;
import com.fms.web_req_reply_api.RoomMaintenanceRequestResult;
import com.google.common.collect.Range;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class TestData {

  MaintenanceService maintenanceService;
  FacilityService facilityService;
  UsageService usageService;

  public TestData() throws SQLException {
    try {
      maintenanceService = new MaintenanceService();
      facilityService = new FacilityService();
      usageService = new UsageService();
    } catch (SQLException e) {
      System.out.println("SQL exception: " + e.toString());
    }
  }

  public static Building sampleBuilding(String name) {
    List<IRoom> rooms = new ArrayList<>();
    rooms.add(new Room(-1, 303, 15));
    rooms.add(new Room(-1, 304, 20));
    return new Building(name, "4 Marshall Ln", "Albequerque", "NM", 66540, rooms);
  }

  public static ArrayList<IBuilding> sampleBuildings() {
    ArrayList<IBuilding> buildings = new ArrayList<>();
    buildings.add(sampleBuilding("Big1"));
    buildings.add(sampleBuilding("B2"));
    return buildings;
  }

  public static ArrayList<IBuilding> sampleDuplicateBuildings() {
    ArrayList<IBuilding> buildings = new ArrayList<>();
    buildings.add(sampleBuilding("B"));
    buildings.add(sampleBuilding("B"));
    return buildings;
  }

  public static Facility sampleFacility() {
    Facility facility = new Facility();
    facility.setId(-1);
    facility.setName("F1");
    facility.setDescription("Sample facility for Unit testing.");
    facility.setBuildings(sampleBuildings());
    return facility;
  }

  public static FacilityMaintenanceRequest sampleFacilityMaintenanceRequest() {
    FacilityMaintenanceRequest facilityMaintenanceRequest = new FacilityMaintenanceRequest();
    facilityMaintenanceRequest.setMaintenanceRequest(sampleMaintenanceRequest());
    facilityMaintenanceRequest.setId(-1);
    return facilityMaintenanceRequest;
  }

  public static FacilityInspection sampleFacilityInspection() {
    return new FacilityInspection(-1, -1, LocalDateTime.of(1990, Month.JANUARY, 8, 12, 30), false);
  }

  public static MaintenanceType sampleMaintenanceType() {
    MaintenanceType maintenanceType = new MaintenanceType();
    maintenanceType.setId(-1);
    maintenanceType.setDescription("Sample maintenance type. ");
    return maintenanceType;
  }

  public static MaintenanceHourlyRate sampleMaintenanceHourlyRate() {
    MaintenanceHourlyRate maintenanceHourlyRate = new MaintenanceHourlyRate();
    maintenanceHourlyRate.setId(-1);
    maintenanceHourlyRate.setFacilityId(-1);
    maintenanceHourlyRate.setMaintenanceTypeId(1);
    maintenanceHourlyRate.setHourlyRate(20.00);
    return maintenanceHourlyRate;
  }

  public static MaintenanceRequest sampleMaintenanceRequest() {
    MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
    maintenanceRequest.setId(-1);
    maintenanceRequest.setDescription("The sink is broken, plz fix.");
    maintenanceRequest.setMaintenanceTypeId(1);
    maintenanceRequest.setVacateRequired(false);
    maintenanceRequest.setRoutine(false);
    return maintenanceRequest;
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
    FacilityMaintenanceSchedule facilityMaintenanceSchedule = new FacilityMaintenanceSchedule();
    facilityMaintenanceSchedule.setId(-1);
    facilityMaintenanceSchedule.setFacilityMaintenanceRequestId(-1);
    facilityMaintenanceSchedule.setStart(LocalDateTime.of(1984, Month.DECEMBER, 17, 15, 30));
    facilityMaintenanceSchedule.setFinish(LocalDateTime.of(2010, Month.SEPTEMBER, 17, 4, 10));
    return facilityMaintenanceSchedule;
  }

  public static RoomMaintenanceRequest sampleRoomMaintenanceRequest() {
    RoomMaintenanceRequest roomMaintenanceRequest = new RoomMaintenanceRequest();
    roomMaintenanceRequest.setMaintenanceRequest(sampleMaintenanceRequest());
    roomMaintenanceRequest.setId(-1);
    return roomMaintenanceRequest;
  }

  public static RoomReservation sampleRoomReservation() {
    RoomReservation roomReservation = new RoomReservation();
    roomReservation.setId(-1);
    roomReservation.setRoomId(-1);
    roomReservation.setMaintenanceRequestId(-1);
    roomReservation.setStart(LocalDateTime.of(1984, Month.DECEMBER, 17, 15, 30));
    roomReservation.setFinish(LocalDateTime.of(2010, Month.SEPTEMBER, 17, 4, 10));
    return roomReservation;
  }

  public static RoomMaintenanceSchedule sampleRoomMaintenanceSchedule() {
    RoomMaintenanceSchedule roomMaintenanceSchedule = new RoomMaintenanceSchedule();
    roomMaintenanceSchedule.setId(-1);
    roomMaintenanceSchedule.setRoomMaintenanceRequestId(-1);
    roomMaintenanceSchedule.setStart(LocalDateTime.of(2019, Month.JANUARY, 13, 20, 8));
    roomMaintenanceSchedule.setFinish(LocalDateTime.of(2019, Month.JANUARY, 13, 21, 8));
    return roomMaintenanceSchedule;
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

  public IFacility prepFacilityInDb() throws FMSException {
    IFacility facility = facilityService.addNewFacility("Test Facility", "Healthcare Facility");
    return facilityService.addFacilityDetail(facility.getId(), TestData.sampleBuildings());
  }

  public RoomMaintenanceRequest prepRoomMaintenanceRequest(IFacility facility) throws FMSException {
    IBuilding building = facility.getBuildings().get(0);
    IRoom room = building.getRooms().get(0);
    return maintenanceService.makeRoomMaintRequest(
        room.getId(), TestData.sampleMaintenanceRequest());
  }

  public IFacilityMaintenanceRequest prepFacilityMaintenanceRequest(IFacility facility)
      throws FMSException {

    return maintenanceService.makeFacilityMaintRequest(
        facility.getId(), TestData.sampleMaintenanceRequest());
  }

  public int prepRoomMaintenanceSchedule(IFacility facility) throws FMSException {
    IBuilding building = facility.getBuildings().get(0);
    IRoom room = building.getRooms().get(0);
    RoomMaintenanceRequest rmr =
        maintenanceService.makeRoomMaintRequest(room.getId(), sampleMaintenanceRequest());
    return maintenanceService.scheduleRoomMaintenance(rmr.getId(), sampleRange());
  }

  public int prepMaintenanceHourlyRate(IFacility facility) throws FMSException {
    IBuilding building = facility.getBuildings().get(0);
    return maintenanceService.insertMaintenanceHourlyRate(
        facility.getId(),
        TestData.sampleMaintenanceRequest().getMaintenanceTypeId(),
        TestData.sampleMaintenanceHourlyRate().getRate());
  }
}
