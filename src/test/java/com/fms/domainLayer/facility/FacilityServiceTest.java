package com.fms.domainLayer.facility;

import com.fms.TestData;
import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.maintenance.MaintenanceCostCalculator;
import com.fms.domainLayer.maintenance.MaintenanceService;
import com.fms.domainLayer.usage.UsageService;
import com.fms.model.Facility;
import com.fms.model.IBuilding;
import com.fms.model.IFacility;
import com.fms.model.IFacilityDetail;
import com.fms.req_reply_api.GetFacilityDetailResult;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacilityServiceTest {

  FacilityService facilityService;
  MaintenanceService maintenanceService;
  UsageService usageService;
  TestData testData;
  MaintenanceCostCalculator maintenanceCostCalculator;

  public FacilityServiceTest() throws SQLException {
    maintenanceService = new MaintenanceService();
    facilityService = new FacilityService();
    usageService = new UsageService();
    testData = new TestData();
    maintenanceCostCalculator = new MaintenanceCostCalculator();
    ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/app-context.xml");

  }

  @Test
  public void listFacilities() {
    ArrayList<Facility> facilityArrayList = facilityService.listFacilities();
    System.out.print("All Facilities in list: " + facilityArrayList.toString());
  }

  // Single test to create, read, and update our facility
  @Test
  public void CRUDFacility() throws FMSException

  {
    IFacility facility =
        facilityService.addNewFacility("Test Facility", "Healthcare Facility");

    assert (facility.getId() > 0);

    System.out.println(facility.toString());
    facilityService.addFacilityDetail(facility.getId(), testData.sampleFacilityDetail());
    facilityService.removeFacility(facility.getId());

    System.out.println("CHECK AGAIN");
    listFacilities();
  }

  @Rule
  public ExpectedException facilityException = ExpectedException.none();

  @Test
  /// Ensure that insert of facility detail with duplicate buildings
  /// is caught.
  public void DuplicateBuildingTest() throws FMSException {

    // Tell the test infrastructure we are going to throw this - its cool
    facilityException.expect(FMSException.class);

    // Create a new facility to play with
    IFacility facility =
            facilityService.addNewFacility("Dup Building Test Facility", "Healthcare Facility");

    int insertedFacilityId = facility.getId();

    // To make test idempotent, clean up the facilty we created in catch clause.
    // Then rethrow the exception since we told test infrastructure to expect it
    try {
      facilityService.addFacilityDetail(insertedFacilityId,
              testData.sampleFacilityDetailDuplicateBuildings());
    } catch(FMSException fse) {
      facilityService.removeFacility(insertedFacilityId);
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
    IFacilityDetail sample = testData.sampleFacilityDetail();
    List<IBuilding> buildings = sample.getBuildings();
    buildings.add(buildings.get(0));

    assert (false == FacilityService.validBuildingNames(sample));
  }

  @Test
  public void getFacilityInformation() throws SQLException {
    GetFacilityDetailResult result = facilityService.getFacilityInformation(1);
    System.out.println("GetFacilityDetailResult -> " + result);
  }
}
