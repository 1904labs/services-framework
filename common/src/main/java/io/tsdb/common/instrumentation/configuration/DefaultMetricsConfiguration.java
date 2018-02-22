package io.tsdb.common.instrumentation.configuration;

import com.codahale.metrics.MetricRegistry;
import io.tsdb.common.configuration.ConfigurationReader;
import io.tsdb.common.configuration.ServiceInfo;

/**
 * @author jcreasy
 *
 */
public class DefaultMetricsConfiguration extends MetricsConfigurationAbstract {

    public DefaultMetricsConfiguration() {
        setMetricRoot(ConfigurationReader.getProperty("instrumentation.schema.root", "tsdb"));

        setMetricPrefix(
                ConfigurationReader.getProperty("instrumentation.schema.prefix",
                        MetricRegistry.name(ServiceInfo.serviceName, ServiceInfo.getHostString(), ServiceInfo.pid.toString())
                )
        );

        setMetricReportingInterval(ConfigurationReader.getIntegerProperty("instrumentation.metrics.reporting.interval", 5));
        setHealthReportingInterval(ConfigurationReader.getIntegerProperty("instrumentation.health.reporting.interval", 60));
        setMetricLoggingInterval(ConfigurationReader.getIntegerProperty("instrumentation.metrics.logging.interval", 0));
        setHealthLoggingInterval(ConfigurationReader.getIntegerProperty("instrumentation.health.logging.interval", 0));

        setMetricReportingTarget(
                ConfigurationReader.getProperty("instrumentation.graphite.host", "metrics"),
                ConfigurationReader.getIntegerProperty("instrumentation.graphite.port", 2003)
        );

        setHealthReportingTarget(
                ConfigurationReader.getProperty("instrumentation.sensu.host", "localhost"),
                ConfigurationReader.getIntegerProperty("instrumentation.sensu.port", 3001)
        );
    }
}
