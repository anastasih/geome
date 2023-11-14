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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vacancies_job_search_services, container, false);

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
        cardItemList = getCardItemsFromDatabase(firstId);
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

    private boolean isPeopleWithDisabilitiesActive = false;
    private boolean isOlderPeopleActive = false;
    private boolean isWithoutExperienceActive = false;
    private boolean isOnlineActive = false;
    private boolean isStudentsActive = false;
    private TextView activeButton;

    private void onKeywordButtonClick(TextView button, boolean isActive) {
        Toast.makeText(getContext(), "before " + isActive, Toast.LENGTH_SHORT).show();
        if (activeButton != null) {
            deactivateButton(activeButton);
        }
        if (!isActive) {
//            Toast.makeText(getContext(), "active Button" + activeButton.getText(), Toast.LENGTH_SHORT).show();
//            if (activeButton != null) {
//                //Toast.makeText(getContext(), "active Button" + activeButton.getText(), Toast.LENGTH_SHORT).show();
//                deactivateButton(activeButton);
//            }
            cardItemList = getCardItemsSortedByKeywords(selectedCategoryId, button.getText().toString());
            activateButton(button);
        } else {
            Toast.makeText(getContext(), "hello2", Toast.LENGTH_SHORT).show();

            cardItemList = getCardItemsFromDatabase(selectedCategoryId);
            //deactivateButton(button);
        }
        adapter = new JobOfferAdapterServices(getContext(), R.layout.job_offer_card, cardItemList);
        lvVacancies.setAdapter(adapter);
        //setActiveState(button, isActive);
        Toast.makeText(getContext(), "after " + isActive, Toast.LENGTH_SHORT).show();
        Log.d("DEBUG", "onKeywordButtonClick: isActive=" + isActive + ", button=" + button.getText());
    }

    private void setActiveState(TextView button, boolean isActive) {
//        if (!isActive) {
//            changeBackgroundKeywordsSelected(button, true);
//        } else {
//            changeBackgroundKeywordsSelected(button, false);
//        }

//        String text = button.getText().toString();
//        switch (text){
//            case "Для студентів":{
//                isStudentsActive = isActive;
//                Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
//                break;
//            }
//            case "Дистанційно":{
//                isOnlineActive = isActive;
//                break;
//            }
//            case "Без досвіду":{
//                isWithoutExperienceActive = isActive;
//                break;
//            }
//            case "Для людей старшого віку":{
//                isOlderPeopleActive = isActive;
//                break;
//            }
//            case "Для людей із інвалідністю":{
//                isPeopleWithDisabilitiesActive = isActive;
//                break;
//            }
//        }

        if (button.getText().toString().contains(peopleWithDisabilitiesButton.getText().toString())) {
            isPeopleWithDisabilitiesActive = !isActive;
        } else if (button.getText().toString().contains(olderPeopleButton.getText().toString())) {
            isOlderPeopleActive = !isActive;
        } else if (button.getText().toString().contains(withoutExperienceButton.getText().toString())) {
            isWithoutExperienceActive = !isActive;
        } else if (button.getText().toString().contains(onlineButton.getText().toString())) {
            isOnlineActive = !isActive;
        } else if (button.getText().toString().contains(studentsButton.getText().toString())) {
            isStudentsActive = !isActive;
        }
        Toast.makeText(getContext(), "sActive=" + isActive + ", button=" + button.getText(), Toast.LENGTH_SHORT).show();
        //Log.d("DEBUG", "setActiveState: isActive=" + isActive + ", button=" + button.getText());
    }

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
        changeBackgroundKeywordsSelected(button, false);
        setActiveState(button, false);
    }

    private void clearActiveStates(TextView button) {
//        isPeopleWithDisabilitiesActive = true;
//        isOlderPeopleActive = true;
//        isWithoutExperienceActive = true;
//        isOnlineActive = true;
//        isStudentsActive = true;

        if (button.getText().toString().contains(peopleWithDisabilitiesButton.getText().toString())) {
            isPeopleWithDisabilitiesActive = true;
        } else if (button.getText().toString().contains(olderPeopleButton.getText().toString())) {
            isOlderPeopleActive = true;
        } else if (button.getText().toString().contains(withoutExperienceButton.getText().toString())) {
            isWithoutExperienceActive = true;
        } else if (button.getText().toString().contains(onlineButton.getText().toString())) {
            isOnlineActive = true;
        } else if (button.getText().toString().contains(studentsButton.getText().toString())) {
            isStudentsActive = true;
        }

        //activeButton = null;
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
                List<JobOfferServices> cardItemListSelected =  getCardItemsFromDatabase(selectedCategoryId);
                adapter = new JobOfferAdapterServices(getContext(), R.layout.job_offer_card, cardItemListSelected);
                lvVacancies.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private List<JobOfferServices> getCardItemsFromDatabase(int selectedCategoryId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<JobOfferServices> cardItemListSelected = new ArrayList<>();

        try {
            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            db = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_JOB_OFFER_SERVICES +
                    " WHERE " + DatabaseHelper.COLUMN_JOB_OFFER_SERVICES_COMPANY_CATEGORY_ID + " = ?";

            cursor = db.rawQuery(query, new String[]{String.valueOf(selectedCategoryId)});

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

                    //if(companyId == 0){
                    JobOfferServices offer = new JobOfferServices(offerId, companyName,
                            offerName, description, address, cityId,
                            salary, companyCategory, keywords, icon, link);

                    cardItemListSelected.add(offer);
                    //}

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
        return cardItemListSelected;
    }

    private List<JobOfferServices> getCardItemsSortedByKeywords(int selectedCategoryId, String wordToCompare) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<JobOfferServices> cardItemListSelected = new ArrayList<>();

        try {
            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            db = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_JOB_OFFER_SERVICES +
                    " WHERE " + DatabaseHelper.COLUMN_JOB_OFFER_SERVICES_COMPANY_CATEGORY_ID + " = ?";

            cursor = db.rawQuery(query, new String[]{String.valueOf(selectedCategoryId)});

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

                    //if(companyId == 0){
//                    JobOfferServices offer = new JobOfferServices(offerId, companyName,
//                            offerName, description, address, cityId,
//                            salary, companyCategory, keywords, icon, link);

                    String[] elements = keywords.split(",");
                    for (String element : elements) {
                        if (element.contains(wordToCompare)) {
                            JobOfferServices offer = new JobOfferServices(offerId, companyName,
                                    offerName, description, address, cityId,
                                    salary, companyCategory, keywords, icon, link);
                            cardItemListSelected.add(offer);
                        }
                    }

                    //cardItemListSelected.add(offer);
                    //}

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
        return cardItemListSelected;
    }
}