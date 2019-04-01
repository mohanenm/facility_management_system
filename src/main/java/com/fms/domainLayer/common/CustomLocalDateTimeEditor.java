package com.fms.domainLayer.common;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

//Solution found from SO: https://stackoverflow.com/questions/31956422/how-to-setup-localdatetime-in-spring-xml-configuration
public class CustomLocalDateTimeEditor extends PropertyEditorSupport {

    public CustomLocalDateTimeEditor() {
    }

    private LocalDateTime parseText(String text) {
        LocalDateTime ldt;
        try {
            ldt = LocalDateTime.parse(text);
        } catch(Exception ee) {
            ldt = null;
        }

        if(ldt == null) {
            try {
                ldt = LocalDateTime.of(LocalDate.parse(text), LocalTime.of(0, 0));
            } catch(Exception ee) {
                ldt = null;
            }
        }

        return ldt;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(parseText(text));
    }

    @Override
    public String getAsText() {
        LocalDateTime value = parseText(String.valueOf(getValue()));
        return (value != null ? value.toString() : "");
    }

}
