package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.geome.Models.AppData;
import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.FragmentReplacer;
import com.example.geome.Models.User;




import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    ImageButton ImageButtonMap;
    ImageButton ImageButtonProfile;
    TextView Categories1;
    TextView Categories2;
    TextView Categories3;
    TextView Categories5;
    ImageButton ImageButtonMain;
    ImageButton ImageButtonRibbon;
    ImageButton ImageButtonCity;
    ImageButton ImageButtonChat;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageButtonMap = findViewById(R.id.ImageButtonMap);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile);
        Categories1 = findViewById(R.id.Categories1);
        Categories2 = findViewById(R.id.Categories2);
        Categories3 = findViewById(R.id.Categories3);
        Categories5 = findViewById(R.id.Categories5);
        ImageButtonMain = findViewById(R.id.ImageButtonMain);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon);
        ImageButtonCity = findViewById(R.id.ImageButtonCity);
        ImageButtonChat = findViewById(R.id.ImageButtonChat);


        initView();
    }
    private void initView() {
        ImageButtonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Перехід до іншої Activity, наприклад, FirstPage.class
                Intent intent = new Intent(HomePage.this, FirstPage.class);
                startActivity(intent);
            }
        });

        ImageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", ProfileFragment.class.getName());
                startActivity(intent);
            }
        });

        Categories1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Код для виконання при натисканні на button1
                // Наприклад, перехід до MainActivity
                Intent intent = new Intent(HomePage.this, Services.class);
                startActivity(intent);
            }
        });

        Categories2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Код для виконання при натисканні на button1
                // Наприклад, перехід до MainActivity
                Intent intent = new Intent(HomePage.this, DeliveryActivity.class);
                startActivity(intent);
            }
        });

        Categories3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Код для виконання при натисканні на button1
                // Наприклад, перехід до MainActivity
                Intent intent = new Intent(HomePage.this, HostelActivity.class);
                startActivity(intent);
            }
        });

        Categories5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, WorkActivity.class);
                startActivity(intent);
            }
        });

        ImageButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Код для виконання при натисканні на button1
                // Наприклад, перехід до MainActivity
                Intent intent = new Intent(HomePage.this, HomePage.class);
                startActivity(intent);
            }
        });

        ImageButtonRibbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", NewsFeedFragment.class.getName());
                startActivity(intent);
            }
        });

        ImageButtonCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", CityFragment.class.getName());
                startActivity(intent);
            }
        });

        ImageButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", ChatFragment.class.getName());
                startActivity(intent);
            }
        });
    }
}
