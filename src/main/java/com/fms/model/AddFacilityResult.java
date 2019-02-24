package com.fms.model;

import com.fms.Utility;
import jdk.jshell.execution.Util;

public class AddFacilityResult {
    public AddFacilityResult(String errorMessage, Facility facility) {
        this.errorMessage = errorMessage;
        this.facility = facility;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Facility getFacility() {
        return facility;
    }

    public String toString() {
        return ("{\n\terrorMessage: " + '"' + Utility.nullStringCheck(errorMessage) + '"' +
                "\n\tfacility: " + Utility.nullStringCheck(facility) +
                "\n}");
    }

    private String errorMessage;
    private Facility facility;

}
