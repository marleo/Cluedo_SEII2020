package com.example.networktest.network;

import com.example.networktest.network.dto.BaseMessage;

import java.io.IOException;

public interface NetworkServer {

    void start() throws IOException;

    void registerCallback(Callback<BaseMessage> callback);

    void broadcastMessage(BaseMessage message);
}
