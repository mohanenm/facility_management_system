package com.fms.domainLayer.maintenance;

import com.fms.domainLayer.common.FMSException;
import com.google.common.collect.Range;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;

public interface IMaintenanceCostCalculator {

    HashMap<String, Double> calcMaintenanceCostForFacility(
            int facilityId, Range<LocalDateTime> costPeriod) throws FMSException, SQLException;

}
