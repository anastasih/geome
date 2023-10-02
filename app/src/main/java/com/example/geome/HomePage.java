package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.LinearLayout;
import android.widget.Toast;



import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.FragmentReplacer;
import com.example.geome.Models.User;




import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    public static final String KEY_USERPROFILE = "androidx.appcompat.app.AppCompatActivity.LogInActivity.user";
    private User user;
    ImageButton ImageButtonMap;
    ImageButton ImageButtonProfile;
    TextView Categories1;
    TextView Categories2;
    TextView Categories3;
    TextView Categories4;
    TextView Categories5;
    ImageButton ImageButtonMain;
    ImageButton ImageButtonRibbon;
    ImageButton ImageButtonCity;
    ImageButton ImageButtonChat;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(LogInActivity.KEY_USERPROFILE);

        ImageButtonMap = findViewById(R.id.ImageButtonMap);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile);
        Categories1 = findViewById(R.id.Categories1);
        Categories2 = findViewById(R.id.Categories2);
        Categories3 = findViewById(R.id.Categories3);
        Categories4 = findViewById(R.id.Categories4);
        Categories5 = findViewById(R.id.Categories5);
        ImageButtonMain = findViewById(R.id.ImageButtonMain);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon);
        ImageButtonCity = findViewById(R.id.ImageButtonCity);
        ImageButtonChat = findViewById(R.id.ImageButtonChat);

//        dbHelper = new DatabaseHelper(this);
//        database = dbHelper.getWritableDatabase();
//
//        // Отримайте ID поточного користувача або іншу інформацію про користувача, яка вам потрібна
//        int userId = getUserId(); // Можливо, вам потрібно коригувати цей метод відповідно до вашої логіки
//
//        if (userId != -1) {
//            // Отримайте список категорій, які обраний користувачем
//            ArrayList<String> selectedCategories = getUserCategories(userId);
//
//            // Отримайте батьківський контейнер для категорій у layout
//            LinearLayout categoryContainer = findViewById(R.id.categoryContainer);
//
//            // Створіть масив ідентифікаторів категорій у layout, ви можете використовувати R.id для кожної категорії
//            int[] categoryIds = {R.id.Categories1, R.id.Categories2, R.id.Categories3, R.id.Categories4};
//            String[] categoryNames = {"Категорія 1", "Категорія 2", "Категорія 3", "Категорія 4"};
//
//            // Перегляньте всі категорії та змініть їх видимість в залежності від вибору користувача
//            for (int i = 0; i < categoryIds.length; i++) {
//                TextView categoryTextView = findViewById(categoryIds[i]);
//                String categoryName = categoryNames[i];
//
//                // Перевірте, чи категорія вибрана користувачем
//                if (selectedCategories.contains(categoryName)) {
//                    // Категорія вибрана - залишаємо її видимою
//                    categoryTextView.setVisibility(View.VISIBLE);
//                } else {
//                    // Категорія не вибрана - робимо її невидимою
//                    categoryTextView.setVisibility(View.GONE);
//                }
//            }
//        } else {
//            // Обробіть випадок, коли не вдалося отримати ID користувача
//            Toast.makeText(this, "Не вдалося отримати ID користувача", Toast.LENGTH_SHORT).show();
//        }
        initView();
    }
