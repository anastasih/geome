package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.Room;
import com.example.geome.Models.RoomsAdapter;

import java.util.Date;
import java.util.List;

public class Accommodation_options extends AppCompatActivity {
    private TextView data1;
    private TextView data2;
    private TextView adults;
    private TextView children;
    private ListView listView_options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_options);

        data1 = findViewById(R.id.data1);
        data2 = findViewById(R.id.data2);
        adults = findViewById(R.id.adults);
        children = findViewById(R.id.children);
        listView_options = findViewById(R.id.listView_options);

        Intent intent = getIntent();
        Date date1 = (Date) intent.getSerializableExtra(payment.KEY_IN_DATE);
        Date date2 = (Date) intent.getSerializableExtra(payment.KEY_OUT_DATE);
        int number1 = (Integer) intent.getSerializableExtra(payment.KEY_NUMBER);
        int IdCompany = (Integer) intent.getSerializableExtra(payment.KEY_ID_COMPANY);

        data1.setText(String.valueOf(date1));
        data2.setText(String.valueOf(date2));
        adults.setText(String.valueOf(number1));

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<Room> rooms = databaseHelper.getRoomsByIdCompany(IdCompany);

        RoomsAdapter adapter = new RoomsAdapter(this, R.layout.hotel_card, rooms);
        listView_options.setAdapter(adapter);

    }
}