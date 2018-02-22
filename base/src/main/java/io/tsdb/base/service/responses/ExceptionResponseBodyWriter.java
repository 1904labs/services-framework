package io.tsdb.base.service.responses;

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
public class ExceptionResponseBodyWriter implements MessageBodyWriter<ErrorMessage> {
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == ErrorMessage.class;
    }

    @Override
    public long getSize(ErrorMessage exceptionResponse, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(ErrorMessage errorMessage, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
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
