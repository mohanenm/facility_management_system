package com.fms.model;

public interface IFacility {
    public int getId();

    public String getName();

    public String getDescription();

    public void setId(int id);

    public void setName(String name);

    public void setDescription(String description);
    IFacilityDetail getFacilityDetail();
    void setFacilityDetail(IFacilityDetail facilityDetail);

}
