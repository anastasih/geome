package com.example.geome;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
public class DeliveryActivity extends AppCompatActivity   {
    DatabaseHelper dbHelper;
    User user;
    private ListView listView_delivery;
    private EditText search_delivery;
    ImageButton ImageButtonMap;
    ImageButton ImageButtonProfile;
    ImageView buttonBack;
    ImageButton ButtonEstablishment;
    ImageButton ButtonSupermarket;
    ImageButton ImageButtonMain;
    ImageButton ImageButtonRibbon;
    ImageButton ImageButtonCity;
    ImageButton ImageButtonChat;
    public  int newWidth = 60;
    public int newHeight = 60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);


        ImageButtonMap = findViewById(R.id.ImageButtonMap_delivery);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile_delivery);

        listView_delivery = findViewById(R.id.listView_delivery);

        buttonBack = findViewById(R.id.buttonBack_delivery);

        search_delivery = findViewById(R.id.search_delivery);

        ButtonEstablishment = findViewById(R.id.ImageButtonEstablishment);
        ButtonSupermarket = findViewById(R.id.ImageButtonSupermarket);

        ImageButtonMain = findViewById(R.id.ImageButtonMain_delivery);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon_delivery);
        ImageButtonCity = findViewById(R.id.ImageButtonCity_delivery);
        ImageButtonChat = findViewById(R.id.ImageButtonChat_delivery);


        dbHelper = new DatabaseHelper(DeliveryActivity.this);
        user = AppData.getInstance().getUser();
        int idCity = dbHelper.getCityForUser(user.getUserPhone());
        List<Company> companies = getCompaniesByCategoryAndCity(idCity, 6);

        CompanyListAdapter adapter = new CompanyListAdapter(this, companies);
        listView_delivery.setAdapter(adapter);
        initView();
    }
//    public void setEditText() {
//        search_delivery.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String keywords = search_delivery.getText().toString();
//                DatabaseHelper dbHelper = new DatabaseHelper(DeliveryActivity.this);
//                List<Map<String, Object>> searchResults = dbHelper.searchNewsByKeywords(keywords);
//
//                List<NewsCard> newsCards = new ArrayList<>();
//
//                for (Map<String, Object> newsItem : searchResults) {
//                    int newsId = (int) newsItem.get("newsId");
//                    int companyId = (int) newsItem.get("companyId");
//                    String newsPhoto = (String) newsItem.get("newsPhoto");
//                    String description = (String) newsItem.get("description");
//                    String companyName = (String) newsItem.get("companyName");
//                    Date date = (Date) newsItem.get("date");
//
//                    NewsCard newsCard = new NewsCard(newsId, companyId, newsPhoto, description, date);
//                    newsCards.add(newsCard);
//                }
//
//                CompanyListAdapter searchAdapter = new CompanyListAdapter(getContext(), R.layout.news_card, newsCards);
//                listView_delivery.setAdapter(searchAdapter);
//            }
//        });
//    }
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

        ButtonEstablishment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DatabaseHelper(DeliveryActivity.this);
                user = AppData.getInstance().getUser();
                int idCity = dbHelper.getCityForUser(user.getUserPhone());
                List<Company> companies = getCompaniesByCategoryAndCity(idCity, 6);

                CompanyListAdapter adapter = new CompanyListAdapter(DeliveryActivity.this, companies);

                listView_delivery.setAdapter(adapter);
                Drawable drawable1 = ContextCompat.getDrawable(DeliveryActivity.this,
                        getResources().getIdentifier("not_supermarket", "drawable", getPackageName()));
                drawable1.setBounds(0, 0, newWidth, newHeight);
                ButtonSupermarket.setImageDrawable(drawable1);

                Drawable drawable2 = ContextCompat.getDrawable(DeliveryActivity.this,
                        getResources().getIdentifier("establishment", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ButtonEstablishment.setImageDrawable(drawable2);
            }
        });
        ButtonSupermarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DatabaseHelper(DeliveryActivity.this);
                user = AppData.getInstance().getUser();
                int idCity = dbHelper.getCityForUser(user.getUserPhone());
                List<Company> companies = getCompaniesByCategoryAndCity(idCity, 6);

                CompanyListAdapter adapter = new CompanyListAdapter(DeliveryActivity.this, companies);
                Drawable drawable1 = ContextCompat.getDrawable(DeliveryActivity.this,
                        getResources().getIdentifier("supermarket", "drawable", getPackageName()));
                drawable1.setBounds(0, 0, newWidth, newHeight);
                ButtonSupermarket.setImageDrawable(drawable1);

                Drawable drawable2 = ContextCompat.getDrawable(DeliveryActivity.this,
                        getResources().getIdentifier("not_establishment", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ButtonEstablishment.setImageDrawable(drawable2);
            }
        });
        ImageButtonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        ImageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        ImageButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        ImageButtonRibbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryActivity.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", NewsFeedFragment.class.getName());
                startActivity(intent);
            }
        });

        ImageButtonCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryActivity.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", CityFragment.class.getName());
                startActivity(intent);
            }
        });

        ImageButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryActivity.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", ChatFragment.class.getName());
                startActivity(intent);
            }
        });
    }
}