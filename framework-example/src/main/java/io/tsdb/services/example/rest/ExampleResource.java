package io.tsdb.services.example.rest;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import io.tsdb.services.example.providers.SayingProvider;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;

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
@Path("/hello")
public class ExampleResource {
    @Inject
    private SayingProvider saying;

    @GET
    @Path("{name}")
    @PermitAll
    @RequiresGuest
    @Produces(MediaType.APPLICATION_JSON)
    public String sayAnonHello(@PathParam("name") String name) {
        saying.setName(name);
        return saying.getName();
    }

    @GET
    @Path("/secure")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @RequiresAuthentication
    public String sayHello() {
        Subject currentUser = SecurityUtils.getSubject();
        saying.setName(currentUser.getPrincipal().toString());
        return saying.getName();
    }
}