package com.example.geome.Models;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.geome.R;
import com.example.geome.Reservation;


import java.util.List;

public class RoomsAdapter extends ArrayAdapter<Room> {

    private int resource;
    private Context context;
    private List<Room> rooms;
    public Booking booking;
    public static final String KEY_BOOKING = "androidx.appcompat.app.AppCompatActivity.RoomsAdapter.booking";
    public static final String KEY_ID_COMPANY = "androidx.appcompat.app.AppCompatActivity.RoomsAdapter.idCompany";
    private LayoutInflater inflater;

    public RoomsAdapter(@NonNull Context context, int resource, @NonNull List<Room> rooms, Booking booking) {
        super(context, resource, rooms);
        this.context = context;
        this.resource = resource;
        this.rooms = rooms;
        inflater = LayoutInflater.from(context);
        this.booking = booking;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(resource, parent, false);
        }

        TextView company_name = view.findViewById(R.id.company_name);
        ImageView icon_news = view.findViewById(R.id.icon_news);
        TextView time_publication = view.findViewById(R.id.time_publication);
        TextView Price = view.findViewById(R.id.Price);
        ImageButton reservation = view.findViewById(R.id.reservation);

        Room room = rooms.get(position);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        Company company = databaseHelper.getCompanyById(room.getIdHotel());
        String photo = company.getCompanyPhoto();

//        CompanyDetails companyDetails = databaseHelper.getCompanyDetailsById(company.getCompanyId());
//        Log.d("Тег", "companyDetails.getCompanyPhoto() = " + companyDetails.getCompanyPhoto());


        if (photo != null) {
            Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(photo, "drawable", context.getPackageName()));
            if (drawable != null) {
                Log.d("Тег", "drawable = " + drawable);
                icon_news.setImageDrawable(drawable);
            }
        }
        time_publication.setText(room.getDescription());
        company_name.setText(room.getNameRoom());
        Price.setText(String.valueOf(room.getPrice()));
        //Price.setText(String.valueOf(booking.getTotalPrice()));
        //double price = Double.parseDouble(String.valueOf(Price.getText()));
        //Log.d("Тег", "price " + price);
        //Log.d("Тег", "price booking " + booking.getTotalPrice());
        //booking.setTotalPrice(price);
        //booking.setRoomId(room.getId());

        //double price = Double.parseDouble(String.valueOf(Price.getText()));
        //room.se(price);
        int idRoom = position + 1;
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Room room = getItem(position);
                booking.setRoomId(idRoom); // Використовуйте ідентифікатор кімнати з об'єкта кімнати
                Log.d("hello ", "idrroom = " + idRoom);
                booking.setTotalPrice(room.getPrice());
//                booking.setRoomId(idRoom);
//                Log.d("hello ", "idrroom = " + idRoom);

                Intent intent = new Intent(context, Reservation.class);
                intent.putExtra(KEY_BOOKING, booking);
                intent.putExtra(KEY_ID_COMPANY, room.getIdHotel());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
