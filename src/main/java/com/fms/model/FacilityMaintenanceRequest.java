package com.fms.model;

import com.google.gson.*;

import java.io.IOException;

public class FacilityMaintenanceRequest {

    public FacilityMaintenanceRequest(int id, int maintenanceRequestId, int facilityId) {
        this.id = id;
        this.maintenanceRequestId = maintenanceRequestId;
        this.facilityId = facilityId;
    }

    public int getId() {
        return id;
    }

    public int getMaintenanceRequestId() {
        return maintenanceRequestId;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public String toString() {
        GsonBuilder builder = new GsonBuilder()
                .setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }


    public static FacilityMaintenanceRequest fromJson(String facilityMaintenanceRequest) throws IOException {
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(facilityMaintenanceRequest);
        JsonObject jsonObject = jsonTree.getAsJsonObject();

        return new FacilityMaintenanceRequest(jsonObject.get("id").getAsInt(),
                jsonObject.get("maintenanceRequestId").getAsInt(),
                jsonObject.get("facilityId").getAsInt());
    }

    @Override
    public boolean equals(Object o) {
        final FacilityMaintenanceRequest r = (FacilityMaintenanceRequest) o;
        if (r == this) {
            return true;
        }
        return id == r.id &&
                maintenanceRequestId == r.maintenanceRequestId &&
                facilityId == r.facilityId;
    }

    private int id;
    private int maintenanceRequestId;
    private int facilityId;
}
