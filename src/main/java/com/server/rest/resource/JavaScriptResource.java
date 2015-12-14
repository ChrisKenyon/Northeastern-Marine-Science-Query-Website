package com.server.rest.resource;

import com.server.dao.JavaScriptRepository;
import com.server.model.JavaScript;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.io.File;
import java.io.IOException;

public class JavaScriptResource extends ServerResource {

    private JavaScriptRepository repository = JavaScriptRepository.getInstance();

    private String jsId;
    @Override
    protected void doInit() throws ResourceException {
        this.jsId = String.valueOf(getAttribute("jsId"));
    }

    @Get("js")
    public Representation getJavaScript()
    {
        JavaScript js = repository.get(jsId);
        if (js == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return new FileRepresentation(new File(js.getFilePath()), MediaType.TEXT_HTML);
    }
}
