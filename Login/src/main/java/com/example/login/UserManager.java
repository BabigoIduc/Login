package com.example.login;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    // Matches UML: -userList : Map<String, User>
    private Map<String, User> userList = new HashMap<>();
    private User currentUser; // Tracks who is logged in
    private static final String FILE_PATH = "users.dat";

    public UserManager() {
        loadUsersFromFile();
        if (userList.isEmpty()) {
            registerUser("admin", "1234");
        }
    }

    public boolean authenticate(String username, String password) {
        User user = userList.get(username);
        if (user != null && user.getPassword().equals(password)) {
            this.currentUser = user; // Save active user for decks/cards later
            return true;
        }
        return false;
    }

    public boolean registerUser(String username, String password) {
        if (userList.containsKey(username)) {
            return false; // Username taken
        }
        userList.put(username, new User(username, password));
        saveUsersToFile();
        return true;
    }

    public void logout() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // Switched to ObjectOutputStream so nested Decks and Cards save automatically
    public void saveUsersToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadUsersFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            userList = (Map<String, User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}