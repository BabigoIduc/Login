package com.example.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String password;
    private List<Deck> deckList;
    //private Settings settings;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.deckList = new ArrayList<>();
        //this.settings = new Settings();
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public List<Deck> getDeckList() { return deckList; }
   // public Settings getSettings() { return settings; }
}