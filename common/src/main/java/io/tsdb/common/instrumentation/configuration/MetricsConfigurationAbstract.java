package io.tsdb.common.instrumentation.configuration;

import com.codahale.metrics.MetricRegistry;
import com.google.common.net.HostAndPort;

import java.net.InetSocketAddress;

/**
 * @author jcreasy
 */
public abstract class MetricsConfigurationAbstract implements MetricsConfigurationInterface {
    private String metricRoot = "metrics";
    private String metricPrefix = "generic.localhost.0";

    private int metricReportingInterval = 5;
    private int healthReportingInterval = 60;
    private int metricLoggingInterval = 60;
    private int healthLoggingInterval = 60;

    private HostAndPort metricReportingTarget = HostAndPort.fromParts("localhost", 2003);
    private HostAndPort healthReportingTarget = HostAndPort.fromParts("localhost", 3001);

    @Override
    public String getMetricRoot() {
        return metricRoot;
    }

    @Override
    public void setMetricRoot(String metricRoot) {
        this.metricRoot = metricRoot;
    }

    @Override
    public String getMetricPrefix() {
        return metricPrefix;
    }

    @Override
    public void setMetricPrefix(String metricPrefix) {
        this.metricPrefix = metricPrefix;
    }

    @Override
    public String getMetricNamespace() {
        return MetricRegistry.name(this.getMetricRoot(), this.getMetricPrefix());
    }

    @Override
    public int getMetricReportingInterval() {
        return metricReportingInterval;
    }

    @Override
    public void setMetricReportingInterval(int metricReportingInterval) {
        this.metricReportingInterval = metricReportingInterval;
    }

    @Override
    public int getHealthReportingInterval() {
        return healthReportingInterval;
    }

    @Override
    public void setHealthReportingInterval(int healthReportingInterval) {
        this.healthReportingInterval = healthReportingInterval;
    }

    @Override
    public int getMetricLoggingInterval() {
        return metricLoggingInterval;
    }

    @Override
    public void setMetricLoggingInterval(int metricLoggingInterval) {
        this.metricLoggingInterval = metricLoggingInterval;
    }

    @Override
    public int getHealthLoggingInterval() {
        return healthLoggingInterval;
    }

    @Override
    public void setHealthLoggingInterval(int healthLoggingInterval) {
        this.healthLoggingInterval = healthLoggingInterval;
    }

    @Override
    public HostAndPort getMetricReportingTarget() {
        return metricReportingTarget;
    }

    @Override
    public InetSocketAddress getMetricReportingAddress() {
        return new InetSocketAddress(metricReportingTarget.getHost(), metricReportingTarget.getPort());
    }

    @Override
    public String getMetricReportingHost() {
        return metricReportingTarget.getHost();
    }

    @Override
    public int getMetricReportingPort() {
        return metricReportingTarget.getPort();
    }

    @Override
    public void setMetricReportingTarget(HostAndPort metricReportingTarget) {
        this.metricReportingTarget = metricReportingTarget;
    }

    @Override
    public void setMetricReportingTarget(String metricReportingHost, int metricReportingPort) {
        this.metricReportingTarget = HostAndPort.fromParts(metricReportingHost, metricReportingPort);
    }

    @Override
    public HostAndPort getHealthReportingTarget() {
        return healthReportingTarget;
    }

    @Override
    public InetSocketAddress getHealthReportingAddress() {
        return new InetSocketAddress(healthReportingTarget.getHost(), healthReportingTarget.getPort());
    }

    @Override
    public String getHealthReportingHost() {
        return healthReportingTarget.getHost();
    }

    @Override
    public int getHealthReportingPort() {
        return healthReportingTarget.getPort();
    }

    @Override
    public void setHealthReportingTarget(HostAndPort metricReportingTarget) {
        this.healthReportingTarget = metricReportingTarget;
    }

    @Override
    public void setHealthReportingTarget(String metricReportingHost, int metricReportingPort) {
        this.healthReportingTarget = HostAndPort.fromParts(metricReportingHost, metricReportingPort);
    }
}
