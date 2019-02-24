package com.fms;

public class Utility {
    public static String nullStringCheck(Object o) {
        if (o == null) {
            return "null";
        } else {
            return o.toString().replace('"', '\'');
        }
    }
}
