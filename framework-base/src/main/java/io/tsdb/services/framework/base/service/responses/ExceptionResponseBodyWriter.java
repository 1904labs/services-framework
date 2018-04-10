package io.tsdb.services.framework.base.service.responses;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author jcreasy
 */
@Provider
@Produces("text/html")
public final class ExceptionResponseBodyWriter implements MessageBodyWriter<ErrorMessage> {
    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return type == ErrorMessage.class;
    }

    @Override
    public long getSize(final ErrorMessage exceptionResponse,
                        final Class<?> type,
                        final Type genericType,
                        final Annotation[] annotations,
                        final MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(final ErrorMessage errorMessage,
                        final Class<?> type, final Type genericType,
                        final Annotation[] annotations,
                        final MediaType mediaType,
                        final MultivaluedMap<String, Object> httpHeaders,
                        final OutputStream entityStream) throws IOException, WebApplicationException {
        Writer writer = new PrintWriter(entityStream);
        writer.write("<html>");
        writer.write("<body>");
        writer.write("<h2>An Error Occurred:</h2>");
        writer.write("<p>Code: " + errorMessage.getCode() + "</p>");
        writer.write("<p>Message: " + errorMessage.getMessage() + "</p>");
        writer.write("<p>Class: " + errorMessage.getClass() + "</p>");
        writer.write("</body>");
        writer.write("</html>");
        writer.flush();
        writer.close();
    }
}
