package com.example.cluedo_seii.network;

import com.example.cluedo_seii.network.dto.RequestDTO;

import java.io.IOException;

public interface NetworkGlobalHost {
    void connect(String host) throws IOException;
}
