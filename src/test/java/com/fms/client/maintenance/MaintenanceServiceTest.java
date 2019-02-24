package com.fms.client.maintenance;

import com.fms.TestData;
import com.fms.model.FacilityMaintenanceRequestResult;
import org.junit.Test;

import java.util.ArrayList;

public class MaintenanceServiceTest {

    static MaintenanceService maintenanceService = new MaintenanceService();

    @Test
    public void facilityMaintenanceRequest() {
        FacilityMaintenanceRequestResult facilityMaintenanceRequest =
                maintenanceService.makeFacilityMaintRequest
                        (1, TestData.sampleMaintenanceRequest());

        System.out.println("Fac maint request -> " + facilityMaintenanceRequest.toString());
    }
}
