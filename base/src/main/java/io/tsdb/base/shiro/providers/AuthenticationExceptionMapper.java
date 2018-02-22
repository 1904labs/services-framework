package io.tsdb.base.shiro.providers;

import io.tsdb.base.service.responses.GenericExceptionMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author jcreasy
 */
@Singleton
@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AuthenticationExceptionMapper.class);

    @Override
    public Response toResponse(AuthenticationException exception) {
        LOGGER.debug("Handled Exception: {}", exception.toString());
        return GenericExceptionMapper.genericResponse(exception, Response.Status.UNAUTHORIZED.getStatusCode());
    }
}