package com.fms.web_req_reply_api;

import com.fms.domainLayer.common.InterfaceAdapter;
import com.fms.domainLayer.facility.Facility;
import com.fms.domainLayer.facility.IBuilding;
import com.fms.domainLayer.facility.IRoom;
import com.google.gson.*;
import java.io.IOException;

public class GetFacilityDetailResult {
  public String getErrorMessage() {
    return errorMessage;
  }

  public Facility getFacility() {
    return facility;
  }

  public GetFacilityDetailResult(String errorMessage, Facility facility) {
    this.errorMessage = errorMessage;
    this.facility = facility;
  }

  public String toString() {
    GsonBuilder builder =
        new GsonBuilder()
            .registerTypeAdapter(IBuilding.class, new InterfaceAdapter<IBuilding>())
            .registerTypeAdapter(IRoom.class, new InterfaceAdapter<IRoom>())
            .setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static GetFacilityDetailResult fromJson(String getFacilityDetailResult)
      throws IOException {
    Gson gson =
        new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(IBuilding.class, new InterfaceAdapter<IBuilding>())
            .registerTypeAdapter(IRoom.class, new InterfaceAdapter<IRoom>())
            .create();
    return gson.fromJson(getFacilityDetailResult, GetFacilityDetailResult.class);
  }

  @Override
  public boolean equals(Object o) {
    final GetFacilityDetailResult g = (GetFacilityDetailResult) o;
    if (g == this) {
      return true;
    }

    return ((facility == null && g.facility == null) || facility.equals(g.facility))
        && ((errorMessage == null && g.errorMessage == null)
            || errorMessage.equals(g.errorMessage));
  }

  private Facility facility;
  private String errorMessage;
}
