package io.tsdb.services.framework.common.configuration;

import org.apache.commons.configuration2.SystemConfiguration;

/**
 * @author jcreasy
 */
class EnvironmentConfiguration extends ConfigurationSourceImpl {
    EnvironmentConfiguration() {
        configuration = new SystemConfiguration();
    }
}
