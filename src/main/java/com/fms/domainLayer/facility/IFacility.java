package com.fms.domainLayer.facility;

import java.util.List;

public interface IFacility {
  int getId();

  String getName();

  String getDescription();

  void setId(int id);

  void setName(String name);

  void setDescription(String description);

  List<IBuilding> getBuildings();

  void setBuildings(List<IBuilding> buildings);
}
