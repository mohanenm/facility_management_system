package com.fms.client.facility;

import com.fms.model.*;
import org.junit.Test;

import com.fms.client.facility.FacilityService;
import com.fms.TestData;


import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FacilityServiceTest {

    static FacilityService facilityService = new FacilityService();

    @Test
    public void listFacilities() {
        ArrayList<Facility> facilityArrayList = facilityService.listFacilities();
        System.out.print("All Facilities in list: " + facilityArrayList.toString());
    }

    //Single test to create, read, and update our facility
    @Test
    public void CRUDFacility() {
        AddFacilityResult result = facilityService
                .addNewFacility("Test Facility", "Healthcare Facility");

        int insertedFacilityId = result.getFacility().getId();
        assert(insertedFacilityId > 0);

        System.out.println(result.toString());

        AddFacilityDetailResult facilityDetailResult = facilityService
                .addFacilityDetail(insertedFacilityId, TestData.sampleFacilityDetail());

        System.out.println("Inserted -> " + facilityDetailResult);

        assert(facilityDetailResult.getErrorMessage() == null ||
                facilityDetailResult.getErrorMessage().isEmpty());

        assert (true == facilityService.removeFacility(insertedFacilityId));


        System.out.println("CHECK AGAIN");
        listFacilities();

    }

    @Test
    public void buildingDuplicatesFails() {
        FacilityDetail sample = TestData.sampleFacilityDetail();
        ArrayList<Building> buildings = sample.getBuildings();
        buildings.add(buildings.get(0));

        assert(false == FacilityService.validBuildingNames(sample));
    }

    @Test
    public void getFacilityInformation() throws SQLException {
        facilityService.getFacilityInformation(1);
    }
}