package io.tsdb.services.framework.base.service.responses;

import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * An exception mapper for {@link IllegalArgumentException}.
 *
 * @author jcreasy
 */
@Singleton
@Provider
public class IlegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(IlegalArgumentExceptionMapper.class);

    @Override
    public final Response toResponse(final IllegalArgumentException exception) {
        LOGGER.debug("Handled Exception: {}", exception.toString());
        return GenericExceptionMapper.genericResponse(exception, Status.BAD_REQUEST.getStatusCode());
    }
}
