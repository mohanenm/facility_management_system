package com.fms.client.facility;

import com.fms.model.AddFacilityResult;
import com.fms.model.Building;
import com.fms.model.FacilityDetail;
import org.junit.Test;

import com.fms.model.Facility;
import com.fms.client.facility.FacilityService;
import com.fms.TestData;


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
        System.out.println(result.toString());

        assert(insertedFacilityId > 0);
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
}