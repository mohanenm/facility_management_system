package com.fms.model;

import static org.junit.Assert.assertEquals;

import com.fms.TestData;
import com.fms.domainLayer.facility.*;
import com.fms.domainLayer.inspection.FacilityInspection;
import com.fms.domainLayer.maintenance.*;
import com.fms.domainLayer.services.FacilityService;
import com.fms.domainLayer.services.MaintenanceService;
import com.fms.domainLayer.services.UsageService;
import com.fms.domainLayer.usage.RoomReservation;
import com.fms.web_req_reply_api.GetFacilityDetailResult;
import com.fms.web_req_reply_api.RoomMaintenanceRequestResult;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.Test;

public class JsonMarshallingTest {

  MaintenanceService maintenanceService;
  FacilityService facilityService;
  UsageService usageService;
  MaintenanceCostCalculator maintenanceCostCalculator;

  public JsonMarshallingTest() throws SQLException {
    maintenanceService = new MaintenanceService();
    facilityService = new FacilityService();
    usageService = new UsageService();
    maintenanceCostCalculator = new MaintenanceCostCalculator();
  }

  @Test
  public void FacilityMarshalling() throws IOException {
    Facility facility = TestData.sampleFacility();
    IFacility again = Facility.fromJson(facility.toString());
    assertEquals(facility, again);
  }

  @Test
  public void FacilityInspectionMarshalling() throws IOException {
    FacilityInspection facilityInspection = TestData.sampleFacilityInspection();
    FacilityInspection again = FacilityInspection.fromJson(facilityInspection.toString());
    assertEquals(facilityInspection, again);
  }

  @Test
  public void GetFacilityDetailResultMarshalling() throws IOException {
    GetFacilityDetailResult getFacilityDetailResult =
        new GetFacilityDetailResult(null, TestData.sampleFacility());
    GetFacilityDetailResult again =
        GetFacilityDetailResult.fromJson(getFacilityDetailResult.toString());
    assertEquals(getFacilityDetailResult, again);
  }

  @Test
  public void BuildingMarshalling() throws IOException {
    IBuilding building = TestData.sampleBuilding("B1");
    IBuilding again = Building.fromJson(building.toString());
    System.out.println("BUILDING -> " + again.toString());
    assertEquals(building, again);
  }

  @Test
  public void RoomMarshalling() throws IOException {
    Room room = new Room(1, 5, 201);
    Room again = room.fromJson(room.toString());
    assertEquals(room, again);
  }

  @Test
  public void MaintenanceTypeMarshalling() throws IOException {
    MaintenanceType maintenanceType = TestData.sampleMaintenanceType();
    MaintenanceType again = MaintenanceType.fromJson(maintenanceType.toString());
    assertEquals(maintenanceType, again);
  }

  @Test
  public void MaintenanceHourlyRateMarshalling() throws IOException {
    MaintenanceHourlyRate maintenanceHourlyRate = TestData.sampleMaintenanceHourlyRate();
    MaintenanceHourlyRate again = MaintenanceHourlyRate.fromJson(maintenanceHourlyRate.toString());
    assertEquals(maintenanceHourlyRate, again);
  }

  @Test
  public void MaintenanceRequestMarshalling() throws IOException {
    MaintenanceRequest maintenanceRequest = TestData.sampleMaintenanceRequest();
    MaintenanceRequest again = MaintenanceRequest.fromJson(maintenanceRequest.toString());
    assertEquals(maintenanceRequest, again);
  }

  @Test
  public void FacilityMaintenanceRequestMarshalling() throws IOException {
    FacilityMaintenanceRequest facilityMaintenanceRequest =
        TestData.sampleFacilityMaintenanceRequest();
    FacilityMaintenanceRequest again =
        FacilityMaintenanceRequest.fromJson(facilityMaintenanceRequest.toString());
    assertEquals(facilityMaintenanceRequest, again);
  }

  //  @Test
  //  public void FacilityMaintenanceRequestResultMarshalling() throws IOException {
  //    FacilityMaintenanceRequestResult facilityMaintenanceRequestResult =
  //        TestData.sampleFacilityMaintenanceRequestResult();
  //    FacilityMaintenanceRequestResult again =
  //        FacilityMaintenanceRequestResult.fromJson(facilityMaintenanceRequestResult.toString());
  //    boolean areEqual = again.equals(facilityMaintenanceRequestResult);
  //    System.out.println("Facility Maintenance Request Result-> " +
  // facilityMaintenanceRequestResult);
  //    assert (areEqual);
  //  }

  @Test
  public void FacilityMaintenanceScheduleMarshalling() throws IOException {
    FacilityMaintenanceSchedule facilityMaintenanceSchedule =
        TestData.sampleFacilityMaintenanceSchedule();
    FacilityMaintenanceSchedule again =
        FacilityMaintenanceSchedule.fromJson(facilityMaintenanceSchedule.toString());
    assertEquals(facilityMaintenanceSchedule, again);
  }

  @Test
  public void RoomMaintenanceRequestMarshalling() throws IOException {
    RoomMaintenanceRequest roomMaintenanceRequest = TestData.sampleRoomMaintenanceRequest();
    RoomMaintenanceRequest again =
        RoomMaintenanceRequest.fromJson(roomMaintenanceRequest.toString());
    assertEquals(roomMaintenanceRequest, again);
  }

  @Test
  public void RoomMaintenanceRequestResultMarshalling() throws IOException {
    RoomMaintenanceRequestResult roomMaintenanceRequestResult =
        TestData.sampleRoomMaintenanceRequestResult();
    RoomMaintenanceRequestResult again =
        RoomMaintenanceRequestResult.fromJson(roomMaintenanceRequestResult.toString());
    assertEquals(roomMaintenanceRequestResult, again);
  }

  @Test
  public void RoomReservationMarshalling() throws IOException {
    RoomReservation roomReservation = TestData.sampleRoomReservation();
    RoomReservation again = RoomReservation.fromJson(roomReservation.toString());
    assertEquals(roomReservation, again);
  }

  @Test
  public void RoomMaintenanceScheduleMarshalling() throws IOException {
    RoomMaintenanceSchedule roomMaintenanceSchedule = TestData.sampleRoomMaintenanceSchedule();
    RoomMaintenanceSchedule again =
        RoomMaintenanceSchedule.fromJson(roomMaintenanceSchedule.toString());
    assertEquals(roomMaintenanceSchedule, again);
  }
}
