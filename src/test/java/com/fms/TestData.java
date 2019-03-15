package com.fms;

import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.facility.FacilityService;
import com.fms.domainLayer.maintenance.MaintenanceService;
import com.fms.domainLayer.usage.UsageService;
import com.fms.model.*;
import com.fms.req_reply_api.FacilityMaintenanceRequestResult;
import com.fms.req_reply_api.RoomMaintenanceRequestResult;
import com.google.common.collect.Range;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


public class TestData {

  MaintenanceService maintenanceService;
  FacilityService facilityService;
  UsageService usageService;
  ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/Spring-config.xml");


  public TestData() throws SQLException {
    try {
      maintenanceService = new MaintenanceService();
      facilityService = new FacilityService();
      usageService = new UsageService();
    } catch(SQLException e) {
      System.out.println("SQL exception: " + e.toString());
    }
  }
//ToDo: create sample building using dependency injection with IRoom interface
  public IBuilding sampleBuilding() {
    List<IRoom> rooms = new ArrayList<>();
    IRoom roomA = (IRoom) context.getBean("room");
    IRoom roomB = (IRoom) context.getBean("room");
    IBuilding sampleBuilding = (IBuilding) context.getBean("building");
    roomA.setBuildingId(1);
    roomA.setCapacity(15);
    roomA.setRoomNumber(101);
    roomB.setBuildingId(1);
    roomB.setCapacity(20);
    roomB.setRoomNumber(102);
    sampleBuilding.setCity("Chicago");
    sampleBuilding.setName("B1");
    sampleBuilding.setId(1);
    sampleBuilding.setRooms(rooms);
    return sampleBuilding;
  }

  public IFacilityDetail sampleFacilityDetail() {
    List<IBuilding> buildings = new ArrayList<>();
    IBuilding sampleBuildingA = sampleBuilding();
    IBuilding sampleBuildingB = (IBuilding) context.getBean("building");
    sampleBuildingB.setId(2);
    sampleBuildingB.setName("B2");
    sampleBuildingB.setCity("Sydney");
    sampleBuildingB.setZip(60660);
    buildings.add(sampleBuildingB);
    buildings.add(sampleBuildingA);
    IFacilityDetail sampleFacilityDetail = (IFacilityDetail) context.getBean("facilityDetail");
    sampleFacilityDetail.setBuildings(buildings);
    return sampleFacilityDetail;
  }

  public IFacilityDetail sampleFacilityDetailDuplicateBuildings() {
    List<IBuilding> buildings = new ArrayList<>();
    buildings.add(sampleBuilding());
    buildings.add(sampleBuilding());
    IFacilityDetail sampleFacilityDetailDup = (IFacilityDetail) context.getBean("facilityDetail");
    sampleFacilityDetailDup.setBuildings(buildings);
    return sampleFacilityDetailDup;
  }

  public IFacility sampleFacility() {
    IFacilityDetail facilityDetail = sampleFacilityDetail();
    IFacility sampleFacility = (IFacility) context.getBean("facility");
    sampleFacility.setDescription("Sample facility for unit testing.");
    sampleFacility.setName("F1");
    sampleFacility.setFacilityDetail(facilityDetail);
    return sampleFacility;
  }

  public FacilityMaintenanceRequest sampleFacilityMaintenanceRequest() {
    return new FacilityMaintenanceRequest(-1, sampleMaintenanceRequest());
  }

  public FacilityInspection sampleFacilityInspection() {
    return new FacilityInspection(-1, -1, LocalDateTime.of(1990, Month.JANUARY, 8, 12, 30), false);
  }

  public MaintenanceType sampleMaintenanceType() {
    return new MaintenanceType(-1, "Sample maintenance type");
  }

  public MaintenanceHourlyRate sampleMaintenanceHourlyRate() {
    return new MaintenanceHourlyRate(-1, -1, 1, 20.00);
  }

  public MaintenanceRequest sampleMaintenanceRequest() {
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

  public RoomMaintenanceRequest sampleRoomMaintenanceRequest() {
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

  public FacilityMaintenanceRequestResult sampleFacilityMaintenanceRequestResult() {
    return new FacilityMaintenanceRequestResult(
        sampleFacilityMaintenanceRequest(),
        "The Facility Maintenance Request you have submitted does not correctly reference an existing facility ");
  }

  public RoomMaintenanceRequestResult sampleRoomMaintenanceRequestResult() {
    return new RoomMaintenanceRequestResult(
        sampleRoomMaintenanceRequest(),
        "The Room Maintenance Request does not correctly reference an existing room ");
  }

  public IFacility prepFacilityInDb() throws FMSException {
    IFacility facility =
            facilityService.addNewFacility("Test Facility", "Healthcare Facility");
    return facilityService.addFacilityDetail(facility.getId(), testData.sampleFacilityDetail());
  }

  public RoomMaintenanceRequest prepRoomMaintenanceRequest(Facility facility) throws FMSException {
    IBuilding building = facility.getFacilityDetail().getBuildings().get(0);
    IRoom room = building.getRooms().get(0);
    return maintenanceService.makeRoomMaintRequest(room.getId(), this.sampleMaintenanceRequest());
  }

  public FacilityMaintenanceRequest prepFacilityMaintenanceRequest(Facility facility) throws FMSException {
    return maintenanceService.makeFacilityMaintRequest(facility.getId(), this.sampleMaintenanceRequest());
  }

  public int prepRoomMaintenanceSchedule(Facility facility) throws FMSException {
    IBuilding building = facility.getFacilityDetail().getBuildings().get(0);
    IRoom room = building.getRooms().get(0);
    RoomMaintenanceRequest rmr = maintenanceService.makeRoomMaintRequest(room.getId(),sampleMaintenanceRequest());
    return maintenanceService.scheduleRoomMaintenance(rmr.getId(), sampleRange());
  }

  public int prepMaintenanceHourlyRate(Facility facility) throws FMSException {
    IBuilding building = facility.getFacilityDetail().getBuildings().get(0);
    return maintenanceService.insertMaintenanceHourlyRate(facility.getId(),
            this.sampleMaintenanceRequest().getMaintenanceTypeId(),
            this.sampleMaintenanceHourlyRate().getRate());
  }
}
