package com.fms.model;

import java.util.List;

public interface IFacilityDetail {
  List<IBuilding> getBuildings();

  void setBuildings(List<IBuilding> buildings);

  //    public static FacilityDetail fromJson(String facilityDetail) throws IOException {
  //        Gson gson = new GsonBuilder().serializeNulls().create();
  //        return gson.fromJson(facilityDetail, FacilityDetail.class);
  //    }
}
