package com.server.rest.resource;

import com.server.dao.JSONRepository;
import com.server.model.JSON;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.io.IOException;

public class JSONListResource extends ServerResource {

    private JSONRepository repository = JSONRepository.getInstance();

    @Get
    public Representation list() {
        return new JacksonRepresentation<>(repository.list());
    }

}
