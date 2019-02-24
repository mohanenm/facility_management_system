package com.fms.model;

import com.fms.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.jshell.execution.Util;

public class AddFacilityDetailResult {
    public AddFacilityDetailResult(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String toString() {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    private String errorMessage;

}
