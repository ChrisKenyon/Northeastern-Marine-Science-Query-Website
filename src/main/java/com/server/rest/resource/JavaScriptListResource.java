package com.server.rest.resource;

import com.server.dao.HtmlRepository;
import com.server.model.Html;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.io.IOException;

public class JavaScriptListResource extends ServerResource {

    private HtmlRepository repository = HtmlRepository.getInstance();

    @Get
    public Representation list() {
        return new JacksonRepresentation<>(repository.list());
    }
    
}
