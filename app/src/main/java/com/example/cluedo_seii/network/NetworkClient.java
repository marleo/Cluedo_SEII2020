package com.example.cluedo_seii.network;

import com.example.cluedo_seii.network.dto.RequestDTO;

import java.io.IOException;

public interface NetworkClient {

    void connect(String host) throws IOException;

    void registerCallback(Callback<RequestDTO> callback);

    void sendMessage(RequestDTO message);
}