//    @SuppressLint("Range")
//    private int getUserId() {
//        // Отримайте ID користувача на основі номера телефону користувача, якщо він авторизований
//        // Ваша реалізація системи реєстрації та авторизації може включати пошук користувача за номером телефону
//        // і повертати його ID.
//
//        // Приклад заглушки:
//        String userPhone = "380152369874"; // Замініть на реальний номер телефону
//        int userId = -1; // Значення за замовчуванням, якщо користувач не авторизований
//
//        String query = "SELECT " + DatabaseHelper.COLUMN_ID +
//                " FROM " + DatabaseHelper.TABLE_NAME +
//                " WHERE " + DatabaseHelper.COLUMN_PHONE + " = ?";
//
//        Cursor cursor = database.rawQuery(query, new String[]{userPhone});
//
//        if (cursor.moveToFirst()) {
//            userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
//        }
//
//        cursor.close();
//        return userId;
//    }
//    private ArrayList<String> getUserCategories(int userId) {
//        // Отримайте список категорій, які обраний користувачем за його ID
//        ArrayList<String> selectedCategories = new ArrayList<>();
//
//        String query = "SELECT " + DatabaseHelper.COLUMN_CATEGORY_NAME +
//                " FROM " + DatabaseHelper.TABLE_USER_CATEGORY +
//                " INNER JOIN " + DatabaseHelper.TABLE_CATEGORIES +
//                " ON " + DatabaseHelper.TABLE_USER_CATEGORY + "." + DatabaseHelper.COLUMN_UC_CATEGORY_ID +
//                " = " + DatabaseHelper.TABLE_CATEGORIES + "." + DatabaseHelper.COLUMN_CATEGORY_ID +
//                " WHERE " + DatabaseHelper.TABLE_USER_CATEGORY + "." + DatabaseHelper.COLUMN_UC_USER_ID +
//                " = " + userId;
//
//        Cursor cursor = database.rawQuery(query, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                @SuppressLint("Range") String categoryName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME));
//                selectedCategories.add(categoryName);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return selectedCategories;
//    }

    private void initView() {
        ImageButtonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Перехід до іншої Activity, наприклад, FirstPage.class
                Intent intent = new Intent(HomePage.this, FirstPage.class);
                startActivity(intent);
            }
        });

        ImageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Код для виконання при натисканні на button1
                // Наприклад, перехід до MainActivity
                Intent intent = new Intent(HomePage.this, FirstPage.class);
                startActivity(intent);
            }
        });

        Categories1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Код для виконання при натисканні на button1
                // Наприклад, перехід до MainActivity
                Intent intent = new Intent(HomePage.this, FirstPage.class);
                startActivity(intent);
            }
        });

        Categories2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Код для виконання при натисканні на button1
                // Наприклад, перехід до MainActivity
                Intent intent = new Intent(HomePage.this, FirstPage.class);
                startActivity(intent);
            }
        });

        Categories3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Код для виконання при натисканні на button1
                // Наприклад, перехід до MainActivity
                Intent intent = new Intent(HomePage.this, FirstPage.class);
                startActivity(intent);
            }
        });

        Categories4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Код для виконання при натисканні на button1
                // Наприклад, перехід до MainActivity
                Intent intent = new Intent(HomePage.this, FirstPage.class);
                startActivity(intent);
            }
        });
        Categories5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Код для виконання при натисканні на button1
                // Наприклад, перехід до MainActivity
                Intent intent = new Intent(HomePage.this, FirstPage.class);
                startActivity(intent);
            }
        });

        ImageButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Код для виконання при натисканні на button1
                // Наприклад, перехід до MainActivity
                Intent intent = new Intent(HomePage.this, HomePage.class);
                startActivity(intent);
            }
        });

        ImageButtonRibbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", NewsFeedFragment.class.getName());
                intent.putExtra(KEY_USERPROFILE, user);
                startActivity(intent);
            }
        });

        ImageButtonCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(HomePage.this, FirstPage.class);
//                startActivity(intent);

                Intent intent = new Intent(HomePage.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", CityFragment.class.getName());
                intent.putExtra(KEY_USERPROFILE, user);
                startActivity(intent);
            }
        });

        ImageButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(HomePage.this, FirstPage.class);
//                startActivity(intent);

//                Intent intent = new Intent(HomePage.this, MainBottomMenuActivity.class);
//                intent.putExtra("fragmentName", ChatFragment.class.getName());
//                intent.putExtra(KEY_USERPROFILE, user);
//                startActivity(intent);

                Intent intent = new Intent(HomePage.this, MainBottomMenuActivity.class);
                intent.putExtra("fragmentName", ChatFragment.class.getName());
                intent.putExtra(LogInActivity.KEY_USERPROFILE, user);
                startActivity(intent);
            }
        });
    }
}
