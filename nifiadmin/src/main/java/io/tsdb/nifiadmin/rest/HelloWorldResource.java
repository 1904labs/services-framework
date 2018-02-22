package io.tsdb.nifiadmin.rest;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import io.tsdb.nifiadmin.providers.SayingProvider;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

import javax.annotation.security.PermitAll;
import javax.servlet.RequestDispatcher;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author jcreasy
 */

@RequestScoped
@Path("/hello")
public class HelloWorldResource {
    @Inject
    private SayingProvider saying;

    @GET
    @Path("{name}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public String sayAnonHello() {
        saying.setName("Anonymous");
        return saying.getName();
    }

    @GET
    @Path("/secure/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @RequiresAuthentication
    public String sayHello(@PathParam("name") String name) {
        saying.setName(name);
        return saying.getName();
    }
}