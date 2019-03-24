package com.fms.domainLayer.inspection;

import java.time.LocalDateTime;

public interface IFacilityInspection {

    int getId();

    int getFacilityId();

    LocalDateTime getCompleted();

    boolean isPassed();

    void setId(int id);

    void setFacilityId(int facilityId);

    void setCompleted(LocalDateTime completed);

    void setPassed(boolean passed);
}
