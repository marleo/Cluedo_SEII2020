package com.example.cluedo_seii.activities;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cluedo_seii.Network.connectionType;
import com.example.cluedo_seii.Network.kryonet.NetworkClientKryo;
import com.example.cluedo_seii.Network.kryonet.NetworkServerKryo;

import com.example.cluedo_seii.R;

import java.io.IOException;

public class NetworkScreen extends AppCompatActivity {
    private connectionType conType;
    private NetworkServerKryo server;
    private NetworkClientKryo client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
    }

    public void selectHost(View view) {
        this.conType = connectionType.HOST;

        TextView txtType = findViewById(R.id.typeText);
        txtType.setText("HOST");

        server = new NetworkServerKryo();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        TextView serverResponse = findViewById(R.id.serverResponse);
        serverResponse.setText(ip);
    }

    public void selectClient(View view) {
        try {
            this.conType = connectionType.CLIENT;

            TextView txtType = findViewById(R.id.typeText);
            txtType.setText("CLIENT");

            client = new NetworkClientKryo();

            /*
            TextView txtResp = findViewById(R.id.serverResponse);
            txtResp.setText(client.getNetworks().toString() + " " + client.getNetworks().size());
             */

            /*
            NetworkFinder nf = new NetworkFinder();
            nf.start();
            */

            //client.connect("localhost");

            client.connect("192.168.178.47");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
