package com.fms.model;

public interface IFacility {
  int getId();

  String getName();

  String getDescription();

  void setId(int id);

  void setName(String name);

  void setDescription(String description);

  IFacilityDetail getFacilityDetail();

  void setFacilityDetail(IFacilityDetail facilityDetail);
}
