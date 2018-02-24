package io.tsdb.stats.main;

/**
 * Created by jcreasy on 2/8/18.
 */

import io.tsdb.services.framework.base.application.ApplicationContext;
import io.tsdb.services.framework.base.guice.BaseServletModule;
import io.tsdb.services.framework.common.configuration.ConfigurationReader;
import io.tsdb.services.framework.common.configuration.ServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info(String.format("Creating Jetty Server for '%s'", ServiceInfo.serviceName));

        ApplicationContext webApp = new ApplicationContext(new BaseServletModule());

        LOGGER.info("Reading configuration file");
        ConfigurationReader.loadPropertiesFile("configuration.properties");

        LOGGER.info(String.format("Starting Jetty Server for '%s'", ServiceInfo.serviceName));

        webApp.start();

        LOGGER.info("We're done!");
    }
}
