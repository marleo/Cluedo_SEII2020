package com.example.cluedo_seii.network.kryonet;

import android.graphics.Point;

import com.example.cluedo_seii.Card;
import com.example.cluedo_seii.CardType;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameCharacter;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.InvestigationFile;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.network.dto.ConnectedDTO;
import com.example.cluedo_seii.network.dto.GameCharacterDTO;
import com.example.cluedo_seii.network.dto.GameDTO;
import com.example.cluedo_seii.network.dto.NewGameRoomRequestDTO;
import com.example.cluedo_seii.network.dto.PlayerDTO;
import com.example.cluedo_seii.network.dto.QuitGameDTO;
import com.example.cluedo_seii.network.dto.RegisterClassDTO;
import com.example.cluedo_seii.network.dto.RequestDTO;
import com.example.cluedo_seii.network.dto.RoomsDTO;
import com.example.cluedo_seii.network.dto.SerializedDTO;
import com.example.cluedo_seii.network.dto.TextMessage;
import com.example.cluedo_seii.network.dto.UserNameRequestDTO;
import com.example.cluedo_seii.spielbrett.Gameboard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class KryoHelper {

    private KryoHelper() {

    }

    /**
     * Diese Methode registriert alle Klassen die für Kryo relevant sind
     * @param kryoNetComponent client, server or globalHost
     *
     *
     */
    public static void registerClasses (KryoNetComponent kryoNetComponent) {
        //Classes with ID are for Server Application
        kryoNetComponent.registerClass(RequestDTO.class, 1);
        kryoNetComponent.registerClass(TextMessage.class);
        kryoNetComponent.registerClass(QuitGameDTO.class);
        kryoNetComponent.registerClass(ConnectedDTO.class);
        kryoNetComponent.registerClass(UserNameRequestDTO.class,7);
        kryoNetComponent.registerClass(GameCharacterDTO.class);
        kryoNetComponent.registerClass(PlayerDTO.class);
        kryoNetComponent.registerClass(LinkedList.class,6);
        kryoNetComponent.registerClass(GameCharacter.class);
        kryoNetComponent.registerClass(GameDTO.class);
        kryoNetComponent.registerClass(Game.class);
        kryoNetComponent.registerClass(HashMap.class);
        kryoNetComponent.registerClass(Player.class);
        kryoNetComponent.registerClass(GameState.class);
        kryoNetComponent.registerClass(InvestigationFile.class);
        kryoNetComponent.registerClass(Card.class);
        kryoNetComponent.registerClass(CardType.class);
        kryoNetComponent.registerClass(Point.class);
        kryoNetComponent.registerClass(Gameboard.class);
        kryoNetComponent.registerClass(Random.class);

        kryoNetComponent.registerClass(RegisterClassDTO.class,2);
        //kryoNetComponent.registerClass(Class.class);
        //kryoNetComponent.registerClass(Object.class);
        kryoNetComponent.registerClass(SerializedDTO.class,3);
        kryoNetComponent.registerClass(NewGameRoomRequestDTO.class,4);
        kryoNetComponent.registerClass(RoomsDTO.class,5);
    }
}
