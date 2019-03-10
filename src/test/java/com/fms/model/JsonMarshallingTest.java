package com.fms.model;

import com.fms.TestData;
import com.fms.req_reply_api.*;//temporary solution
import org.junit.Test;

import java.io.IOException;

public class JsonMarshallingTest {

  @Test
  public void FacilityMarshalling() throws IOException {
    Facility facility = new Facility(1, "F1", "Psychological Testing");
    facility.setFacilityDetail(TestData.sampleFacilityDetail());
    Facility again = Facility.fromJson(facility.toString());
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
    Building building = TestData.sampleBuilding("B1");
    Building again = Building.fromJson(building.toString());
    boolean areEqual = again.equals(building);
    assert (areEqual);
  }

  @Test
  public void RoomMarshalling() throws IOException {
    Room room = new Room(1, 5, 201);
    Room again = room.fromJson(room.toString());
    boolean areEqual = again.equals(room);
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
