package io.tsdb.services.framework.common.instrumentation.reporters;

import com.codahale.metrics.InstrumentedScheduledExecutorService;
import com.codahale.metrics.health.HealthCheck;
import io.tsdb.services.framework.common.instrumentation.Metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author jcreasy
 */
abstract class AbstractHealthcheckReporter implements Runnable {
    private static final InstrumentedScheduledExecutorService scheduler = new InstrumentedScheduledExecutorService(Executors.newScheduledThreadPool(2), Metrics.getMetricRegistry());

    abstract void report(List<HealthcheckResult> results);

    AbstractHealthcheckReporter(int reportingInterval) {
        scheduler.scheduleAtFixedRate(this, reportingInterval, reportingInterval, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        List<HealthcheckResult> checkResults = new ArrayList<>();

        for (Map.Entry<String, HealthCheck.Result> entry : Metrics.getHealthCheckRegistry().runHealthChecks().entrySet()) {
            checkResults.add(new HealthcheckResult(entry.getKey(), entry.getValue()));
        }
        report(checkResults);
    }
}
