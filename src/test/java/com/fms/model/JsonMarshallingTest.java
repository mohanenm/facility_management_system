package com.fms.model;

import org.junit.Test;

import java.io.IOException;

public class JsonMarshallingTest {
    @Test
    public void JsonMarshalling() throws IOException {
        Building building = new Building(1, "B1",
                "4 Marshall Ln", "Albequerque", "NM", 66540);
        Building again = Building.fromJson(building.toString());
        System.out.println("There and back again -> " + again.toString());

        boolean areEqual = again.equals(building);

        assert(areEqual);

    }
}
