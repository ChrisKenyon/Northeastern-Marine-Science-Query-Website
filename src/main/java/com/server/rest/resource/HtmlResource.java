package com.server.rest.resource;

import com.server.dao.HtmlRepository;
import com.server.model.Html;
import org.restlet.data.*;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.io.File;
import java.io.IOException;

public class HtmlResource extends ServerResource {

    private HtmlRepository repository = HtmlRepository.getInstance();

    private Long htmlId;

    @Override
    protected void doInit() throws ResourceException {
        if (getAttribute("htmlId") == null)
            this.htmlId = 0L;
        else this.htmlId = Long.valueOf(getAttribute("htmlId"));
    }

    @Get("html")
    public Representation getHtml()
    {
        Html html = repository.get(htmlId);
        if (html == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return new FileRepresentation(new File(html.getFilePath()), MediaType.TEXT_HTML);
    }
}
