package com.fms.domainLayer.facility;

import com.fms.domainLayer.common.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.List;

public class Facility implements IFacility {

  public Facility() {}

  public Facility(int id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public Facility(int id, String name, String description, List<IBuilding> buildings) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.buildings = buildings;
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
    GsonBuilder builder =
        new GsonBuilder()
            .registerTypeAdapter(IBuilding.class, new InterfaceAdapter<IBuilding>())
            .registerTypeAdapter(IRoom.class, new InterfaceAdapter<IRoom>())
            .setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static Facility fromJson(String facility) throws IOException {
    Gson gson =
        new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(IBuilding.class, new InterfaceAdapter<IBuilding>())
            .registerTypeAdapter(IRoom.class, new InterfaceAdapter<IRoom>())
            .create();
    return gson.fromJson(facility, Facility.class);
  }

  @Override
  public boolean equals(Object o) {
    final Facility f = (Facility) o;
    if (f == this) {
      return true;
    }

    return id == f.id
        && name.equals(f.name)
        && description.equals(f.description)
        && buildings.equals(f.buildings);
  }

  private int id;
  private String name;
  private String description;

  @Override
  public List<IBuilding> getBuildings() {
    return buildings;
  }

  public void setBuildings(List<IBuilding> buildings) {
    this.buildings = buildings;
  }

  List<IBuilding> buildings;
}
