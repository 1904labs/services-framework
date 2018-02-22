package io.tsdb.common.instrumentation.reporters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * The HealthCheck for Sensu needs to be in a very specific format and layout to work properly. There are suttle
 * differences in handling and it is important that is be serialized properly.
 * @author jcreasy
 */

public class HealthcheckResultSerializer implements JsonSerializer<HealthcheckResult>, JsonDeserializer<HealthcheckResult> {
    private static final GsonBuilder builder = new GsonBuilder()
            .serializeNulls()
            .enableComplexMapKeySerialization();

    static {
        builder.registerTypeAdapter(HealthcheckResult.class, new HealthcheckResultSerializer());
    }

    static Gson gsonMetricInfoFactory(boolean pretty) {
        if (pretty) {
            return builder.setPrettyPrinting().create();
        } else {
            return builder.create();
        }
    }

    @Override
    public JsonElement serialize(final HealthcheckResult healthResult, final Type type, final JsonSerializationContext context) {
        JsonObject response = new JsonObject();
        response.add("name", new JsonPrimitive(healthResult.getName()));
        response.add("output", new JsonPrimitive(healthResult.getOutput()));
        response.add("status", new JsonPrimitive(healthResult.getStatus().getValue()));
        response.add("handlers", handlersToJSON(healthResult.getHandlers()));
        response.add("tags", tagsToJSON(healthResult.getTags()));
        return response;
    }

    private static JsonElement handlersToJSON(List<String> handlers) {
        Gson gson = gsonMetricInfoFactory(false);
        return gson.toJsonTree(handlers);
    }

    private static JsonElement tagsToJSON(Map<String, String> tags) {
        Gson gson = gsonMetricInfoFactory(false);
        return gson.toJsonTree(tags);
    }

    @Override
    public HealthcheckResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return gsonMetricInfoFactory(false).fromJson(json, HealthcheckResult.class);
    }

    static String serializeList(final List<HealthcheckResult> healthResultList) {
        Gson gson = gsonMetricInfoFactory(false);
        Type typeOfHealthResultList = new TypeToken<List<HealthcheckResult>>() {
        }.getType();
        return gson.toJson(healthResultList, typeOfHealthResultList);
    }
}