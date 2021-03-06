package com.example.covidtrackinghack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class question1 extends AppCompatActivity {
    Button button_yes, button_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);
        button_no = findViewById(R.id.button_no_question1);
        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), question2.class));
            }
        });
        button_yes = findViewById(R.id.button_yes_question1);
        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), question2.class));
            }
        });
    }

}