package io.tsdb.base.service.responses;

import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * An exception mapper for {@link NotFoundException}.
 *
 * @author jcreasy
 */
@Singleton
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(NotFoundExceptionMapper.class);

    @Override
    public final Response toResponse(final NotFoundException exception) {
        LOGGER.debug("Handled Exception: {}", exception.toString());
        return GenericExceptionMapper.genericResponse(exception, Status.NOT_FOUND.getStatusCode());
    }
}
