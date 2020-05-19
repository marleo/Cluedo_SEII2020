package com.example.cluedo_seii.Network.dto;

public class UserNameRequestDTO extends RequestDTO {
    private String username;

    public UserNameRequestDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
