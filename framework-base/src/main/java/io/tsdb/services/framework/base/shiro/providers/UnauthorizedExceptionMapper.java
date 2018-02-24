package io.tsdb.services.framework.base.shiro.providers;

import io.tsdb.services.framework.base.service.responses.GenericExceptionMapper;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author jcreasy
 */

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UnauthorizedExceptionMapper.class);

    @Override
    public Response toResponse(UnauthorizedException exception) {
        LOGGER.debug("Handled Exception: {}", exception.toString());
        return GenericExceptionMapper.genericResponse(exception, Response.Status.FORBIDDEN.getStatusCode());
    }
}