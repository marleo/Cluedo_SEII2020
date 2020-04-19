package com.example.cluedo_seii.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.cluedo_seii.R;


public class SettingScreen extends AppCompatActivity {

    private static Boolean cheatEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);

        final Switch cheatToggle = findViewById(R.id.cheatToggle);
        cheatToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cheatEnabled = isChecked;
            }
        });

        final Button networkSetting = findViewById(R.id.network_setting);
        networkSetting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(SettingScreen.this, NetworkScreen.class));
            }
        });
    }

    public static Boolean getCheatEnabled() {
        return cheatEnabled;
    }
}
