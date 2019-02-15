


package base_layer;

public class Facility {

    private int facilityID;
    private facilitydetail detailsAboutFacility;

    public Facility() {}

    public facilitydetail getDetailsAboutFacility() {
        return detailsAboutFacility;
    }

    public void setDetailsAboutFacility(facilitydetail detailsAboutFacility) {
        this.detailsAboutFacility = detailsAboutFacility;
    }

    public void setFacilityID(int facilityID) {
        this.facilityID = facilityID;
    }

    public int getFacilityID() {
        return facilityID;
    }


}
