package io.tsdb.services.framework.base.service.filters;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * This is an example filter, and it demonstrates how to do user authentication without Shiro
 * @author jcreasy
 */
@SuppressWarnings("unused")
public abstract class SecurityContainerRequestFilter implements ContainerRequestFilter
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityContainerRequestFilter.class);

    @SuppressWarnings("WeakerAccess")
    public static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<>());
    @SuppressWarnings("WeakerAccess")
    public static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<>());
    @SuppressWarnings("WeakerAccess")
    public static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<>());

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        LOGGER.info("Processing Request for {}", requestContext.getUriInfo().getAbsolutePath());
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();

        //Access allowed for all
        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        //Access denied for all
        if (method.isAnnotationPresent(DenyAll.class)) {
            requestContext.abortWith(ACCESS_FORBIDDEN);
            return;
        }

        try {
            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            //Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            //If no authorization information present; block access
            if (authorization == null || authorization.isEmpty()) {
                LOGGER.info("Access Denied, no authorization header present");
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }

            //Get encoded username and password
            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

            //Decode username and password
            String usernameAndPassword;
            try {
                usernameAndPassword = new String(Base64.decode(encodedUserPassword));
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                requestContext.abortWith(SERVER_ERROR);
                return;
            }

            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            //Verifying Username and password
            LOGGER.debug("Username is: {}", username);
            LOGGER.trace("Password is: {}", password);

            //Verify user access
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

                //Is user valid?
                if (!isUserAllowed(username, password, rolesSet)) {
                    LOGGER.info("Access Denied, user is not valid");
                    requestContext.abortWith(ACCESS_DENIED);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            requestContext.abortWith(SERVER_ERROR);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public abstract boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet);
}