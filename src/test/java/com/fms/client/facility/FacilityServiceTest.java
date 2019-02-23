package com.fms.client.facility;

import org.junit.Test;

import com.fms.model.Facility;
import com.fms.client.facility.FacilityService;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FacilityServiceTest {

    static FacilityService facilityService = new FacilityService();

    @Test
    public void listFacilities() {
        ArrayList<Facility> facilityArrayList = facilityService.listFacilities();
        System.out.print("All Facilities in list: " + facilityArrayList.toString());
    }

    @Test
    public void addNewFacility() {

        Facility facility = facilityService.addNewFacility("F1", "Healthcare Facility");

        System.out.println("Added :" + facility.toString());

        assert(facility.getId() > 0);
        assertEquals("F1", facility.getName());
        assertEquals("Healthcare Facility", facility.getDescription());
    }
}