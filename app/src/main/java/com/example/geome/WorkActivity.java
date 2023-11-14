package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.JobOffer;
import com.example.geome.Models.JobOfferAdapter;
import com.example.geome.Models.NewFeedAdapter;
import com.example.geome.Models.NewsCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkActivity extends AppCompatActivity {

    public TextView workServiceButton, workPartnersButton;
    public EditText search_news;

    public ImageView buttonBack;
    public ImageButton ImageButtonMain, ImageButtonRibbon, ImageButtonCity, ImageButtonChat, ImageButtonProfile;
    private VacanciesServiceCompaniesFragment companiesServiceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        initView();
    }

    private void initView(){
        workServiceButton = findViewById(R.id.workServiceButton);
        workPartnersButton = findViewById(R.id.workPartnersButton);
        buttonBack = findViewById(R.id.buttonBack);
        ImageButtonMain = findViewById(R.id.ImageButtonMain);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon);
        ImageButtonCity = findViewById(R.id.ImageButtonCity);
        ImageButtonChat = findViewById(R.id.ImageButtonChat);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile);
        search_news = findViewById(R.id.search_news);

        try {
            Class<?> fragmentClass = Class.forName(VacanciesServiceCompaniesFragment.class.getName());
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            companiesServiceFragment = (VacanciesServiceCompaniesFragment) fragment; // Отримати посилання на фрагмент
            replaceFragment(fragment);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        setEditText();

//        try {
//            Class<?> fragmentClass = Class.forName(VacanciesServiceCompaniesFragment.class.getName());
//            Fragment fragment = (Fragment) fragmentClass.newInstance();
//            replaceFragment(fragment);
//        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
//            e.printStackTrace();
//        }

        workServiceButton.setOnClickListener(this::workServiceButtonClick);
        workPartnersButton.setOnClickListener(this::workPartnersButtonClick);
        buttonBack.setOnClickListener(this::buttonBackClick);
        ImageButtonMain.setOnClickListener(this::ImageButtonMainClick);
        ImageButtonRibbon.setOnClickListener(this::ImageButtonRibbonClick);
        ImageButtonCity.setOnClickListener(this::ImageButtonCityClick);
        ImageButtonChat.setOnClickListener(this::ImageButtonChatClick);
        ImageButtonProfile.setOnClickListener(this::ImageButtonProfileClick);
    }
    private void ImageButtonProfileClick(View view) {
        Intent intent = new Intent(WorkActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ProfileFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonChatClick(View view) {
        Intent intent = new Intent(WorkActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ChatFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonCityClick(View view) {
        Intent intent = new Intent(WorkActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", CityFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonRibbonClick(View view) {
        Intent intent = new Intent(WorkActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", NewsFeedFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonMainClick(View view) {
        Intent intent = new Intent(WorkActivity.this, HomePage.class);
        startActivity(intent);
    }
    private void buttonBackClick(View view) {
        Intent intent = new Intent(WorkActivity.this, HomePage.class);
        startActivity(intent);
    }
    private void workServiceButtonClick(View view) {
        try {
            Class<?> fragmentClass = Class.forName(VacanciesServiceCompaniesFragment.class.getName());
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            workServiceButton.setTextColor(0xFFFA5A00);
            workPartnersButton.setTextColor(0xFFD8D8D8);
            replaceFragment(fragment);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
    private void workPartnersButtonClick(View view) {
        try {
            Class<?> fragmentClass = Class.forName(VacanciesJobSearchServicesFragment.class.getName());
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            workServiceButton.setTextColor(0xFFD8D8D8);
            workPartnersButton.setTextColor(0xFFFA5A00);
            replaceFragment(fragment);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void setEditText() {
        search_news.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keywords = search_news.getText().toString();
                DatabaseHelper dbHelper = new DatabaseHelper(WorkActivity.this);
                List<Map<String, Object>> searchResults = dbHelper.searchJobOffersByKeywords(keywords);

                List<JobOffer> offers = new ArrayList<>();

                for (Map<String, Object> offerItem : searchResults) {
                    int offerId = (int) offerItem.get("Id");
                    int companyId = (int) offerItem.get("companyId");
                    String offerName = (String) offerItem.get("offerName");
                    String description = (String) offerItem.get("description");
                    String salary = (String) offerItem.get("salary");
                    String email = (String) offerItem.get("email");
                    String phone = (String) offerItem.get("phone");
                    String requirements = (String) offerItem.get("requirements");
                    String category = (String) offerItem.get("category");
                    String companyName = (String) offerItem.get("companyName");
                    String categoryCompany = (String) offerItem.get("categoryCompany");
                    String keywordsTable = (String) offerItem.get("keywords");

                    JobOffer newOffer = new JobOffer(offerId, companyId, companyName, offerName,
                            description, salary, email, phone, requirements, category, categoryCompany,
                            keywordsTable);
                    offers.add(newOffer);

                    //int companyCategory = Integer.parseInt(categoryCompany);
                    // Перевіряємо, чи відповідає пропозиція роботи обраній категорії
//                    if (selectedCategory == companyCategory) {
//                        JobOffer newOffer = new JobOffer(offerId, companyId, companyName, offerName,
//                                description, salary, email, phone, requirements, category, categoryCompany);
//                        offers.add(newOffer);
//                    }
                }

                companiesServiceFragment.updateFragmentContent(offers);

//                JobOfferAdapter searchAdapter = new JobOfferAdapter(WorkActivity.this, R.layout.job_offer_card, offers);
//                lvNews.setAdapter(searchAdapter);

                //return offers;
            }
        });
    }
}