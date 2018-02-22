package io.tsdb.nifiadmin.providers;

import io.tsdb.nifiadmin.model.Saying;

import javax.ws.rs.ext.Provider;

/**
 * @author jcreasy
 */

@Provider
public class SayingProvider implements Saying {

    private String name;
    private String template;

    public SayingProvider() {
        this.template = "Hello %s!";
    }

    @Override
    public String getName() {
        return String.format(template, name);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}