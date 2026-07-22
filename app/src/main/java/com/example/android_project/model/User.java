package com.example.android_project.model;

import java.io.Serializable;

public class User implements Serializable {

    private final String uid;
    private final String name;
    private final String email;
    private final String phone;

    public User(String uid, String name, String email, String phone) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
