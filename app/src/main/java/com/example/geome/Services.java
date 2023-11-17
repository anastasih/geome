package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.Company;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.example.geome.Models.NewFeedAdapter;
import com.example.geome.Models.NewsCard;
import com.example.geome.Models.User;
import com.example.geome.Models.AppData;
import com.example.geome.Models.CompanyListAdapter;
import com.example.geome.Models.CompanyDetails;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import java.util.List;

public class Services extends AppCompatActivity {
    DatabaseHelper dbHelper;
    User user;
    public ListView listView_services;
    public EditText search_news;
    ImageButton ImageButtonProfile;
    ImageView buttonBack;
    ImageButton ButtonAll;
    ImageButton ButtonBarbershop;
    ImageButton ButtonBeautysalon;
    ImageButton ImageButtonMain;
    ImageButton ImageButtonRibbon;
    ImageButton ImageButtonCity;
    ImageButton ImageButtonChat;
    public  int newWidth = 60;
    public int newHeight = 60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile_services);

        listView_services = findViewById(R.id.listView_services);
        listView_services.setClickable(true);
        listView_services.setFocusable(true);

        buttonBack = findViewById(R.id.buttonBack_services);
        search_news = findViewById(R.id.search_news);

        ButtonAll = findViewById(R.id.ButtonAll);
        ButtonBarbershop = findViewById(R.id.ButtonBarbershop);
        ButtonBeautysalon = findViewById(R.id.ButtonBeautysalon);

        ImageButtonMain = findViewById(R.id.ImageButtonMain_services);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon_services);
        ImageButtonCity = findViewById(R.id.ImageButtonCity_services);
        ImageButtonChat = findViewById(R.id.ImageButtonChat_services);

        dbHelper = new DatabaseHelper(Services.this);
        user = AppData.getInstance().getUser();
        int idCity = dbHelper.getCityForUser(user.getUserPhone());
        List<Company> companies = dbHelper.getCompaniesByCityId(idCity);
        List<Company> sortedCompanies = new ArrayList<>();
        for (Company item: companies) {
            if(Integer.parseInt(item.getIdCategory())  == 1 ||
                    Integer.parseInt(item.getIdCategory()) == 2 ||
                    Integer.parseInt(item.getIdCategory()) == 3 ||
                    Integer.parseInt(item.getIdCategory()) == 10 ||
                    Integer.parseInt(item.getIdCategory()) == 11){
                sortedCompanies.add(item);
            }
        }
        CompanyListAdapter adapter = new CompanyListAdapter(this, R.layout.delivery_card, sortedCompanies);
        listView_services.setAdapter(adapter);
        listView_services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Отримати об'єкт, який був натиснутий, на основі позиції
                Company selectedCompany = (Company) parent.getItemAtPosition(position);

                // Перетворення об'єкта JobOffer в JSON рядок
                String jobOfferJson = new Gson().toJson(selectedCompany);

                Intent intent = new Intent(Services.this, Business_services_page.class);
                intent.putExtra("selectedCompanyJson", jobOfferJson);
                startActivity(intent);
            }
        });

        search_news.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keywords = search_news.getText().toString();
                DatabaseHelper dbHelper = new DatabaseHelper(Services.this);
                User user1 = AppData.getInstance().getUser();
                int IdCity = dbHelper.getCityForUser(user1.getUserPhone());
                List<Company> companies = dbHelper.getCompaniesByCityId(idCity);
                List<Company> sortedCompanies = new ArrayList<>();
                for (Company item: companies) {
                    if(Integer.parseInt(item.getIdCategory())  == 1 ||
                            Integer.parseInt(item.getIdCategory()) == 2 ||
                            Integer.parseInt(item.getIdCategory()) == 3 ||
                            Integer.parseInt(item.getIdCategory()) == 10 ||
                            Integer.parseInt(item.getIdCategory()) == 11){
                        sortedCompanies.add(item);
                    }
                }

                List<Company> filteredCompanyNames = new ArrayList<>();

                for (Company company : sortedCompanies) {
                    String companyName = company.getCompanyName().toLowerCase();
                    if (companyName.contains(keywords.toLowerCase())) {
                        filteredCompanyNames.add(company);
                    }
                }
                CompanyListAdapter searchAdapter = new CompanyListAdapter(Services.this, R.layout.delivery_card, filteredCompanyNames);
                listView_services.setAdapter(searchAdapter);
            }
        });

        initView();
    }
    public List<Company> getCompaniesByCategoryAndCity(int idCity, int idCategory) {
        List<Company> allCompanies = dbHelper.getCompaniesByCityId(idCity);
        List<Company> filteredCompanies = new ArrayList<>();

        for (Company company : allCompanies) {
            int companyCategoryId = Integer.parseInt(company.getIdCategory());
            if (companyCategoryId == idCategory) {
                filteredCompanies.add(company);
            }
        }

        return filteredCompanies;
    }

    private void initView() {

        ButtonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DatabaseHelper(Services.this);
                user = AppData.getInstance().getUser();
                int idCity = dbHelper.getCityForUser(user.getUserPhone());
                List<Company> companies = getCompaniesByCategoryAndCity(idCity, 3);

                CompanyListAdapter adapter = new CompanyListAdapter(Services.this, R.layout.delivery_card,companies);

                listView_services.setAdapter(adapter);
                Drawable drawable1 = ContextCompat.getDrawable(Services.this,
                        getResources().getIdentifier("not_beautysalon", "drawable", getPackageName()));
                drawable1.setBounds(0, 0, newWidth, newHeight);
                ButtonBeautysalon.setImageDrawable(drawable1);

                Drawable drawable2 = ContextCompat.getDrawable(Services.this,
                        getResources().getIdentifier("not_barbershop", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ButtonBarbershop.setImageDrawable(drawable2);

                Drawable drawable3 = ContextCompat.getDrawable(Services.this,
                        getResources().getIdentifier("all", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ButtonAll.setImageDrawable(drawable3);
            }
        });

        ButtonBarbershop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DatabaseHelper(Services.this);
                user = AppData.getInstance().getUser();
                int idCity = dbHelper.getCityForUser(user.getUserPhone());
                List<Company> companies = getCompaniesByCategoryAndCity(idCity, 1);

                CompanyListAdapter adapter = new CompanyListAdapter(Services.this, R.layout.delivery_card,companies);

                listView_services.setAdapter(adapter);
                Drawable drawable1 = ContextCompat.getDrawable(Services.this,
                        getResources().getIdentifier("not_beautysalon", "drawable", getPackageName()));
                drawable1.setBounds(0, 0, newWidth, newHeight);
                ButtonBeautysalon.setImageDrawable(drawable1);

                Drawable drawable2 = ContextCompat.getDrawable(Services.this,
                        getResources().getIdentifier("barbershop", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ButtonBarbershop.setImageDrawable(drawable2);

                Drawable drawable3 = ContextCompat.getDrawable(Services.this,
                        getResources().getIdentifier("not_all", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ButtonAll.setImageDrawable(drawable3);
            }
        });

        ButtonBeautysalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DatabaseHelper(Services.this);
                user = AppData.getInstance().getUser();
                int idCity = dbHelper.getCityForUser(user.getUserPhone());
                List<Company> companies = getCompaniesByCategoryAndCity(idCity, 2);

                CompanyListAdapter adapter = new CompanyListAdapter(Services.this, R.layout.delivery_card,companies);

                listView_services.setAdapter(adapter);
                Drawable drawable1 = ContextCompat.getDrawable(Services.this,
                        getResources().getIdentifier("beautysalon", "drawable", getPackageName()));
                drawable1.setBounds(0, 0, newWidth, newHeight);
                ButtonBeautysalon.setImageDrawable(drawable1);

                Drawable drawable2 = ContextCompat.getDrawable(Services.this,
                        getResources().getIdentifier("not_barbershop", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ButtonBarbershop.setImageDrawable(drawable2);

                Drawable drawable3 = ContextCompat.getDrawable(Services.this,
                        getResources().getIdentifier("not_all", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ButtonAll.setImageDrawable(drawable3);
            }
        });

        ImageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", ProfileFragment.class.getName());
                startActivity(intent);
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services.this, HomePage.class);
                startActivity(intent);
            }
        });

        ImageButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services.this, HomePage.class);
                startActivity(intent);
            }
        });

        ImageButtonRibbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", NewsFeedFragment.class.getName());
                startActivity(intent);
            }
        });

        ImageButtonCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", CityFragment.class.getName());
                startActivity(intent);
            }
        });

        ImageButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", ChatFragment.class.getName());
                startActivity(intent);
            }
        });
    }
}