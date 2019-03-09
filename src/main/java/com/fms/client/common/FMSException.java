package com.fms.client.common;

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
}
