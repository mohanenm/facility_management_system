package com.fms.client.facility;

import com.fms.TestData;
import com.fms.client.common.FacilityServiceException;
import com.fms.model.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;
import java.util.ArrayList;

public class FacilityServiceTest {

  static FacilityService facilityService = new FacilityService();

  @Test
  public void listFacilities() {
    ArrayList<Facility> facilityArrayList = facilityService.listFacilities();
    System.out.print("All Facilities in list: " + facilityArrayList.toString());
  }

  // Single test to create, read, and update our facility
  @Test
  public void CRUDFacility() throws FacilityServiceException

  {
    AddFacilityResult result =
        facilityService.addNewFacility("Test Facility", "Healthcare Facility");

    int insertedFacilityId = result.getFacility().getId();
    assert (insertedFacilityId > 0);

    System.out.println(result.toString());

    facilityService.addFacilityDetail(insertedFacilityId, TestData.sampleFacilityDetail());

    assert (true == facilityService.removeFacility(insertedFacilityId));

    System.out.println("CHECK AGAIN");
    listFacilities();
  }

  @Rule
  public ExpectedException facilityException = ExpectedException.none();

  @Test
  /// Ensure that insert of facility detail with duplicate buildings
  /// is caught.
  public void DuplicateBuildingTest() throws FacilityServiceException {

    // Tell the test infrastructure we are going to throw this - its cool
    facilityException.expect(FacilityServiceException.class);

    // Create a new facility to play with
    AddFacilityResult result =
            facilityService.addNewFacility("Dup Building Test Facility", "Healthcare Facility");

    int insertedFacilityId = result.getFacility().getId();

    // To make test idempotent, clean up the facilty we created in catch clause.
    // Then rethrow the exception since we told test infrastructure to expect it
    try {
      facilityService.addFacilityDetail(insertedFacilityId,
              TestData.sampleFacilityDetailDuplicateBuildings());
    } catch(FacilityServiceException fse) {
      assert (true == facilityService.removeFacility(insertedFacilityId));
      throw fse;
    }

    // Better not get here
    assert(false);
  }

  @Test
  /// Ensure that our validation logic catches the case where a
  /// facility has duplicate named buildings. We catch it so
  /// the db does not have to.
  public void buildingDuplicatesFails() {
    FacilityDetail sample = TestData.sampleFacilityDetail();
    ArrayList<Building> buildings = sample.getBuildings();
    buildings.add(buildings.get(0));

    assert (false == FacilityService.validBuildingNames(sample));
  }

  @Test
  public void getFacilityInformation() throws SQLException {
    GetFacilityDetailResult result = facilityService.getFacilityInformation(1);
    System.out.println("GetFacilityDetailResult -> " + result);
  }
}
