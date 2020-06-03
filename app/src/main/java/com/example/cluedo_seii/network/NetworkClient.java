package com.example.cluedo_seii.network;

import com.esotericsoftware.kryonet.Connection;
import com.example.cluedo_seii.network.dto.ConnectedDTO;
import com.example.cluedo_seii.network.dto.GameCharacterDTO;
import com.example.cluedo_seii.network.dto.RequestDTO;
import com.example.cluedo_seii.network.kryonet.NetworkClientKryo;

import java.io.IOException;

public interface NetworkClient {

    void connect(String host) throws IOException;

    void registerCallback(Callback<RequestDTO> callback);

    void registerConnectionCallback(Callback<ConnectedDTO> callback);

    void registerCharacterCallback(Callback<GameCharacterDTO> callback);

    void sendMessage(RequestDTO message);
}
