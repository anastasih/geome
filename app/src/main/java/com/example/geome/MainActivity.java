package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FirstPage.class);

                //Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                //Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                //Intent intent = new Intent(MainActivity.this, SignUpActivityFinish.class);
                startActivity(intent);
            }
        });
    }
}