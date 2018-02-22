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
import java.util.EnumSet;

/**
 * The entry point for the base service, sets up Jetty containers
 * @author jcreasy
 */
public class ApplicationContext extends AbstractApplicationContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContext.class);
    private BaseServletContextListener servletContextListener;
    private static final String WEBROOT_INDEX = "/webroot/";
    private static final String TEMPORARY_ROOT = "file:///Users/jcreasy/code/charter/data-manager/build/install/webroot";

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
     * Initializes ({@link BaseServletContextListener} with guice module(s)
     * passed in.
     *
     * @param modules guice module(s)
     */
    protected void initializeGuice(final Module... modules) {
        servletContextListener = new BaseServletContextListener(modules);
    }

    private void setupServer() {
        LOGGER.info("Creating HTTP servers");
        appServer = configureServer(getApplicationPort());
        adminServer = configureServer(getAdminPort());

        LOGGER.info("Creating HTTP handlers");
        appServer.setHandler(getApplicationHandler());
        adminServer.setHandler(getAdminHandler());

        final ServletContextHandler context = new ServletContextHandler(
                appServer, "/*", ServletContextHandler.SESSIONS);

        // attach listener who instantiate our injector
        context.addEventListener(servletContextListener);
        context.addFilter(GuiceFilter.class, "/*", EnumSet.of(javax.servlet.DispatcherType.REQUEST, javax.servlet.DispatcherType.ASYNC));

        // Since this is a ServletContextHandler we must manually configure JSP support.
        try {
            enableEmbeddedJspSupport(context, TEMPORARY_ROOT);
        } catch (IOException e) {
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
     * Stops current running server(s) - app / admin server instances.
     */
    @SuppressWarnings("unused")
    public final void stop() {
        if (null != appServer) {
            try {
                appServer.stop();
            } catch (Exception ex) {
                LOGGER.error("Error when stopping app server instance", ex);
            }
        } else {
            LOGGER.error("app server instance is NOT running");
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