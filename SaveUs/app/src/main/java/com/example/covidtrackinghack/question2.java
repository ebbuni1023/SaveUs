package com.example.covidtrackinghack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class question2 extends AppCompatActivity {
    Button button_yes2, button_no2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);
        button_no2 = findViewById(R.id.button2);

        button_no2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), youGotNoCovid.class));
            }
        });
        button_yes2 = findViewById(R.id.button_yes_question2);
        button_yes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), youGotCovid.class));
            }
        });
    }
}