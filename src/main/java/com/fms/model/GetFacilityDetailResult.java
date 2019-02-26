package com.fms.model;

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
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static GetFacilityDetailResult fromJson(String getFacilityDetailResult)
      throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(getFacilityDetailResult);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    JsonElement facilityElement = jsonObject.get("facility");

    Facility facility =
        facilityElement == null
            ? null
            : Facility.fromJson(facilityElement.getAsJsonObject().toString());

    JsonElement errorMessageElement = jsonObject.get("errorMessage");

    String errorMessage = errorMessageElement == null ? null : errorMessageElement.getAsString();
    return new GetFacilityDetailResult(errorMessage, facility);
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
