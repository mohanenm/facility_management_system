package com.fms.domainLayer.maintenance;

import com.google.gson.*;
import java.io.IOException;

public class MaintenanceType implements IMaintenanceType {

  public MaintenanceType() {}

  public MaintenanceType(int id, String description) {
    this.id = id;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String toString() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static MaintenanceType fromJson(String maintenanceType) throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(maintenanceType);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    return new MaintenanceType(
        jsonObject.get("id").getAsInt(), jsonObject.get("description").getAsString());
  }

  @Override
  public boolean equals(Object o) {
    final MaintenanceType m = (MaintenanceType) o;
    if (m == this) {
      return true;
    }
    return id == m.id && description.equals(m.description);
  }

  private String description;
  private int id;
}
