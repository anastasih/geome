package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.Models.AppData;
import com.example.geome.Models.Booking;
import com.example.geome.Models.Company;
import com.example.geome.Models.CompanyListAdapter;
import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.RoomsAdapter;
import com.example.geome.Models.User;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class Reservation extends AppCompatActivity {

    private TextView buttonPay;
    public Booking booking;
    private TextView data1;
    private TextView data2;
    private TextView adults;
    private TextView children;
    private TextView room;
    private TextView value;
    private TextView TotalValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent intent = getIntent();
        booking = (Booking) intent.getSerializableExtra(RoomsAdapter.KEY_BOOKING);

        //Toast.makeText(this, "name = " + booking.getCheckOutDate(), Toast.LENGTH_SHORT).show();

        buttonPay = findViewById(R.id.buttonPay);
        data1 = findViewById(R.id.data1);
        data2 = findViewById(R.id.data2);
        adults = findViewById(R.id.adults);
        children = findViewById(R.id.children);
        room = findViewById(R.id.room);
        value = findViewById(R.id.value);
        TotalValue = findViewById(R.id.TotalValue);

        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", new Locale("uk"));
        String formattedDate = sdf.format(booking.getCheckInDate());
        data1.setText(formattedDate);
        formattedDate = sdf.format(booking.getCheckOutDate());
        data2.setText(formattedDate);
        adults.setText(String.valueOf(booking.getNumGuests()));
        children.setText(String.valueOf(booking.getChildren()));
        room.setText(String.valueOf(booking.getNumRooms()));
        value.setText(String.valueOf(booking.getTotalPrice()));
        TotalValue.setText(String.valueOf(booking.getTotalPrice()));
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(Reservation.this);
                long result = databaseHelper.addBooking(String.valueOf(booking.getRoomId()) ,
                        booking.getGuestName(),
                        String.valueOf(booking.getCheckInDate()),
                        String.valueOf(booking.getCheckOutDate()),
                        booking.getNumGuests(),
                        booking.getChildren(),
                        booking.getChildAge(),
                        booking.getNumRooms(),
                        booking.getTotalPrice());
                if (result == -1) {
                    Toast.makeText(Reservation.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Reservation.this, "Add", Toast.LENGTH_SHORT).show();
                }
                showReservationDialog();
            }
        });
    }



    public void showReservationDialog() {
        ReservationDialogFragment dialogFragment = new ReservationDialogFragment(booking);
        dialogFragment.show(getSupportFragmentManager(), "ReservationDialog");
    }
}