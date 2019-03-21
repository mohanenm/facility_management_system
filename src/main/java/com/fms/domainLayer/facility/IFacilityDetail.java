package com.fms.domainLayer.facility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

public interface IFacilityDetail {
  List<IBuilding> getBuildings();

  void setBuildings(List<IBuilding> buildings);

  static FacilityDetail fromJson(String facilityDetail) throws IOException {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(facilityDetail, FacilityDetail.class);
    }
}
