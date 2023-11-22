package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.geome.Models.Booking;
import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.Room;
import com.example.geome.Models.RoomsAdapter;
import java.text.SimpleDateFormat;
import java.util.Locale;

import java.util.Date;
import java.util.List;

public class Accommodation_options extends AppCompatActivity {
    private TextView data1;
    private TextView data2;
    private TextView adults;
    private TextView children;
    private TextView room;
    private ListView listView_options;
    public Booking booking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_options);

        data1 = findViewById(R.id.data1);
        data2 = findViewById(R.id.data2);
        adults = findViewById(R.id.adults);
        children = findViewById(R.id.children);
        room = findViewById(R.id.room);
        listView_options = findViewById(R.id.listView_options);

        Intent intent = getIntent();
        booking = (Booking) intent.getSerializableExtra(payment.KEY_BOOKING);
//        Date date1 = (Date) intent.getSerializableExtra(payment.KEY_IN_DATE);
//        Date date2 = (Date) intent.getSerializableExtra(payment.KEY_OUT_DATE);
//        int number1 = (Integer) intent.getSerializableExtra(payment.KEY_NUMBER);
        int IdCompany = (Integer) intent.getSerializableExtra(payment.KEY_ID_COMPANY);
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", new Locale("uk"));
        String formattedDate = sdf.format(booking.getCheckInDate());
        data1.setText(formattedDate);
        formattedDate = sdf.format(booking.getCheckOutDate());
        data2.setText(formattedDate);
        adults.setText(String.valueOf(booking.getNumGuests()));
        children.setText(String.valueOf(booking.getChildren()));
        room.setText(String.valueOf(booking.getNumRooms()));


        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<Room> rooms = databaseHelper.getAvailableRoomsByIdCompany(IdCompany, booking.getCheckInDate(), booking.getCheckOutDate());

        RoomsAdapter adapter = new RoomsAdapter(this, R.layout.hotel_card, rooms, booking);
        listView_options.setAdapter(adapter);

    }
}