package com.fms.model;

public class Inspection {

    private int facility_ID;
    private String inspection_type;
    private String inspection_detail;

    public Inspection() {}

    public String getInspection_type() {
        return inspection_type;
    }

    public void setInspection_type(String inspection_type) {
        this.inspection_type = inspection_type;
    }

    public String getInspection_detail() {
        return inspection_detail;
    }

    public void setInspection_detail(String inspection_detail) {
        this.inspection_detail = inspection_detail;
    }

    public int getFacility_ID() {
        return facility_ID;
    }

    public void setFacility_ID(int facility_ID) {
        this.facility_ID = facility_ID;
    }

}
