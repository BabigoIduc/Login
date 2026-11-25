package com.example.login;

import java.io.Serializable;

public class Deck implements Serializable {
    private String name;
    private String description;

    public Deck(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return name + " - " + description;
    }
}