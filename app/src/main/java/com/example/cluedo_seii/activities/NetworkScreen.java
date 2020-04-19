package com.example.cluedo_seii.activities;

import android.os.Bundle;
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

            String[] hosts = new String[1];
            hosts[0] = "10.0.2.2";
            client.execute(hosts);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
