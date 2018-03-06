package io.tsdb.services.framework.common.instrumentation;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.codahale.metrics.*;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.health.jvm.ThreadDeadlockHealthCheck;
import com.codahale.metrics.jvm.BufferPoolMetricSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.codahale.metrics.logback.InstrumentedAppender;
import com.google.common.net.HostAndPort;
import io.tsdb.services.framework.common.instrumentation.configuration.DefaultMetricsConfiguration;
import io.tsdb.services.framework.common.instrumentation.metricsets.FileSystemMetricSet;
import io.tsdb.services.framework.common.instrumentation.metricsets.OperatingSystemMetricSet;
import io.tsdb.services.framework.common.instrumentation.metricsets.RuntimeMetricSet;
import io.tsdb.services.framework.common.instrumentation.reporters.Log4jHealthcheckReporter;
import io.tsdb.services.framework.common.instrumentation.reporters.SensuHealthcheckReporter;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Initializes DropWizard/Coda Hale Metrics.
 * Provides commmon layout and function for metrics across projects and classes
 * Centralizes metrics, reporters, heatlhchecks and base metricsets
 * @author jcreasy
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Metrics {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Metrics.class);
    private static final DefaultMetricsConfiguration METRICS_CONFIGURATION = new DefaultMetricsConfiguration();
    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();
    private static final HealthCheckRegistry HEALTH_CHECK_REGISTRY = new HealthCheckRegistry();

    static {
        Metrics.registerMetricSet("jvm.gc", new GarbageCollectorMetricSet());
        Metrics.registerMetricSet("jvm.memory", new MemoryUsageGaugeSet());
        Metrics.registerMetricSet("jvm.threads", new ThreadStatesGaugeSet());
        Metrics.registerMetricSet("jvm.runtime", new RuntimeMetricSet());
        Metrics.registerMetricSet("jvm.os", new OperatingSystemMetricSet());
        Metrics.registerMetricSet("jvm.filesystem", new FileSystemMetricSet());
        Metrics.registerAll("jvm.buffers", new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()), Metrics.getMetricRegistry());
        Metrics.registerHealthCheck("DeadlockDetection", new ThreadDeadlockHealthCheck());

        final LoggerContext factory = (LoggerContext) LoggerFactory.getILoggerFactory();
        final Logger root = factory.getLogger(Logger.ROOT_LOGGER_NAME);

        final InstrumentedAppender instrumentedAppender = new InstrumentedAppender(Metrics.getMetricRegistry());
        instrumentedAppender.setContext(root.getLoggerContext());
        instrumentedAppender.start();
        root.addAppender(instrumentedAppender);
    }

    /**
     * Initializes the reporters for Metrics and Healthchecks.
     */
    private static void startReport() {
        if (METRICS_CONFIGURATION.getMetricReportingInterval() > 0) {
            final Graphite graphite = new Graphite(METRICS_CONFIGURATION.getMetricReportingAddress());
            final GraphiteReporter graphiteReporter = GraphiteReporter.forRegistry(Metrics.getMetricRegistry())
                    .prefixedWith(METRICS_CONFIGURATION.getMetricNamespace())
                    .convertRatesTo(TimeUnit.SECONDS)
                    .convertDurationsTo(TimeUnit.MILLISECONDS)
                    .filter(MetricFilter.ALL)
                    .build(graphite);
            graphiteReporter.start(METRICS_CONFIGURATION.getMetricReportingInterval(), TimeUnit.SECONDS);
        }

        if (METRICS_CONFIGURATION.getMetricLoggingInterval() > 0) {
            final Slf4jReporter logReporter = Slf4jReporter.forRegistry(Metrics.getMetricRegistry())
                    .outputTo(LoggerFactory.getLogger(METRICS_CONFIGURATION.getMetricNamespace()))
                    .convertRatesTo(TimeUnit.SECONDS)
                    .convertDurationsTo(TimeUnit.MILLISECONDS)
                    .build();
            logReporter.start(METRICS_CONFIGURATION.getMetricLoggingInterval(), TimeUnit.SECONDS);
        }

        if (METRICS_CONFIGURATION.getHealthLoggingInterval() > 0) {
            final Log4jHealthcheckReporter logReporter = new Log4jHealthcheckReporter(METRICS_CONFIGURATION.getHealthLoggingInterval());
        }

        if (METRICS_CONFIGURATION.getHealthReportingInterval() > 0) {
            HostAndPort healthReportingAddress = METRICS_CONFIGURATION.getHealthReportingTarget();
            final SensuHealthcheckReporter healthReporter = new SensuHealthcheckReporter(METRICS_CONFIGURATION.getHealthReportingInterval(), healthReportingAddress);
        }
    }

    /**
     * Iterates a MetricSet registering all the metrics in the set.
     *
     * @param prefix    the prefix for the metric name
     * @param metricSet the metricSet to be iterated
     * @param registry  the registry to register the metrics in
     */
    private static void registerAll(final String prefix, final MetricSet metricSet, final MetricRegistry registry) {
        for (Map.Entry<String, Metric> entry : metricSet.getMetrics().entrySet()) {
            if (entry.getValue() instanceof MetricSet) {
                registerAll(prefix + "." + entry.getKey(), (MetricSet) entry.getValue(), registry);
            } else {
                registry.register(prefix + "." + entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Called to initialize the Metrics helper class.
     * <p>
     * Starts the reporters.
     */
    public static void initialize() {
        Metrics.startReport();
    }

    /**
     * Submit a Metric set to be registered with the Metrics registry.
     *
     * @param prefix    the prefix for the metric name
     * @param metricSet the metric set to be registered
     */
    public static void registerMetricSet(final String prefix, final MetricSet metricSet) {
        Metrics.registerAll(prefix, metricSet, Metrics.getMetricRegistry());
    }

    /**
     * Registers a health check with the Health Check Registry.
     *
     * @param name        name of the health check
     * @param healthcheck a health check object to regiser.
     */
    @SuppressWarnings("SameParameterValue")
    public static void registerHealthCheck(final String name, final HealthCheck healthcheck) {
        Metrics.getHealthCheckRegistry().register(name, healthcheck);
    }

    /**
     * Retrieve a Counter instance from the Registry (get or create).
     *
     * @param originatingClass a Class to use as a namespace for the metric
     * @param names            names to append to the namespace
     * @return returns a Counter instance
     */
    public static Counter getCounter(final Class<?> originatingClass, String... names) {
        return Metrics.getCounter(MetricRegistry.name(originatingClass, names));
    }

    /**
     * Fetches a Counter instance by name.
     *
     * @param name the name of the counter
     * @return the counter
     */
    private static Counter getCounter(String name) {
        return METRIC_REGISTRY.counter(name);
    }

    /**
     * Retrieve a Timer instance from the Registry (get or create).
     *
     * @param originatingClass a Class to use as a namespace for the metric
     * @param names            names to append to the namespace
     * @return a Timer
     */
    public static Timer getTimer(final Class<?> originatingClass, final String... names) {
        return Metrics.getTimer(MetricRegistry.name(originatingClass, names));
    }

    /**
     * Fetches a Timer instance by name.
     *
     * @param name the name of the timer
     * @return the timer
     */
    private static Timer getTimer(final String name) {
        return METRIC_REGISTRY.timer(name);
    }

    /**
     * Retrieve a Meter instance from the Registry (get or create).
     *
     * @param originatingClass a Class to use as a namespace for the metric
     * @param names            names to append to the namespace
     * @return a Meter
     */
    public static Meter getMeter(final Class<?> originatingClass, final String... names) {
        return Metrics.getMeter(MetricRegistry.name(originatingClass, names));
    }

    /**
     * Fetches a Meter instance by name.
     *
     * @param name the name of the meter
     * @return the meter
     */
    private static Meter getMeter(final String name) {
        return Metrics.METRIC_REGISTRY.meter(name);
    }

    public static void incCounter(final Class<?> originatingClass, final String... names) {
        String name = MetricRegistry.name(originatingClass, names);
        Counter counter = Metrics.getCounter(name);
        counter.inc();
    }

    public static void decCounter(final Class<?> originatingClass, final String... names) {
        String name = MetricRegistry.name(originatingClass, names);
        Counter counter = Metrics.getCounter(name);
        counter.dec();
    }

    public static void markMeter(final Class<?> originatingClass, final String... names) {
        String name = MetricRegistry.name(originatingClass, names);
        Meter meter = Metrics.getMeter(name);
        meter.mark();
    }

    public static Timer.Context getTimerContext(final Class<?> originatingClass, final String... names) {
        String name = MetricRegistry.name(originatingClass, names);
        Timer timer = getTimer(name);
        return timer.time();
    }

    public void startTimer(final Class<?> originatingClass, final String... names) {
        Metrics.getTimerContext(originatingClass, names);
    }

    public void stopTimer(final Class<?> originatingClass, final String... names) {
        Timer.Context timerContext = Metrics.getTimerContext(originatingClass, names);
        timerContext.stop();
    }

    public static MetricRegistry getMetricRegistry() {
        return Metrics.METRIC_REGISTRY;
    }

    public static HealthCheckRegistry getHealthCheckRegistry() {
        return Metrics.HEALTH_CHECK_REGISTRY;
    }
}
