/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.tsdb.services.framework.base.shiro.modules;

import com.google.inject.Provides;
import com.google.inject.name.Names;
import io.tsdb.services.framework.common.configuration.ConfigurationReader;
import io.tsdb.services.framework.common.configuration.ServiceInfo;
import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.text.IniRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.servlet.ServletContext;
import java.net.MalformedURLException;

/**
 * ShiroServletModule, this is the meat of the Shiro integration.
 */
public final class ShiroServletModule extends ShiroWebModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroServletModule.class);
    private static final String SHIRO_INI_URL = ConfigurationReader.getProperty("shiro.configuration", "classpath:shiro.ini");
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final ServletContext servletContext;

    /**
     * The ShiroServletModule.
     *
     * @param context a ServletContext to operate on
     */
    public ShiroServletModule(final ServletContext context) {
        super(context);
        LOGGER.debug("Initialize ShiroWebModule");
        this.servletContext = context;
    }

    @Override
    protected void configureShiroWeb() {
        LOGGER.debug("Configure ShiroWebModule");
        bindConstant().annotatedWith(Names.named("shiro.loginUrl")).to("/login.jsp");
        try {
            this.bindRealm().toConstructor(IniRealm.class.getConstructor(Ini.class));
        } catch (NoSuchMethodException e) {
            addError("Could not locate proper constructor for IniRealm.", e);
        }

        this.addFilterChain("/login.jsp", AUTHC);
        this.addFilterChain("/logout", LOGOUT);
        this.addFilterChain("/index.jsp", ANON);
        this.addFilterChain("/home.jsp", ANON);
        this.addFilterChain("/static/**", ANON);
        this.addFilterChain("/" + ServiceInfo.serviceName, ANON);
        this.addFilterChain("/**", ANON);
    }

    /**
     * Loads the Shiro INI file.
     * This is where the user and group information comes from.
     *
     * @return returns in INI file objects
     * @throws MalformedURLException thrown if the URL cannot be resolved
     */
    @Provides
    @Singleton
    Ini loadShiroIni() throws MalformedURLException {
        LOGGER.debug("Load Shiro INI");
        try {
            return Ini.fromResourcePath(SHIRO_INI_URL);
        } catch (Exception e) {
            LOGGER.error("Could not load INI", e);
            throw e;
        }
    }
}
