package com.example.cluedo_seii.Network.dto;

import androidx.annotation.NonNull;

public class TextMessage extends RequestDTO {
    public String text;

    public TextMessage() {

    }

    public TextMessage(String text) {
        this.text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("TextMessage: %s",text);
    }
}
