package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.JobOffer;
import com.example.geome.Models.JobOfferAdapter;
import com.example.geome.Models.NewFeedAdapter;
import com.example.geome.Models.NewsCard;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CategoryWorkActivity extends AppCompatActivity {

    public TextView titleCategoryWork;
    public EditText search_news;

    public ImageView buttonBack;
    public ImageButton ImageButtonMain, ImageButtonRibbon, ImageButtonCity, ImageButtonChat, ImageButtonProfile;
    public ListView lvVacancies;
    public String categotyTitle;
    public List<JobOffer> cardItemList;
    private TextView lastClickedMenuItem = null;
    private int defaultMenuItemIndex = 0; //  індекс елемента, який має бути вибраним за замовчуванням

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_work);

        initView();
    }

    private void initView(){
        titleCategoryWork = findViewById(R.id.titleCategoryWork);
        buttonBack = findViewById(R.id.buttonBack);
        ImageButtonMain = findViewById(R.id.ImageButtonMain);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon);
        ImageButtonCity = findViewById(R.id.ImageButtonCity);
        ImageButtonChat = findViewById(R.id.ImageButtonChat);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile);
        lvVacancies = findViewById(R.id.lvVacancies);
        search_news = findViewById(R.id.search_news);

        String title = getIntent().getStringExtra("textViewName");
        categotyTitle = getIntent().getStringExtra("categoryTitle");
        titleCategoryWork.setText(title);

        cardItemList = getCardItemsFromDatabase(categotyTitle);
        JobOfferAdapter adapter = new JobOfferAdapter(CategoryWorkActivity.this, R.layout.job_offer_card, cardItemList);
        lvVacancies.setAdapter(adapter);

        buttonBack.setOnClickListener(this::buttonBackClick);
        ImageButtonMain.setOnClickListener(this::ImageButtonMainClick);
        ImageButtonRibbon.setOnClickListener(this::ImageButtonRibbonClick);
        ImageButtonCity.setOnClickListener(this::ImageButtonCityClick);
        ImageButtonChat.setOnClickListener(this::ImageButtonChatClick);
        ImageButtonProfile.setOnClickListener(this::ImageButtonProfileClick);

        lvVacancies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Отримати об'єкт, який був натиснутий, на основі позиції
                JobOffer selectedOffer = (JobOffer) parent.getItemAtPosition(position);

                // Перетворення об'єкта JobOffer в JSON рядок
                String jobOfferJson = new Gson().toJson(selectedOffer);

                Intent intent = new Intent(CategoryWorkActivity.this, JobOfferDetailsActivity.class);
                intent.putExtra("selectedOfferJson", jobOfferJson);
                startActivity(intent);
            }
        });

        setEditText();
        setHorizontalMenuList();
    }

    private void setHorizontalMenuList(){
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontalScrollView);
        LinearLayout menuLinearLayout = findViewById(R.id.menuLinearLayout);

        String[] menuItems = null;
        if(categotyTitle.equals("Послуги")){
            menuItems = new String[]{"Барбершоп", "Б'юті-салон", "Приватна медицина", "Адвокатські компанії"};
        }
        else if(categotyTitle.equals("Доставка")){
            menuItems = new String[]{"Заклад харчування", "Супермаркет"};
        }
        else if(categotyTitle.equals("Житло")){
            menuItems = new String[]{"Готель", "Апартаменти"};
        }
        else {
            horizontalScrollView.setVisibility(View.GONE);
        }
        //String[] menuItems = {"Item 1", "Item 2", "Item 3", "Item 4"};

        if (menuItems != null && menuItems.length > 0) {
            for (int i = 0; i < menuItems.length; i++) {
                TextView menuItem = new TextView(this);
                menuItem.setText(menuItems[i]);
                menuItem.setTextSize(16);
                menuItem.setPadding(50, 0, 25, 0);
                Typeface typeface = getResources().getFont(R.font.montserrat_bold);
                menuItem.setTypeface(typeface);

                if (i == defaultMenuItemIndex) {
                    menuItem.setTextColor(0xFFFA5A00); // Встановлюємо колір для елемента за замовчуванням
                    menuItem.setPaintFlags(menuItem.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // Підкреслюємо текст
                    lastClickedMenuItem = menuItem; // Оновлюємо останній натиснутий елемент
                    String categoryName = lastClickedMenuItem.getText().toString();
                    int selectedCategory = getCategoryByTitle(categoryName);
                    updateListView(selectedCategory);
                } else {
                    menuItem.setTextColor(0xFFD8D8D8);
                }

                // Обробник натискання на елемент меню
                menuItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (lastClickedMenuItem != null) {
                            lastClickedMenuItem.setTextColor(0xFFD8D8D8);
                            lastClickedMenuItem.setPaintFlags(lastClickedMenuItem.getPaintFlags() & ~Paint.UNDERLINE_TEXT_FLAG); // Знімаємо підкреслення з попереднього елемента
                        }
                        lastClickedMenuItem = (TextView) view;
                        lastClickedMenuItem.setTextColor(0xFFFA5A00);
                        lastClickedMenuItem.setPaintFlags(lastClickedMenuItem.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // Підкреслюємо текст вибраного елемента

                        String categoryName = lastClickedMenuItem.getText().toString();
                        int selectedCategory = getCategoryByTitle(categoryName);
                        updateListView(selectedCategory);
                    }
                });

                menuLinearLayout.addView(menuItem);
            }
        }
    }
    private void ImageButtonProfileClick(View view) {
        Intent intent = new Intent(CategoryWorkActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ProfileFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonChatClick(View view) {
        Intent intent = new Intent(CategoryWorkActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ChatFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonCityClick(View view) {
        Intent intent = new Intent(CategoryWorkActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", CityFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonRibbonClick(View view) {
        Intent intent = new Intent(CategoryWorkActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", NewsFeedFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonMainClick(View view) {
        Intent intent = new Intent(CategoryWorkActivity.this, HomePage.class);
        startActivity(intent);
    }
    private void buttonBackClick(View view) {
        Intent intent = new Intent(CategoryWorkActivity.this, WorkActivity.class);
        startActivity(intent);
    }

    private List<JobOffer> getCardItemsFromDatabase(String title) {
        List<JobOffer> cardItemList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            DatabaseHelper dbHelper = new DatabaseHelper(CategoryWorkActivity.this);
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
                    String keywords = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_KEYWORDS));
                    String salary = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JOB_OFFER_SALARY));

                    JobOffer offer = new JobOffer(offerId, companyId, companyName, offerName, description,
                            salary, email, phone, requirements, category, companyCategory, keywords);

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

        List<JobOffer> selectedItems = new ArrayList<>();
        for (JobOffer offer : cardItemList) {
            if (offer.getCategory().equals(title)) {
                selectedItems.add(offer);
            }
        }

        return selectedItems;
    }

    private int getCategoryByTitle(String title) {
        if (categotyTitle.equals("Послуги")) {
            if (title.equals("Барбершоп")) {
                return 1;
            } else if (title.equals("Б'юті-салон")) {
                return 2;
            } else if (title.equals("Приватна медицина")) {
                return 3;
            } else if (title.equals("Адвокатські компанії")) {
                return 4;
            }
        } else if (categotyTitle.equals("Доставка")) {
            if (title.equals("Заклад харчування")) {
                return 5;
            } else if (title.equals("Супермаркет")) {
                return 6;
            }
        } else if (categotyTitle.equals("Житло")) {
            if (title.equals("Готель")) {
                return 7;
            } else if (title.equals("Апартаменти")) {
                return 8;
            }
        } else if (categotyTitle.equals("Таксі")) {
            return 9;
        }
        return -1; // Повертаємо значення за замовчуванням, якщо категорія не знайдена
    }

    private void updateListView(int selectedCategory) {
        // Створюємо новий список `JobOffer` на основі вибраної категорії
        List<JobOffer> filteredList = new ArrayList<>();
        for (JobOffer offer : cardItemList) {
            int companyCategory = Integer.parseInt(offer.getCompanyCategory());
            if (companyCategory == selectedCategory) {
                filteredList.add(offer);
            }
        }

        // Оновлюємо `ListView` і адаптер з новим списком
        JobOfferAdapter newAdapter = new JobOfferAdapter(CategoryWorkActivity.this, R.layout.job_offer_card, filteredList);
        lvVacancies.setAdapter(newAdapter);
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
                String categoryName = lastClickedMenuItem.getText().toString(); // Отримуємо обрану категорію
                int selectedCategory = getCategoryByTitle(categoryName);

                // Виконуємо пошук пропозицій роботи за ключовими словами та категорією
                List<JobOffer> offers = performJobOfferSearch(keywords, selectedCategory);

                // Оновлюємо `ListView` і адаптер з новим списком
                JobOfferAdapter newAdapter = new JobOfferAdapter(CategoryWorkActivity.this, R.layout.job_offer_card, offers);
                lvVacancies.setAdapter(newAdapter);
            }
        });
    }

    // Функція для виконання пошуку пропозицій роботи за ключовими словами та категорією
    private List<JobOffer> performJobOfferSearch(String keywords, int selectedCategory) {
        DatabaseHelper dbHelper = new DatabaseHelper(CategoryWorkActivity.this);
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
            String keywordsCompany = (String) offerItem.get("keywords");

            int companyCategory = Integer.parseInt(categoryCompany);
            // Перевіряємо, чи відповідає пропозиція роботи обраній категорії
            if (selectedCategory == companyCategory) {
                JobOffer newOffer = new JobOffer(offerId, companyId, companyName, offerName,
                        description, salary, email, phone, requirements, category, categoryCompany, keywordsCompany);
                offers.add(newOffer);
            }
        }

        return offers;
    }



}