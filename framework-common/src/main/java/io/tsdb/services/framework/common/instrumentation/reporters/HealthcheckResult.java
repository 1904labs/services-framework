package io.tsdb.services.framework.common.instrumentation.reporters;

import com.codahale.metrics.health.HealthCheck;
import com.google.gson.Gson;
import io.tsdb.services.framework.common.configuration.ServiceInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jcreasy
 */
@SuppressWarnings("WeakerAccess")
public class HealthcheckResult implements Serializable {
    private final List<String> handlers = new ArrayList<>();
    private final Map<String, String> tags = new HashMap<>();
    private String name;
    private String output;
    private HealthcheckResultStatus status = HealthcheckResultStatus.OK;

    @SuppressWarnings("unused")
    public HealthcheckResult(String name, String output) {
        this(name, output, HealthcheckResultStatus.UNKNOWN);
    }

    public HealthcheckResult(String name, HealthCheck.Result healthCheckResult) {
        this.setName(name);
        this.setOutput(healthCheckResult.getMessage());
        if (healthCheckResult.isHealthy()) {
            this.setStatus(HealthcheckResultStatus.OK);
        } else {
            this.setStatus(HealthcheckResultStatus.CRITICAL);
        }
    }

    private HealthcheckResult(String name, String output, HealthcheckResultStatus status) {
        this.setName(name);
        this.setOutput(output);
        this.setStatus(status);
        setDefaultHandlers();
        setDefaultTags();
    }

    private void setDefaultTags() {
        this.addTag("servicename", ServiceInfo.serviceName);
        this.addTag("processname", ServiceInfo.processName);
        this.addTag("hostname", ServiceInfo.getHostString());
        this.addTag("adminport", String.valueOf(ServiceInfo.adminPort));
        this.addTag("adminpath", ServiceInfo.adminPath);
    }

    private void setDefaultHandlers() {
        this.addHandler("notification-standard");
    }

    public HealthcheckResultStatus getStatus() {
        return status;
    }

    public void setStatus(HealthcheckResultStatus status) {
        this.status = status;
    }

    @SuppressWarnings("unused")
    public int getStatusValue() {
        return status.getValue();
    }

    public String getOutput() {
        return output;
    }

    private void setOutput(String output) {
        this.output = output;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public List<String> getHandlers() {
        return handlers;
    }

    public void addHandler(@SuppressWarnings("SameParameterValue") String handler) {
        this.handlers.add(handler);
    }

    public void addTag(String tagk, String tagv) {
        this.tags.put(tagk, tagv);
    }

    public String toString(boolean pretty) {
        Gson gson = HealthcheckResultSerializer.gsonMetricInfoFactory(pretty);
        return gson.toJson(this);
    }
}