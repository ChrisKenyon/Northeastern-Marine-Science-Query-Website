package com.database;
import com.server.model.JSON;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Date;

/**
 * Created by cjken on 12/1/2015.
 */
enum QueryType { SELECT, INSERT , UPDATE, DELETE  } // kate do you think we will need update or delete?
enum WhaleDatabase  { SITES, MICROSITES, SENSOR_DATA }
public class WhalesDBAccessor {
    Connection connection;

    public WhalesDBAccessor() {
        try {
            this.connection = this.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getData(List<String> countries, List<String> states, List<String> sensorTypes,
                          List<String> exposures, List<String> zones, List<String> subzones,
                          List<String> locations, String start, String end){

        String select = "SELECT  m.DISPLAY_ID, r.DATE_TIME, r.TEMPERATURE";
        String from = "FROM SITES s join MICROSITES m on (s.ID = m.SITE_ID) " +
                "join SENSOR_RECORD r on (r.MICROSITE_ID = m.ID)";
        String where = "WHERE ";

        Boolean whereHasFirst = true;
        //Match countries
        if (countries != null && !countries.isEmpty()) {
            if(!whereHasFirst){
                where += " and ";
            }
            where += buildOrClause("s.COUNTRY", countries);
            whereHasFirst = false;
        }

        //Match state or province
        if (states != null && !states.isEmpty()) {
            if(!whereHasFirst){
                where += " and ";
            }
            where += buildOrClause("s.STATE_PROVINCE", states);
            whereHasFirst = false;
        }

        //Match sensor types
        if (sensorTypes != null && !sensorTypes.isEmpty()) {
            if(!whereHasFirst){
                where += " and ";
            }
            where += buildOrClause("m.BIOMIMIC", sensorTypes);
            whereHasFirst = false;
        }

        //Match exposure
        if (exposures != null && !exposures.isEmpty()) {
            if(!whereHasFirst){
                where += " and ";
            }
            where += buildOrClause("m.WAVE_EXPOSURE", exposures);
            whereHasFirst = false;
        }

        //Match zones
        if (zones != null && !zones.isEmpty()) {
            if(!whereHasFirst){
                where += " and ";
            }
            where += buildOrClause("m.ZONE", zones);
            whereHasFirst = false;
        }

        //Match subzones
        if (subzones != null && !subzones.isEmpty()) {
            if(!whereHasFirst){
                where += " and ";
            }
            where += buildOrClause("m.SUB_ZONE", subzones);
            whereHasFirst = false;
        }

        //Match subzones
        if (subzones != null && !subzones.isEmpty()) {
            if(!whereHasFirst){
                where += " and ";
            }
            where += buildOrClause("m.SUB_ZONE", subzones);
            whereHasFirst = false;
        }

        //Match location names
        if (locations != null && !locations.isEmpty()) {
            if(!whereHasFirst){
                where += " and ";
            }
            where += buildOrClause("s.LOCATION_NAME", locations);
            whereHasFirst = false;
        }
        if (start != null && start != "")
            where += "  and r.DATE_TIME >= '" + start + "'";
        if(end != null && end != "")
            where += " and r.DATE_TIME <= '" + end + "'";
        String order = " ORDER BY m.DISPLAY_ID, r.DATE_TIME";

        String query = select + " " + from;
        if (!whereHasFirst) {
            query += " " + where;
        }

        query += order + ";";

        System.out.println(query); //TODO debug statement

        Statement statement = null;
        ResultSet resultSet;

        String resultString = "";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                resultString += resultSet.getString("DISPLAY_ID") + ", ";
                resultString += resultSet.getString("DATE_TIME") + ", ";
                resultString += resultSet.getString("TEMPERATURE") + "\n";
            }
            System.out.print(resultString);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }



        return resultString;
    }



