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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


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
public class HostelActivity extends AppCompatActivity implements AdapterView.OnItemClickListener   {
    public DatabaseHelper dbHelper;
    public User user;
    public ListView listView_hostel;
    public ImageButton ImageButtonMap;
    public ImageButton ImageButtonProfile;
    public ImageView buttonBack;
    EditText search_news;
    public ImageButton ButtonHostel1;
    public ImageButton ButtonApps1;
    public ImageButton ImageButtonMain;
    public ImageButton ImageButtonRibbon;
    public ImageButton ImageButtonCity;
    public ImageButton ImageButtonChat;
    public  int newWidth = 60;
    public int newHeight = 60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel);

        ImageButtonMap = findViewById(R.id.ImageButtonMap_hostel);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile_hostel);

        listView_hostel = findViewById(R.id.listView_hostel);

        buttonBack = findViewById(R.id.buttonBack_hostel);
        search_news = findViewById(R.id.search_news);

        ButtonHostel1 = findViewById(R.id.ButtonHostel);
        ButtonApps1 = findViewById(R.id.ButtonApps);

        ImageButtonMain = findViewById(R.id.ImageButtonMain_hostel);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon_hostel);
        ImageButtonCity = findViewById(R.id.ImageButtonCity_hostel);
        ImageButtonChat = findViewById(R.id.ImageButtonChat_hostel);

        search_news.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keywords = search_news.getText().toString();
                DatabaseHelper dbHelper = new DatabaseHelper(HostelActivity.this);
                User user1 = AppData.getInstance().getUser();
                int IdCity = dbHelper.getCityForUser(user1.getUserPhone());
                List<Company> searchResultsByCity = getCompaniesByCategoryAndCity(IdCity, 7);;

                List<Company> filteredCompanyNames = new ArrayList<>();

                for (Company company : searchResultsByCity) {
                    String companyName = company.getCompanyName().toLowerCase();
                    if (companyName.contains(keywords.toLowerCase())) {
                        filteredCompanyNames.add(company);
                    }
                }
                CompanyListAdapter searchAdapter = new CompanyListAdapter(HostelActivity.this, R.layout.delivery_card, filteredCompanyNames);
                listView_hostel.setAdapter(searchAdapter);
            }
        });


        dbHelper = new DatabaseHelper(HostelActivity.this);
        user = AppData.getInstance().getUser();
        int idCity = dbHelper.getCityForUser(user.getUserPhone());
        List<Company> companies = getCompaniesByCategoryAndCity(idCity, 7);


        listView_hostel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Отримати об'єкт, який був натиснутий, на основі позиції
                Company selectedCompany = (Company) parent.getItemAtPosition(position);

                // Перетворення об'єкта JobOffer в JSON рядок
                String jobOfferJson = new Gson().toJson(selectedCompany);

                Intent intent = new Intent(HostelActivity.this, Business_services_page_hostel.class);
                intent.putExtra("selectedCompanyJson", jobOfferJson);
                startActivity(intent);
            }
        });
        CompanyListAdapter adapter = new CompanyListAdapter(this, R.layout.delivery_card, companies);
        listView_hostel.setAdapter(adapter);
        //listView_hostel.setOnItemClickListener(this);
        initView();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("HostelActivity", "onItemClick triggered from XML");
        Company selectedCompany = (Company) parent.getItemAtPosition(position);
        String jobOfferJson = new Gson().toJson(selectedCompany);
        Intent intent = new Intent(HostelActivity.this, Business_services_page_hostel.class);
        intent.putExtra("selectedCompanyJson", jobOfferJson);
        startActivity(intent);
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

        ButtonHostel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DatabaseHelper(HostelActivity.this);
                user = AppData.getInstance().getUser();
                int idCity = dbHelper.getCityForUser(user.getUserPhone());
                List<Company> companies = getCompaniesByCategoryAndCity(idCity, 7);

                CompanyListAdapter adapter = new CompanyListAdapter(HostelActivity.this, R.layout.delivery_card,companies);

                listView_hostel.setAdapter(adapter);
                Drawable drawable1 = ContextCompat.getDrawable(HostelActivity.this,
                        getResources().getIdentifier("not_apps", "drawable", getPackageName()));
                drawable1.setBounds(0, 0, newWidth, newHeight);
                ButtonApps1.setImageDrawable(drawable1);

                Drawable drawable2 = ContextCompat.getDrawable(HostelActivity.this,
                        getResources().getIdentifier("hostel", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ButtonHostel1.setImageDrawable(drawable2);
            }
        });
        ButtonApps1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DatabaseHelper(HostelActivity.this);
                user = AppData.getInstance().getUser();
                int idCity = dbHelper.getCityForUser(user.getUserPhone());
                List<Company> companies = getCompaniesByCategoryAndCity(idCity, 8);

                CompanyListAdapter adapter = new CompanyListAdapter(HostelActivity.this, R.layout.delivery_card,companies);
                listView_hostel.setAdapter(adapter);
                Drawable drawable1 = ContextCompat.getDrawable(HostelActivity.this,
                        getResources().getIdentifier("apps", "drawable", getPackageName()));
                drawable1.setBounds(0, 0, newWidth, newHeight);
                ButtonApps1.setImageDrawable(drawable1);

                Drawable drawable2 = ContextCompat.getDrawable(HostelActivity.this,
                        getResources().getIdentifier("not_hostel", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ButtonHostel1.setImageDrawable(drawable2);
            }
        });
        ImageButtonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostelActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        ImageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostelActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostelActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        ImageButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostelActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        ImageButtonRibbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostelActivity.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", NewsFeedFragment.class.getName());
                startActivity(intent);
            }
        });

        ImageButtonCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostelActivity.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", CityFragment.class.getName());
                startActivity(intent);
            }
        });

        ImageButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostelActivity.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", ChatFragment.class.getName());
                startActivity(intent);
            }
        });
    }
}