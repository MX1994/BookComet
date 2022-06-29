package com.bookcomet;

import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author mx
 */
public class Main 
{
    public static final String BASE_URI = "http://localhost:8080/";
    
    /**       
     * Starts HTTP server exposing JAX-RS resources defined in this application.
     */
    public static HttpServer startServer() 
    {
        // create a resource config that scans JAX-RS resources and providers in com.bookcomet package
        final ResourceConfig rc = new ResourceConfig().packages("com.bookcomet");
        rc.register(JacksonFeature.class);
        rc.register(DeclarativeLinkingFeature.class);
        // start a new instance of http server exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException 
    {
        
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }

}
