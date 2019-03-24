package com.fms.domainLayer.services;

import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.facility.Facility;
import com.fms.domainLayer.facility.IBuilding;
import com.fms.domainLayer.facility.IFacility;

import java.util.ArrayList;
import java.util.List;

public interface IFacilityService {

    ArrayList<Facility> listFacilities();
    IFacility addNewFacility(String name, String description) throws FMSException;
    void removeFacility(int facilityId) throws FMSException;
    IFacility addFacilityDetail(int facilityId, List<IBuilding> buildings)
            throws FMSException;
    IFacility getFacilityInformation(int facilityId) throws FMSException;

}
