package com.fms.domainLayer.facility;

import java.util.List;

public interface IFacilityDetail {
    public List<IBuilding> getBuildings();

    public void setBuildings(List<IBuilding> buildings);

//    public static FacilityDetail fromJson(String facilityDetail) throws IOException {
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        return gson.fromJson(facilityDetail, FacilityDetail.class);
//    }
}
