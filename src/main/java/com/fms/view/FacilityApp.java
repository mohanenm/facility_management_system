package com.fms.view;

import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.facility.IFacility;
import com.fms.domainLayer.maintenance.*;
import com.fms.domainLayer.services.IFacilityService;
import com.fms.domainLayer.services.IMaintenanceService;
import com.fms.domainLayer.services.IUsageService;
import com.google.common.collect.Range;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;

public class FacilityApp {

    public static Range<LocalDateTime> sampleRange() {
        LocalDateTime lower = LocalDateTime.of(1984, Month.SEPTEMBER, 17, 3, 30);
        LocalDateTime upper = LocalDateTime.of(1984, Month.SEPTEMBER, 17, 4, 00);
        return Range.open(lower, upper);
    }

    public static Range<LocalDateTime> sampleRangeConflicting() {
        LocalDateTime lower = LocalDateTime.of(1984, Month.DECEMBER, 10, 15, 30);
        LocalDateTime upper = LocalDateTime.of(2010, Month.SEPTEMBER, 17, 4, 10);
        return Range.open(lower, upper);
    }

    public static void main(String args[]) throws FMSException {

        //Creating a new application context for our wired service package beans
        ApplicationContext serviceContext = new ClassPathXmlApplicationContext(
                "file:src/main/java/META_INF/FacilityAppBeans.xml");

        //Using FacilityAppBeans.xml to create facilities for testing
        IFacility facility = (IFacility) serviceContext.getBean("loyolaFacility");

        System.out.println("Loaded Facility From FacilityAppBeans Config -> " + facility + "\n----------\n");

        IFacilityService facilityService = (IFacilityService) serviceContext.getBean("facilityService");
        System.out.println("Loaded Facility Service\n----------\n");

        IFacility persistedFacility = null;

        //CRUD for facility service
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
            if (persistedFacility != null) {
                facilityService.removeFacility(persistedFacility.getId());
            }
        }

        IMaintenanceService maintenanceService = (IMaintenanceService) serviceContext.getBean("maintenanceService");
        System.out.println("Loaded Maintenance Service\n----------\n");

        IMaintenanceRequest maintenanceRequestRoom = (IMaintenanceRequest) serviceContext.getBean("maintenanceRequestRoom");

        System.out.println("Loaded Maintenance Request (for room) From FacilityAppBeans Config -> "
                + maintenanceRequestRoom + "\n----------\n");

        IMaintenanceRequest maintenanceRequestFac = (IMaintenanceRequest) serviceContext.getBean("maintenanceRequestFac");

        System.out.println("Loaded Maintenance Request (for facility) From FacilityAppBeans Config -> "
                + maintenanceRequestFac + "\n----------\n");

        //setting null request and schedule objects
        IRoomMaintenanceRequest roomMaintenanceRequest = null;
        IFacilityMaintenanceRequest facilityMaintenanceRequest = null;
        //CRUD for maintenance service
        try {
            //adding detailed facility for maintenance service CRUD test
            persistedFacility = facilityService.addNewFacility(facility.getName(), facility.getDescription());
            persistedFacility = facilityService.addFacilityDetail(persistedFacility.getId(), facility.getBuildings());

            //creating a room maintenance request
            roomMaintenanceRequest = maintenanceService.makeRoomMaintRequest(persistedFacility.getBuildings()
                    .get(0 ).getRooms().get(0).getId(), maintenanceRequestRoom);

            System.out.println("Room maintenance request -> " + roomMaintenanceRequest.toString());

            //setting hourly rate of room maintenance request to $25.00/hr
            int roomMaintenanceHourlyRateId = maintenanceService.insertMaintenanceHourlyRate(persistedFacility.getId(),
                    roomMaintenanceRequest.getMaintenanceRequest().getMaintenanceTypeId(), 25.00);


            facilityMaintenanceRequest = maintenanceService.makeFacilityMaintRequest(persistedFacility.getId(),
                    maintenanceRequestFac);

            System.out.println("Facility maintenance request -> " + facilityMaintenanceRequest.toString());

            int facilityMaintenanceHourlyRateId = maintenanceService.insertMaintenanceHourlyRate(persistedFacility.getId(),
                    facilityMaintenanceRequest.getMaintenanceRequest().getMaintenanceTypeId(), 45.00);

            int roomMaintenanceScheduleId = maintenanceService.scheduleRoomMaintenance(roomMaintenanceRequest.getId(), sampleRange());
            System.out.println("ID of roomMaintenanceSchedule confirming successful no-conflict reservation -> " + roomMaintenanceScheduleId);

            int facilityMaintenanceScheduleId = maintenanceService.scheduleFacilityMaintenance(facilityMaintenanceRequest.getId(),
                    facilityMaintenanceRequest.getMaintenanceRequest().isVacateRequired(),
                    facilityMaintenanceRequest.getMaintenanceRequest().isRoutine(),
                    sampleRange());
            System.out.println("ID of facilityMaintenanceSchedule confirming successful no-conflict reservation -> " + facilityMaintenanceScheduleId);

            MaintenanceCostCalculator maintenanceCostCalculator = new MaintenanceCostCalculator();
            HashMap<String, Double> totalCost = maintenanceCostCalculator.calcMaintenanceCostForFacility(persistedFacility.getId(), sampleRange());
            System.out.println("Total cost of scheduled maintenance: " + totalCost);


        } catch (FMSException e) {
            e.printStackTrace();
        } finally {
            if (persistedFacility != null || roomMaintenanceRequest != null || facilityMaintenanceRequest != null) {
                facilityService.removeFacility(persistedFacility.getId());
            }
        }

        IUsageService usageService = (IUsageService) serviceContext.getBean("usageService");
        System.out.println("\nLoaded Usage Service\n----------\n");

        //CRUD for usage service
        try {
            //adding detailed facility for maintenance service CRUD test
            persistedFacility = facilityService.addNewFacility(facility.getName(), facility.getDescription());
            persistedFacility = facilityService.addFacilityDetail(persistedFacility.getId(), facility.getBuildings());


        } catch (FMSException e) {
            e.printStackTrace();
        } finally {
            if (persistedFacility != null || roomMaintenanceRequest != null || facilityMaintenanceRequest != null) {
                facilityService.removeFacility(persistedFacility.getId());
            }
        }


    }
}