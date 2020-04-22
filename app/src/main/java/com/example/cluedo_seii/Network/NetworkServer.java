package com.example.cluedo_seii.Network;

import com.example.cluedo_seii.Network.dto.BaseMessage;

import java.io.IOException;

public interface NetworkServer {

    void start() throws IOException;

    void registerCallback(Callback<BaseMessage> callback);

    void broadcastMessage(BaseMessage message);
}
