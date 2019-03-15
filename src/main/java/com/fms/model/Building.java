package com.fms.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

public class Building implements IBuilding {

  public Building() {}

  /// Client constructor - no id specified
  public Building(
      String name,
      String streetAddress,
      String city,
      String state,
      int zip) {
    this.id = -1;
    this.name = name;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
  }

  /// Constructor for database, id required

  public Building(
          int id,
          String name,
          String streetAddress,
          String city,
          String state,
          int zip) {
    this.id = id;
    this.name = name;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
  }

  public String getName() {
    return name;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public List<IRoom> getRooms() {
    return rooms;
  }

  public int getZip() {
    return zip;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setZip(int zip) {
    this.zip = zip;
  }

  public void setRooms(List<IRoom> rooms) {
    this.rooms = rooms;
  }

  public static Building fromJson(String building) throws IOException {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.fromJson(building, Building.class);
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  @Override
  public boolean equals(Object o) {
    final Building b = (Building) o;
    if (b == this) {
      return true;
    }

    return id == b.id
        && name.equals(b.name)
        && streetAddress.equals(b.streetAddress)
        && city.equals(b.city)
        && state.equals(b.state)
        && zip == b.zip;
  }


  private int id;
  private String name;
  private String streetAddress;
  private String city;
  private String state;
  private int zip;
  private List<IRoom> rooms;
}
