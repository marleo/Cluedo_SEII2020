package com.example.cluedo_seii.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cluedo_seii.Notepad;
import com.example.cluedo_seii.R;

public class MoreNotesScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad_morenotes);

        final EditText editText = findViewById(R.id.addMoreNotes);
        final Button btn = findViewById(R.id.addNotesButton);
        final TextView textView2 = findViewById(R.id.moreNotes);


        View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                textView2.append(message);




            }
        };
        btn.setOnClickListener(onButtonClickListener);

    }
}
