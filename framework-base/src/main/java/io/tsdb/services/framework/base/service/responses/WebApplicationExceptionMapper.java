package io.tsdb.services.framework.base.service.responses;

import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * An exception mapper for {@link WebApplicationException}.
 *
 * @author jcreasy
 */
@Singleton
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WebApplicationExceptionMapper.class);

    @Override
    public final Response toResponse(final WebApplicationException exception) {
        LOGGER.debug("Handled Exception: {}", exception.toString());
        return GenericExceptionMapper.genericResponse(exception, Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }
}
