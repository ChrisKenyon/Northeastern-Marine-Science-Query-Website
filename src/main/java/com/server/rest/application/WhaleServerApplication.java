package com.server.rest.application;

import com.server.rest.resource.*;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

public class WhaleServerApplication extends Application {

    public WhaleServerApplication() {
        setName("SavingWhales");
        setDescription("Saving whales is our job");
        setOwner("Team9 Save the Whales");
        setAuthor("Chris Kenyon");
    }

    @Override
    public Restlet createInboundRoot() {
        String rootUri="C:\\Users\\normal\\Documents\\Northeastern University\\Fall 2015\\Software Dev\\HowToSaveWhales\\src\\main\\resources\\";
        Directory directory = new Directory(getContext(),rootUri);
        directory.setDeeplyAccessible(true);

        Router router = new Router(getContext());
        router.attach("/", HtmlResource.class);
        router.attach("/pages", HtmlListResource.class);
        router.attach("/{htmlId}", HtmlResource.class);
        router.attach("/js", JavaScriptListResource.class);
        router.attach("/js/{jsId}", JavaScriptResource.class);
        router.attach("/css", CSSListResource.class);
        router.attach("/css/{cssId}", CSSResource.class);
        router.attach("/data", JSONListResource.class);
        router.attach("/data/{jsonMsg}", JSONResource.class);
        router.attach("/get/options", JSONResource.class);
        return router;
    }
}
