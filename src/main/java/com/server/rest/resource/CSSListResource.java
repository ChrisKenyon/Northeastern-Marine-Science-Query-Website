package com.server.rest.resource;

import com.server.dao.CSSRepository;
import com.server.model.CSS;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.io.IOException;

public class CSSListResource extends ServerResource {

    private CSSRepository repository = CSSRepository.getInstance();

    @Get
    public Representation list() {
        return new JacksonRepresentation<>(repository.list());
    }
}
