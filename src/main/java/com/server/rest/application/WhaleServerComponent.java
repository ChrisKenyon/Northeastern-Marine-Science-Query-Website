package com.server.rest.application;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;

public class WhaleServerComponent extends Component {

    public static void main(String[] args) {
        try{
            new WhaleServerComponent().start();
        }
        catch (Exception ex)
        {
            System.out.println("We don't like exceptions");
        }
    }

    public WhaleServerComponent() {
        Server server = new Server(Protocol.HTTP, 8182);
        getServers().add(server);
        //server.getContext().getParameters().set("tracing", "true");

        getClients().add(Protocol.FILE);

        getDefaultHost().attachDefault(new WhaleServerApplication());

        System.out.println("Server started on port 8012.");
        System.out.println("Application is now available on http://localhost:8182/web/index.html");
    }
}
