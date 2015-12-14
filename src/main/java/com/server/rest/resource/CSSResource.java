package com.server.rest.resource;

import com.server.dao.CSSRepository;
import com.server.model.CSS;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.io.File;

public class CSSResource extends ServerResource {

    private CSSRepository repository = CSSRepository.getInstance();

    private String cssId;

    @Override
    protected void doInit() throws ResourceException {
        this.cssId = String.valueOf(getAttribute("cssId"));
    }

    @Get("css")
    public Representation getCSS()
    {
        CSS css = repository.get(cssId);
        if (css == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return new FileRepresentation(new File(css.getFilePath()), MediaType.TEXT_CSS);
    }
}
