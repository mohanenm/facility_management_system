package com.fms.client.usage;

import com.fms.dal.DBUsage;
import com.fms.model.*;
import com.fms.model.FacilityDetail;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class usageService{

    DBUsage dbUsage = new DBUsage();
    //Building building = new Building();
    

        public ArrayList<FacilityInspection> listInspections(FacilityInspection inspectUse) {
            try {
                return dbUsage.listInspections(roomUse);
            } catch (Exception se) {
                System.err.println("usageService: Threw an Exception retrieving list of inspections.");
                System.err.println(se.getMessage());
            }

            return null;
        }
        /***
         * Checks if a particular room at a facility is in use during an interval.
         * Use 0 for room number if checking the entire facility.
         * @param facUse instance of FacilityUse to be checked which indicates the room number and start/end dates
         * @return true if facility/room is in use, otherwise false
         */
        public boolean isInUseDuringInterval(RoomReservation roomUse) {
            
            //ensures the start and end data are valid and room number exists
            if (roomUse.getStart().isAfter(roomUse.getFinish())) {
                System.out.println("Start date must be before end date.");
            } else if (roomUse.getRoomId() != roomUse.getRoomId()) {
                System.out.println("Invalid room. There are only "); 
            } else {
                try {
                    return dbUsage.isInUseDuringInterval(room);
                } catch (Exception se) {
                    System.err.println("Usage Service: Threw an Exception checking if facility is in use during interval.");
                    System.err.println(se.getMessage());
                }
            }

            return true;
        }

        /***
         * Assigns a facility and room number to use from a particular start date to a particular end date.
         * Enter room number 0 if assigning entire facility to use.
         * @param facUse the instance of FacilityUse to be assigned which indicates room number and start/end dates
         */
        public void assignFacilityToUse(RoomReservation facUse) {

            //ensures the start and end data are valid, room number exists, and room isn't already in use at that time
            if (facUse.getStart().isAfter(facUse.getFinish())) {
                System.out.println("Start date must be before end date.");
            } else if (facUse.getRoomId() != facUse.getRoomId())  {
                System.out.println("Invalid room number. There are only ");
            } else if (isInUseDuringInterval(facUse)) {
                System.out.println("This room at the facility is already in use during this interval.");
            } else {
                try {
                    dbUsage.readRoomReservations(facUse);
                } catch (Exception se) {
                    System.err.println("usageService: Threw an Exception assigning a facility to use.");
                    System.err.println(se.getMessage());
                }
            }

        }

        /***
         * Lists the usage assignments at a particular facility first by room number and then by date.
         * @param fac Facility to list the usage assignments for
         * @return a list of FacilityUse objects containing a room number, start date, and end date.
         */
        public List<FacilityUse> listActualUsage(Facility fac) {
            try {
                return dbUsage.listActualUsage(fac);
            } catch (Exception se) {
                System.err.println("usageService: Threw an Exception retrieving list of usage.");
                System.err.println(se.getMessage());
            }

            return null;
        }

        /***
         * Calculates the current usage rate of a particular facility.
         * @param fac Facility to calculate the usage rate of
         * @return number of rooms currently being used divided by the total number of rooms at a facility
         */
        public double calcUsageRate(Facility fac) {

            try {
                FacilityService facService = new FacilityService();
                int totalRooms = fac.getDetailsAboutFacility().getNumberOfRooms();
                int roomsAvailable = facService.requestAvailableCapacity(fac);
                int roomsInUse = totalRooms - roomsAvailable;
                return Math.round(((double)roomsInUse / totalRooms) * 100d)/100d;

            } catch (Exception se) {
                System.err.println("usageService: Threw an Exception retrieving list of usage for calculating the usage rate.");
                System.err.println(se.getMessage());
            }

            return 0.00;
        }

        public void vacateFacility(Facility fac, int roomNumber) {

            try {
                ArrayList<Facility> usageList = listActualUsage(fac);
                if (roomNumber > fac.getDetailsAboutFacility().getNumberOfRooms()) {
                    System.out.println("Invalid room number. There are only " +
                            fac.getDetailsAboutFacility().getNumberOfRooms() + " rooms at this facility.");
                } else {
                    for (FacilityUse use : usageList) {
                        //if room number matches usage list (or usage list entry is for entire facility)
                        //and room is currently in use, set vacateQuery
                        if (use.getRoomNumber() == 0 || (use.getRoomNumber() == roomNumber))  {
                            if ((LocalDate.now().equals(use.getStartDate())) || LocalDate.now().isAfter(use.getStartDate())) {
                                if ((LocalDate.now().equals(use.getEndDate())) || (LocalDate.now().isBefore(use.getEndDate()))) {
                                    dbUsage.vacateFacility(fac, roomNumber);
                                }
                            } else {
                                System.out.println("This room is not currently in use. Unable to vacate at this time.");
                            }
                        }
                    }
                }
            }
            catch (Exception se) {
                System.err.println("usageService: Threw an Exception vacating a facility.");
                System.err.println(se.getMessage());
            }

        }

        /***
         * Gets the creation date of a facility, which is the earliest assigned start date at the facility.
         * Method is used in MaintenanceService to calculate the problem rate for a facility.
         * @param fac Facility to get the start date for
         * @return start date of the facility
         */
        public LocalDate getFacilityStartDate(Facility fac) {
            try {
                return dbUsage.getFacilityStart(fac);
            } catch (Exception se) {
                System.err.println("usageService: Threw an Exception retrieving the facility start date.");
                System.err.println(se.getMessage());
            }
            return null;
        }
