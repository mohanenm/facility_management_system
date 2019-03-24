package com.fms.domainLayer.inspection;

public class Inspection {

    private int facilityID;
    private String inspectionType;
    private String inspectionDetail;

    public Inspection() {}

    public String getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(String inspectionType) {
        this.inspectionType = inspectionType;
    }

    public String getInspectionDetail() {
        return inspectionDetail;
    }

    public void setInspectionDetail(String inspectionDetail) {
        this.inspectionDetail = inspectionDetail;
    }

    public int getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(int facilityID) {
        this.facilityID = facilityID;
    }

}

