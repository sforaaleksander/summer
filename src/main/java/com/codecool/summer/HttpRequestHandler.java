package com.codecool.summer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
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
        String uri = httpExchange.getRequestURI().toString();
        String response = "";
        int httpStatusCode;
        if (!handlerMethods.containsKey(uri)){
            sendResponse(httpExchange, 404, "wrong path provided");
            return;
        }
        try {
            response = (String) handlerMethods.get(uri).invoke(null);
            httpStatusCode = 200;
        } catch (IllegalAccessException | InvocationTargetException e) {
            response = "internal server error";
            httpStatusCode = 500;
            e.printStackTrace();
        }
        sendResponse(httpExchange, httpStatusCode, response);
    }

    private void sendResponse(HttpExchange httpExchange, int httpStatusCode, String message) throws IOException {
        httpExchange.sendResponseHeaders(httpStatusCode, message.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(message.getBytes());
        os.close();
    }
}
