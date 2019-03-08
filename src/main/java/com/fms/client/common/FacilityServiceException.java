package com.fms.client.common;

public class FacilityServiceException extends Exception {

    public FacilityErrorCode getErrorCode() {
        return errorCode;
    }

    private FacilityErrorCode errorCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    private String errorMessage;


    public FacilityServiceException(FacilityErrorCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
