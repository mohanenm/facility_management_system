package com.fms.domainLayer.inspection;

import java.time.LocalDateTime;

public class VerboseInspection extends FacilityInspectionDecorator {

    public VerboseInspection(IFacilityInspection inner){
        super(inner);
    }

    public int getId(){
        System.out.println("Getting inspection id...");
        return super.getId();
    }

    public int getFacilityId(){
        System.out.println("Getting facility id...");
        return super.getFacilityId();
    }

    public LocalDateTime getCompleted(){
        System.out.println("Getting time inspection was completed...");
        return super.getCompleted();
    }

    public boolean isPassed(){
        System.out.println("Checking if the inspection passed...");
        return super.isPassed();
    }

    public void setId(int id){
        System.out.println("Setting inspection id...");
        super.setId(id);
    }

    public void setFacilityId(int facilityId){
        System.out.println("Setting facility id...");
        super.setFacilityId(facilityId);
    }

    public void setCompleted(LocalDateTime completed){
        System.out.println("Setting inspection completion time...");
        super.setCompleted(completed);
    }

    public void setPassed(boolean passed){
        System.out.println("Setting inspection passing status...");
        super.setPassed(passed);
    }



}
