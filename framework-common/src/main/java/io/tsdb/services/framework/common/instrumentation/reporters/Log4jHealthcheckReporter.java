package io.tsdb.services.framework.common.instrumentation.reporters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author jcreasy
 */
public class Log4jHealthcheckReporter extends AbstractHealthcheckReporter {
    private final static Logger LOGGER = LoggerFactory.getLogger(Log4jHealthcheckReporter.class);

    public Log4jHealthcheckReporter(int reportingInterval) {
        super(reportingInterval);
    }

    @Override
    public void report(List<HealthcheckResult> results) {
        for (HealthcheckResult result : results) {
            LOGGER.info("{}: is {}", result.getName(), result.getStatus().toString());
            LOGGER.debug(result.toString(true));
        }
    }
}