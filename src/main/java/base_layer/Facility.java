package base_layer;
import java.util.ArrayList;
public class Facility {

    private String name;
    private int facilityID;
    private String phoneNumber;
    private String description;
   private ArrayList<Room> roomsInFacility;

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

    public String toString(){
        return("~~~~~~~~~~~~~~~~~~~~~~~~~~" + "\nFacility ID: " + facilityID + "\nFacility Name: " + name + " \nPhone: " + phoneNumber + " \nDescription: " + description + "\n~~~~~~~~~~~~~~~~~~~~~~~~~");
    }


    public static class FacilityBuilder {

        private String name;
        private int facilityID;
        private String phoneNumber;
        private String description;
        private ArrayList<Room> roomsInFacility = new ArrayList<>();

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

        public FacilityBuilder addRoom(ArrayList<Room> roomsInFacility, Room e){
            roomsInFacility.add(e);
            this.roomsInFacility = roomsInFacility;
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
