package io.tsdb.stats.rest;

import com.google.inject.servlet.RequestScoped;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author jcreasy
 */

@RequestScoped
@Path("/stats")
public class StatsResource {
    @GET
    @Path("query")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public String sayAnonHello() {
        return "5,4,3,3";
    }

    @GET
    @Path("/secure/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @RequiresAuthentication
    public String sayHello(@PathParam("name") String name) {
        return name;
    }
}