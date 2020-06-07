package com.example.cluedo_seii.network.dto;


public class TextMessage extends RequestDTO {
    public String text;

    public TextMessage() {

    }

    public TextMessage(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("TextMessage: %s",text);
    }
}
