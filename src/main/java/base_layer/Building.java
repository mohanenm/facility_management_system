package base_layer;

public class Building {

    private int buildingId;
    private String streetAddress;
    private String city;
    private String state;
    private int zip;

    public Building() {}

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public int getZip() {
        return zip;
    }
    public void setZip(int zip) {
        this.zip = zip;
    }

    public String toString(){
        return("\nBuilding ID: " + buildingId + " \n Building Address: " + streetAddress + " " + city + ", " + state + " " + zip);
    }
}
