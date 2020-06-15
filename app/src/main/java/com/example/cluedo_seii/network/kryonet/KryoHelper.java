package com.example.cluedo_seii.network.kryonet;

import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import android.graphics.Point;

import com.example.cluedo_seii.Card;
import com.example.cluedo_seii.CardType;
import com.example.cluedo_seii.Game;
import com.example.cluedo_seii.GameCharacter;
import com.example.cluedo_seii.GameState;
import com.example.cluedo_seii.InvestigationFile;
import com.example.cluedo_seii.MyPoint;
import com.example.cluedo_seii.Notepad;
import com.example.cluedo_seii.Player;
import com.example.cluedo_seii.network.dto.AccusationMessageDTO;
import com.example.cluedo_seii.network.dto.CheatDTO;
import com.example.cluedo_seii.network.dto.BroadcastDTO;
import com.example.cluedo_seii.network.dto.ConnectedDTO;
import com.example.cluedo_seii.network.dto.GameCharacterDTO;
import com.example.cluedo_seii.network.dto.GameDTO;
import com.example.cluedo_seii.network.dto.NewGameRoomRequestDTO;
import com.example.cluedo_seii.network.dto.PlayerDTO;
import com.example.cluedo_seii.network.dto.QuitGameDTO;
import com.example.cluedo_seii.network.dto.RegisterClassDTO;
import com.example.cluedo_seii.network.dto.RequestDTO;
import com.example.cluedo_seii.network.dto.RoomsDTO;
import com.example.cluedo_seii.network.dto.SendToOnePlayerDTO;
import com.example.cluedo_seii.network.dto.SerializedDTO;
import com.example.cluedo_seii.network.dto.SuspicionAnswerDTO;
import com.example.cluedo_seii.network.dto.SuspicionDTO;
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
        kryoNetComponent.registerClass(AccusationMessageDTO.class);
        kryoNetComponent.registerClass(String.class);
        kryoNetComponent.registerClass(InvestigationFile.class);
        kryoNetComponent.registerClass(Card.class);
        kryoNetComponent.registerClass(CardType.class);
        kryoNetComponent.registerClass(Notepad.class);
        kryoNetComponent.registerClass(Card[].class);
        kryoNetComponent.registerClass(TextView[].class);
        kryoNetComponent.registerClass(CheatDTO.class);
        kryoNetComponent.registerClass(AppCompatTextView.class);
        kryoNetComponent.registerClass(MyPoint.class);
        kryoNetComponent.registerClass(Gameboard.class);
        kryoNetComponent.registerClass(Random.class);
        kryoNetComponent.registerClass(RegisterClassDTO.class,2);
        kryoNetComponent.registerClass(SuspicionDTO.class);
        kryoNetComponent.registerClass(SuspicionAnswerDTO.class);
        kryoNetComponent.registerClass(SerializedDTO.class,3);
        kryoNetComponent.registerClass(NewGameRoomRequestDTO.class,4);
        kryoNetComponent.registerClass(RoomsDTO.class,5);
        kryoNetComponent.registerClass(SendToOnePlayerDTO.class,8);
        kryoNetComponent.registerClass(BroadcastDTO.class,9);
    }
}
