package io.tsdb.services.example.providers;

import io.tsdb.services.example.model.Saying;

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