package com.fms.domainLayer.facility;

import com.fms.dal.DBFacility;
import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.common.FacilityErrorCode;
import com.fms.model.*;
import com.fms.req_reply_api.GetFacilityDetailResult;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class FacilityService {

  Logger logger = LogManager.getLogger();

  DBFacility dbFacility;

  public FacilityService() throws SQLException {
    dbFacility = new DBFacility();
  }

  public ArrayList<Facility> listFacilities() {
    return dbFacility.readAllFacilities();
  }

  public IFacility addNewFacility(String name, String description) throws FMSException {
    try {
      return dbFacility.createFacility(name, description);
    } catch (SQLException e) {

      System.out.println("Caught excp: " + e.toString());
      // TODO: if(e.getErrorCode() == /** yadayda **/)
      // Here we assume the error is that the insert failed because that name is
      // already in DB and our constraint was triggered. But maybe its a different error.
      // We could possibly interrogate the errorcode to see what the real db problem is
      // and not *ASS*ume its the constraint
      throw new FMSException(FacilityErrorCode.NAMED_FACILITY_ALREADY_EXISTS,
              "Facility Names Must Be Unique");
    }
  }

  public void removeFacility(int facilityId) throws FMSException {
    try {
      dbFacility.deleteFacility(facilityId);
    } catch (SQLException e) {
      throw new FMSException(FacilityErrorCode.UNABLE_TO_DELETE_REQUEST,
              "Unable to delete facility: " + facilityId + "Exception: " + e);
    }
  }

  ///  Possible errors
  ///  - Buildings with same name
  ///  - If facility_id is a bad id not mapping to existing facility
  ///  - Any issues with insert into building, room,
  public IFacility addFacilityDetail(int facilityId, IFacilityDetail facilityDetail) throws FMSException {
    if (validBuildingNames(facilityDetail)) {
      try {
        return dbFacility.addFacilityDetail(facilityId, facilityDetail);
      } catch (SQLException e) {
        throw new FMSException(FacilityErrorCode.INSERT_FACILITY_DETAIL_FAILED,
                "Unable to add facility detail for facility: " + facilityId +
                "\nException: " + e);
      }
    } else {
      FMSException fmsException = new  FMSException(FacilityErrorCode.BUILDING_ALREADY_EXISTS,
              "Building already exists");
      logger.log(Level.ERROR, fmsException.toString());

      throw fmsException;
    }
  }

  /**
   * Count number of occurrences of each building name at the facility. Any building name that
   * appears more than once is an exception.
   *
   * @param facilityDetail
   * @return false if there are empty building names or if there are duplicates
   */
  public static boolean validBuildingNames(IFacilityDetail facilityDetail) {
    HashSet<String> buildingNames = new HashSet<>();
    Logger logger = LogManager.getLogger();

    for (IBuilding building : facilityDetail.getBuildings()) {
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