    public String getSite(Double lati, Double longi, String country,  String state, String name, String displayId) {
        String select = "SELECT * ";
        String from = "FROM SITES ";
        String where = "";

        Boolean first = true;

        if(lati != null){
            if(!first){
                where += " and ";
            }
            first = false;
            where += "LATITUDE = " + lati;
        }
        if(longi != null){
            if(!first){
                where += " and ";
            }
            first = false;
            where += "LONGITUDE = " + longi;
        }
        if(country != null && country != ""){
            if(!first){
                where += " and ";
            }
            first = false;
            where += buildVarCharEquals("COUNTRY", country);
        }
        if(state != null && state != ""){
            if(!first){
                where += " and ";
            }
            first = false;
            where += buildVarCharEquals("STATE_PROVINCE ", state);
        }

        String query = select + from + where;
        query += ";";

        System.out.println(query); //TODO debug statement

        Statement statement = null;
        ResultSet resultSet;

        String resultString = "";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                resultString += resultSet.getString("DISPLAY_ID") + ", ";
                resultString += resultSet.getString("LOCATION_NAME") + ", ";
                resultString += resultSet.getString("COUNTRY") + ", ";
                resultString += resultSet.getString("STATE_PROVINCE") + ", ";
                resultString += resultSet.getString("LATITUDE") + ", ";
                resultString += resultSet.getString("LONGITUDE") + "\n";
            }
            System.out.print(resultString);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }



        return resultString;
    }



    public List<String> getExposures(){
        String query = "SELECT DISTINCT(WAVE_EXPOSURE) FROM MICROSITES;";

        ArrayList<String> resultList = new ArrayList();

        Statement statement = null;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                resultList.add(resultSet.getString("WAVE_EXPOSURE"));
            }
            System.out.println(resultList);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return resultList;

    }

    public List<String> getZones(){
        String query = "SELECT DISTINCT(ZONE) FROM MICROSITES;";

        ArrayList<String> resultList = new ArrayList();

        Statement statement = null;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                resultList.add(resultSet.getString("ZONE"));
            }
            System.out.println(resultList);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return resultList;

    }

    public List<String> getTypes(){
        String query = "SELECT DISTINCT(BIOMIMIC) FROM MICROSITES;";

        ArrayList<String> resultList = new ArrayList();

        Statement statement = null;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                resultList.add(resultSet.getString("BIOMIMIC"));
            }
            System.out.println(resultList);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return resultList;

    }

    public List<String> getSubZones(){
        String query = "SELECT DISTINCT(SUB_ZONE) FROM MICROSITES;";

        ArrayList<String> resultList = new ArrayList();

        Statement statement = null;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                resultList.add(resultSet.getString("SUB_ZONE"));
            }
            System.out.println(resultList);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return resultList;

    }

    public List<String> getLocationNames(){
        String query = "SELECT DISTINCT(LOCATION_NAME) FROM SITES;";

        ArrayList<String> resultList = new ArrayList();

        Statement statement = null;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                resultList.add(resultSet.getString("LOCATION_NAME"));
            }
            System.out.println(resultList);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return resultList;

    }

    public List<String> getCountries(){
        String query = "SELECT DISTINCT(COUNTRY) FROM SITES;";

        ArrayList<String> resultList = new ArrayList();

        Statement statement = null;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                resultList.add(resultSet.getString("COUNTRY"));
            }
            System.out.println(resultList);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return resultList;

    }

    public List<String> getStates(){
        String query = "SELECT DISTINCT(STATE_PROVINCE) FROM SITES;";

        ArrayList<String> resultList = new ArrayList();

        Statement statement = null;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                resultList.add(resultSet.getString("STATE_PROVINCE"));
            }
            System.out.println(resultList);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return resultList;

    }

    public String getSites(){
        //TODO

        String select = "SELECT * ";
        String from = "FROM SITES ";

        String query = select + from;

        query += ";";

        System.out.println(query); //TODO debug statement

        Statement statement = null;
        ResultSet resultSet;

        String resultString = "";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                resultString += resultSet.getString("DISPLAY_ID") + ", ";
                resultString += resultSet.getString("LOCATION_NAME") + ", ";
                resultString += resultSet.getString("COUNTRY") + ", ";
                resultString += resultSet.getString("STATE_PROVINCE") + ", ";
                resultString += resultSet.getString("LATITUDE") + ", ";
                resultString += resultSet.getString("LONGITUDE") + "\n";
            }
            System.out.print(resultString);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }



        return resultString;
    }

    public Integer getSiteID(String displayId){
        String query = "SELECT ID FROM SITES WHERE DISPLAY_ID = '" + displayId + "';";

        System.out.println(query);

        Statement statement = null;
        ResultSet resultSet;

        Integer result = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                result = resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;

    }

    public Integer getSensorID(String displayId){
        String query = "SELECT ID FROM MICROSITES WHERE DISPLAY_ID = '" + displayId + "';";

        System.out.println(query);

        Statement statement = null;
        ResultSet resultSet;

        Integer result = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                result = resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;

    }

    public Boolean addSite(Double lati, Double longi, String country, String state, String locationName, String displayId){
        //TODO
        String insert = "INSERT INTO SITES (LATITUDE, LONGITUDE, COUNTRY, STATE_PROVINCE, LOCATION_NAME, DISPLAY_ID) ";
        String values = "VALUES ( " + lati + ", " + longi + ", '" + country + "', '" + state + "', '"
                        + locationName + "', '" + displayId + "');";

        String query = insert + values;
        System.out.println(query);


        Statement statement = null;
        Integer result;
        try {
            statement = connection.createStatement();
            result = statement.executeUpdate(query);
            System.out.println(result + " lines changed");

            if (result < 1)
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public Boolean addSensor(String siteDisplayId, String displayId, String type, String zone, String subZone,
                             String exposure, Double tideHeight){
        //TODO
        Integer siteId = getSiteID(siteDisplayId);
        if (siteId == null) {
            return false;
        }

        String insert = "INSERT INTO MICROSITES (DISPLAY_ID, BIOMIMIC, ZONE, SUB_ZONE, WAVE_EXPOSURE, TIDE_HEIGHT, SITE_ID) ";
        String values = "VALUES ('" + displayId + "', '" + type + "', '" + zone + "', '" + subZone + "', '" +
                        exposure + "', " + tideHeight + ", " + siteId + ");";

        String query = insert + values;
        System.out.println(query);

        Statement statement = null;
        Integer result;
        try {
            statement = connection.createStatement();
            result = statement.executeUpdate(query);
            System.out.println(result + " lines changed");
            if (result < 1)
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /* format refers to the format of the date given in dateTime and must be specified as a SimpleDateTimeFormat pattern
    *
    * */
    public Boolean addSensorData(String sensorDisplayId, String dateTime, Double temperature, String format ){
        //TODO
        SimpleDateFormat given = new SimpleDateFormat(format);
        SimpleDateFormat needed = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;

        try {
            date = given.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        String insertDate = needed.format(date);

        System.out.println("Date is " + insertDate);

        Integer sensorId = getSensorID(sensorDisplayId);
        if (sensorId == null) {
            return false;
        }

        String insert = "INSERT INTO SENSOR_RECORD (MICROSITE_ID, DATE_TIME, TEMPERATURE) ";
        String values = "VALUES (" + sensorId + ", '" + insertDate + "', " + temperature + ");";

        String query = insert + values;
        System.out.println(query);

        Statement statement = null;
        Integer result;
        try {
            statement = connection.createStatement();
            result = statement.executeUpdate(query);
            System.out.println(result + " lines changed");
            if (result < 1)
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private String buildOrClause(String field, List<String> matches) {
        String clause = "(";
        Boolean first = true;

        for(String match : matches){
            if(!first){
                clause += " or ";
            }
            first = false;

            clause += buildVarCharEquals(field, match);

        }
        clause += ")";
        return clause;
    }

    private String buildVarCharEquals(String field, String value){
        return field + " = '" + value + "'";
    }

    // For very basic queries - Not sure if this is how we should be doing it but just wanted to get some ideas down
    // not very good code, probably should've used string builder. All the shits I give: _______
    // Examples:
    // select * from sites where LOCATION = 'Whales Waterfront';
    // insert into sites (LATITUDE, LONGITUDE, COUNTRY, STATE_PROVINCE, LOCATION_NAME, DISPLAY_ID) values (30.00, 30.00, 'USA', 'MA', 'Waterfront', '1234');
    // update sites set LOCATION_NAME = 'Whales Waterfront' where ID = 1;
    // delete * from sites where LOCATION_NAME = 'Whales Waterfront'
    public String createQueryForSites(String location, String country, String state, Double latitude, Double longitude, Integer id, QueryType queryType) throws InvalidArgumentException {
        String query = makePrefix(queryType) + "SITES ";

        boolean first = true;
        switch (queryType)
        {
            case SELECT:
                first = true;
                if (!location.isEmpty())
                {
                    query += "WHERE LOCATION_NAME = '" + location + "'";
                    first = false;
                }
                if (!country.isEmpty())
                {
                    if (first) {
                        query += "WHERE COUNTRY = '" + country + "' ";
                        first = false;
                    }
                    else query += "&& COUNTRY = '" + country + "'";
                }
                if (!state.isEmpty())
                {
                    if (first) {
                        query += "WHERE STATE_PROVINCE = '" + state + "' ";
                        first = false;
                    }
                    else query += "&& STATE_PROVINCE = '" + state + "'";
                }
                if (!(latitude == 0 && longitude == 0))
                {
                    if (first) {
                        query += "WHERE LATITUDE = " + latitude + " && LONGITUDE = " + longitude;
                    }
                    else query += "&& LATITUDE = " + latitude + " && LONGITUDE = " + longitude;
                }
                query+=";";
                break;
            case INSERT:
                if (location.isEmpty() || country.isEmpty() || state.isEmpty())
                    throw new InvalidArgumentException(new String[]{location,country,state});
                query += "(LATITUDE, LONGITUDE, COUNTRY, STATE_PROVINCE, LOCATION_NAME, DISPLAY_ID) values ("
                        + latitude.toString() + ", " + longitude.toString() + ", '" + location + "', '"
                        + country + "', '" + state + "', 'WHALE-LOC-" + location +  "');";
                break;
            // update and delete work by id
            case UPDATE:
                query += "SET ";
                first = true;
                if (!location.isEmpty())
                {
                    query += "LOCATION_NAME = '" + location + "'";
                    first = false;
                }
                if (!country.isEmpty())
                {
                    if (first) {
                        query += "COUNTRY = '" + country + "' ";
                        first = false;
                    }
                    else query += "&& COUNTRY = '" + country + "'";
                }
                if (!state.isEmpty())
                {
                    if (first) {
                        query += "STATE_PROVINCE = '" + state + "' ";
                        first = false;
                    }
                    else query += "&& STATE_PROVINCE = '" + state + "'";
                }
                if (!(latitude == 0 && longitude == 0))
                {
                    if (first) {
                        query += "LATITUDE = " + latitude + " && LONGITUDE = " + longitude;
                    }
                    else query += "&& LATITUDE = " + latitude + " && LONGITUDE = " + longitude;
                }
                // fall through
            case DELETE:
                query += "WHERE ID = " + id.toString() + ";";
                break;
        }

        return query;
    }

    private String makePrefix(QueryType queryType)
    {
        switch (queryType)
        {
            case SELECT:
                return "SELECT * FROM ";
            case INSERT:
                return "INSERT INTO ";
            case UPDATE:
                return "UPDATE ";
            case DELETE:
                return "DELETE * FROM ";
        }
        return "error";
    }

    private Connection getConnection() throws SQLException {

        Properties connProps = new Properties();
        connProps.put("user", "root");
        connProps.put("password", "whale");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/SAVE_THE_WHALES", connProps);
    }
}
