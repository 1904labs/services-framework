package io.tsdb.services.framework.common.configuration;

import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author jcreasy
 */
public class ConfigurationSourceImpl implements ConfigurationSource {
    protected Configuration configuration;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationSource.class);

    @Override
    public String getProperty(String property) throws PropertyNotFoundException {
        if (!configuration.containsKey(property)) {
            throw new PropertyNotFoundException();
        }
        return configuration.getProperty(property).toString();
    }

    @Override
    public String getProperty(String property, String defaultValue) {
        try {
            if (configuration.containsKey(property)) {
                return configuration.getProperty(property).toString();
            } else {
                return defaultValue;
            }
        } catch (Exception e) {
            LOGGER.warn("Exception getting configuration value: " + e.getMessage());
            return defaultValue;
        }
    }

    @Override
    public String updateProperty(String property, String value) {
        configuration.setProperty(property, value);
        return getProperty(property, value);
    }

    @Override
    public Boolean hasProperty(String property) {
        return configuration.containsKey(property);
    }

    @Override
    public List<String> getProperties() {
        return getProperties(configuration.getKeys());
    }

    @SuppressWarnings("WeakerAccess")
    public static List<String> getProperties(Iterator<String> keys) {
        List<String> propertiesList = new ArrayList<>();
        while (keys.hasNext()) {
            propertiesList.add(keys.next());
        }
        return propertiesList;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
