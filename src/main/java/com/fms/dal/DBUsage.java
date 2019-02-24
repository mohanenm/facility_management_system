package com.fms.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import com.fms.client.facility.*;
import com.fms.client.usage.*;
import com.fms.model.Building;
import com.fms.model.Facility;
import com.fms.model.FacilityDetail;

public class DBUsage {

    public static ArrayList<Facility> readAllFacilities() {
        ArrayList<Facility> result = new ArrayList<>();
        try {

            Statement st = DBConnection.getConnection().createStatement();
            String query = "SELECT id, name, description  FROM facility";
            ResultSet facilityResultSet = st.executeQuery(query);


            while (facilityResultSet.next()) {
                result.add(new Facility(facilityResultSet.getInt("id"),
                        facilityResultSet.getString("name"),
                        facilityResultSet.getString("description"))
                );

            }

            //close to manage resources
            facilityResultSet.close();
        } catch (SQLException e) {
            System.out.println("caught exception: " + e.toString());
        }
        return result;

    public boolean isInUseDuringInterval(int facilityId, FacilityDetail, facilityDetail) throws SQLException {
                PreparedStatement preparedStatement = DBConnection
                        .getConnection()
                        .prepareStatement("select id from facility where id = ?");
                preparedStatement.setInt(1, facilityId);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();

                for(Building building: facilityDetail.getBuildings()) {
                    createBuilding(facilityId, building);
                }
            }

            //check if dates in database overlap with input interval
            while (useRS.next()) {
                LocalDate assignStart = useRS.getDate("start_date").toLocalDate();
                LocalDate assignEnd = useRS.getDate("end_date").toLocalDate();
                if (facUse.getStartDate().isBefore(assignEnd) && (assignStart.isBefore(facUse.getEndDate()) ||
                        assignStart.equals(facUse.getEndDate()))) {
                    result = true;
                    break;
                }
            }

            //close to manage resources
            useRS.close();
            st.close();

        } catch (SQLException se) {
            System.err.println("UseDAO: Threw a SQLException checking if "
                    + "facility is in use during an interval.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return result;

    }


    /***
     * Lists all the usage assignments at a particular facility from the use table.
     * Usage assignments are first sorted by room number and then by date.
     * @param fac Facility to list the usage assignments for
     * @return a list of FacilityUse objects containing a room number, start date, and end date
     */
    public List<FacilityUse> listActualUsage(Facility fac) {

        List<FacilityUse> listOfUsage = new ArrayList<FacilityUse>();

        try {

            Statement st = DBHelper.getConnection().createStatement();
            String listUsageQuery = "SELECT * FROM use WHERE facility_id = '" +
                    fac.getFacilityID() + "' ORDER BY room_number, start_date";

            ResultSet useRS = st.executeQuery(listUsageQuery);
            System.out.println("UseDAO: *************** Query " + listUsageQuery + "\n");

            while (useRS.next()) {
                FacilityUse use = new FacilityUse();
                use.setFacilityID(fac.getFacilityID());
                use.setRoomNumber(useRS.getInt("room_number"));
                use.setStartDate(useRS.getDate("start_date").toLocalDate());
                use.setEndDate(useRS.getDate("end_date").toLocalDate());
                listOfUsage.add(use);
            }

            //close to manage resources
            useRS.close();
            st.close();
            return listOfUsage;

        } catch (SQLException se) {
            System.err.println("UseDAO: Threw a SQLException retreiving list of usage from use table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return null;

    }

    /***
     * Gets the creation date of a facility, which is the earliest start date
     * assigned in the use table for that facility.
     * @param fac Facility to get the start date
     * @return start date for the facility
     */
    public LocalDate getFacilityStartDate(Facility fac) {

        LocalDate facilityStartDate = null;
        try {

            Statement st = DBConnection.getConnection().createStatement();
            String getFacilityStartDateQuery = "SELECT start_date FROM use WHERE facility = '" +
                    fac.getFacilityDetail() + "' ORDER BY start_date LIMIT 1";

            ResultSet useRS = st.executeQuery(getFacilityStartDateQuery);
            System.out.println("UseDAO: *************** Query " + getFacilityStartDateQuery + "\n");

            while (useRS.next()) {
                facilityStartDate = useRS.getDate("start_date").toLocalDate();
            }

            //close to manage resources
            useRS.close();
            st.close();

        } catch (SQLException se) {
            System.err.println("UseDAO: Threw a SQLException retreiving facility start date "
                    + "from the use table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return facilityStartDate;
    }


}