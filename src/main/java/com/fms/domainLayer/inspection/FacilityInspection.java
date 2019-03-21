package com.fms.domainLayer.inspection;

import com.google.gson.*;
import java.io.IOException;
import java.time.LocalDateTime;

public class FacilityInspection {

  public FacilityInspection(int id, int facilityId, LocalDateTime completed, boolean passed) {
    this.id = id;
    this.facilityId = facilityId;
    this.completed = completed;
    this.passed = passed;
  }

  public int getId() {
    return id;
  }

  public int getFacilityId() {
    return facilityId;
  }

  public LocalDateTime getCompleted() {
    return completed;
  }

  public boolean isPassed() {
    return passed;
  }

  public String toString() {
    GsonBuilder builder =
        new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    Gson gson = builder.create();
    return gson.toJson(this);
  }

  public static FacilityInspection fromJson(String facilityInspection) throws IOException {
    JsonParser parser = new JsonParser();
    JsonElement jsonTree = parser.parse(facilityInspection);
    JsonObject jsonObject = jsonTree.getAsJsonObject();

    JsonObject completedAsJsonObject = jsonObject.get("completed").getAsJsonObject();
    JsonObject dateObject = completedAsJsonObject.get("date").getAsJsonObject();
    JsonObject timeObject = completedAsJsonObject.get("time").getAsJsonObject();

    LocalDateTime completed =
        LocalDateTime.of(
            dateObject.get("year").getAsInt(),
            dateObject.get("month").getAsInt(),
            dateObject.get("day").getAsInt(),
            timeObject.get("hour").getAsInt(),
            timeObject.get("minute").getAsInt(),
            timeObject.get("second").getAsInt(),
            timeObject.get("nano").getAsInt());

    return new FacilityInspection(
        jsonObject.get("id").getAsInt(),
        jsonObject.get("facilityId").getAsInt(),
        completed,
        jsonObject.get("passed").getAsBoolean());
  }

  @Override
  public boolean equals(Object o) {
    final FacilityInspection i = (FacilityInspection) o;
    if (i == this) {
      return true;
    }
    return id == i.id
        && facilityId == i.facilityId
        && completed.equals(i.completed)
        && passed == i.passed;
  }

  private int id;
  private int facilityId;
  private LocalDateTime completed;
  private boolean passed;
}
