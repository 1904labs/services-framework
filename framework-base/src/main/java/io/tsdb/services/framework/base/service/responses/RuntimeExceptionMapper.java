package io.tsdb.services.framework.base.service.responses;

import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * An exception mapper for {@link RuntimeException}.
 *
 * @author jcreasy
 */
@Singleton
@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RuntimeExceptionMapper.class);

    @Override
    public final Response toResponse(final RuntimeException exception) {
        LOGGER.debug("Handled Exception: {}", exception.toString());
        return GenericExceptionMapper.genericResponse(exception, Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }
}
