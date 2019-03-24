package com.fms.view;

import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.facility.IFacility;
import com.fms.domainLayer.services.IFacilityService;
import com.fms.domainLayer.services.IMaintenanceService;
import com.fms.domainLayer.services.IUsageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FacilityApp {
    public static void main(String args[]) throws FMSException {

        //Creating a new application context for our wired service package beans
        ApplicationContext serviceContext = new ClassPathXmlApplicationContext(
                "file:src/main/java/META_INF/FacilityAppBeans.xml");

        //Using FacilityAppBeans.xml to create facilities for testing
        IFacility facility = (IFacility) serviceContext.getBean("loyolaFacility");

        System.out.println("Loaded Facility From Spring Config -> " + facility +"\n----------\n");

        IFacilityService facilityService = (IFacilityService) serviceContext.getBean("facilityService");
        System.out.println("Loaded Facility Service\n----------\n");

        IUsageService usageService = (IUsageService) serviceContext.getBean("usageService");
        System.out.println("Loaded Usage Serivce\n----------\n");

        IMaintenanceService maintenanceService = (IMaintenanceService) serviceContext.getBean("maintenanceService");
        System.out.println("Loaded Maintenance Service\n----------\n");

        IFacility persistedFacility = null;
        try {

            persistedFacility = facilityService.addNewFacility(facility.getName(), facility.getDescription());
            System.out.println("Persisted Spring Configured Service -> " + persistedFacility + "\n----------\n");

            persistedFacility = facilityService.addFacilityDetail(persistedFacility.getId(), facility.getBuildings());

            System.out.println("Added/Persisted Details -> " + persistedFacility + "\n----------\n");

            System.out.println("\n\n-------- LISTING FACILITIES --------\n");

            System.out.println(facilityService.getFacilityInformation(persistedFacility.getId()));
        } catch (FMSException e) {
            e.printStackTrace();
        } finally {
            if(persistedFacility != null) {
                facilityService.removeFacility(persistedFacility.getId());
            }
        }
    }
}
