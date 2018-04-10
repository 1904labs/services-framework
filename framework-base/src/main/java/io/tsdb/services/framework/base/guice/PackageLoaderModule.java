package io.tsdb.services.framework.base.guice;

import com.google.inject.AbstractModule;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import java.util.Set;

/**
 * @author jcreasy
 */
public final class PackageLoaderModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(PackageLoaderModule.class);
    private String restEasyRestPackages;

    /**
     * Loads Packages.
     * @param packages the list of packages to load
     */
    PackageLoaderModule(final String packages) {
        restEasyRestPackages = packages;
    }

    @Override
    protected void configure() {
        loadFixedProviders();
    }

    /**
     * Binds a located Class.
     * @param classToBind class to bind
     * @throws Exception thrown if it fails
     */
    private void bindClass(final Class classToBind) throws Exception {
        LOGGER.debug("Binding: {}", classToBind.getCanonicalName());
        bind(Class.forName(classToBind.getName()));
    }

    /**
     * Look through and load the providers.
     */
    private void loadFixedProviders() {
        String[] pkgs = restEasyRestPackages.split(";");
        Reflections reflections;
        for (String pkg : pkgs) {
            reflections = new Reflections(pkg);
            LOGGER.debug("Trying Package: {}", pkg);
            Set<Class<?>> resourceClasses = reflections.getTypesAnnotatedWith(Path.class);
            for (Class<?> classToBind : resourceClasses) {
                try {
                    bindClass(classToBind);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            Set<Class<?>> providerClasses = reflections.getTypesAnnotatedWith(Provider.class);
            for (Class<?> classToBind : providerClasses) {
                try {
                    bindClass(classToBind);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }
}
