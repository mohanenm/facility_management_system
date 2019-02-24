package com.fms.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FacilityMaintenanceRequestResult {


    public FacilityMaintenanceRequestResult(FacilityMaintenanceRequest facilityMaintenanceRequest, String errorMessage) {
        this.facilityMaintenanceRequest = facilityMaintenanceRequest;
        this.errorMessage = errorMessage;
    }


    public String toString() {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }


    @Override
    public boolean equals(Object o) {
        final FacilityMaintenanceRequestResult g = (FacilityMaintenanceRequestResult) o;
        if (g == this) {
            return true;
        }

        return ((facilityMaintenanceRequest == null && g.facilityMaintenanceRequest == null) ||
                facilityMaintenanceRequest.equals(g.facilityMaintenanceRequest)) &&
                ((errorMessage == null && g.errorMessage == null) ||
                        errorMessage.equals(g.errorMessage));
    }

    FacilityMaintenanceRequest facilityMaintenanceRequest;
    String errorMessage;
}
