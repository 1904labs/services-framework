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
public class PackageLoaderModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(PackageLoaderModule.class);
    private String REST_EASY_REST_PACKAGES;

    PackageLoaderModule(String packages) {
        REST_EASY_REST_PACKAGES = packages;
    }

    @Override
    protected void configure() {
        loadFixedProviders();
    }

    private void bindClass(Class classToBind) throws Exception {
        LOGGER.debug("Binding: {}", classToBind.getCanonicalName());
        bind(Class.forName(classToBind.getName()));
    }

    private void loadFixedProviders() {
        String[] pkgs = REST_EASY_REST_PACKAGES.split(";");
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