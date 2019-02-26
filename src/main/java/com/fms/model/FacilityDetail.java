package com.fms.model;

import com.google.gson.*;
import java.io.IOException;
import java.util.ArrayList;

public class FacilityDetail {
  public FacilityDetail(ArrayList<Building> buildings) {
    this.buildings = buildings;
  }

  public ArrayList<Building> getBuildings() {
    return buildings;
  }

  public static FacilityDetail fromJson(String facilityDetail) throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(facilityDetail);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    ArrayList<Building> buildings = new ArrayList<>();

    final JsonArray buildingsJson = jsonObject.get("buildings").getAsJsonArray();

    for (JsonElement building : buildingsJson) {
      String buildingJson = building.getAsJsonObject().toString();
      buildings.add(Building.fromJson(buildingJson));
    }

    return new FacilityDetail(buildings);
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

  private ArrayList<Building> buildings;
}
