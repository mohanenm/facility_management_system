package com.fms.model;
import com.fms.Utility;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.StringReader;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class Building {
    public Building(int id, String name, String streetAddress, String city, String state, int zip) {
        this.id  = id;
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getZip() {
        return zip;
    }

    public int getId() {
        return id;
    }

    public static Building fromJson(String building) throws IOException {

        System.out.println("Building -> " + building);
        JsonReader reader = new JsonReader(new StringReader(building));
        reader.beginObject();

        int id = 0;
        String name = null, streetAddress = null, city = null, state = null;
        int zip = 0;

        while(reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.NAME)) {
                String key = reader.nextName();
                //reader.skipValue();

                switch(key)
                {
                    case "id": {
                        System.out.println("Got id");
                        id = reader.nextInt();
                        break;
                    }
                    case "name": {
                        System.out.println("Got name ");
                        name = reader.nextString();
                        break;
                    }
                    case "streetAddress": {
                        System.out.println("Got streetAddress");
                        streetAddress = reader.nextString();
                        break;
                    }
                    case "city": {
                        System.out.println("Got city");
                        city = reader.nextString();
                        break;
                    }
                    case "state": {
                        System.out.println("Got state");
                        state = reader.nextString();
                        break;
                    }
                    case "zip": {
                        System.out.println("Got zip");
                        zip = reader.nextInt();
                        break;
                    }
                    default:
                        System.out.println("no match");
                }
            }

            //reader.skipValue();
        }

        return new Building(id, name, streetAddress, city, state, zip);
    }

    public String toString() {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        final Building b = (Building) o;
        if(b == this) {
            return true;
        }

        return id == b.id &&
                name.equals(b.name) &&
                streetAddress.equals(b.streetAddress) &&
                city.equals(b.city) &&
                state.equals(b.state) &&
                zip == b.zip;
    }


    private int id;
    private String name;
    private String streetAddress;
    private String city;
    private String state;
    private int zip;
}
