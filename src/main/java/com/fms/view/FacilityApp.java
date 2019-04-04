package com.fms.view;

import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.facility.IFacility;
import com.fms.domainLayer.facility.IRoom;
import com.fms.domainLayer.facility.RoomObserver;
import com.fms.domainLayer.inspection.FacilityInspection;
import com.fms.domainLayer.maintenance.*;
import com.fms.domainLayer.services.IFacilityService;
import com.fms.domainLayer.services.IMaintenanceService;
import com.fms.domainLayer.services.IUsageService;
import com.google.common.collect.Range;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
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


        IMaintenanceService maintenanceService = (IMaintenanceService) serviceContext.getBean("maintenanceService");
        System.out.println("Loaded Maintenance Service\n----------\n");

        IMaintenanceRequest maintenanceRequestRoom = (IMaintenanceRequest) serviceContext.getBean("maintenanceRequestRoom");

        System.out.println("Loaded Maintenance Request (for room) From FacilityAppBeans Config -> "
                + maintenanceRequestRoom + "\n----------\n");

        IMaintenanceRequest maintenanceRequestFac = (IMaintenanceRequest) serviceContext.getBean("maintenanceRequestFac");

        System.out.println("Loaded Maintenance Request (for facility) From FacilityAppBeans Config -> "
                + maintenanceRequestFac + "\n----------\n");

        //setting (default) null request and schedule objects
        IRoomMaintenanceRequest roomMaintenanceRequest;
        IFacilityMaintenanceRequest facilityMaintenanceRequest;

        //CRUD for maintenance service

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

            MaintenanceCostCalculator maintenanceCostCalculator = (MaintenanceCostCalculator) serviceContext.getBean("maintenanceCostCalculator");
            HashMap<String, Double> totalCost = maintenanceCostCalculator.calcMaintenanceCostForFacility(persistedFacility.getId(), sampleRange());
            System.out.println("Total cost of scheduled maintenance: " + totalCost);

        IUsageService usageService = (IUsageService) serviceContext.getBean("usageService");
        System.out.println("\nLoaded Usage Service\n----------\n");

            //CRUD for usage service
            IRoom testRoom = persistedFacility.getBuildings().get(0).getRooms().get(0);
            int testRoomId = testRoom.getId();
            System.out.println("Test room ID: " + testRoomId);

            boolean isInUse = usageService.isInUseDuringInterval(testRoomId, sampleRange());
            System.out.println("Facility is in use during interval (should return false, we haven't scheduled anything in this block) : " + isInUse);

            //using maintenance service to make a room request
            MaintenanceRequest maintenanceRequest = (MaintenanceRequest) serviceContext.getBean("maintenanceRequest");
            roomMaintenanceRequest = maintenanceService.makeRoomMaintRequest(testRoomId, maintenanceRequest);
            maintenanceService.scheduleRoomMaintenance(roomMaintenanceRequest.getId(), sampleRange());
            System.out.println("Schedule a room maintenance that would conflict with isInUseDuringInterval method -> " + roomMaintenanceRequest.toString());

            //ToDo: figure out why this ain't returning true
            isInUse = usageService.isInUseDuringInterval(testRoomId, sampleRange());
            System.out.println("Facility is in use during interval (should return true, we have scheduled a use during this range) : " + isInUse);

            ArrayList<FacilityInspection> inspections = usageService.listInspections(persistedFacility.getId(), sampleRange());
            System.out.println("Listing Facility Inspections (should return empty, we haven't added any) : " + inspections.toString());

            //ToDo: add listed inspections which should display below
            FacilityInspection facilityInspection = (FacilityInspection) serviceContext.getBean("facilityInspection");
            facilityInspection.setFacilityId(persistedFacility.getId());
            facilityInspection.setCompleted(sampleRange().lowerEndpoint());
            facilityInspection.setPassed(true);
            usageService.addCompletedInspection(facilityInspection);
            System.out.println("");

            inspections = usageService.listInspections(persistedFacility.getId(), sampleRange());
            System.out.println("Listing Facility Inspections (should with newly scheduled inspections) : "
                    + inspections.toString() + "\n----------\n");

        //Testing observer pattern
            //RoomObserver test
            System.out.println(" Observer for test room #101\n----------\n");
            RoomObserver roomObserver = (RoomObserver) serviceContext.getBean("roomAObserver");
            System.out.println("Initial Room toString (should return with vacant state): " + testRoom.getRoomState());
            roomObserver.update();
            System.out.println("Initial Room toString (should return with vacant state): " + testRoom.getRoomState());




        } catch (FMSException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (persistedFacility != null) {
                facilityService.removeFacility(persistedFacility.getId());
            }
        }
    }
}