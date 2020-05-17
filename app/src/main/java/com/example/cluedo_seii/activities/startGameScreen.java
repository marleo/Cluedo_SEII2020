package com.example.cluedo_seii.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cluedo_seii.Network.connectionType;
import com.example.cluedo_seii.Network.dto.FirstConnectDTO;
import com.example.cluedo_seii.Network.dto.QuitGameDTO;
import com.example.cluedo_seii.Network.dto.RequestDTO;
import com.example.cluedo_seii.Network.dto.TextMessage;
import com.example.cluedo_seii.Network.kryonet.NetworkClientKryo;
import com.example.cluedo_seii.Network.kryonet.NetworkServerKryo;
import com.example.cluedo_seii.R;

import java.io.IOException;

public class startGameScreen extends AppCompatActivity {
    private connectionType conType;
    private NetworkServerKryo server;
    private NetworkClientKryo client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_screen);
    }

    public void selectHost(View view) {
        this.conType = connectionType.HOST;

        server = NetworkServerKryo.getInstance();
        server.registerClass(RequestDTO.class);
        server.registerClass(TextMessage.class);
        server.registerClass(QuitGameDTO.class);
        server.registerClass(FirstConnectDTO.class);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        TextView serverResponse = findViewById(R.id.ipAddress);
        serverResponse.setText(ip);
    }

    public void selectClient(View view) {
        try {
            this.conType = connectionType.CLIENT;

            client = NetworkClientKryo.getInstance();


            client.registerClass(RequestDTO.class);
            client.registerClass(TextMessage.class);
            client.registerClass(QuitGameDTO.class);
            client.registerClass(FirstConnectDTO.class);

            //client.connect("localhost");

            EditText ipInput = findViewById(R.id.ipAddress);
            String ip = ipInput.getText().toString();

            if (ip.equals("ip")) {
                //TODO delete hardcoded ip
                ip = "192.168.178.47";
            }



            try {
                client.connect(ip);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
