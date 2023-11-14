package com.example.geome;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.Models.AppData;
import com.example.geome.Models.Company;
import com.example.geome.Models.CompanyDetails;
import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.User;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_city, container, false);

        spinnerCity = rootView.findViewById(R.id.spinnerCity);
        imageCity = rootView.findViewById(R.id.imageCity);
        cityName = rootView.findViewById(R.id.cityName);
        cityDescription = rootView.findViewById(R.id.cityDescription);

        dbHelper = new DatabaseHelper(getContext());
        cityData = dbHelper.getCityData();
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
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}