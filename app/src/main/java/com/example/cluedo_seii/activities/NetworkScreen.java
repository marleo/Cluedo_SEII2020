package com.example.networktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.networktest.network.kryonet.NetworkClientKryo;
import com.example.networktest.network.kryonet.NetworkServerKryo;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private connectionType conType;
    private NetworkServerKryo server;
    private NetworkClientKryo client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
