package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    ImageView buttonBack_options;
    ImageButton ImageButtonMap;
    ImageButton ImageButtonProfile;
    ImageButton ImageButtonMain;
    ImageButton ImageButtonRibbon;
    ImageButton ImageButtonCity;
    ImageButton ImageButtonChat;
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

        buttonBack_options = findViewById(R.id.buttonBack_options);

        ImageButtonMap = findViewById(R.id.ImageButtonMap);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile);

        ImageButtonMain = findViewById(R.id.ImageButtonMain);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon);
        ImageButtonCity = findViewById(R.id.ImageButtonCity);
        ImageButtonChat = findViewById(R.id.ImageButtonChat);

        buttonBack_options.setOnClickListener(this::buttonBackClick);
        ImageButtonMap.setOnClickListener(this::ImageButtonMapClick);
        ImageButtonProfile.setOnClickListener(this::ImageButtonProfileClick);

        ImageButtonMain.setOnClickListener(this::ImageButtonMainClick);
        ImageButtonRibbon.setOnClickListener(this::ImageButtonRibbonClick);
        ImageButtonCity.setOnClickListener(this::ImageButtonCityClick);
        ImageButtonChat.setOnClickListener(this::ImageButtonChatClick);

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
        adults.setText(String.valueOf(booking.getNumGuests()) + " дорослих");
        children.setText(String.valueOf(booking.getChildren())+ " дітей");
        room.setText(String.valueOf(booking.getNumRooms()) + " номер");

//        String date1 = String.valueOf(booking.getCheckInDate());
//        String date2 = String.valueOf(booking.getCheckOutDate());


        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<Room> rooms = databaseHelper.getAvailableRoomsByIdCompany(IdCompany,
                booking.getCheckInDate(), booking.getCheckOutDate(), booking.getNumGuests());
        //List<Room> rooms = databaseHelper.getAvailableRoomsByIdCompany(IdCompany, date1, date2);

//        Log.d("hello ", "date 1 = " + date1);

        RoomsAdapter adapter = new RoomsAdapter(this, R.layout.hotel_card, rooms, booking);
        listView_options.setAdapter(adapter);

    }

    private void buttonBackClick(View view) {
        Intent intent = new Intent(Accommodation_options.this, HostelActivity.class);
        startActivity(intent);
    } private void ImageButtonMapClick(View view) {
        Intent intent = new Intent(Accommodation_options.this, HomePage.class);
        startActivity(intent);
    }
    private void ImageButtonProfileClick(View view) {
        Intent intent = new Intent(Accommodation_options.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ProfileFragment.class.getName());
        startActivity(intent);
    }

    private void ImageButtonChatClick(View view) {
        Intent intent = new Intent(Accommodation_options.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ChatFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonCityClick(View view) {
        Intent intent = new Intent(Accommodation_options.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", CityFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonRibbonClick(View view) {
        Intent intent = new Intent(Accommodation_options.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", NewsFeedFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonMainClick(View view) {
        Intent intent = new Intent(Accommodation_options.this, HomePage.class);
        startActivity(intent);
    }

}