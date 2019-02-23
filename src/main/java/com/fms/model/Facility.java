package com.fms.model;

public class Facility {

    public Facility(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return ("{\n\tfacility id: " + id +
                "\n\tname: " + name +
                "\n\tdescription: "+ description +
                "\n}");
    }


    private int id;
    private String name;
    private String description;
}
