package client;

import base_layer.*;

public class Client {
    public static void main(String []args){

        Room rA = new Room();
        rA.setRoomNum(101);

        Room rB = new Room();
        rB.setRoomNum(102);


        Facility HR = new Facility.FacilityBuilder("Human Resources", 001)
            .withPhone("(555)555-5555")
            .withDescription("The Human Resources  Management facility is in charge of hiring and managing employees across all facilities of AbstractCorp.")
                .build();

        HR.getRoomsInFacility().add(rA);
        HR.getRoomsInFacility().add(rB);

        System.out.print(HR.toString());
/*
        Facility Elec = new Facility();
        Elec.setFacilityID(002);

        Elec.setDescription("The Electrical and Wiring Management facility is in charge of implementing and maintaining the power grid across the buildings of AbstractCorp.");
        Elec.setName("Electrical and Wiring");
        Elec.setPhoneNumber("(666)666-6666");
        System.out.print(Elec.toString());

        Facility IT = new Facility();
        IT.setFacilityID(003);

        IT.setDescription("The Information Technology facility is in charge of maintaining computer and phone networks across all facilities, " +
                "all employees of AbstractCorp can call upon them for tech support.");
        IT.setPhoneNumber("(777)777-7777");
*/
        Building A = new Building();
        A.setBuildingId(0001);
        A.setCity("Abstractville");
        A.setState("Illinois");
        A.setStreetAddress("6969 Cool Address Lane");
        A.toString();

        Building B = new Building();
        B.setBuildingId(0002);
        B.setCity("Abstractville");
        B.setState("Illinois");
        B.setStreetAddress("6970 Cool Address Lane");

        Building C = new Building();
        C.setBuildingId(0003);



    }
}
