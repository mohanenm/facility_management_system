package com.fms.client.facility;

import com.fms.client.common.FacilityErrorCode;
import com.fms.client.common.FacilityServiceException;
import com.fms.dal.DBFacility;
import com.fms.model.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class FacilityService {

  DBFacility dbFacility = new DBFacility();

  public ArrayList<Facility> listFacilities() {
    return dbFacility.readAllFacilities();
  }

  public AddFacilityResult addNewFacility(String name, String description) {
    try {
      return new AddFacilityResult(null, dbFacility.createFacility(name, description));
    } catch (SQLException e) {

      System.out.println("Caught excp: " + e.toString());

      return new AddFacilityResult(
          "Failed to insert new facility: " + name + " ->" + e.toString(), null);
    }
  }

  public boolean removeFacility(int facilityId) {
    return dbFacility.deleteFacility(facilityId);
  }

  ///  Possible errors
  ///  - Buildings with same name
  ///  - If facility_id is a bad id not mapping to existing facility
  ///  - Any issues with insert into building, room,
  public void addFacilityDetail(int facilityId, FacilityDetail facilityDetail) throws FacilityServiceException {
    if (validBuildingNames(facilityDetail)) {
      try {
        dbFacility.addFacilityDetail(facilityId, facilityDetail);
      } catch (SQLException e) {
        throw new FacilityServiceException(FacilityErrorCode.INSERT_FACILITY_DETAIL_FAILED,
                "Unable to add facility detail for facility: " + facilityId +
                "\nException: " + e);
      }
    } else {
      throw new FacilityServiceException(FacilityErrorCode.BUILDING_ALREADY_EXISTS,
              "Building already exists");
    }
  }

  /**
   * Count number of occurrences of each building name at the facility. Any building name that
   * appears more than once is an exception.
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
        logger.log(Level.ERROR, "Found empty building name");
        return false;
      }

      if (buildingNames.contains(buildingName)) {
        logger.log(Level.ERROR, "Found duplicate building name: " + buildingName);
        return false;
      }

      buildingNames.add(buildingName);
    }

    return true;
  }

  public GetFacilityDetailResult getFacilityInformation(int facilityId) throws SQLException {
    try {
      return dbFacility.getFacilityInformation(facilityId);
    } catch (SQLException e) {
      return new GetFacilityDetailResult(
          "Unable to get facility information: " + e.toString(), null);
    }
  }
}
