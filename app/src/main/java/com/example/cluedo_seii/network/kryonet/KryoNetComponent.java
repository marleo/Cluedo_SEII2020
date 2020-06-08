package com.example.cluedo_seii.network.kryonet;

import com.example.cluedo_seii.Game;

public interface KryoNetComponent {
    void registerClass(Class c);
    void registerClass(Class c, int id);
    void sendGame(Game game);
}
