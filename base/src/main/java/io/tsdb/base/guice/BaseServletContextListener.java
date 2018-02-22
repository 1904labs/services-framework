package io.tsdb.base.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * @author jcreasy
 */
public class BaseServletContextListener extends GuiceServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseServletContextListener.class);
    private Module[] modules;
    @SuppressWarnings("WeakerAccess")
    ServletContext servletContext;

    /**
     * Creates with guice module(s) provided.
     *
     * @param guiceModules Modules to be loaded into the context
     */
    public BaseServletContextListener(final Module... guiceModules) {
        LOGGER.debug("Registering Modules to Load");
        this.modules = guiceModules;
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        servletContext = event.getServletContext();
        super.contextInitialized(event);
    }

    @Override
    protected Injector getInjector() {
        //new SampleShiroNativeSessionsServletModule(servletContext), ShiroWebModule.guiceFilterModule()
        return Guice.createInjector(modules);
    }
}
