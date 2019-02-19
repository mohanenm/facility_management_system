package base_layer;

public class Facility {

    private int facilityID;
    private FacilityDetails detailsAboutFacility;

    public Facility() {}

    public FacilityDetails getDetailsAboutFacility() {
        return detailsAboutFacility;
    }

    public void setDetailsAboutFacility(FacilityDetails detailsAboutFacility) {
        this.detailsAboutFacility = detailsAboutFacility;
    }

    public void setFacilityID(int facilityID) {
        this.facilityID = facilityID;
    }

    public int getFacilityID() {
        return facilityID;
    }


}
