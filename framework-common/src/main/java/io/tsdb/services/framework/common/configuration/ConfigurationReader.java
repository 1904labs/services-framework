package io.tsdb.services.framework.common.configuration;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.tree.OverrideCombiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The ConfigurationReader class is used to read the various configuration
 * sources with varied priorities.
 * <p>
 * Priority List (later values override earlier ones):
 * 1) Environment Variables
 * 2) Command Line Parameters ex. -Dconfig.reload.interval.mins=4
 * 3) environment.properties
 * 4) application.properties
 * <p>
 * @author jcreasy
 */
@SuppressWarnings({"SameParameterValue", "unused"})
public final class ConfigurationReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationReader.class);
    private static final CombinedConfiguration COMBINED_CONFIGURATION = new CombinedConfiguration(new OverrideCombiner());
    static final EnvironmentConfiguration ENVIRONMENT_CONFIGURATION = new EnvironmentConfiguration();
    private static final String CONFIG = ENVIRONMENT_CONFIGURATION.getProperty("config", "environment.properties");
    private static final boolean RELOADABLE = Boolean.parseBoolean(ENVIRONMENT_CONFIGURATION.getProperty("config.reloadable", "false"));

    static {
        COMBINED_CONFIGURATION.addConfiguration(ENVIRONMENT_CONFIGURATION.getConfiguration());
        loadPropertiesFile(CONFIG, RELOADABLE);
        loadPropertiesFile("resources/application.properties", RELOADABLE);
    }

    /**
     * Private Constructor.
     */
    private ConfigurationReader() {

    }

    /**
     * Loads property file into combined configuration.
     *
     * @param fileName
     *            properties file to be loaded
     */
    public static synchronized void loadPropertiesFile(final String fileName) {
        ConfigurationReader.loadPropertiesFile(fileName, RELOADABLE);
    }

    /**
     * Loads properties file into the combined configuration, optionally
     * RELOADABLE.
     *
     * @param fileName
     *            properties file to be reloaded
     * @param makeReloadable
     *            whether to refresh configuration values every 10 minutes
     */
    private static synchronized void loadPropertiesFile(final String fileName, final boolean makeReloadable) {
        LOGGER.debug("Loading Configuration File: {}", fileName);
        final PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration(fileName, makeReloadable);
        if (propertiesConfiguration.getConfiguration() != null) {
            LOGGER.debug("Successfully loaded {}", fileName);
            try {
                LOGGER.debug("Adding resulting configuraiton to the Combined Configuration");
                COMBINED_CONFIGURATION.addConfiguration(propertiesConfiguration.getConfiguration());
                LOGGER.debug("Success");
            } catch (Exception e) {
                LOGGER.warn("Could not load properties file: {}", fileName);
                LOGGER.debug("Could not load properties file", e);
            }
        }
    }

    /**
     * Updates property, will NOT save permanently
     *
     * @param property
     *            property to be updated
     * @param value
     *            value to be put in the property
     * @return the value as read from the configuration after the update
     */
    public static synchronized String updateProperty(final String property, final String value) {
        COMBINED_CONFIGURATION.setProperty(property, value);
        return getProperty(property, value);
    }

    /**
     * Gets a property from the COMBINED_CONFIGURATION.
     *
     * @param property
     *            property to be retrieved
     * @return value of the retrieved property
     */
    @SuppressWarnings("WeakerAccess")
    public static String getProperty(final String property) {
        String value;
        LOGGER.debug("getProperty: {}", property);
        try {
            value = COMBINED_CONFIGURATION.getString(property);
        } catch (Exception e) {
            value = COMBINED_CONFIGURATION.getString(property);
        }
        LOGGER.debug("Value: {}", value);
        return value;
    }

    /**
     * Returns true if {@link #COMBINED_CONFIGURATION} contains the provided
     * key.
     *
     * @param property
     *            provided key to look for in {@link #COMBINED_CONFIGURATION}
     * @return whether or not {@link #COMBINED_CONFIGURATION} contains the
     *         provided key
     */
    @SuppressWarnings("WeakerAccess")
    public static boolean hasProperty(final String property) {
        return COMBINED_CONFIGURATION.containsKey(property);
    }

    /**
     * Gets a property from COMBINED_CONFIGURATION.
     *
     * @param property
     *            property to be retrieved
     * @param defaultValue
     *            default value if no value exists
     * @return returns the value from COMBINED_CONFIGURATION or the provided
     *         default value
     */
    public static String getProperty(final String property, final String defaultValue) {
        String value;
        LOGGER.debug("getProperty: {}", property);
        LOGGER.debug("hasProperty: {}", COMBINED_CONFIGURATION.containsKey(property));
        try {
            value = COMBINED_CONFIGURATION.getString(property, defaultValue);
        } catch (Exception e) {
            value = COMBINED_CONFIGURATION.getString(property, defaultValue);
        }
        LOGGER.debug("Value: {}", value);
        return value;
    }

    public static Boolean getBooleanProperty(final String property, final Boolean defaultValue) {
        String value = getProperty(property, defaultValue.toString());
        return Boolean.parseBoolean(value);
    }

    public static int getIntegerProperty(final String property, final int defaultValue) {
        String value = getProperty(property, String.valueOf(defaultValue));
        return Integer.parseInt(value);
    }

    public static long getLongProperty(final String property, final long defaultValue) {
        Long retValue = defaultValue;
        String value = getProperty(property, String.valueOf(defaultValue));
        try {
            if (value != null) {
                retValue = Long.parseLong(value);
            }
        } catch (NumberFormatException ignored) {
        }
        return retValue;
    }

    public static List<String> getProperties() {
        return ConfigurationSourceImpl.getProperties(COMBINED_CONFIGURATION.getKeys());
    }
}