package com.server.rest.resource;
import com.database.WhalesDBAccessor;
import com.server.dao.JSONRepository;
import com.server.model.JSON;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.adapter.HttpRequest;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class JSONResource extends ServerResource {

    private JSONRepository repository = JSONRepository.getInstance();

    @Override
    protected void doInit() throws ResourceException {
    }

    @Post("json")
    public FileRepresentation retrieve(Representation representation) {

        JacksonRepresentation<JSON> jsonRepresentation = new JacksonRepresentation<JSON>(representation, JSON.class);
        JSON json = null;
        try {
            json = jsonRepresentation.getObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (json == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }

        json.setId(((HttpRequest)getRequest()).getHttpCall().getRequestUri());
        repository.create(json);

        try {
            json.createResult();
            String file = json.getResultFile();
            return new FileRepresentation(new File(file), MediaType.TEXT_CSV);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Get("csv")
    public FileRepresentation get()
    {
        JSON json = repository.get(((HttpRequest)getRequest()).getHttpCall().getRequestUri());
        String file = json.getResultFile();
        return new FileRepresentation(new File(file), MediaType.TEXT_CSV);
    }

    @Get("json")
    public Representation getOptions()
    {
        if (getRequestAttributes().containsKey("jsonMsg"))
            return get();
        WhalesDBAccessor dba = new WhalesDBAccessor();
        // query for options
        String siteId = dba.getSites();
        ArrayList<String> locations = (ArrayList<String>)dba.getLocationNames();
        ArrayList<String> countries= (ArrayList<String>)dba.getCountries();
        ArrayList<String> states= (ArrayList<String>)dba.getStates();
        ArrayList<String> sensorTypes = (ArrayList<String>)dba.getTypes();
        ArrayList<String> exposures = (ArrayList<String>)dba.getExposures();
        ArrayList<String> zones = (ArrayList<String>)dba.getZones();
        ArrayList<String> subzones = (ArrayList<String>)dba.getSubZones();
        JSON json = new JSON(siteId,locations,countries,states,sensorTypes,exposures,zones,subzones);
        return new JacksonRepresentation<JSON>(json);
    }
}
