package io.tsdb.common.instrumentation.reporters;

import com.google.common.net.HostAndPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * @author jcreasy
 */
public class SensuHealthcheckReporter extends AbstractHealthcheckReporter {
    private final static Logger LOGGER = LoggerFactory.getLogger(SensuHealthcheckReporter.class);
    private String clientSocketHost = "localhost";
    private int clientSocketPort = 3030;

    public SensuHealthcheckReporter(int reportingInterval) {
        super(reportingInterval);
    }

    public SensuHealthcheckReporter(int reportingInterval, String clientSocketHost, int clientSocketPort) {
        super(reportingInterval);
        this.setClientSocketHost(clientSocketHost);
        this.setClientSocketPort(clientSocketPort);
    }

    public SensuHealthcheckReporter(int reportingInterval, HostAndPort sensuAddress) {
        super(reportingInterval);
        this.setClientSocketHost(sensuAddress.getHost());
        this.setClientSocketPort(sensuAddress.getPort());
    }

    @Override
    public void report(List<HealthcheckResult> results) {
        try {
            String serializedResults = HealthcheckResultSerializer.serializeList(results);
            Socket socket = new Socket(this.getClientSocketHost(), this.getClientSocketPort());
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            output.print(serializedResults);
            output.flush();
            output.close();
        } catch (IOException e) {
            LOGGER.error("Could not send report to Sensu: " + e.getMessage());
        }
    }

    private String getClientSocketHost() {
        return clientSocketHost;
    }

    private void setClientSocketHost(String clientSocketHost) {
        this.clientSocketHost = clientSocketHost;
    }

    private int getClientSocketPort() {
        return clientSocketPort;
    }

    private void setClientSocketPort(int clientSocketPort) {
        this.clientSocketPort = clientSocketPort;
    }
}