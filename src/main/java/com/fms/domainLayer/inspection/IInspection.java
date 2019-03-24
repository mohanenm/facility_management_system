package com.fms.domainLayer.inspection;

public interface IInspection {

    String getInspectionType();

    void setInspectionType(String inspectionType);

    String getInspectionDetail();

    void setInspectionDetail(String inspectionDetail);

    int getFacilityID();

    void setFacilityID(int facilityID);
}
