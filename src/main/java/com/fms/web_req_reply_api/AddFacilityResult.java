package com.fms.web_req_reply_api;

import com.fms.domainLayer.facility.Facility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AddFacilityResult {
  public AddFacilityResult(String errorMessage, Facility facility) {
    this.errorMessage = errorMessage;
    this.facility = facility;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public Facility getFacility() {
    return facility;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  private String errorMessage;
  private Facility facility;
}
