package com.example.geome.Models;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.geome.MakeReviewActivity;
import com.example.geome.R;
import com.example.geome.Reservation;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ServiceHistoryAdapter extends ArrayAdapter<Booking> {
    private int resource;
    private Context context;
    private List<Booking> bookings;
    private LayoutInflater inflater;
    public static final String KEY_BOOKING = "androidx.appcompat.app.AppCompatActivity.ServiceHistoryAdapter.booking";
    public static final String KEY_ID_COMPANY = "androidx.appcompat.app.AppCompatActivity.ServiceHistoryAdapter.idCompany";

    public ServiceHistoryAdapter(@NonNull Context context, int resource, @NonNull List<Booking> bookings) {
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

        ImageView background = view.findViewById(R.id.background);
        ImageView photo_card = view.findViewById(R.id.icon_news);
        TextView company_name = view.findViewById(R.id.company_name_news);
        TextView time_publication = view.findViewById(R.id.time_publication_news);
        TextView makeComment = view.findViewById(R.id.makeComment);
        RatingBar companyRating = view.findViewById(R.id.company_rating);
        TextView comment = view.findViewById(R.id.comment);

        Booking booking = bookings.get(position);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        Log.d("hello ", "idbooking = " + booking.getId());
        int idCompany = databaseHelper.getCompanyByIdBooking(booking.getId());
        Log.d("hello ", "idcompany = " + idCompany);
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
        time_publication.setText(formattedDate);

        boolean has_review = hasReview(booking.getUserId());
//        if(has_review == true){
//            makeComment.setVisibility(View.GONE);
//            companyRating.setVisibility(View.VISIBLE);
//            comment.setVisibility(View.VISIBLE);
//
//            Reviews review = getReviewForBooking(booking.getId(), idCompany);
//
//            double weightLocation = 0.25;
//            double weightService = 0.25;
//            double weightAvailability = 0.25;
//            double weightComfort = 0.25;
//
//            // Обчислення загального рейтингу
//            double totalRating = (review.getLocation() * weightLocation) +
//                    (review.getService()* weightService) +
//                    (review.getAvailability() * weightAvailability) +
//                    (review.getComfort() * weightComfort);
//
//            try {
//                //double companyRatingValue = Double.parseDouble(company.getCompanyRating());
//                companyRating.setRating((float) totalRating);
//            } catch (NumberFormatException e) {
//                // Обробка помилки, якщо не вдається перетворити рядок у число
//                companyRating.setRating(0.0f); // Можливо, ви хочете задати значення за замовчуванням
//            }
//        }
//        else{
//            companyRating.setVisibility(View.GONE);
//            comment.setVisibility(View.GONE);
//            makeComment.setVisibility(View.VISIBLE);
//        }

        companyRating.setVisibility(View.GONE);
        comment.setVisibility(View.GONE);
        makeComment.setVisibility(View.GONE);
        background.setVisibility(View.GONE);
        makeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Room room = getItem(position);
//                booking.setRoomId(idRoom); // Використовуйте ідентифікатор кімнати з об'єкта кімнати
//                Log.d("hello ", "idrroom = " + idRoom);
//                booking.setTotalPrice(room.getPrice());
////                booking.setRoomId(idRoom);
////                Log.d("hello ", "idrroom = " + idRoom);
//
//                Intent intent = new Intent(context, Reservation.class);
//                intent.putExtra(KEY_BOOKING, booking);
//                intent.putExtra(KEY_ID_COMPANY, room.getIdHotel());
//                context.startActivity(intent);

                Intent intent = new Intent(context, MakeReviewActivity.class);
                intent.putExtra(KEY_BOOKING, booking);
                intent.putExtra(KEY_ID_COMPANY, idCompany);
                context.startActivity(intent);
            }
        });

        return view;
    }

    public Reviews getReviewForBooking(int bookingId, int companyId) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Reviews reviews = null;

        String query = "SELECT " + DatabaseHelper.COLUMN_COMPANY_REVIEWS_USER_COMMENT +
                " FROM " + DatabaseHelper.TABLE_COMPANY_REVIEWS +
                " WHERE " + DatabaseHelper.COLUMN_COMPANY_REVIEWS_ID_BOOKING + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(bookingId)});

        if (cursor != null && cursor.moveToFirst()) {
            double locationRating = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPANY_REVIEWS_LOCATION)));
            double serviceRating = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPANY_REVIEWS_SERVICE)));
            double availabilityRating = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPANY_REVIEWS_AVAILABILITY)));
            double comfortRating = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPANY_REVIEWS_COMFORT)));
            String comment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPANY_REVIEWS_USER_COMMENT));
            int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPANY_REVIEWS_ID_USER));
            reviews = new Reviews(companyId, locationRating, serviceRating, availabilityRating,
                    comfortRating, comment, userId, bookingId);
        }

        cursor.close();
        db.close();

        return reviews;
    }


    public boolean hasReview(int user_id){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "SELECT EXISTS(" +
                "SELECT 1 FROM " + DatabaseHelper.TABLE_COMPANY_REVIEWS + " cr " +
                "JOIN " + DatabaseHelper.TABLE_BOOKINGS + " b ON cr."
                + DatabaseHelper.COLUMN_COMPANY_REVIEWS_ID_BOOKING + " = b." + DatabaseHelper.COLUMN_BOOKING_ID +
                " WHERE b." + DatabaseHelper.COLUMN_BOOKING_USER_ID + " = ?)";

        boolean hasReview = false;

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(user_id)});
        if (cursor != null && cursor.moveToFirst()) {
            hasReview = cursor.getInt(0) == 1; // Якщо значення 1, то true, інакше false
            cursor.close();
        }
        return hasReview;
    }
}
