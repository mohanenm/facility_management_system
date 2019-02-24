package com.fms.model;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import com.fms.TestData;

public class JsonMarshallingTest {


    @Test
    public void FacilityMarshalling() throws IOException {
        Facility facility = new Facility(1, "F1", "Psychological Testing");
        facility.setFacilityDetail(TestData.sampleFacilityDetail());
        Facility again = Facility.fromJson(facility.toString());
        boolean areEqual = again.equals(facility);
        assert (areEqual);
    }

    @Test
    public void FacilityDetailMarshalling() throws IOException {
        FacilityDetail facilityDetail = TestData.sampleFacilityDetail();
        FacilityDetail again = FacilityDetail.fromJson(facilityDetail.toString());
        boolean areEqual = again.equals(facilityDetail);
        assert (areEqual);
    }

    @Test
    public void BuildingMarshalling() throws IOException {
        Building building = TestData.sampleBuilding("B1");
        Building again = Building.fromJson(building.toString());
        boolean areEqual = again.equals(building);
        assert (areEqual);
    }


    @Test
    public void RoomMarshalling() throws IOException {
        Room room = new Room(1, 5, 201);
        Room again = room.fromJson(room.toString());
        boolean areEqual = again.equals(room);
        assert (areEqual);
    }


}
