package com.example.cluedo_seii.network.kryonet;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameCharacter;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.network.dto.ConnectedDTO;
import com.example.cluedo_seii.network.dto.GameCharacterDTO;
import com.example.cluedo_seii.network.dto.GameDTO;
import com.example.cluedo_seii.network.dto.PlayerDTO;
import com.example.cluedo_seii.network.dto.QuitGameDTO;
import com.example.cluedo_seii.network.dto.RequestDTO;
import com.example.cluedo_seii.network.dto.TextMessage;
import com.example.cluedo_seii.network.dto.UserNameRequestDTO;

import java.util.HashMap;
import java.util.LinkedList;

public class KryoHelper {
    public static void registerClasses (KryoNetComponent kryoNetComponent) {

        kryoNetComponent.registerClass(RequestDTO.class);
        kryoNetComponent.registerClass(TextMessage.class);
        kryoNetComponent.registerClass(QuitGameDTO.class);
        kryoNetComponent.registerClass(ConnectedDTO.class);
        kryoNetComponent.registerClass(UserNameRequestDTO.class);
        kryoNetComponent.registerClass(GameCharacterDTO.class);
        kryoNetComponent.registerClass(PlayerDTO.class);
        kryoNetComponent.registerClass(LinkedList.class);
        kryoNetComponent.registerClass(GameCharacter.class);
        kryoNetComponent.registerClass(GameDTO.class);
        kryoNetComponent.registerClass(Game.class);
        kryoNetComponent.registerClass(HashMap.class);
        kryoNetComponent.registerClass(Player.class);
    }
}
