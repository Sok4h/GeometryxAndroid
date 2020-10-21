package com.sokah.geometryx.model;

public class User {

    private String type="User";
    private  String name;
    private int tspaceship;

    public User(String name, int tspaceship) {
        this.name = name;
        this.tspaceship = tspaceship;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
