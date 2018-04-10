package io.tsdb.services.framework.base.application;

import com.google.inject.Singleton;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.component.AbstractLifeCycle;

/**
 * JspStarter for embedded ServletContextHandlers
 * <p>
 * This is added as a bean that is a jetty LifeCycle on the ServletContextHandler.
 * This bean's doStart method will be called as the ServletContextHandler starts,
 * and will call the ServletContainerInitializer for the jsp engine.
 *
 * @author jcreasy
 */
@Singleton
public final class JspStarter extends AbstractLifeCycle implements ServletContextHandler.ServletContainerInitializerCaller {
    private JettyJasperInitializer sci;
    private ServletContextHandler context;

    /**
     * a JSP Starter.
     *
     * @param providedContext the ServletContextHandler to embded JSP support
     */
    JspStarter(final ServletContextHandler providedContext) {
        this.sci = new JettyJasperInitializer();
        this.context = providedContext;
        this.context.setAttribute("org.apache.tomcat.JarScanner", new StandardJarScanner());
    }

    @Override
    protected void doStart() throws Exception {
        ClassLoader old = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(context.getClassLoader());
        try {
            sci.onStartup(null, context.getServletContext());
            super.doStart();
        } finally {
            Thread.currentThread().setContextClassLoader(old);
        }
    }
}
