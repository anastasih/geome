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

public class ReservationDialogFragment extends DialogFragment {
    private ImageView closeButton;
    private TextView buttonServiceHistory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reservation_dialog, container, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view);
        AlertDialog dialog = builder.create();

        closeButton = view.findViewById(R.id.closeButton);
        buttonServiceHistory = view.findViewById(R.id.buttonService_history);

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
