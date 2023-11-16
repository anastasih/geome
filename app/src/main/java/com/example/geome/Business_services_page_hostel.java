package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.Models.Company;
import com.example.geome.Models.Reviews;
import com.example.geome.Models.CompanyDetails;
import com.example.geome.Models.DatabaseHelper;
import com.google.gson.Gson;

public class Business_services_page_hostel extends AppCompatActivity {

    public TextView name_hostel;
    public TextView street;
    public TextView rating;
    public RatingBar company_rating;
    ImageView imageLogo;
    ImageView buttonBack_hostel;
    ImageButton ImageButtonProfile;
    ImageButton ImageButtonMain;
    ImageButton ImageButtonRibbon;
    ImageButton ImageButtonCity;
    ImageButton ImageButtonChat;
    ImageButton ImageButtonMain_word;
    ImageButton ImageButtonResidence_word;
    ImageButton ImageButtonReviews;
    ImageButton ImageButtonInfo;
    ImageView image_hostel;

    public FrameLayout fragmentContainer;
    public  int newWidth = 60;
    public int newHeight = 60;
    public int IdCompany;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_services_page_hostel);

        // Отримання JSON рядка з інтента
        String jobCompanyJson = getIntent().getStringExtra("selectedCompanyJson");

        // Перетворення JSON рядка назад в об'єкт JobOffer
        Company selectedCompany = new Gson().fromJson(jobCompanyJson, Company.class);

        name_hostel = findViewById(R.id.name_hostel);
        name_hostel.setText(selectedCompany.getCompanyName());

        imageLogo = findViewById(R.id.imageLogo);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile);
        buttonBack_hostel = findViewById(R.id.buttonBack_delivery);

        ImageButtonMain = findViewById(R.id.ImageButtonMain);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon);
        ImageButtonCity = findViewById(R.id.ImageButtonCity);
        ImageButtonChat = findViewById(R.id.ImageButtonChat);

        ImageButtonMain_word = findViewById(R.id.ImageButtonMain_word);
        ImageButtonResidence_word = findViewById(R.id.ImageButtonResidence_word);
        ImageButtonReviews = findViewById(R.id.ImageButtonReviews);
        ImageButtonInfo = findViewById(R.id.ImageButtonInfo);

        fragmentContainer = findViewById(R.id.fragmentContainer);

        street = findViewById(R.id.street);
        rating = findViewById(R.id.rating);
        company_rating = findViewById(R.id.company_rating);
        image_hostel = findViewById(R.id.image_hostel);



        IdCompany = selectedCompany.getCompanyId();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        CompanyDetails companyDetails = databaseHelper.getCompanyDetailsById(IdCompany);
        street.setText(companyDetails.getCompanyAddress());
        String photo = companyDetails.getCompanyPhoto();
        if (photo != null) {
            Drawable drawable = ContextCompat.getDrawable(this, getResources().getIdentifier(companyDetails.getCompanyPhoto(), "drawable", getPackageName()));
            if (drawable != null) {
                image_hostel.setImageDrawable(drawable);
            }
        }
        Reviews reviews = databaseHelper.getRatingCompanyByCompanyId(IdCompany);
        double weightLocation = 0.25;
        double weightService = 0.25;
        double weightAvailability = 0.25;
        double weightComfort = 0.25;

        // Обчислення загального рейтингу
        double totalRating = (reviews.getLocation() * weightLocation) + ( reviews.getService()* weightService) + (reviews.getAvailability() * weightAvailability) + (reviews.getComfort() * weightComfort);
        try {
            //double companyRatingValue = Double.parseDouble(totalRating);
            company_rating.setRating((float) totalRating);
        } catch (NumberFormatException e) {
            // Обробка помилки, якщо не вдається перетворити рядок у число
            company_rating.setRating(0.0f); // Можливо, ви хочете задати значення за замовчуванням
        }
        rating.setText(String.valueOf(totalRating));
        replaceFragment(new Feeds(IdCompany));

        initView();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void initView() {

        ImageButtonMain_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Drawable drawable = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("main_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonMain_word.setImageDrawable(drawable);

                Drawable drawable2 = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("not_residence_word", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ImageButtonResidence_word.setImageDrawable(drawable2);

                drawable = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("not_reviews_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonReviews.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("not_info_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonInfo.setImageDrawable(drawable);
                replaceFragment(new Feeds(IdCompany));
            }
        });

        ImageButtonResidence_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("not_main_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonMain_word.setImageDrawable(drawable);

                Drawable drawable2 = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("residence_word", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ImageButtonResidence_word.setImageDrawable(drawable2);

                drawable = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("not_reviews_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonReviews.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("not_info_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonInfo.setImageDrawable(drawable);
                replaceFragment(new Feeds(IdCompany));
            }
        });

        ImageButtonReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("not_main_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonMain_word.setImageDrawable(drawable);

                Drawable drawable2 = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("not_residence_word", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ImageButtonResidence_word.setImageDrawable(drawable2);

                drawable = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("reviews_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonReviews.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("not_info_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonInfo.setImageDrawable(drawable);
                replaceFragment(new com.example.geome.Reviews(IdCompany));
            }
        });

        ImageButtonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("not_main_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonMain_word.setImageDrawable(drawable);

                Drawable drawable2 = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("not_residence_word", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ImageButtonResidence_word.setImageDrawable(drawable2);

                drawable = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("not_reviews_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonReviews.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(Business_services_page_hostel.this,
                        getResources().getIdentifier("info_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonInfo.setImageDrawable(drawable);
                replaceFragment(new Info(IdCompany));
            }
        });

        buttonBack_hostel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Business_services_page_hostel.this, HostelActivity.class);
                startActivity(intent);
            }
        });

        ImageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Business_services_page_hostel.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", ProfileFragment.class.getName());
                startActivity(intent);
            }
        });

        ImageButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Business_services_page_hostel.this, HomePage.class);
                startActivity(intent);
            }
        });

        ImageButtonRibbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Business_services_page_hostel.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", NewsFeedFragment.class.getName());
                startActivity(intent);
            }
        });

        ImageButtonCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Business_services_page_hostel.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", CityFragment.class.getName());
                startActivity(intent);
            }
        });

        ImageButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Business_services_page_hostel.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", ChatFragment.class.getName());
                startActivity(intent);
            }
        });
    }

}