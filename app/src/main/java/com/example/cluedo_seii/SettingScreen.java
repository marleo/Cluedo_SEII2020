package com.example.cluedo_seii;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;


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
    }

    public static Boolean getCheatEnabled() {
        return cheatEnabled;
    }
}
