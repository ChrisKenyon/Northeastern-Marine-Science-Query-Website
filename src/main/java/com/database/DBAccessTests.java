package com.database;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by cjken on 12/1/2015.
 */
public class DBAccessTests {

    WhalesDBAccessor dbA;

    DBAccessTests()
    {
        dbA = new WhalesDBAccessor();
    }

    public static void main(String[] args) throws Exception {
        DBAccessTests tests = new DBAccessTests();
        tests.test();
    }

    public void test()
    {
        try {
            Connection connection = dbA.connection;
            testSqlSites(connection);
        }
        catch (Exception ex)
        {
            System.out.print(ex.getMessage());
        }
    }


    public void testSqlSites(Connection connection) throws SQLException, InvalidArgumentException {
        Statement statement = connection.createStatement();
        Double lat = 0.0, lng = 0.0;
        String country = "", state = "", loc = "";
        ResultSet resultSet;
        String query;

        System.out.println("The Test Should Be Running?");
/* insert isnt working even though the query is right? */
        // test query builder - insert
     /*   lat = 5.0; lng = 5.0; country = "Canada"; state = "Vancouver"; loc = "Canada Site, ay?";
        query = dbA.createQueryForSites(loc, country, state, lat, lng, 0, QueryType.INSERT);
        System.out.print("JSON Built: " + query);
        resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            lat = resultSet.getDouble("LATITUDE");
            lng = resultSet.getDouble("LONGITUDE");
            country = resultSet.getString("COUNTRY");
            state = resultSet.getString("STATE_PROVINCE");
            loc = resultSet.getString("LOCATION_NAME");
            System.out.printf("Insert JSON insert test\nLocation: %s in %s,%s \nCo-ord: (%s,%s)\n", loc, state, country, lat.toString(), lng.toString());
        }*/

        // basic test
        query = "SELECT * FROM SITES";
        resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            lat = resultSet.getDouble("LATITUDE");
            lng = resultSet.getDouble("LONGITUDE");
            country = resultSet.getString("COUNTRY");
            state = resultSet.getString("STATE_PROVINCE");
            loc = resultSet.getString("LOCATION_NAME");
            System.out.printf("Basic JSON Test\nLocation: %s in %s,%s \nCo-ord: (%s,%s)\n", loc, state, country, lat.toString(), lng.toString());
        }

        // test query builder - select
        lat = 0.0; lng = 0.0; country = ""; state = ""; loc = "";
        query = dbA.createQueryForSites(loc, country, state, lat, lng, 0, QueryType.SELECT);
        System.out.print("JSON Built: " + query + "\n");
        resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            lat = resultSet.getDouble("LATITUDE");
            lng = resultSet.getDouble("LONGITUDE");
            country = resultSet.getString("COUNTRY");
            state = resultSet.getString("STATE_PROVINCE");
            loc = resultSet.getString("LOCATION_NAME");
            System.out.printf("Create JSON select test\nLocation: %s in %s,%s \nCo-ord: (%s,%s)\n", loc, state, country, lat.toString(), lng.toString());
        }

        // test query builder - delete
//        lat = 5.0; lng = 5.0; country = "Canada"; state = "Vancouver"; loc = "Canada Site, ay?";
//        query = dbA.createQueryForSites(loc, country, state, lat, lng, 0, QueryType.SELECT);
//        System.out.print("JSON Built: " + query);
//        resultSet = statement.executeQuery(query);
//        Integer id = 0;
//        while (resultSet.next()) {
//            id = resultSet.getInt("ID");
//            System.out.printf("Create JSON test\nDelete ID = %s", id.toString());
//        }
//        query = dbA.createQueryForSites("", "", "", 0.0, 0.0, id, QueryType.DELETE);
//        resultSet = statement.executeQuery(query);

        getDataTest();
        getOptionsListsTest();
        addDataTests();
    }

    public void getDataTest() {
        System.out.println("Testing getData");

        ArrayList<String> countries = new ArrayList();
        String siteId = null;
        ArrayList<String> states = new ArrayList();
        ArrayList<String> sensorTypes = new ArrayList();
        ArrayList<String> exposures = new ArrayList();
        ArrayList<String> zones = new ArrayList();
        ArrayList<String> subzones = new ArrayList();
        String startTime = null;
        String endTime = null;
        dbA.getData(countries, states, sensorTypes, exposures, zones, subzones, null, startTime, endTime);


        countries.add("USA");
        dbA.getData(countries, states, sensorTypes, exposures, zones, subzones, null, startTime, endTime);

        countries.add("Canada");
        countries.add("Australlia");
        dbA.getData(countries, states, sensorTypes, exposures, zones, subzones, null, startTime, endTime);


    }

    public void getOptionsListsTest(){
        dbA.getCountries();
        dbA.getExposures();
        dbA.getLocationNames();
        dbA.getStates();
        dbA.getTypes();
        dbA.getZones();
        dbA.getSubZones();

    }

    public void addDataTests(){
        Double lati = 10.0;
        Double longi = 10.0;
        String country = "USA";
        String state = "MA";
        String locationName = "NEU";
        String siteDisplayId = "site" + (new Random()).nextInt();

        String sensorDisplayId = "sensor" + (new Random()).nextInt();
        String type = "Robo";
        String zone = "abc";
        String subZone = "123";
        String exposure = "low";
        Double tideHeight = 5.1;

        String dateTime = "01/27/91";
        Double temperature = 20.0;

        String dateFormat = "MM/dd/yy";

        dbA.addSite(lati, longi, country, state, locationName, siteDisplayId);
        dbA.addSensor(siteDisplayId, sensorDisplayId, type, zone, subZone, exposure, tideHeight);
        dbA.addSensorData(sensorDisplayId, dateTime, temperature, dateFormat);


    }
}
