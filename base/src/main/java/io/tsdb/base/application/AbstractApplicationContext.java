package io.tsdb.base.application;

import com.codahale.metrics.Timer;
import com.codahale.metrics.jetty9.InstrumentedConnectionFactory;
import com.codahale.metrics.jetty9.InstrumentedHandler;
import com.codahale.metrics.jetty9.InstrumentedQueuedThreadPool;
import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import io.tsdb.common.instrumentation.Metrics;
import org.eclipse.jetty.jsp.JettyJspServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author jcreasy
 */
abstract class AbstractApplicationContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractApplicationContext.class);
    private static final Timer TIMER_HTTP_CONNECTION = Metrics.getTimer(ApplicationContext.class, "http", "connection");

    private int adminPort;
    private String adminPath;
    private int applicationPort;
    private String applicationPath;

    /**
     * Initializes infrastructure, such as Metrics / ConsulClient / etc.
     */
    void initializeInfrastructure() {
        LOGGER.debug("Initializing Metrics");
        Metrics.initialize();

        LOGGER.debug("Registering with Consul");
        //ConsulClient.initialize();
    }

    /**
     * Gets application path.
     *
     * @return - path string
     */
    String getApplicationPath() {
        return applicationPath;
    }

    /**
     * Sets application path.
     *
     * @param path application path
     */
    void setApplicationPath(final String path) {
        this.applicationPath = path;
    }

    /**
     * Gets application port.
     *
     * @return port
     */
    int getApplicationPort() {
        return applicationPort;
    }

    /**
     * Sets application port number.
     *
     * @param port application port
     */
    void setApplicationPort(final int port) {
        this.applicationPort = port;
    }

    /**
     * Gets administration port.
     *
     * @return port
     */
    int getAdminPort() {
        return adminPort;
    }

    /**
     * Sets administration port.
     *
     * @param port administration port
     */
    void setAdminPort(final int port) {
        this.adminPort = port;
    }

    /**
     * Gets administration path.
     *
     * @return path
     */
    String getAdminPath() {
        return adminPath;
    }

    /**
     * Sets administration path.
     *
     * @param path administration path
     */
    void setAdminPath(final String path) {
        this.adminPath = path;
    }

    /**
     * Sets configuration for application / administration servers.
     *
     * @param appPath            application path
     * @param appPort            application port
     * @param administrationPort administration port
     * @param administrationPath administration path
     */
    protected void configureApplication(final String appPath, final int appPort, final int administrationPort,
                                        final String administrationPath) {
        LOGGER.info("Configuring Server Path and Ports");
        LOGGER.info("Application Path is :{}/{}", appPort, appPath);
        LOGGER.info("Admin Path is :{}/{}", administrationPort, administrationPath);

        setApplicationPort(appPort);
        setApplicationPath(appPath + "/*");
        setAdminPort(administrationPort);
        setAdminPath(administrationPath + "/*");
    }

    /**
     * Creates a server instance with specific port.
     *
     * @param port server port
     * @return Server a server instance
     */
    protected Server configureServer(final int port) {
        LOGGER.info("Creating Server for port {} ", port);
        final ThreadPool threadPool = new InstrumentedQueuedThreadPool(Metrics.getMetricRegistry());
        final Server appServer = new Server(threadPool);
        final ServerConnector mainConnector = new ServerConnector(appServer,
                new InstrumentedConnectionFactory(new HttpConnectionFactory(), TIMER_HTTP_CONNECTION));
        mainConnector.setPort(port);
        appServer.addConnector(mainConnector);
        return appServer;
    }

    protected String getWebRootResourceUri(final String webroot) throws FileNotFoundException, URISyntaxException {
        URL indexUri = this.getClass().getResource(webroot);
        if (indexUri == null) {
            throw new FileNotFoundException("Unable to find resource " + webroot);
        }
        // Points to wherever /webroot/ (the resource) is
        return indexUri.toURI().toASCIIString();
    }

    /**
     * Creates a ServletContextHandler instance.
     *
     * @return ServletContextHandler a ServletContextHandler instance
     */
    private ServletContextHandler createContextHandler() {
        return new ServletContextHandler();
    }

    /**
     * Adds handler ({@link ServletContextHandler} to administration sever.
     *
     * @param adminHandler a ServletContextHandler for administration server
     */
    private void addAdminServlets(final ServletContextHandler adminHandler) {
        LOGGER.info("AdminServlets added at: {}", getAdminPath());
        final ServletHolder adminHolder = new ServletHolder(new AdminServlet());
        adminHandler.setAttribute(MetricsServlet.METRICS_REGISTRY, Metrics.getMetricRegistry());
        adminHandler.setAttribute(HealthCheckServlet.HEALTH_CHECK_REGISTRY, Metrics.getHealthCheckRegistry());
        adminHandler.addServlet(adminHolder, getAdminPath());
    }

    /**
     * Creates instrumented handler for the handler instance passed in.
     *
     * @param handler a Handler instance
     * @return InstrumentedHandler a instrumented handler
     */
    private InstrumentedHandler getInstrumentedHandler(final Handler handler) {
        final InstrumentedHandler instrumentedHandler = new InstrumentedHandler(Metrics.getMetricRegistry());
        instrumentedHandler.setHandler(handler);
        return instrumentedHandler;
    }

    /**
     * Creates a ServletContextHandler,then returns it after adding to admin
     * servlet.
     *
     * @return InstrumentedHandler of admin servlet
     */
    protected InstrumentedHandler getAdminHandler() {
        ServletContextHandler handler = createContextHandler();
        addAdminServlets(handler);
        return getInstrumentedHandler(handler);
    }

    /**
     * Creates a instrumented servlet handler.
     *
     * @return InstrumentedHandler an instrumented handler instance
     */
    protected InstrumentedHandler getApplicationHandler() {
        ServletHandler handler = new ServletHandler();
        return getInstrumentedHandler(handler);
    }

    /**
     * Setup JSP Support for ServletContextHandlers.
     * @param servletContextHandler the ServletContextHandler to configure
     * @throws IOException if unable to configure
     */
    protected void enableEmbeddedJspSupport(final ServletContextHandler servletContextHandler, final String webroot) throws IOException {
        //noinspection UnnecessaryLocalVariable
        String baseUri = webroot;

        // TODO: This path needs to be loaded from the JAR not the local filesystem
        /*
        // Base URI for servlet context
        URI baseUri = null;
        try {
            baseUri = getWebRootResourceUri(webroot);
        } catch (FileNotFoundException | URISyntaxException e) {
            LOGGER.error(e.getMessage(), e);
        }*/

       servletContextHandler.setResourceBase(baseUri);

        // Establish Scratch directory for the servlet context (used by JSP compilation)
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File scratchDir = new File(tempDir.toString(), "embedded-jetty-jsp");

        if (!scratchDir.exists()) {
            if (!scratchDir.mkdirs()) {
                throw new IOException("Unable to create scratch directory: " + scratchDir);
            }
        }
        servletContextHandler.setAttribute("javax.servlet.context.tempdir", scratchDir);

        // Set Classloader of Context to be sane (needed for JSTL)
        // JSP requires a non-System classloader, this simply wraps the
        // embedded System classloader in a way that makes it suitable
        // for JSP to use
        ClassLoader jspClassLoader = new URLClassLoader(new URL[0], this.getClass().getClassLoader());
        servletContextHandler.setClassLoader(jspClassLoader);

        // Manually call JettyJasperInitializer on context startup
        servletContextHandler.addBean(new JspStarter(servletContextHandler));

        // Create / Register JSP Servlet (must be named "jsp" per spec)
        ServletHolder holderJsp = new ServletHolder("jsp", JettyJspServlet.class);
        holderJsp.setInitOrder(0);
        holderJsp.setInitParameter("logVerbosityLevel", "DEBUG");
        holderJsp.setInitParameter("fork", "false");
        holderJsp.setInitParameter("xpoweredBy", "false");
        holderJsp.setInitParameter("compilerTargetVM", "1.8");
        holderJsp.setInitParameter("compilerSourceVM", "1.8");
        holderJsp.setInitParameter("keepgenerated", "true");
        servletContextHandler.addServlet(holderJsp, "*.jsp");

        // Default Servlet (always last, always named "default")
        ServletHolder holderDefault = new ServletHolder("default", DefaultServlet.class);
        holderDefault.setInitParameter("resourceBase", baseUri);
        holderDefault.setInitParameter("dirAllowed", "true");
        servletContextHandler.addServlet(holderDefault, "/");
    }
}