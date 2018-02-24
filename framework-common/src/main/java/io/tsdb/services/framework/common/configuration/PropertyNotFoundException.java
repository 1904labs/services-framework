package io.tsdb.services.framework.common.configuration;

/**
 * @author jcreasy
 */
@SuppressWarnings("unused")
public class PropertyNotFoundException extends Exception {
    @SuppressWarnings("WeakerAccess")
    public PropertyNotFoundException() {
        super();
    }

    public PropertyNotFoundException(Exception e) {
        super(e);
    }

    public PropertyNotFoundException(String message) {
        super(message);
    }

    public PropertyNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
