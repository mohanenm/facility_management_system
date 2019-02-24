package com.fms.client.facility;

import com.fms.dal.DBFacility;
import com.fms.model.AddFacilityResult;
import com.fms.model.Facility;

import javax.sql.rowset.serial.SerialException;
import java.sql.SQLException;
import java.util.ArrayList;

public class FacilityService {

    public ArrayList<Facility> listFacilities() {
        return DBFacility.readAllFacilities();
    }

    public AddFacilityResult addNewFacility(String name, String description) {
        try {
            return new AddFacilityResult(null, DBFacility.createFacility(name, description));
        } catch (SQLException e) {

            System.out.println("Caught excp: " + e.toString());

            return new AddFacilityResult("Failed to insert new facility: " + name + " ->" + e.toString(),
                    null);
        }
    }



    public boolean removeFacility(int facilityId) {
        return DBFacility.deleteFacility(facilityId);
    }

}
