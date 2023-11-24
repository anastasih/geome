package com.example.geome.Models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.geome.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BookingAdapter extends ArrayAdapter<Booking> {
    private int resource;
    private Context context;
    private List<Booking> bookings;
    private LayoutInflater inflater;

    public BookingAdapter(@NonNull Context context, int resource, @NonNull List<Booking> bookings) {
        super(context, resource, bookings);
        this.context = context;
        this.resource = resource;
        this.bookings = bookings;
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
        TextView time_booking = view.findViewById(R.id.time_booking);

        Booking booking = bookings.get(position);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        int idCompany = databaseHelper.getCompanyByIdBooking(booking.getId());
        Company company = databaseHelper.getCompanyById(idCompany);

        String photo = company.getCompanyPhoto();

        if (photo != null) {
            Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(photo, "drawable", context.getPackageName()));
            if (drawable != null) {
                photo_card.setImageDrawable(drawable);
            }
        }

        company_name.setText(company.getCompanyName());
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", new Locale("uk"));
        String formattedDate = sdf.format(booking.getCheckInDate());
        time_booking.setText(formattedDate);

        return view;
    }
}
