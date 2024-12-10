package com.pluralsight.backend.server_demo;

public class Cat {
    private String name;
    private String catchPhrase;

    public Cat(String name, String catchPhrase) {
        this.name = name;
        this.catchPhrase = catchPhrase;
    }

    //Needed to make a bean
    public Cat() {

    }

    //Neaded to make a bean
    public String getName() {
        return name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }
}
