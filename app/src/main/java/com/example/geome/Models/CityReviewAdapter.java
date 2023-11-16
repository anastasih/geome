package com.example.geome.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.geome.R;

import java.io.File;
import java.util.List;

public class CityReviewAdapter extends ArrayAdapter<CityReviews> {
    private int resource;
    private Context context;
    private List<CityReviews> all_reviews;
    private LayoutInflater inflater;

    public CityReviewAdapter(@NonNull Context context, int resource, @NonNull List<CityReviews> reviews) {
        super(context, resource, reviews);
        this.context = context;
        this.resource = resource;
        this.all_reviews = reviews;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = inflater.inflate(resource, parent, false);

        ImageView user_icon = item.findViewById(R.id.user_icon);
        TextView user_name = item.findViewById(R.id.user_name);
        RatingBar review_city = item.findViewById(R.id.review_city);
        TextView time_publication_review = item.findViewById(R.id.time_publication_review);
        TextView review_description = item.findViewById(R.id.review_description);
        ImageView reviewPhoto = item.findViewById(R.id.reviewPhoto);

        CityReviews reviews = all_reviews.get(position);

        String userPhotoName = getUserPhotoName(reviews.getIdUser());

        if (userPhotoName != null && userPhotoName.length() != 0) {
            //  шлях до збереженого зображення в кеші
            File imageFile = new File(getContext().getCacheDir(), userPhotoName);

            // Перевірка, чи файл існує
            if (imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

                int desiredWidth = 126;
                int desiredHeight = 126;

                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, true);
                Bitmap roundedBitmap = getRoundedBitmap(resizedBitmap);
                user_icon.setImageBitmap(roundedBitmap);
            }
        }
        else {
            userPhotoName = "background_user_icon";
            Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(userPhotoName, "drawable", getContext().getPackageName()));
            user_icon.setImageDrawable(drawable);
        }

        String imageName = reviews.getPhoto();
        if (imageName.length() != 0) {
            Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(imageName, "drawable", context.getPackageName()));
            reviewPhoto.setImageDrawable(drawable);
            reviewPhoto.setVisibility(View.VISIBLE);
        } else {
            reviewPhoto.setVisibility(View.GONE);
        }

        review_description.setText(reviews.getComment());
        time_publication_review.setText(String.valueOf(reviews.getDatePublication()));
        user_name.setText(getUserName(reviews.getIdUser()));

        try {
            double companyRatingValue = Double.parseDouble(reviews.getRating());
            review_city.setRating((float) companyRatingValue);
        } catch (NumberFormatException e) {
            // Обробка помилки, якщо не вдається перетворити рядок у число
            review_city.setRating(0.0f); // Можливо, ви хочете задати значення за замовчуванням
        }

        return item;
    }

    private String getUserPhotoName(int userId) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        String userPhotoName = dbHelper.getUserIconById(userId);

        return userPhotoName;
    }
    private String getUserName(int userId) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        String userName = dbHelper.getUserNameById(userId);

        return userName;
    }
    private Bitmap getRoundedBitmap(Bitmap srcBitmap) {
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        int size = Math.min(width, height);

        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, size, size);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);

        canvas.drawRoundRect(rectF, size / 2, size / 2, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(srcBitmap, rect, rect, paint);

        return output;
    }
}
