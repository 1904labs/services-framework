package io.tsdb.common.configuration;

import org.apache.commons.configuration2.Configuration;

import java.util.List;

/**
 * @author jcreasy
 */
@SuppressWarnings("unused")
public interface ConfigurationSource {
    String getProperty(String property) throws PropertyNotFoundException;

    String getProperty(String property, String defaultValue);

    String updateProperty(String property, String value);

    Boolean hasProperty(String property);

    List<String> getProperties();

    Configuration getConfiguration();
}
