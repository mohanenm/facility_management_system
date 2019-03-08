package com.fms.client.usage;

import com.fms.TestData;
import com.fms.model.FacilityInspection;
import com.fms.model.RoomRequestResult;
import com.google.common.collect.Range;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UsageServiceTest {

  static UsageService usageService = new UsageService();

  @Test
  public void roomRequest() throws SQLException {
    RoomRequestResult roomRequestResult =
        usageService.roomRequestResult(1, TestData.sampleRoomReservation());

    System.out.println("room request -> " + roomRequestResult.toString());
  }

  @Test
  public void RoomSchedule() throws SQLException {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    boolean hasConflict =
        usageService.scheduleRoomReservation(
            1,
            Range.closed(
                LocalDateTime.of(2019, 1, 30, 7, 30), LocalDateTime.of(2019, 1, 30, 7, 50)));

    /// Based on data inserted by schema + seed script
    assert (hasConflict);

    boolean noConflict =
        usageService.scheduleRoomReservation(
            1,
            Range.closed(
                LocalDateTime.of(2019, 1, 30, 8, 1), LocalDateTime.of(2019, 1, 30, 8, 29)));

    assert (noConflict == false);
  }

  // TODO
  //    @Test
  //    public void facilityMaintenanceRequestResult() {
  //        FacilityMaintenanceRequestResult facilityMaintenanceRequest =
  //                maintenanceService.makeFacilityMaintRequest
  //                        (1, TestData.sampleMaintenanceRequest());
  //
  //        System.out.println("Fac maint request -> " + facilityMaintenanceRequest.toString());
  //    }

  @Test //currently just testing listing inspections, will add insert functionality once passes
  public void addInspectionToList() throws SQLException {
    ArrayList<FacilityInspection> listOfInspections = usageService.listInspections(1,
            Range.closed(
                    LocalDateTime.of(2018, 1, 1, 1, 1, 0, 0),
                    LocalDateTime.of(2019, 10, 1, 1, 1, 0, 0)));
    System.out.println("Inspections of facility within given range -> " + listOfInspections.toString());
  }

}
