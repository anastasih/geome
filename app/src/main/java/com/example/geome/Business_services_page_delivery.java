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

import com.example.geome.Models.Company;
import com.example.geome.Models.CompanyDetails;
import com.example.geome.Models.DatabaseHelper;
import com.google.gson.Gson;

public class Business_services_page_delivery extends AppCompatActivity {
    public TextView name_delivery;

    public TextView rating;
    public RatingBar company_rating;
    ImageView imageLogo;
    ImageView buttonBack_delivery;
    ImageButton ImageButtonProfile;
    ImageButton ImageButtonMain;
    ImageButton ImageButtonRibbon;
    ImageButton ImageButtonCity;
    ImageButton ImageButtonChat;
    ImageButton ImageButtonMain_word;
    ImageButton ImageButtonDelivery_word;
    ImageButton ImageButtonReviews;
    ImageButton ImageButtonInfo;
    ImageView image_delivery;

    public FrameLayout fragmentContainer;
    public  int newWidth = 60;
    public int newHeight = 60;
    public int IdCompany;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_services_page_delivery);

        String jobCompanyJson = getIntent().getStringExtra("selectedCompanyJson");

        // Перетворення JSON рядка назад в об'єкт JobOffer
        Company selectedCompany = new Gson().fromJson(jobCompanyJson, Company.class);

        name_delivery = findViewById(R.id.name_delivery);
        name_delivery.setText(selectedCompany.getCompanyName());

        imageLogo = findViewById(R.id.imageLogo);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile);
        buttonBack_delivery = findViewById(R.id.buttonBack_delivery);

        ImageButtonMain = findViewById(R.id.ImageButtonMain);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon);
        ImageButtonCity = findViewById(R.id.ImageButtonCity);
        ImageButtonChat = findViewById(R.id.ImageButtonChat);

        ImageButtonMain_word = findViewById(R.id.ImageButtonMain_word);
        ImageButtonDelivery_word = findViewById(R.id.ImageButtonDelivery_word);
        ImageButtonReviews = findViewById(R.id.ImageButtonReviews);
        ImageButtonInfo = findViewById(R.id.ImageButtonInfo);

        fragmentContainer = findViewById(R.id.fragmentContainer);


        rating = findViewById(R.id.rating);
        company_rating = findViewById(R.id.company_rating);
        image_delivery = findViewById(R.id.image_delivery);

        IdCompany = selectedCompany.getCompanyId();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        CompanyDetails companyDetails = databaseHelper.getCompanyDetailsById(IdCompany);
        String photo = companyDetails.getCompanyPhoto();
        if (photo != null) {
            Drawable drawable = ContextCompat.getDrawable(this, getResources().getIdentifier(companyDetails.getCompanyPhoto(), "drawable", getPackageName()));
            if (drawable != null) {
                image_delivery.setImageDrawable(drawable);
            }
        }
        com.example.geome.Models.Reviews reviews = databaseHelper.getRatingCompanyByCompanyId(IdCompany);
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
                Drawable drawable = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("main_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonMain_word.setImageDrawable(drawable);

                Drawable drawable2 = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("not_delivery", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ImageButtonDelivery_word.setImageDrawable(drawable2);

                drawable = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("not_reviews_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonReviews.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("not_info_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonInfo.setImageDrawable(drawable);
                replaceFragment(new Feeds(IdCompany));
            }
        });

        ImageButtonDelivery_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("not_main_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonMain_word.setImageDrawable(drawable);

                Drawable drawable2 = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("delivery", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ImageButtonDelivery_word.setImageDrawable(drawable2);

                drawable = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("not_reviews_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonReviews.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("not_info_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonInfo.setImageDrawable(drawable);
                replaceFragment(new Feeds(IdCompany));
            }
        });

        ImageButtonReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("not_main_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonMain_word.setImageDrawable(drawable);

                Drawable drawable2 = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("not_delivery", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ImageButtonDelivery_word.setImageDrawable(drawable2);

                drawable = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("reviews_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonReviews.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("not_info_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonInfo.setImageDrawable(drawable);
                replaceFragment(new Reviews(IdCompany));
            }
        });

        ImageButtonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("not_main_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonMain_word.setImageDrawable(drawable);

                Drawable drawable2 = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("not_delivery", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ImageButtonDelivery_word.setImageDrawable(drawable2);

                drawable = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("not_reviews_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonReviews.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(Business_services_page_delivery.this,
                        getResources().getIdentifier("info_word", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonInfo.setImageDrawable(drawable);
                replaceFragment(new Info(IdCompany));
            }
        });

        buttonBack_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Business_services_page_delivery.this, DeliveryActivity.class);
                startActivity(intent);
            }
        });

        ImageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Business_services_page_delivery.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", ProfileFragment.class.getName());
                startActivity(intent);
            }
        });

        ImageButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Business_services_page_delivery.this, HomePage.class);
                startActivity(intent);
            }
        });

        ImageButtonRibbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Business_services_page_delivery.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", NewsFeedFragment.class.getName());
                startActivity(intent);
            }
        });

        ImageButtonCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Business_services_page_delivery.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", CityFragment.class.getName());
                startActivity(intent);
            }
        });

        ImageButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Business_services_page_delivery.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", ChatFragment.class.getName());
                startActivity(intent);
            }
        });
    }
}