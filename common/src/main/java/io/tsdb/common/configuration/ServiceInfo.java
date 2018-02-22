package io.tsdb.common.configuration;

import io.tsdb.common.instrumentation.Metrics;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Sets up the base configuration properties and constants for the service.
 * @author jcreasy
 */
public class ServiceInfo {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Metrics.class);
    public static final String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
    public static final Long pid = Long.parseLong(processName.split("@")[0]);

    static {
        initializeLog4jBridge();
        LOGGER.info("ProcessName: " + processName);
        MDC.put("processname", ServiceInfo.processName);
        MDC.put("servicename", ServiceInfo.serviceName);
    }

    public static String serviceName = ConfigurationReader.getProperty("service.name", System.getProperty("serviceName", "datamanager"));

    public static int mainPort = ConfigurationReader.getIntegerProperty(serviceName + ".main.port", 8080);

    // Default: /servicename
    public static String mainPath = ConfigurationReader.getProperty(serviceName + ".main.path", System.getProperty("mainPath", "/" + serviceName));

    //Default: io.tsdb.servicename.rest
    public static String endpointPackage = ConfigurationReader.getProperty(serviceName + ".main.endpointpackage",
            System.getProperty("mainPath", "io.tsdb." + serviceName + ".rest"));

    //Default: io.tsdb.servicename.providers
    public static String providersPackage = ConfigurationReader.getProperty(serviceName + ".main.providerspackage",
            System.getProperty("mainPath", "io.tsdb." + serviceName + ".providers"));

    public static int adminPort = ConfigurationReader.getIntegerProperty(serviceName + ".admin.port", 8081);

    //Default: /admin
    public static String adminPath = ConfigurationReader.getProperty(serviceName + ".admin.path",
            System.getProperty("adminPath", "/admin"));

    private static void initializeLog4jBridge() {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        Logger.getLogger("global").setLevel(Level.FINEST);
    }

    public static String getHostString() {
        String hostname;
        if (ConfigurationReader.hasProperty("service.hostname")) {
            hostname = ConfigurationReader.getProperty("service.hostname");
        } else {

            try {
                hostname = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                hostname = getMachineIP();
            }
        }
        return hostname;
    }

    public static String getMachineIP() {
        try {
            String hostIP = InetAddress.getLocalHost().getHostAddress();
            if (!hostIP.equals("127.0.0.1")) {
                return hostIP;
            }

            /*
             * Above method often returned "127.0.0.1", In this case we need to
             * check all the available network interfaces
             */
            Enumeration<NetworkInterface> nInterfaces = NetworkInterface.getNetworkInterfaces();
            while (nInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = nInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    String address = inetAddresses.nextElement().getHostAddress();
                    if (!address.equals("127.0.0.1")) {
                        return address;
                    }
                }
            }
        } catch (UnknownHostException | SocketException e1) {
            LOGGER.error(e1.getMessage());
        }
        return "127.0.0.1";
    }
}