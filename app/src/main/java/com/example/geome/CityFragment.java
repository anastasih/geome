package com.example.geome;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.Models.AppData;
import com.example.geome.Models.CityReviewAdapter;
import com.example.geome.Models.CityReviews;
import com.example.geome.Models.Company;
import com.example.geome.Models.CompanyDetails;
import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.NewFeedAdapter;
import com.example.geome.Models.NewsCard;
import com.example.geome.Models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


public class CityFragment extends Fragment {
    public DatabaseHelper dbHelper;
    private User user;
    public ImageView imageCity;
    public TextView cityName, cityDescription;
    public Spinner spinnerCity;
    public Map<Integer, String> cityData ;
    public List<String> cityNames;
    public List<Integer> cityIds;
    public ListView lvUserReviews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_city, container, false);

        spinnerCity = rootView.findViewById(R.id.spinnerCity);
        imageCity = rootView.findViewById(R.id.imageCity);
        cityName = rootView.findViewById(R.id.cityName);
        cityDescription = rootView.findViewById(R.id.cityDescription);
        lvUserReviews = rootView.findViewById(R.id.lvUserReviews);

        dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db1 = dbHelper.getReadableDatabase();
        cityData = dbHelper.getCityData(db1);

        //cityData = dbHelper.getCityData();
        cityNames = new ArrayList<>(cityData.values());
        cityIds = new ArrayList<>(cityData.keySet());
        user = AppData.getInstance().getUser();
        int userCity = dbHelper.getCityForUser(user.getUserPhone());

        String selectedCityName = cityNames.get(userCity);
        cityName.setText(selectedCityName);

        String description = dbHelper.getCityDescriptionById(userCity);
        if(description != null){
            cityDescription.setText(description);
        }
        String cityPhoto = dbHelper.getCityPhotoById(userCity);

        if (cityPhoto != null) {
            int resId = getResources().getIdentifier(cityPhoto, "drawable", getContext().getPackageName());
            if (resId != 0) {
                Drawable imageDrawable = getResources().getDrawable(resId);

                Drawable[] layers = new Drawable[2];
                layers[0] = imageDrawable;

                GradientDrawable gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{Color.TRANSPARENT, Color.argb(180, 0, 0, 0)});

                layers[1] = gradientDrawable;

                LayerDrawable layerDrawable = new LayerDrawable(layers);
                imageCity.setImageDrawable(layerDrawable);
            }
        }
        setSpinner();

        //List<CityReviews> cardItemList = dbHelper.getCityReviewsByIdCity(userCity);
        List<CityReviews> cardItemList = getCardItemsFromDatabase(userCity);
        CityReviewAdapter adapter = new CityReviewAdapter(getActivity(), R.layout.review_city, cardItemList);
        lvUserReviews.setAdapter(adapter);

        return rootView;
    }

    private void setSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cityNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedCityId = cityIds.get(position);

                String selectedCityName = cityNames.get(position);
                cityName.setText(selectedCityName);

                String description = dbHelper.getCityDescriptionById(selectedCityId);

                if(description != null){
                    cityDescription.setText(description);
                }

                String cityPhoto = dbHelper.getCityPhotoById(selectedCityId);

                if (cityPhoto != null) {
                    int resId = getResources().getIdentifier(cityPhoto, "drawable", getContext().getPackageName());
                    if (resId != 0) {
                        Drawable imageDrawable = getResources().getDrawable(resId);

                        Drawable[] layers = new Drawable[2];
                        layers[0] = imageDrawable;

                        GradientDrawable gradientDrawable = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                new int[]{Color.TRANSPARENT, Color.argb(180, 0, 0, 0)});

                        layers[1] = gradientDrawable;

                        LayerDrawable layerDrawable = new LayerDrawable(layers);
                        imageCity.setImageDrawable(layerDrawable);
                    }
                }

                List<CityReviews> cardItemList = getCardItemsFromDatabase(selectedCityId);
                CityReviewAdapter adapter = new CityReviewAdapter(getActivity(), R.layout.review_city, cardItemList);
                lvUserReviews.setAdapter(adapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private List<CityReviews> getCardItemsFromDatabase(int id) {
        List<CityReviews> cardItemList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
            db = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_USER_REVIEWS_CITY +
                    " WHERE " + DatabaseHelper.COLUMN_USER_REVIEWS_CITY_ID_CITY + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

            if (cursor.moveToFirst()) {
                do {
                    int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_REVIEWS_CITY_ID_USER));
                    int cityId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_REVIEWS_CITY_ID_CITY));
                    String comment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_REVIEWS_CITY_COMMENT));
                    String rating = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_REVIEWS_CITY_RATING));
                    String photo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_REVIEWS_CITY_PHOTO));
                    String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_REVIEWS_CITY_DATE));
                    SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
                    try {
                        Date date = inputDateFormat.parse(time);
                        SimpleDateFormat outputDateFormat = new SimpleDateFormat("MM-dd", Locale.US);
                        String formattedDate = outputDateFormat.format(date);
                        //SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.US);
                        //Date date = dateFormat.parse(time);
                        CityReviews cityReviews = new CityReviews(userId, cityId, formattedDate, comment, rating, photo);
                        cardItemList.add(cityReviews);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return cardItemList;
    }
}