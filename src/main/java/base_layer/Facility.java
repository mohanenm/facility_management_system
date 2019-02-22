package base_layer;
import java.util.ArrayList;
public class Facility {

    private String name;
    private int facilityID;
    private String phoneNumber;
    private String description;
    private java.util.ArrayList<Integer> roomsInFacility;
    private int buildingID;

    public Facility() {
    }

    public String getName() {
        return name;
    }

    public int getFacilityID() {
        return facilityID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription(){
        return description;
    }
    public ArrayList<Integer> getRoomsInFacility(){
        return roomsInFacility;
    }

    public String toString(){
        return("~~~~~~~~~~~~~~~~~~~~~~~~~~" + "\nFacility ID: " + facilityID + "\nFacility Name: " + name + " \nPhone: " + phoneNumber + " \nDescription: " + description + "\nRooms in Facility: " + roomsInFacility + "\n~~~~~~~~~~~~~~~~~~~~~~~~~");
    }


    public static class FacilityBuilder {

        private String name;
        private int facilityID;
        private String phoneNumber;
        private String description;
        private ArrayList<Integer> roomsInFacility = new ArrayList<>();

        public FacilityBuilder(String name, int facilityID){
            this.name = name;
            this.facilityID = facilityID;
        }

        public FacilityBuilder withPhone(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public  FacilityBuilder withDescription(String description){
            this.description = description;
            return this;
        }

        public Facility build() {
            return new Facility(this);
        }
    }
    private Facility(FacilityBuilder b){
        this.name = b.name;
        this.facilityID = b.facilityID;
        this.phoneNumber = b.phoneNumber;
        this.description = b.description;
        this.roomsInFacility = b.roomsInFacility;
    }
}
