package com.fms.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

public class FacilityDetail implements IFacilityDetail {


  public FacilityDetail(){};

  public List<IBuilding> getBuildings() {
    return buildings;
  }

  public void setBuildings(List<IBuilding> buildings) {
    this.buildings = buildings;
  }

  public static FacilityDetail fromJson(String facilityDetail) throws IOException {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.fromJson(facilityDetail, FacilityDetail.class);
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  @Override
  public boolean equals(Object o) {
    final FacilityDetail d = (FacilityDetail) o;
    if (d == this) {
      return true;
    }

    return buildings.equals(d.buildings);
  }

  private List<IBuilding> buildings;
}
