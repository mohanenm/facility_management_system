package com.fms.client.maintenance;

import com.fms.TestData;
import com.fms.model.Facility;
import org.junit.Test;

import java.util.ArrayList;

public class MaintenanceServiceTest {

    static MaintenanceService maintenanceService = new MaintenanceService();

    @Test
    public void facilityMaintenanceRequest() {
        maintenanceService.makeFacilityMaintRequest(1, TestData.sampleMaintenanceRequest());
    }
}
