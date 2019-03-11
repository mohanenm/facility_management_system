package com.fms.domainLayer.maintenance;

import com.fms.domainLayer.common.FMSException;
import com.fms.domainLayer.common.FacilityErrorCode;
import com.fms.dal.DBMaintenance;
import com.google.common.collect.Range;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class MaintenanceCostCalculator {
    DBMaintenance dbMaintenance;

    Logger logger = LogManager.getLogger();

    public MaintenanceCostCalculator() {
        try {
            dbMaintenance = new DBMaintenance();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Could not initialize `DBMaintenance` -> " + e.toString());
        }
    }
    public HashMap<String, Double> calcMaintenanceCostForFacility
            (int facilityId, Range<LocalDateTime> costPeriod)
            throws FMSException, SQLException {
        try {
            return dbMaintenance.calcMaintenanceCostForFacility(facilityId, costPeriod);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to calculate the total maintenance cost, excp: " + e);
            throw new FMSException(FacilityErrorCode.UNABLE_TO_CALCULATE_COST,
                    "Could not calculate the total maintenance cost for this facility.");
        }
    }
}
