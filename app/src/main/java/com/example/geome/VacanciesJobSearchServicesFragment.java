package com.example.geome;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.JobOffer;
import com.example.geome.Models.JobOfferAdapter;
import com.example.geome.Models.JobOfferAdapterServices;
import com.example.geome.Models.JobOfferServices;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class VacanciesJobSearchServicesFragment extends Fragment {
    public Spinner spinnerWorkCategory;
    public ListView lvVacancies;
    public List<JobOfferServices> cardItemList;
    public JobOfferAdapterServices adapter;
    public TextView peopleWithDisabilitiesButton, olderPeopleButton, withoutExperienceButton,
            onlineButton, studentsButton;
    public int selectedCategoryId;
    public List<JobOfferServices> allJobOffers;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vacancies_job_search_services, container, false);

        allJobOffers = getCardItemsAll();
        initView(rootView);


        return rootView;
    }

    private void initView(View rootView) {
        spinnerWorkCategory = rootView.findViewById(R.id.spinnerWorkCategory);
        lvVacancies = rootView.findViewById(R.id.lvVacancies);
        peopleWithDisabilitiesButton = rootView.findViewById(R.id.peopleWithDisabilitiesButton);
        olderPeopleButton = rootView.findViewById(R.id.olderPeopleButton);
        withoutExperienceButton = rootView.findViewById(R.id.withoutExperienceButton);
        onlineButton = rootView.findViewById(R.id.onlineButton);
        studentsButton = rootView.findViewById(R.id.studentsButton);

        peopleWithDisabilitiesButton.setOnClickListener(this::peopleWithDisabilitiesButtonClick);
        olderPeopleButton.setOnClickListener(this::olderPeopleButtonClick);
        withoutExperienceButton.setOnClickListener(this::withoutExperienceButtonClick);
        onlineButton.setOnClickListener(this::onlineButtonClick);
        studentsButton.setOnClickListener(this::studentsButtonClick);

        int firstId = 1;
        //cardItemList = getCardItemsFromDatabase(firstId);
        cardItemList = getCardItemsSorted(1);
        adapter = new JobOfferAdapterServices(getContext(), R.layout.job_offer_card, cardItemList);
        lvVacancies.setAdapter(adapter);

        lvVacancies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobOfferServices selectedOffer = (JobOfferServices) parent.getItemAtPosition(position);
                String link = selectedOffer.getLink();

                if (link != null && !link.isEmpty()) {
                    Uri webpage = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    intent.setPackage("com.android.chrome");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        setSpinner();
    }

    private void setSpinner() {
        DatabaseHelper db = new DatabaseHelper(getContext());
        Map<Integer, String> categoryData = db.getCompanyCategoryData();
        List<String> categoryNames = new ArrayList<>(categoryData.values());
        List<Integer> categoryIds = new ArrayList<>(categoryData.keySet());

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categoryNames);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkCategory.setAdapter(adapterSpinner);

        spinnerWorkCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategoryId = categoryIds.get(position);

                if (activeButton != null) {
                    deactivateButton(activeButton);
                }
                isPeopleWithDisabilitiesActive = false;
                isOlderPeopleActive = false;
                isWithoutExperienceActive = false;
                isOnlineActive = false;
                isStudentsActive = false;
                activeButton = null;


                List<JobOfferServices> cardItemListSelected =  getCardItemsSorted(selectedCategoryId);
                adapter = new JobOfferAdapterServices(getContext(), R.layout.job_offer_card, cardItemListSelected);
                lvVacancies.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private boolean isPeopleWithDisabilitiesActive = false;
    private boolean isOlderPeopleActive = false;
    private boolean isWithoutExperienceActive = false;
    private boolean isOnlineActive = false;
    private boolean isStudentsActive = false;
    private TextView activeButton;
    private void peopleWithDisabilitiesButtonClick(View view) {
        onKeywordButtonClick(peopleWithDisabilitiesButton, isPeopleWithDisabilitiesActive);
    }

    private void olderPeopleButtonClick(View view) {
        onKeywordButtonClick(olderPeopleButton, isOlderPeopleActive);
    }

    private void withoutExperienceButtonClick(View view) {
        onKeywordButtonClick(withoutExperienceButton, isWithoutExperienceActive);
    }

    private void onlineButtonClick(View view) {
        onKeywordButtonClick(onlineButton, isOnlineActive);
    }

    private void studentsButtonClick(View view) {
        onKeywordButtonClick(studentsButton, isStudentsActive);
    }

    private boolean isSortAscending = true; // Змінна для відстеження порядку сортування
    private String lastSortKeyword = "";
    private void onKeywordButtonClick(TextView button, boolean isActive) {
        if (activeButton != null) {
            deactivateButton(activeButton);
        }

        if (!isActive) {
            if (!lastSortKeyword.equals(button.getText().toString())) {
                isSortAscending = true;
                lastSortKeyword = button.getText().toString();
            } else {
                isSortAscending = !isSortAscending;
            }

            cardItemList = getCardItemsSorted(selectedCategoryId, lastSortKeyword);
            activateButton(button);
        } else {
            deactivateButton(activeButton);
            cardItemList = allJobOffers;
            lastSortKeyword = "";
        }

        adapter = new JobOfferAdapterServices(getContext(), R.layout.job_offer_card, cardItemList);
        lvVacancies.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void changeBackgroundKeywordsSelected(TextView textView, boolean isActive) {
        GradientDrawable background = (GradientDrawable) textView.getBackground();
        if (isActive) {
            background.setColor(0xFFFFECE1);
            background.setStroke(2, 0xFFFFECE1);
            textView.setBackground(background);
            textView.setTextColor(0xFFFA5A00);
        } else {
            background.setColor(0xFFFFFFFF);
            background.setStroke(2, 0xFF032323);
            textView.setBackground(background);
            textView.setTextColor(0xFF032323);
        }
    }

    private void activateButton(TextView button) {
        activeButton = button;
        changeBackgroundKeywordsSelected(button, true);
    }

    private void deactivateButton(TextView button) {
        if (button != null) {
            changeBackgroundKeywordsSelected(button, false);
            cardItemList = getCardItemsSorted(selectedCategoryId);
        }
    }

    private List<JobOfferServices> getCardItemsAll() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<JobOfferServices> cardItemList = new ArrayList<>();

        try {
            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            db = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_JOB_OFFER_SERVICES;
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    int offerId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_SERVICES_ID));
                    String companyName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_SERVICES_COMPANY_NAME));
                    String icon = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_SERVICES_COMPANY_ICON));
                    String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_SERVICES_DESCRIPTION));
                    String offerName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_SERVICES_NAME));
                    String companyCategory = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_SERVICES_COMPANY_CATEGORY_ID));
                    String keywords = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_SERVICES_KEYWORDS));
                    String salary = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_SERVICES_SALARY));
                    String link = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_SERVICES_LINK));
                    String address = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_SERVICES_ADDRESS));
                    String cityId = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_SERVICES_CITY_ID));

                    JobOfferServices offer = new JobOfferServices(offerId, companyName,
                            offerName, description, address, cityId,
                            salary, companyCategory, keywords, icon, link);
                    cardItemList.add(offer);
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
    private List<JobOfferServices> getCardItemsSorted(int id, String wordToCompare) {
        List<JobOfferServices> cardItemListSorted = new ArrayList<>();

        for (JobOfferServices item : allJobOffers) {
            if(Integer.parseInt(item.getCompanyCategory()) == id){
                String keywords = item.getKeywords();
                String[] elements =  keywords.split(",");
                for (String element : elements) {
                    if (element.contains(wordToCompare)) {
                        cardItemListSorted.add(item);
                    }
                }
            }
        }

        return cardItemListSorted;
    }
    private List<JobOfferServices> getCardItemsSorted(int id) {
        List<JobOfferServices> cardItemListSorted = new ArrayList<>();

        for (JobOfferServices item : allJobOffers) {
            if(Integer.parseInt(item.getCompanyCategory()) == id){
                cardItemListSorted.add(item);
            }
        }

        return cardItemListSorted;
    }

}