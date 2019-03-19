package com.fms.web_req_reply_api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AddFacilityDetailResult {
  public AddFacilityDetailResult(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().serializeNulls().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  private String errorMessage;
}
