package com.fms.client.facility;

import com.fms.dal.DBFacility;
import com.fms.model.Facility;
import java.util.ArrayList;

public class FacilityService {

    public ArrayList<Facility> listFacilities() {
        return DBFacility.readAllFacilities();
    }

    public Facility addNewFacility(String name, String description) {
        return new Facility(1, name, description);
    }

}
