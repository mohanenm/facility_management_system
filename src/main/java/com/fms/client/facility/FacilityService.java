package com.fms.client.facility;

import com.fms.dal.DBFacility;
import com.fms.model.*;

import javax.sql.rowset.serial.SerialException;
import java.sql.SQLException;
import java.util.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    ///  Possible errors
    ///  - Buildings with same name
    ///  - If facility_id is a bad id not mapping to existing facility
    ///  - Any issues with insert into building, room,
    public AddFacilityDetailResult addFacilityDetail(int facilityId, FacilityDetail facilityDetail) {
        if (validBuildingNames(facilityDetail)) {
            try {
               DBFacility.addFacilityDetail(facilityId, facilityDetail);
               return new AddFacilityDetailResult(null);
            } catch (SQLException e) {
                return new AddFacilityDetailResult("Unable to add facility detail to database: " +
                        e.toString());
            }

        } else {
            return new AddFacilityDetailResult("Duplicate or missing building names within facility!");
        }
    }

    /**
     * Count number of occurrences of each building name at the facility.
     * Any building name that appears more than once is an exception.
     *
     * @param facilityDetail
     * @return false if there are empty building names or if there are duplicates
     */
    public static boolean validBuildingNames(FacilityDetail facilityDetail) {
        HashSet<String> buildingNames = new HashSet<>();

        for (Building building : facilityDetail.getBuildings()) {
            Logger logger = LogManager.getLogger();
            String buildingName = building.getName();
            if (buildingName == null || buildingName.isEmpty()) {
                logger.log(Level.DEBUG, "Found empty building name");
                return false;
            }

            if (buildingNames.contains(buildingName)) {
                logger.log(Level.DEBUG, "Found duplicate building name: " + buildingName);
                return false;
            }

            buildingNames.add(buildingName);
        }

        return true;
    }

    public static GetFacilityDetailResult getFacilityInformation(int facilityId) throws SQLException{
        try {
            return DBFacility.getFacilityInformation(facilityId);
        } catch (SQLException e) {
            return new GetFacilityDetailResult("Unable to get facility information: " +
                    e.toString(), null);
        }
    }


}
