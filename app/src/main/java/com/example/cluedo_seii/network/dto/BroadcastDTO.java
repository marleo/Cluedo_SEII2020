package com.example.cluedo_seii.network.dto;

public class BroadcastDTO extends RequestDTO {
    private String serializedObject;

    public String getSerializedObject() {
        return serializedObject;
    }

    public void setSerializedObject(String serializedObject) {
        this.serializedObject = serializedObject;
    }
}
