package com.example.cluedo_seii.network.kryonet;

import com.example.cluedo_seii.network.connectionType;

public class SelectedConType {
    private static connectionType connectionType;

    public static com.example.cluedo_seii.network.connectionType getConnectionType() {
        return connectionType;
    }

    public static void setConnectionType(com.example.cluedo_seii.network.connectionType connectionType) {
        SelectedConType.connectionType = connectionType;
    }
}
