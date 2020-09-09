package com.codecool.summer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Map;

public class HttpRequestHandler implements HttpHandler {
    final Map<String, Method> handlerMethods;

    public HttpRequestHandler(Map<String, Method> handlerMethods) {
        this.handlerMethods = handlerMethods;
    }

    /**
     * Invokes proper method handling proper endpoint and sends HTTP Response back.
     * @param httpExchange Encapsulated HTTP request.
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        sendResponse(httpExchange, 500, "Not Implemented");
    }

    private void sendResponse(HttpExchange httpExchange, int httpStatusCode, String message) throws IOException {
        httpExchange.sendResponseHeaders(200, message.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(message.getBytes());
        os.close();
    }
}
