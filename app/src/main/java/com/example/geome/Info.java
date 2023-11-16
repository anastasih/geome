package com.example.geome;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.net.Uri;
import android.content.Intent;
import android.content.ActivityNotFoundException;

import com.example.geome.Models.Company;
import com.example.geome.Models.DatabaseHelper;

public class Info extends Fragment {
    public TextView phone;
    public TextView mail;

    ImageButton inst;
    ImageButton faceb;
    ImageButton youtube;
    public int Id;
    public Info(int Id){
        this.Id = Id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        phone = rootView.findViewById(R.id.phone);
        mail = rootView.findViewById(R.id.mail);

        inst = rootView.findViewById(R.id.inst);
        faceb = rootView.findViewById(R.id.faceb);
        youtube = rootView.findViewById(R.id.youtube);

        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "https://www.instagram.com/?hl=ru";
                if (link != null && !link.isEmpty()) {
                    Uri webpage = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    intent.setPackage("com.android.chrome");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        faceb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "https://www.facebook.com/?locale=uk_UA";
                if (link != null && !link.isEmpty()) {
                    Uri webpage = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    intent.setPackage("com.android.chrome");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "https://www.youtube.com/";
                if (link != null && !link.isEmpty()) {
                    Uri webpage = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    intent.setPackage("com.android.chrome");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

        Company company = databaseHelper.getCompanyById(Id);
        phone.setText(company.getPhone());
        mail.setText(company.getEmail());
        return rootView;
    }
}