package com.example.geome;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.geome.Models.Booking;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ReservationDialogFragment extends DialogFragment {
    private ImageView closeButton;
    private TextView buttonServiceHistory;
    private TextView name_room;
    private TextView data1;
    private TextView data2;
    private TextView adults;
    private TextView children;
    private TextView room;
    private TextView value;
    private TextView TotalValue;
    public Booking booking;
    public ReservationDialogFragment(Booking booking){
        this.booking = booking;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reservation_dialog, container, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view);
        AlertDialog dialog = builder.create();

        closeButton = view.findViewById(R.id.closeButton);
        buttonServiceHistory = view.findViewById(R.id.buttonService_history);

        name_room = view.findViewById(R.id.name_room);
        data1 = view.findViewById(R.id.data1);
        data2 = view.findViewById(R.id.data2);
        adults = view.findViewById(R.id.adults);
        children = view.findViewById(R.id.children);
        room = view.findViewById(R.id.room);
        value = view.findViewById(R.id.value);
        TotalValue = view.findViewById(R.id.TotalValue);

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

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Закриття діалогового вікна
                getDialog().dismiss();
            }
        });


        buttonServiceHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Відкриття нової activity для історії сервісів
                Intent intent = new Intent(requireContext(), ServiceHistoryActivity.class);
                startActivity(intent);
                // Закриття діалогового вікна
                dialog.dismiss();
            }
        });

        return view;
    }
}
