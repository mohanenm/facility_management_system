package client;

import base_layer.Building;
import base_layer.Facility;
import base_layer.FacilityDetails;

public class Client {
    public static void main(String []args){

        Facility HR = new Facility();
        HR.setFacilityID(001);

        FacilityDetails HRDetails = new FacilityDetails();
        HRDetails.setDescription("The Human Resources Management facility is in charge of hiring and managing employees across all facilities of AbstractCorp.");
        HRDetails.setName("Human Resources");
        HRDetails.setPhoneNumber(555-555-5555);


        HR.setDetailsAboutFacility(HRDetails);
        System.out.print(HR.getFacilityID());

        Facility Elec = new Facility();
        Elec.setFacilityID(002);

        FacilityDetails ElecDetails = new FacilityDetails();
        ElecDetails.setDescription("The Electrical and Wiring Management facility is in charge of implementing and maintaining the power grid across the buildings of AbstractCorp.");
        ElecDetails.setName("Electrical and Wiring");
        ElecDetails.setPhoneNumber(666-666-6666);

        Facility IT = new Facility();
        IT.setFacilityID(003);

        FacilityDetails ITDetails = new FacilityDetails();
        ITDetails.setDescription("The Information Technology facility is in charge of maintaining computer and phone networks across all facilities, " +
                "all employees of AbstractCorp can call upon them for tech support.");
        ITDetails.setPhoneNumber(777-777-7777);

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
