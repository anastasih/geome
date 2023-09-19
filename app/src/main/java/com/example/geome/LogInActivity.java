package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.User;

public class LogInActivity extends AppCompatActivity {
    public static final String KEY_USER = "androidx.appcompat.app.AppCompatActivity.LogInActivity.user";
    TextView buttonSignUp;
    TextView buttonLogIn;
    EditText phoneEditText;
    EditText passwordEditText;
    User userLog;
    ImageView buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initDate();
        initView();
    }
    private void initDate() {
        userLog = new User();
    }
    private void initView() {
        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonLogIn = findViewById(R.id.buttonLogIn);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        buttonBack = findViewById(R.id.buttonBack);

        buttonSignUp.setOnClickListener(this::openSignUp);
        buttonLogIn.setOnClickListener(this::logIn);
        buttonBack.setOnClickListener(this::returnBack);
        setEditTexes();
    }
    private void returnBack(View view){
        onBackPressed();
    }
    public void logIn(View view){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME,
                null,
                DatabaseHelper.COLUMN_PHONE + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?",
                new String[]{userLog.getUserPhone(), userLog.getUserPassword()},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Знайдено користувача з вказаним номером телефону та паролем
            int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
            int phoneIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE);
            int passwordIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD);
            // інші індекси для інших полів даних

            int userId = cursor.getInt(idIndex);
            String userName = cursor.getString(nameIndex);
            String userPhone = cursor.getString(phoneIndex);
            String userPassword = cursor.getString(passwordIndex);
            // інші поля даних

            // Перевірка правильності паролю
            String enteredPassword = userLog.getUserPassword();
            if (enteredPassword.equals(userPassword)) {
                // Пароль вірний, створення об'єкта User і перехід на сторінку HomePage
                User loggedInUser = new User();
                loggedInUser.setUserName(userName);
                loggedInUser.setUserPhone(userPhone);
                loggedInUser.setUserPassword(userPassword);
                // інші поля даних

                cursor.close();
                db.close();

                // Перехід на сторінку HomePage
                Intent intent = new Intent(this, HomePage.class);
                startActivity(intent);
                finish(); // Закрити поточну активність, щоб не можна було повернутися на сторінку входу
            } else {
                cursor.close();
                db.close();
            }
        } else {
            Toast.makeText(this, "Неправильний номер телефону або пароль", Toast.LENGTH_SHORT).show();
            cursor.close();
            db.close();
        }
    }

    private void setEditTexes(){
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                userLog.setUserPhone("380" + phoneEditText.getText().toString());
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                userLog.setUserPassword(passwordEditText.getText().toString());
            }
        });
    }
    private void openSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}