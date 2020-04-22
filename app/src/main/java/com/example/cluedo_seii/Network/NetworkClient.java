package com.example.cluedo_seii.Network;

import com.example.cluedo_seii.Network.dto.BaseMessage;

import java.io.IOException;

public interface NetworkClient {

    void connect(String host) throws IOException;

    void registerCallback(Callback<BaseMessage> callback);

    void sendMessage(BaseMessage message);
}
