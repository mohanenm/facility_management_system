package com.fms.domainLayer.inspection;

import java.time.LocalDateTime;

public class FacilityInspectionDecorator implements IFacilityInspection {

    private IFacilityInspection inner;

    public FacilityInspectionDecorator(IFacilityInspection inner){
        this.inner = inner;
    }


    @Override
    public int getId() {
        return inner.getId();
    }

    @Override
    public int getFacilityId() {
        return inner.getFacilityId();
    }

    @Override
    public LocalDateTime getCompleted() {
        return inner.getCompleted();
    }

    @Override
    public boolean isPassed() {
        return inner.isPassed();
    }

    @Override
    public void setId(int id) {
        inner.setId(id);
    }

    @Override
    public void setFacilityId(int facilityId) {
        inner.setFacilityId(facilityId);
    }

    @Override
    public void setCompleted(LocalDateTime completed) {
        inner.setCompleted(completed);
    }

    @Override
    public void setPassed(boolean passed) {
        inner.setPassed(passed);
    }
}
