package com.example.geome;

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
public class HostelActivity extends AppCompatActivity   {
    DatabaseHelper dbHelper;
    User user;
    private ListView listView_delivery;
    ImageButton ImageButtonMap;
    ImageButton ImageButtonProfile;
    ImageView buttonBack;
    ImageButton ButtonHostel1;
    ImageButton ButtonApps1;
    ImageButton ImageButtonMain;
    ImageButton ImageButtonRibbon;
    ImageButton ImageButtonCity;
    ImageButton ImageButtonChat;
    public  int newWidth = 60;
    public int newHeight = 60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel);


        ImageButtonMap = findViewById(R.id.ImageButtonMap_hostel);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile_hostel);

        listView_delivery = findViewById(R.id.listView_hostel);

        buttonBack = findViewById(R.id.buttonBack_hostel);

        ButtonHostel1 = findViewById(R.id.ButtonHostel);
        ButtonApps1 = findViewById(R.id.ButtonApps);

        ImageButtonMain = findViewById(R.id.ImageButtonMain_hostel);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon_hostel);
        ImageButtonCity = findViewById(R.id.ImageButtonCity_hostel);
        ImageButtonChat = findViewById(R.id.ImageButtonChat_hostel);


        dbHelper = new DatabaseHelper(HostelActivity.this);
        user = AppData.getInstance().getUser();
        int idCity = dbHelper.getCityForUser(user.getUserPhone());
        List<Company> companies = getCompaniesByCategoryAndCity(idCity, 8);

        CompanyListAdapter adapter = new CompanyListAdapter(this, companies);
        listView_delivery.setAdapter(adapter);
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

        ButtonHostel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DatabaseHelper(HostelActivity.this);
                user = AppData.getInstance().getUser();
                int idCity = dbHelper.getCityForUser(user.getUserPhone());
                List<Company> companies = getCompaniesByCategoryAndCity(idCity, 8);

                CompanyListAdapter adapter = new CompanyListAdapter(HostelActivity.this, companies);

                listView_delivery.setAdapter(adapter);
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

                CompanyListAdapter adapter = new CompanyListAdapter(HostelActivity.this, companies);
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