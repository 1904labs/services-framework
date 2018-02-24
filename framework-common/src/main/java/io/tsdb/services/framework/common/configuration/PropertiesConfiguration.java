package io.tsdb.services.framework.common.configuration;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.*;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.tsdb.services.framework.common.configuration.ConfigurationReader.ENVIRONMENT_CONFIGURATION;

/**
 * @author jcreasy
 */
class PropertiesConfiguration extends ConfigurationSourceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesConfiguration.class);
    private String filename = "resources/application.properties";

    PropertiesConfiguration(String filename, boolean reloadable) {
        this.filename = filename;
        if (reloadable) {
            readPropertiesFileReloadable(this.filename);
        } else {
            readPropertiesFile(this.filename);
        }
    }

    private void readPropertiesFile(String filename) {
        File propertiesFile = new File(filename);
        Parameters params = new Parameters();
        ClasspathLocationStrategy classpath = new ClasspathLocationStrategy();

        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(org.apache.commons.configuration2.PropertiesConfiguration.class)
                        .configure(params.fileBased()
                                .setLocationStrategy(classpath)
                                .setFile(propertiesFile));

        try {
            this.configuration = builder.getConfiguration();
        } catch (ConfigurationException e) {
            LOGGER.error("Could not load configuration " + filename + ": " + e.getMessage());
            LOGGER.debug("Could not load configuration " + filename, e);
        }
    }

    private void readPropertiesFileReloadable(String propertiesFile) {
        try {
            LOGGER.debug("Trying to load: " + propertiesFile);
            Parameters params = new Parameters();
            ClasspathLocationStrategy classpath = new ClasspathLocationStrategy();
            FileSystemLocationStrategy filesystem = new FileSystemLocationStrategy();
            ProvidedURLLocationStrategy providedUrl = new ProvidedURLLocationStrategy();
            List<FileLocationStrategy> subs = Arrays.asList(providedUrl, filesystem, classpath);
            FileLocationStrategy strategy = new CombinedLocationStrategy(subs);

            ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>
                            (org.apache.commons.configuration2.PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setLocationStrategy(strategy)
                                    .setFileName(propertiesFile));

            int reloadInterval = Integer.parseInt(ENVIRONMENT_CONFIGURATION.getProperty("config.reload.interval.mins", "10"));
            LOGGER.debug("Loaded: " + propertiesFile);
            PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(builder.getReloadingController(),
                    null, reloadInterval, TimeUnit.MINUTES);
            trigger.start();

            this.configuration = builder.getConfiguration();
        } catch (ConfigurationException e) {
            LOGGER.error("Could not load configuration " + filename + ": " + e.getMessage());
            LOGGER.debug("Could not load configuration " + filename, e);
        }
    }
}
