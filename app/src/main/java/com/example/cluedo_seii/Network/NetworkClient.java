package com.example.networktest.network;

import com.example.networktest.network.dto.BaseMessage;

import java.io.IOException;

public interface NetworkClient {

    void connect(String host) throws IOException;

    void registerCallback(Callback<BaseMessage> callback);

    void sendMessage(BaseMessage message);
}
