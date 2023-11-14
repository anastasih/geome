package com.example.geome;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.JobOffer;
import com.example.geome.Models.JobOfferAdapter;
import com.example.geome.Models.NewFeedAdapter;
import com.example.geome.Models.NewsCard;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacanciesServiceCompaniesFragment extends Fragment {
    public ListView lvpopularVacancies;
    public ImageView categoryServicesButton, categoryDeliveryButton, categoryAccommodationButton, categoryTaxiButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vacancies_service_companies, container, false);

        initView(rootView);

        return rootView;
    }

    private void initView(View rootView){
        lvpopularVacancies = rootView.findViewById(R.id.lvpopularVacancies);
        categoryServicesButton = rootView.findViewById(R.id.categoryServicesButton);
        categoryDeliveryButton = rootView.findViewById(R.id.categoryDeliveryButton);
        categoryAccommodationButton = rootView.findViewById(R.id.categoryAccommodationButton);
        categoryTaxiButton = rootView.findViewById(R.id.categoryTaxiButton);


        List<JobOffer> cardItemList = getCardItemsFromDatabase();
        JobOfferAdapter adapter = new JobOfferAdapter(getActivity(), R.layout.job_offer_card, cardItemList);
        lvpopularVacancies.setAdapter(adapter);
        lvpopularVacancies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Отримати об'єкт, який був натиснутий, на основі позиції
                JobOffer selectedOffer = (JobOffer) parent.getItemAtPosition(position);

                // Перетворення об'єкта JobOffer в JSON рядок
                String jobOfferJson = new Gson().toJson(selectedOffer);

                Intent intent = new Intent(getContext(), JobOfferDetailsActivity.class);
                intent.putExtra("selectedOfferJson", jobOfferJson);
                startActivity(intent);
            }
        });

        categoryServicesButton.setOnClickListener(this::categoryServicesButtonClick);
        categoryDeliveryButton.setOnClickListener(this::categoryDeliveryButtonClick);
        categoryAccommodationButton.setOnClickListener(this::categoryAccommodationButtonClick);
        categoryTaxiButton.setOnClickListener(this::categoryTaxiButtonClick);
    }

    public void updateFragmentContent(List<JobOffer> updatedOffers) {
        // Оновіть список або адаптер фрагмента з новими даними
        if(updatedOffers != null && updatedOffers.size() > 0){
            JobOfferAdapter adapter = new JobOfferAdapter(getActivity(), R.layout.job_offer_card, updatedOffers);
            lvpopularVacancies.setAdapter(adapter);
        } else{
            List<JobOffer> cardItemList = getCardItemsFromDatabase();
            JobOfferAdapter adapter = new JobOfferAdapter(getActivity(), R.layout.job_offer_card, cardItemList);
            lvpopularVacancies.setAdapter(adapter);
        }
    }
    private void categoryTaxiButtonClick(View view) {
        Intent intent = new Intent(getActivity(), CategoryWorkActivity.class);
        intent.putExtra("textViewName", "Вакансії категорії “Таксі”");
        intent.putExtra("categoryTitle", "Таксі");
        startActivity(intent);
    }
    private void categoryAccommodationButtonClick(View view) {
        Intent intent = new Intent(getActivity(), CategoryWorkActivity.class);
        intent.putExtra("textViewName", "Вакансії категорії “Житло”");
        intent.putExtra("categoryTitle", "Житло");
        startActivity(intent);
    }
    private void categoryDeliveryButtonClick(View view) {
        Intent intent = new Intent(getActivity(), CategoryWorkActivity.class);
        intent.putExtra("textViewName", "Вакансії категорії “Доставка”");
        intent.putExtra("categoryTitle", "Доставка");
        startActivity(intent);
    }
    private void categoryServicesButtonClick(View view) {
        Intent intent = new Intent(getActivity(), CategoryWorkActivity.class);
        intent.putExtra("textViewName", "Вакансії категорії “Послуги”");
        intent.putExtra("categoryTitle", "Послуги");
        startActivity(intent);
    }

    private List<JobOffer> getCardItemsFromDatabase() {
        List<JobOffer> cardItemList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
            db = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_JOB_OFFER;
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    int offerId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_ID));
                    int companyId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_ID_COMPANY));
                    String companyName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_COMPANY_NAME));
                    String offerName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_NAME));
                    String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_DESCRIPTION));
                    String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_EMAIL));
                    String phone = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_PHONE));
                    String requirements = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_REQUIREMENTS));
                    String category = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_CATEGORY));
                    String companyCategory = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_CATEGORY_COMPANY));
                    String salary = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_SALARY));
                    String keywords = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_KEYWORDS));

                        //int salaryOffer = Integer.parseInt(salary);
                    if(companyId != 0){
                        JobOffer offer = new JobOffer(offerId, companyId, companyName, offerName, description,
                                salary, email, phone, requirements, category, companyCategory, keywords);
                        cardItemList.add(offer);
                    }

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
}