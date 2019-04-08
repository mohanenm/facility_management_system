package com.fms.domainLayer.facility;

import com.fms.domainLayer.common.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

abstract class Building implements IBuilding {

  protected Structure structure;

  public Building() {}

  public Building(Structure structure){
    this.structure = structure;
  }

  public abstract String getName();

  public abstract String getStreetAddress();

  public abstract String getCity();

  public abstract String getState();

  public abstract List<IRoom> getRooms();

  public abstract int getZip();

  public abstract int getId();

  public abstract void setId(int id);

  public abstract void setName(String name);

  public abstract void setStreetAddress(String streetAddress);

  public abstract void setCity(String city);

  public abstract void setState(String state);

  public abstract void setZip(int zip);

  public abstract void setRooms(List<IRoom> rooms);

}
