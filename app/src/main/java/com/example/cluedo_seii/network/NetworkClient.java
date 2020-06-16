package com.example.cluedo_seii.network;

import com.example.cluedo_seii.network.dto.ConnectedDTO;
import com.example.cluedo_seii.network.dto.GameCharacterDTO;
import com.example.cluedo_seii.network.dto.RequestDTO;

import java.io.IOException;

public interface NetworkClient {

    void connect(String host) throws IOException;

    void registerConnectionCallback(Callback<ConnectedDTO> callback);

    void registerCharacterCallback(Callback<GameCharacterDTO> callback);

    void sendMessage(RequestDTO message);
}
