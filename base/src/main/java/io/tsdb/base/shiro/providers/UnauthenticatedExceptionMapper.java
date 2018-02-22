package io.tsdb.base.shiro.providers;

import io.tsdb.base.service.responses.GenericExceptionMapper;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author jcreasy
 */
@Provider
public class UnauthenticatedExceptionMapper implements ExceptionMapper<UnauthenticatedException> {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UnauthorizedExceptionMapper.class);

    @Override
    public Response toResponse(UnauthenticatedException exception) {
        LOGGER.debug("Handled Exception: {}", exception.toString());
        return GenericExceptionMapper.genericResponse(exception, Response.Status.UNAUTHORIZED.getStatusCode());
    }
}