package com.example.geome.Models;

public class AppData {
    private static AppData instance;
    private User user;

    private AppData() {
        // Приватний конструктор
    }

    public static synchronized AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

