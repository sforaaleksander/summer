package com.codecool.summer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Summer {
    final Map<String, Method> handlerMethods;

    public Summer() {
        handlerMethods = new HashMap<>();
    }

    /**
     * Accepts any number of handlers and extracts all methods annotated with {@link WebRoute}.
     *
     * @param handlerClasses classes with methods annotated with {@link WebRoute}.
     */
    public void registerHandlers(Class<?>... handlerClasses) {
        Method[] allMethods = ExampleHandler.class.getDeclaredMethods();
        List<Method> webRouteMethods = Arrays.stream(allMethods)
                .filter(e -> e.getAnnotation(WebRoute.class) != null)
                .map(e -> handlerMethods.put(e.getAnnotation(WebRoute.class).path(), e))
                .collect(Collectors.toList());
        };

    /**
     * Starts HTTP server, waits for HTTP requests and redirects them to one of registered handler methods.
     */
    public void run() {
        // TODO
    }
}
