package com.codecool.summer;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.*;
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
        List<Method> allMethods = getAllMethods(handlerClasses);
        allMethods.stream()
                .filter(e -> e.getAnnotation(WebRoute.class) != null)
                .forEach(e -> handlerMethods.put(e.getAnnotation(WebRoute.class).path(), e));
        }

    private List<Method> getAllMethods(Class<?>[] handlerClasses) {
        List<Method> allMethods = new ArrayList<>();
        Arrays.stream(handlerClasses).forEach(e -> allMethods.addAll(Arrays.asList(e.getMethods())));
        return allMethods;
    }

    /**
     * Starts HTTP server, waits for HTTP requests and redirects them to one of registered handler methods.
     */
    public void run() throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new HttpRequestHandler(handlerMethods));
        server.setExecutor(null);
        server.start();
    }

    public void printMethods(){
        for (Map.Entry<String, Method> entry : handlerMethods.entrySet()) {
            System.out.println("PATH: " + entry.getKey() + ", METHOD: " + entry.getValue().toString());
        }
    }
}
