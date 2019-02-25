package com.fms.client.maintenance;

import com.fms.TestData;
import com.fms.model.FacilityMaintenanceRequestResult;
import com.google.common.collect.Range;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MaintenanceServiceTest {

    static MaintenanceService maintenanceService = new MaintenanceService();

    @Test
    public void facilityMaintenanceRequest() throws SQLException {
        FacilityMaintenanceRequestResult facilityMaintenanceRequest =
                maintenanceService.makeFacilityMaintRequest
                        (1, TestData.sampleMaintenanceRequest());

        System.out.println("Fac maint request -> " + facilityMaintenanceRequest.toString());
    }

    @Test
    public void scheduleFacilityMaintenance() throws SQLException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

        boolean hasConflict = maintenanceService.scheduleFacilityMaintenance(
                1, Range.closed(
                        LocalDateTime.of(2019, 1, 30, 7, 30),
                        LocalDateTime.of(2019, 1, 30, 7, 50)));

        /// Based on data inserted by schema + seed script
        assert(hasConflict);

        boolean noConflict = maintenanceService.scheduleFacilityMaintenance(
                1, Range.closed(
                        LocalDateTime.of(2019, 1, 30, 8, 1),
                        LocalDateTime.of(2019, 1, 30, 8, 29)));

        assert(noConflict == false);

    }


    //TODO
//    @Test
//    public void facilityMaintenanceRequestResult() {
//        FacilityMaintenanceRequestResult facilityMaintenanceRequest =
//                maintenanceService.makeFacilityMaintRequest
//                        (1, TestData.sampleMaintenanceRequest());
//
//        System.out.println("Fac maint request -> " + facilityMaintenanceRequest.toString());
//    }

}
