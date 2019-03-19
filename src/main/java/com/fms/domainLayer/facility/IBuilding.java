package com.fms.domainLayer.facility;

import java.util.List;

public interface IBuilding {
  String getName();

  String getStreetAddress();

  String getCity();

  String getState();

  List<IRoom> getRooms();

  int getZip();

  int getId();

  void setId(int id);

  void setName(String name);

  void setStreetAddress(String streetAddress);

  void setCity(String city);

  void setState(String state);

  void setZip(int zip);

  void setRooms(List<IRoom> rooms);
  // public static Building fromJson(String building) throws IOException;
}
