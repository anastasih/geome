package com.example.geome.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.geome.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PaymentHistoryAdapter extends ArrayAdapter<Payment> {
    private int resource;
    private Context context;
    private List<Payment> payments;
    private LayoutInflater inflater;

    public PaymentHistoryAdapter(@NonNull Context context, int resource, @NonNull List<Payment> payments) {
        super(context, resource, payments);
        this.context = context;
        this.resource = resource;
        this.payments = payments;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(resource, parent, false);
        }

        ImageView photo_card = view.findViewById(R.id.icon_news);
        TextView company_name = view.findViewById(R.id.company_name_news);
        TextView time_payment = view.findViewById(R.id.time_payment);
        TextView amount = view.findViewById(R.id.amount);

        Payment payment = payments.get(position);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        Company company = databaseHelper.getCompanyById(payment.getIdCompany());
        Log.d("hello", "payment Company Adapter = " + payment.getIdCompany());
        String photo = company.getCompanyPhoto();

        if (photo != null) {
            Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(photo, "drawable", context.getPackageName()));
            if (drawable != null) {
                photo_card.setImageDrawable(drawable);
            }
        }

        company_name.setText(company.getCompanyName());
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", new Locale("uk"));
        String formattedDate = sdf.format(payment.getDate());
        time_payment.setText(formattedDate);
        amount.setText(String.valueOf(payment.getAmount()) + amount.getText());

        return view;
    }
}
