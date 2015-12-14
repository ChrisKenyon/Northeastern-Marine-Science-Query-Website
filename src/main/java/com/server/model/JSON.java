package com.server.model;

import com.database.WhalesDBAccessor;
import com.fasterxml.jackson.databind.deser.Deserializers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JSON {

    private String BaseURI = "C:\\Users\\cjken\\IdeaProjects\\WhaleServer\\src\\main\\resources";

    private String id;
    private String filePath = BaseURI + id + ".csv";
    boolean idSet = false;

    private String site_id;
    private List<String> locations;


    private List<String> countries;


    private List<String> states;
    private List<String> types;
    private List<String> exposures;
    private List<String> zones;
    private List<String> sub_zones;
    private String start_date_time;
    private String end_date_time;
    private boolean done;

    public JSON() {
    }

    public JSON(String siteId, List<String> locations, List<String> countries, List<String> states,
                List<String> sensorTypes, List<String> exposures, List<String> zones, List<String> subzones)
    {
        this.site_id = siteId;
        this.locations = locations;
        this.countries = countries;
        this.states = states;
        this.types = sensorTypes;
        this.exposures = exposures;
        this.zones = zones;
        this.sub_zones = subzones;
    }

    public void createResult() throws FileNotFoundException, SQLException {
        if (!idSet) throw new FileNotFoundException("Id wasn't set");

        WhalesDBAccessor dbAccessor = new WhalesDBAccessor();
        String rs = dbAccessor.getData(countries, states, types, exposures, zones, sub_zones, locations, start_date_time, end_date_time);
        PrintWriter csvOutput = new PrintWriter(filePath);
        csvOutput.print("Microsite, Date/Time, Temperature (C)\n");
        csvOutput.print(rs);
        csvOutput.close();
        setDone(true);
    }

    public String getResultFile(){
        return filePath;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        idSet = true;
        this.id = id;
        filePath = BaseURI + id + ".csv";
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    public String getSite_id() {
        return site_id;
    }
    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
    public List<String> getLocations() {
        return locations;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }
    public List<String> getStates() {
        return states;
    }

    public void setExposures(List<String> exposures) {
        this.exposures = exposures;
    }
    public List<String> getExposures() {
        return exposures;
    }

    public void setSub_zones(List<String> sub_zones) {
        this.sub_zones = sub_zones;
    }
    public List<String> getSub_zones() {
        return sub_zones;
    }

    public void setStart_date_time(String start_date_time) {
        this.start_date_time = start_date_time;
    }
    public String getStart_date_time() {
        return start_date_time;
    }

    public void setEnd_date_time(String end_date_time) {
        this.end_date_time = end_date_time;
    }
    public String getEnd_date_time() {
        return end_date_time;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }
    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setZones(List<String> zones) {
        this.zones = zones;
    }

    public List<String> getZones() {
        return zones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JSON json = (JSON) o;
        return id.equals(json.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
