package com.fms.service.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FMSException extends Exception {

    public FacilityErrorCode getErrorCode() {
        return errorCode;
    }

    private FacilityErrorCode errorCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    private String errorMessage;


    public FMSException(FacilityErrorCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String toString() {
        GsonBuilder builder = new GsonBuilder().serializeNulls().setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

}
