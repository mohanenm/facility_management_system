package service;




//import data from database

import base_layer.Facility;
import maintain.Maintenance.*;
import maintain.Schedule;

import javax.jws.soap.SOAPBinding;


public class use {

    // DATA CALL ?
    // pivate UseDB useDB = new UseDB();


// class to list all inspections in a facility(and put that into a list)


// check if a certain facility is in use during some time interval

    public boolean UseIntervalValue(Schedule facilityUse) {

        if (facilityUse.getStartMaintenance().isAfter(facilityUse.getEndMaintenance())) {
            System.out.println("Start must be before End");
        } else if (facilityUse.getRoomNumber() > facilityUse.getDetails().getRoomOfNumbers) {
            System.out.println("bad room number. Only " +
                    facilityUse.getDetails().getNumberofRoom() + " in facility");
        } else {
            try {
                // return useDB.UseInterval(facilityUse);
            } catch (Exception se) {
                System.err.println("threw exception");
                System.err.println(se.getMessage());
            }
        }
        return true;
    }


    // assign facility and room number

    public void assignFacilityUse(Schedule facilityUse) {
        if (facilityUse.getStartMaintenance().isAfter(facilityUse.getEndMaintenance())) {
            System.out.println("Start must be before End");
        } else if (facilityUse.getRoomNumber() > facilityUse.getDetails().getRoomOfNumbers) {
            System.out.println("bad room number. Only " +
                    facilityUse.getDetails().getNumberofRoom() + " in facility");
        } else if (UseIntervalValue(facilityUse)) {
            System.out.println("this room at the facility is being used during interval");

        } else {
            try {
                // return useDB.UseInterval(facilityUse);
            } catch (Exception se) {
                System.err.println("threw exception");
                System.err.println(se.getMessage());
            }
        }
    }
    // other shit to do
    // list assignments at a facility by room and date
    // return a list of facility use objects containing details


    /*
    // CLASS TO CALCULATE COST
     */
}