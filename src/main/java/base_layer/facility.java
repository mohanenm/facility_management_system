public class Facility {

    private int facilityID;
    private FacilityDetail detailsAboutFacility;

    public Facility() {}

    public FacilityDetail getDetailsAboutFacility() {
        return detailsAboutFacility;
    }

    public void setDetailsAboutFacility(FacilityDetail detailsAboutFacility) {
        this.detailsAboutFacility = detailsAboutFacility;
    }

    public void setFacilityID(int facilityID) {
        this.facilityID = facilityID;
    }

    public int getFacilityID() {
        return facilityID;
    }


}
