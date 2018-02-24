package io.tsdb.base.application;

import com.codahale.metrics.Timer;
import com.codahale.metrics.jetty9.InstrumentedConnectionFactory;
import com.codahale.metrics.jetty9.InstrumentedHandler;
import com.codahale.metrics.jetty9.InstrumentedQueuedThreadPool;
import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import io.tsdb.common.configuration.ConfigurationReader;
import io.tsdb.common.configuration.ServiceInfo;
import io.tsdb.common.instrumentation.Metrics;
import org.eclipse.jetty.jsp.JettyJspServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
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
@SuppressWarnings("WeakerAccess")
abstract class AbstractApplicationContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractApplicationContext.class);
    private static final Timer TIMER_HTTP_CONNECTION = Metrics.getTimer(ApplicationContext.class, "http", "connection");
    private static final String BASEROOT_INDEX = "/baseroot/";
    private static final String APPLICATION_INDEX = "/application/";

    private int adminPort;
    private String adminPath;
    private int applicationPort;
    private String applicationPath;

    /**
     * Initializes infrastructure, such as Metrics / ConsulClient / etc.
     */
    protected void initializeInfrastructure() {
        LOGGER.debug("Initializing Metrics");
        Metrics.initialize();

        //TODO: Add Consul Service Discover here
    }

    /**
     * Gets application path.
     *
     * @return - path string
     */
    protected String getApplicationPath() {
        return applicationPath;
    }

    /**
     * Sets application path.
     *
     * @param path application path
     */
    protected void setApplicationPath(final String path) {
        this.applicationPath = path;
    }

    /**
     * Gets application port.
     *
     * @return port
     */
    protected int getApplicationPort() {
        return applicationPort;
    }

    /**
     * Sets application port number.
     *
     * @param port application port
     */
    protected void setApplicationPort(final int port) {
        this.applicationPort = port;
    }

    /**
     * Gets administration port.
     *
     * @return port
     */
    protected int getAdminPort() {
        return adminPort;
    }

    /**
     * Sets administration port.
     *
     * @param port administration port
     */
    protected void setAdminPort(final int port) {
        this.adminPort = port;
    }

    /**
     * Gets administration path.
     *
     * @return path
     */
    protected String getAdminPath() {
        return adminPath;
    }

    /**
     * Sets administration path.
     *
     * @param path administration path
     */
    protected void setAdminPath(final String path) {
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
        setApplicationPath(appPath);
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
        final URL indexUri = this.getClass().getResource(webroot);
        if (indexUri == null) {
            throw new FileNotFoundException("Unable to find resource " + webroot);
        }

        LOGGER.debug("Found WebRootResourceUri: {} at {}", webroot, indexUri.toURI().toASCIIString());
        return indexUri.toURI().toASCIIString();
    }

    /**
     * Creates a ServletContextHandler instance.
     *
     * @return ServletContextHandler a ServletContextHandler instance
     */
    private ServletContextHandler getContextHandler() {
        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setSessionHandler(new SessionHandler());
        return context;
    }

    /**
     * Adds handler ({@link ServletContextHandler} to administration sever.
     *
     * @param servletContextHandler a ServletContextHandler for administration server
     */
    protected void addAdminServlets(final ServletContextHandler servletContextHandler) {
        final String adminPath = getAdminPath();
        LOGGER.info("Serving Admin Servlets from: {}", adminPath);
        final ServletHolder adminHolder = new ServletHolder(new AdminServlet());
        servletContextHandler.setAttribute(MetricsServlet.METRICS_REGISTRY, Metrics.getMetricRegistry());
        servletContextHandler.setAttribute(HealthCheckServlet.HEALTH_CHECK_REGISTRY, Metrics.getHealthCheckRegistry());
        servletContextHandler.addServlet(adminHolder, adminPath);
    }

    /**
     * Creates instrumented handler for the handler instance passed in.
     *
     * @param handler a Handler instance
     * @return InstrumentedHandler a instrumented handler
     */
    protected InstrumentedHandler getInstrumentedHandler(final Handler handler) {
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
        ServletContextHandler handler = getContextHandler();
        addAdminServlets(handler);
        return getInstrumentedHandler(handler);
    }

    /**
     * Allows the developer to use dependency injection ot load dynamic servlets
     *
     * @param context the ServletContextHandler to configure
     * @param path    the path for the added Servlets
     */
    protected abstract void addDynamicServlets(final ServletContextHandler context, final String path);

    /**
     * Creates a instrumented servlet handler.
     *
     * @return InstrumentedHandler an instrumented handler instance
     */
    protected InstrumentedHandler getApplicationHandler() throws IOException, URISyntaxException {
        LOGGER.info("Setting up Application Handler");
        final ServletContextHandler context = getContextHandler();

        final String contextPath = getApplicationPath();
        LOGGER.info("Application Context Path: {}", contextPath);
        context.setContextPath(contextPath);

        final InstrumentedHandler handler = getResourceHandler(APPLICATION_INDEX);
        context.insertHandler(handler);

        // Include Admin Servlets at /admin
        if (ConfigurationReader.getBooleanProperty(ServiceInfo.serviceName + ".admin.enable", false)) {
            addAdminServlets(context);
        }

        setContextResourceBase(context, BASEROOT_INDEX);

        // Enable JSP Support, used to serve authentication pages in the base
        enableEmbeddedJspSupport(context);

        addDynamicServlets(context, getApplicationPath());
        addDefaultHandler(context, "/", getWebRootResourceUri(BASEROOT_INDEX));

        return getInstrumentedHandler(context);
    }

    protected InstrumentedHandler getResourceHandler(final String resourceBase) throws FileNotFoundException, URISyntaxException {
        final String resourceBaseUri = getWebRootResourceUri(resourceBase);
        LOGGER.info("Serving static files from: {}", resourceBaseUri);

        final ResourceHandler handler = new ResourceHandler();
        handler.setDirectoriesListed(false);
        handler.setWelcomeFiles(new String[]{"index.html"});
        handler.setResourceBase(resourceBaseUri);

        return getInstrumentedHandler(handler);
    }

    protected void setContextResourceBase(final ServletContextHandler servletContextHandler, final String path) throws FileNotFoundException, URISyntaxException {
        // Base URI for servlet context
        final String baseUri = getWebRootResourceUri(path);
        LOGGER.debug("Serving files from: {}", baseUri);
        servletContextHandler.setResourceBase(baseUri);
    }

    /**
     * Setup JSP Support for ServletContextHandlers.
     * @param servletContextHandler the ServletContextHandler to configure
     * @throws IOException if unable to configure
     */
    protected void enableEmbeddedJspSupport(final ServletContextHandler servletContextHandler) throws IOException {
        LOGGER.info("Enabled JSP Support on default handler");

        // Establish Scratch directory for the servlet context (used by JSP compilation)
        final File tempDir = new File(System.getProperty("java.io.tmpdir"));
        final File scratchDir = new File(tempDir.toString(), "embedded-jetty-jsp");

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
        final ClassLoader jspClassLoader = new URLClassLoader(new URL[0], this.getClass().getClassLoader());
        servletContextHandler.setClassLoader(jspClassLoader);

        // Manually call JettyJasperInitializer on context startup
        final JspStarter jspStarter = new JspStarter(servletContextHandler);
        servletContextHandler.addBean(jspStarter);

        // Create / Register JSP Servlet (must be named "jsp" per spec)
        final ServletHolder holderJsp = new ServletHolder("jsp", JettyJspServlet.class);
        holderJsp.setInitOrder(0);
        holderJsp.setInitParameter("logVerbosityLevel", "DEBUG");
        holderJsp.setInitParameter("fork", "false");
        holderJsp.setInitParameter("xpoweredBy", "false");
        holderJsp.setInitParameter("compilerTargetVM", "1.8");
        holderJsp.setInitParameter("compilerSourceVM", "1.8");
        holderJsp.setInitParameter("keepgenerated", "true");
        servletContextHandler.addServlet(holderJsp, "*.jsp");
    }

    protected void addDefaultHandler(final ServletContextHandler servletContextHandler, @SuppressWarnings("SameParameterValue") final String path, final String baseUri) {
        LOGGER.info("Added Default Handler:for {} on {}", path, baseUri);

        // Default Servlet (always last, always named "default")
        final ServletHolder holderDefault = new ServletHolder("default", DefaultServlet.class);
        holderDefault.setInitParameter("resourceBase", baseUri);
        holderDefault.setInitParameter("dirAllowed", "true");
        servletContextHandler.addServlet(holderDefault, path);
    }
}