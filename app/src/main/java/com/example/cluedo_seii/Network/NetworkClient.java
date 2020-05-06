package com.example.cluedo_seii.Network;

import com.example.cluedo_seii.Network.dto.BaseMessage;
import com.example.cluedo_seii.Network.dto.RequestDTO;

import java.io.IOException;

public interface NetworkClient {

    void connect(String host) throws IOException;

    void registerCallback(Callback<RequestDTO> callback);

    void sendMessage(RequestDTO message);
}
