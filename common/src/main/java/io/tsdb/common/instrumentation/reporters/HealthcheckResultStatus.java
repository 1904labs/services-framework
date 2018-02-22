package io.tsdb.common.instrumentation.reporters;

/**
 * @author jcreasy
 */
public enum HealthcheckResultStatus {
    OK(0), WARNING(1), CRITICAL(2), UNKNOWN(3);

    private final int i;

    HealthcheckResultStatus(int i) {
        this.i = i;
    }

    public int getValue() {
        return i;
    }
}