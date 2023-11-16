package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.JobOffer;
import com.google.gson.Gson;

public class JobOfferDetailsActivity extends AppCompatActivity {

    public TextView titleOffer, position, employmentTime, salary, description, requirements, acceptJobOffer;
    public ImageView icon_company, buttonBack;

    public ImageButton ImageButtonMain, ImageButtonRibbon, ImageButtonCity, ImageButtonChat, ImageButtonProfile;
    public  JobOffer selectedOffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_offer_details);

        initView();
        initDate();
    }
    private void initDate(){
        // Отримання JSON рядка з інтента
        String jobOfferJson = getIntent().getStringExtra("selectedOfferJson");

        // Перетворення JSON рядка назад в об'єкт JobOffer
        selectedOffer = new Gson().fromJson(jobOfferJson, JobOffer.class);

        titleOffer.setText(titleOffer.getText() + "“" + selectedOffer.getOffer_name() + "”" );
        position.setText(selectedOffer.getCompany_name());
        salary.setText(selectedOffer.getSalary() + "₴");
        description.setText(selectedOffer.getOffer_description());
        requirements.setText(selectedOffer.getRequirements());
        int companyId = selectedOffer.getId_company();
        String imageName = getCompanyLogoName(companyId);
        if (imageName.length() != 0) {
            Drawable drawable = ContextCompat.getDrawable(JobOfferDetailsActivity.this, JobOfferDetailsActivity.this.getResources().getIdentifier(imageName, "drawable", JobOfferDetailsActivity.this.getPackageName()));
            icon_company.setImageDrawable(drawable);
            icon_company.setVisibility(View.VISIBLE);
        } else {
            icon_company.setVisibility(View.GONE);
        }
    }
    private void initView(){
        titleOffer = findViewById(R.id.titleOffer);
        position = findViewById(R.id.position);
        employmentTime = findViewById(R.id.employmentTime);
        salary = findViewById(R.id.salary);
        description = findViewById(R.id.description);
        requirements = findViewById(R.id.requirements);
        acceptJobOffer = findViewById(R.id.acceptJobOffer);
        icon_company = findViewById(R.id.icon_company);
        buttonBack = findViewById(R.id.buttonBack);
        ImageButtonMain = findViewById(R.id.ImageButtonMain);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon);
        ImageButtonCity = findViewById(R.id.ImageButtonCity);
        ImageButtonChat = findViewById(R.id.ImageButtonChat);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile);

        buttonBack.setOnClickListener(this::returnBack);
        ImageButtonMain.setOnClickListener(this::ImageButtonMainClick);
        ImageButtonRibbon.setOnClickListener(this::ImageButtonRibbonClick);
        ImageButtonCity.setOnClickListener(this::ImageButtonCityClick);
        ImageButtonChat.setOnClickListener(this::ImageButtonChatClick);
        ImageButtonProfile.setOnClickListener(this::ImageButtonProfileClick);
        acceptJobOffer.setOnClickListener(this::acceptJobOfferClick);
    }
    private void acceptJobOfferClick(View view) {
        Intent intent = new Intent(JobOfferDetailsActivity.this, ApplyVacancyActivity.class);
        intent.putExtra("selectedOfferJson", selectedOffer.getOffer_name());
        intent.putExtra("selectedOfferIdJson", selectedOffer.getId());
        startActivity(intent);
    }
    private void ImageButtonProfileClick(View view) {
        Intent intent = new Intent(JobOfferDetailsActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ProfileFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonChatClick(View view) {
        Intent intent = new Intent(JobOfferDetailsActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ChatFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonCityClick(View view) {
        Intent intent = new Intent(JobOfferDetailsActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", CityFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonRibbonClick(View view) {
        Intent intent = new Intent(JobOfferDetailsActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", NewsFeedFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonMainClick(View view) {
        Intent intent = new Intent(JobOfferDetailsActivity.this, HomePage.class);
        startActivity(intent);
    }
    private void returnBack(View view){
        onBackPressed();
    }

    private String getCompanyLogoName(int companyId) {
        DatabaseHelper dbHelper = new DatabaseHelper(JobOfferDetailsActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String companyName = dbHelper.getCompanyLogoNameById(companyId);

        return companyName;
    }
}