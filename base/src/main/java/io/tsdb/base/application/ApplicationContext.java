package io.tsdb.base.application;

import com.google.inject.Module;
import com.google.inject.servlet.GuiceFilter;
import io.tsdb.base.guice.BaseServletContextListener;
import io.tsdb.common.configuration.ServiceInfo;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.EnumSet;

/**
 * The entry point for the base service, sets up Jetty containers
 * @author jcreasy
 */
public class ApplicationContext extends AbstractApplicationContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContext.class);
    private BaseServletContextListener servletContextListener;

    private Server appServer;
    private Server adminServer;

    /**
     * Creates ApplicationContext with specific guice module(s)
     * {@link com.google.inject.Module}.
     */
    public ApplicationContext(final Module... modules) {
        initializeInfrastructure();
        configureApplication(ServiceInfo.mainPath, ServiceInfo.mainPort, ServiceInfo.adminPort, ServiceInfo.adminPath);
        initializeGuice(modules);
    }

    /**
     * We are using Guice to dnyamically load Filters and JAX-RS endpoints
     * @param context the ServletContextHandler to configure
     * @param path    the path for the added Servlets
     */
    @Override
    protected void addDynamicServlets(ServletContextHandler context, String path) {
        LOGGER.info("Adding Guice Injected Servlets at: {}", path);
        context.addEventListener(servletContextListener);
        context.addFilter(GuiceFilter.class, "/*", EnumSet.of(javax.servlet.DispatcherType.REQUEST, javax.servlet.DispatcherType.ASYNC));
    }

    /**
     * Initializes ({@link BaseServletContextListener} with guice module(s)
     * passed in.
     *
     * @param modules guice module(s)
     */
    private void initializeGuice(final Module... modules) {
        servletContextListener = new BaseServletContextListener(modules);
    }

    /**
     * Setup the Application and Admin servers
     */
    private void setupServer() {
        LOGGER.info("Creating HTTP servers");
        appServer = configureServer(getApplicationPort());
        adminServer = configureServer(getAdminPort());

        LOGGER.info("Creating HTTP handlers");
        try {
            appServer.setHandler(getApplicationHandler());
            adminServer.setHandler(getAdminHandler());
        } catch (IOException | URISyntaxException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Starts application and administration servers.
     */
    public final void start() {
        setupServer();

        try {
            appServer.start();
        } catch (Exception e) {
            LOGGER.error("Could not start appServer: {}", e.getMessage());
            LOGGER.debug("Could not start appServer", e);
            System.exit(4);
        }

        try {
            adminServer.start();
        } catch (Exception e) {
            LOGGER.error("Could not start adminServer: {}", e.getMessage());
            LOGGER.debug("Could not start adminServer", e);
            System.exit(5);
        }
    }

    /**
     * Stops current running server(s) - application / admin server instances.
     */
    @SuppressWarnings("unused")
    public final void stop() {
        if (null != appServer) {
            try {
                appServer.stop();
            } catch (Exception ex) {
                LOGGER.error("Error when stopping application server instance", ex);
            }
        } else {
            LOGGER.error("application server instance is NOT running");
        }

        if (null != adminServer) {
            try {
                adminServer.stop();
            } catch (Exception ex) {
                LOGGER.error("Error when stopping admin server instance", ex);
            }
        } else {
            LOGGER.error("admin server instance is NOT running");
        }
    }
}