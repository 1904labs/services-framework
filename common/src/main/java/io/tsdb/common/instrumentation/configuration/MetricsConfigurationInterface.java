package io.tsdb.common.instrumentation.configuration;

import com.google.common.net.HostAndPort;

import java.net.InetSocketAddress;

/**
 * @author jcreasy
 */
public interface MetricsConfigurationInterface {
    String getMetricRoot();

    void setMetricRoot(String metricRoot);

    String getMetricPrefix();

    void setMetricPrefix(String metricPrefix);

    String getMetricNamespace();

    int getMetricReportingInterval();

    void setMetricReportingInterval(int reportingInterval);

    int getHealthReportingInterval();

    void setHealthReportingInterval(int reportingInterval);

    int getMetricLoggingInterval();

    void setMetricLoggingInterval(int loggingInterval);

    int getHealthLoggingInterval();

    void setHealthLoggingInterval(int logginInterval);

    HostAndPort getMetricReportingTarget();

    InetSocketAddress getMetricReportingAddress();

    String getMetricReportingHost();

    int getMetricReportingPort();

    void setMetricReportingTarget(HostAndPort metricReportingTarget);

    void setMetricReportingTarget(String metricReportingHost, int metricReportingPort);

    HostAndPort getHealthReportingTarget();

    InetSocketAddress getHealthReportingAddress();

    String getHealthReportingHost();

    int getHealthReportingPort();

    void setHealthReportingTarget(HostAndPort metricReportingTarget);

    void setHealthReportingTarget(String metricReportingHost, int metricReportingPort);
}
