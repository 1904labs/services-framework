package io.tsdb.base.guice;

import com.google.inject.servlet.ServletModule;
import io.tsdb.base.shiro.modules.ShiroServletModule;
import io.tsdb.base.shiro.modules.ShiroAnnotationsModule;
import io.tsdb.common.configuration.ServiceInfo;
import org.apache.shiro.guice.web.GuiceShiroFilter;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;

/**
 * @author jcreasy
 */
@WebListener
public class BaseServletModule extends ServletModule {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BaseServletModule.class);

    /**
     * Configures servlet with default settings, RESTful endpoint package(s), etc.
     */
    @Override
    protected void configureServlets() {

        String endpointPackages = "io.tsdb.base.service.filters;"
                + "io.tsdb.base.service.responses;"
                + "io.tsdb.base.service.providers;"
                + "io.tsdb.base.service.rest;"
                + "io.tsdb.base.shiro.providers;"
                + ServiceInfo.endpointPackage + ";" // io.tsdb.$servicename.rest
                + ServiceInfo.providersPackage + ";"; // io.tsdb.$servicename.providers

        install(new ShiroServletModule(getServletContext()));
        install(new ShiroAnnotationsModule());

        LOGGER.info("Rest Endpoint Packages: {}", endpointPackages);
        super.configureServlets();

        LOGGER.info("Bootstrap Main Servlet");
        install(new PackageLoaderModule(endpointPackages));

        filter("/*").through(GuiceShiroFilter.class);
        filter("/*").through(GuiceRestEasyFilterDispatcher.class);
    }
}