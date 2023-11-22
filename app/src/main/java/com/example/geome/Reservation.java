package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.geome.Models.AppData;
import com.example.geome.Models.Company;
import com.example.geome.Models.CompanyListAdapter;
import com.example.geome.Models.DatabaseHelper;

import java.util.List;

public class Reservation extends AppCompatActivity {

    private TextView buttonPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        buttonPay = findViewById(R.id.buttonPay);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReservationDialog();
            }
        });
    }



    public void showReservationDialog() {
        ReservationDialogFragment dialogFragment = new ReservationDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "ReservationDialog");
    }
}