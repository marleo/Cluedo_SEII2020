package com.example.cluedo_seii.network.dto;

public class UserNameRequestDTO extends RequestDTO {
    private String username;
    //for global Games only
    private int id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
