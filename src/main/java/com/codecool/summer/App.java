package com.codecool.summer;


import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        Summer summer = new Summer();
        summer.registerHandlers(ExampleHandler.class);
        summer.run();
    }
}
