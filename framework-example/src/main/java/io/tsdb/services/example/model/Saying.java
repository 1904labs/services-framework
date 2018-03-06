package io.tsdb.services.example.model;

import javax.inject.Singleton;

/**
 * @author jcreasy
 */
@Singleton
public interface Saying {
    String getName();

    void setName(String name);
}
