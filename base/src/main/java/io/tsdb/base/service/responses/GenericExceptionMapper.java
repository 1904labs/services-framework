package io.tsdb.base.service.responses;

import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A base generic exception mapper.
 *
 * @author jcreasy
 */
public abstract class GenericExceptionMapper {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GenericExceptionMapper.class);
    /**
     * Returns a response in json type with specific exception message / status
     * code.
     *
     * @param e          Exception
     * @param statusCode status codea
     * @return response json type with specific exception message / status code
     */
    public static Response genericResponse(final Exception e, final int statusCode) {
        LOGGER.debug("Constructing Response for {}", e.getClass().toString(), e);
        ErrorMessage error = new ErrorMessage();
        error.setMessage(e.getMessage());
        error.setCode(statusCode);

        return Response.status(statusCode).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
}
