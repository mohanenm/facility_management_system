package com.fms.model;

import com.fms.TestData;
import com.fms.domainLayer.facility.*;
import com.fms.domainLayer.inspection.FacilityInspection;
import com.fms.domainLayer.maintenance.*;
import com.fms.domainLayer.services.FacilityService;
import com.fms.domainLayer.services.MaintenanceService;
import com.fms.domainLayer.services.UsageService;
import com.fms.domainLayer.usage.RoomReservation;
import com.fms.web_req_reply_api.*;//temporary solution
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

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
    IFacility facility = TestData.sampleFacility();
    facility.setFacilityDetail(TestData.sampleFacilityDetail());
    IFacility again = Facility.fromJson(facility.toString());
    boolean areEqual = again.equals(facility);
    assert (areEqual);
  }

  @Test
  public void FacilityDetailMarshalling() throws IOException {
    FacilityDetail facilityDetail = TestData.sampleFacilityDetail();
    FacilityDetail again = FacilityDetail.fromJson(facilityDetail.toString());
    boolean areEqual = again.equals(facilityDetail);
    assert (areEqual);
  }

  @Test
  public void FacilityInspectionMarshalling() throws IOException {
    FacilityInspection facilityInspection = TestData.sampleFacilityInspection();
    FacilityInspection again = FacilityInspection.fromJson(facilityInspection.toString());
    boolean areEqual = again.equals(facilityInspection);
    assert (areEqual);
  }

  @Test
  public void GetFacilityDetailResultMarshalling() throws IOException {
    GetFacilityDetailResult getFacilityDetailResult =
        new GetFacilityDetailResult(null, TestData.sampleFacility());
    GetFacilityDetailResult again =
        GetFacilityDetailResult.fromJson(getFacilityDetailResult.toString());
    boolean areEqual = again.equals(getFacilityDetailResult);

    System.out.println("GetFacilityDetailResult -> " + getFacilityDetailResult.toString());
    assert (areEqual);
  }

  @Test
  public void BuildingMarshalling() throws IOException {
    IBuilding building = TestData.sampleBuilding("B1");
    IBuilding again = Building.fromJson(building.toString());
    boolean areEqual = again.equals(building);
    assert (areEqual);
  }

  @Test
  public void RoomMarshalling() throws IOException {
    Room room = new Room(1, 5, 201);
    Room again = room.fromJson(room.toString());
    boolean areEqual = again.equals(room);
    System.out.println("Room Info ->" + room);
    assert (areEqual);
  }

  @Test
  public void MaintenanceTypeMarshalling() throws IOException {
    MaintenanceType maintenanceType = TestData.sampleMaintenanceType();
    MaintenanceType again = MaintenanceType.fromJson(maintenanceType.toString());
    boolean areEqual = again.equals(maintenanceType);
    System.out.println("Maintenance type -> " + maintenanceType);
    assert (areEqual);
  }

  @Test
  public void MaintenanceHourlyRateMarshalling() throws IOException {
    MaintenanceHourlyRate maintenanceHourlyRate = TestData.sampleMaintenanceHourlyRate();
    MaintenanceHourlyRate again = MaintenanceHourlyRate.fromJson(maintenanceHourlyRate.toString());
    boolean areEqual = again.equals(maintenanceHourlyRate);
    System.out.println("Maintenance rate -> " + maintenanceHourlyRate);
    assert (areEqual);
  }

  @Test
  public void MaintenanceRequestMarshalling() throws IOException {
    MaintenanceRequest maintenanceRequest = TestData.sampleMaintenanceRequest();
    MaintenanceRequest again = MaintenanceRequest.fromJson(maintenanceRequest.toString());
    boolean areEqual = again.equals(maintenanceRequest);
    System.out.println("Maintenance request -> " + maintenanceRequest);
    assert (areEqual);
  }

  @Test
  public void FacilityMaintenanceRequestMarshalling() throws IOException {
    FacilityMaintenanceRequest facilityMaintenanceRequest =
        TestData.sampleFacilityMaintenanceRequest();
    FacilityMaintenanceRequest again =
        FacilityMaintenanceRequest.fromJson(facilityMaintenanceRequest.toString());
    boolean areEqual = again.equals(facilityMaintenanceRequest);
    System.out.println("Facility Maintenance Request -> " + facilityMaintenanceRequest);
    assert (areEqual);
  }

//  @Test
//  public void FacilityMaintenanceRequestResultMarshalling() throws IOException {
//    FacilityMaintenanceRequestResult facilityMaintenanceRequestResult =
//        TestData.sampleFacilityMaintenanceRequestResult();
//    FacilityMaintenanceRequestResult again =
//        FacilityMaintenanceRequestResult.fromJson(facilityMaintenanceRequestResult.toString());
//    boolean areEqual = again.equals(facilityMaintenanceRequestResult);
//    System.out.println("Facility Maintenance Request Result-> " + facilityMaintenanceRequestResult);
//    assert (areEqual);
//  }

  @Test
  public void FacilityMaintenanceScheduleMarshalling() throws IOException {
    FacilityMaintenanceSchedule facilityMaintenanceSchedule =
        TestData.sampleFacilityMaintenanceSchedule();
    FacilityMaintenanceSchedule again =
        FacilityMaintenanceSchedule.fromJson(facilityMaintenanceSchedule.toString());
    boolean areEqual = again.equals(facilityMaintenanceSchedule);
    System.out.println("Facility Maintenance Schedule -> " + facilityMaintenanceSchedule);
    assert (areEqual);
  }

  @Test
  public void RoomMaintenanceRequestMarshalling() throws IOException {
    RoomMaintenanceRequest roomMaintenanceRequest = TestData.sampleRoomMaintenanceRequest();
    RoomMaintenanceRequest again =
        RoomMaintenanceRequest.fromJson(roomMaintenanceRequest.toString());
    boolean areEqual = again.equals(roomMaintenanceRequest);
    System.out.println("Room Maintenance request -> " + roomMaintenanceRequest);
    assert (areEqual);
  }

  @Test
  public void RoomMaintenanceRequestResultMarshalling() throws IOException {
    RoomMaintenanceRequestResult roomMaintenanceRequestResult =
        TestData.sampleRoomMaintenanceRequestResult();
    RoomMaintenanceRequestResult again =
        RoomMaintenanceRequestResult.fromJson(roomMaintenanceRequestResult.toString());
    boolean areEqual = again.equals(roomMaintenanceRequestResult);
    System.out.println("Room Maintenance request result -> " + roomMaintenanceRequestResult);
    assert (areEqual);
  }

  @Test
  public void RoomReservationMarshalling() throws IOException {
    RoomReservation roomReservation = TestData.sampleRoomReservation();
    RoomReservation again = RoomReservation.fromJson(roomReservation.toString());
    boolean areEqual = again.equals(roomReservation);
    System.out.println("Room Reservation request -> " + roomReservation);
    assert (areEqual);
  }

  @Test
  public void RoomMaintenanceScheduleMarshalling() throws IOException {
    RoomMaintenanceSchedule roomMaintenanceSchedule = TestData.sampleRoomMaintenanceSchedule();
    RoomMaintenanceSchedule again =
        RoomMaintenanceSchedule.fromJson(roomMaintenanceSchedule.toString());
    boolean areEqual = again.equals(roomMaintenanceSchedule);
    System.out.println("Room Maintenance Schedule request -> " + roomMaintenanceSchedule);
    assert (areEqual);
  }
}
