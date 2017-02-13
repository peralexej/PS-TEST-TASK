package com.peterservice.context;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Контекстная мапа для хранения объектов
 */

public class TestContext {

    private static final ThreadLocal<Map<String, Object>> context = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };

    public static Object getContextVariable(String key) {
        return context.get().get(key);
    }

    public static void put(String key, Object value) {
        context.get().put(key, value);
    }

    public static Logger getLogger() {
        return (Logger) getContextVariable(ContextKey.LOGGER);
    }
}