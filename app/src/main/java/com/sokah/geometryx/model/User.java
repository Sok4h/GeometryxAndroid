package com.sokah.geometryx.model;

public class User {

    private String type="User";
    private  String name;
    private int spaceShip;

    public User(String name, int spaceShip) {
        this.name = name;
        this.spaceShip = spaceShip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
