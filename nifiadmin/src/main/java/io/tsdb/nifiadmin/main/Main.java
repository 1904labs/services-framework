package io.tsdb.nifiadmin.main;

import com.labs1904.nifi.NifiClient;
import io.tsdb.base.application.ApplicationContext;
import io.tsdb.base.guice.BaseServletModule;
import io.tsdb.common.configuration.ConfigurationReader;
import io.tsdb.common.configuration.ServiceInfo;
import io.tsdb.nifiadmin.guice.NifiAdminServiceModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jcreasy
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info(String.format("Creating Jetty Server for '%s'", ServiceInfo.serviceName));

        ApplicationContext webApp = new ApplicationContext(new NifiAdminServiceModule(), new BaseServletModule());

        LOGGER.info("Reading configuration file");
        ConfigurationReader.loadPropertiesFile("configuration.properties");

        LOGGER.info(String.format("Starting Jetty Server for '%s'", ServiceInfo.serviceName));
        webApp.start();

        LOGGER.info("We're done!");
    }
}
