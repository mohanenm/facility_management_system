package com.fms.model;

import com.google.gson.*;
import java.io.IOException;

public class Facility implements IFacility{

  public Facility() {}

  public Facility(int id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public Facility(int id, String name, String description, IFacilityDetail facilityDetail) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.facilityDetail = facilityDetail;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public void setFacilityDetail(IFacilityDetail facilityDetail) {
    this.facilityDetail = facilityDetail;
  }

  public IFacilityDetail getFacilityDetail() {
    return facilityDetail;
  }

  public static Facility fromJson(String facility) throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(facility);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    JsonElement facilityDetailJE = jsonObject.get("facilityDetail");

    FacilityDetail facilityDetail =
        facilityDetailJE == null
            ? null
            : FacilityDetail.fromJson(facilityDetailJE.getAsJsonObject().toString());

    return new Facility(
        jsonObject.get("id").getAsInt(),
        jsonObject.get("name").getAsString(),
        jsonObject.get("description").getAsString(),
        facilityDetail);
  }

  @Override
  public boolean equals(Object o) {
    final Facility f = (Facility) o;
    if (f == this) {
      return true;
    }

    return facilityDetail.equals(f.facilityDetail);
  }

  private int id;
  private String name;
  private String description;
  private IFacilityDetail facilityDetail;
}
