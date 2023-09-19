package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.ArrayAdapter;

import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.User;


public class SignUpActivity extends AppCompatActivity {
    public static final String KEY_USER = "androidx.appcompat.app.AppCompatActivity.SignUpActivity.user";
    private User user;
    private TextView buttonNext;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private Spinner spinnerCity;
    private CheckBox checkboxLocation;
    private CheckBox checkboxPrivatePolicy;
    private ImageView seePassword;
    private ImageView buttonBack;
    private TextView buttonLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initDate();
        initView();
    }
    private void initDate() {
        user = new User();
    }
    private void initView() {
        buttonNext = findViewById(R.id.buttonNext);
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        spinnerCity = findViewById(R.id.spinnerCity);
        checkboxLocation = findViewById(R.id.checkboxLocation);
        checkboxPrivatePolicy = findViewById(R.id.checkboxPrivatePolicy);
        seePassword = findViewById(R.id.password_see);
        buttonBack = findViewById(R.id.buttonBack);
        buttonLogIn = findViewById(R.id.buttonLogIn);

        setEditTexes();
        setCheckboxes();
        setSpinner();

        buttonNext.setOnClickListener(this::openNext);
        seePassword.setOnClickListener(this::toSeePassword);
        buttonBack.setOnClickListener(this::returnBack);
        buttonLogIn.setOnClickListener(this::logIn);
    }

    private void returnBack(View view){
        onBackPressed();
    }
    private void setEditTexes(){
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                user.setUserName(nameEditText.getText().toString());
            }
        });

        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                user.setUserPhone("380" + phoneEditText.getText().toString());
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                user.setUserPassword(passwordEditText.getText().toString());
            }
        });
    }
    private void setCheckboxes(){
        user.setAccessGeo(true);
        checkboxLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user.setAccessGeo(true);
                } else {
                    user.setAccessGeo(false);
                }
            }
        });
        user.setPrivatePolicy(true);
        checkboxPrivatePolicy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user.setPrivatePolicy(true);
                } else {
                    user.setPrivatePolicy(false);
                }
            }
        });
    }
    private void setSpinner(){
        String[] cities = {"Житомир", "Київ", "Львів", "Харків", "Одеса", "Дніпро"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter);
        int selectedCityIndex = 0;
        spinnerCity.setSelection(selectedCityIndex);
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getItemAtPosition(position).toString();
                user.setUserCity(selectedValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Обробка відсутності вибору
            }
        });
    }
    private void toSeePassword(View view){
        if (passwordEditText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
    private void openNext(View view) {
        if(user.getUserPassword().length() < 8){
            Toast.makeText(this, "Пароль повинен мати не менше 8 символів!", Toast.LENGTH_SHORT).show();
        }
        else if(!user.getUserPhone().matches("\\d{12}")){
            Toast.makeText(this, "Неправильний формат мобільного телефону!", Toast.LENGTH_SHORT).show();
        }
        else if(user.getUserName().length() == 0){
            Toast.makeText(this, "Введіть і'мя та прізвище!", Toast.LENGTH_SHORT).show();
        }
        else{
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query(DatabaseHelper.TABLE_NAME,
                    new String[]{DatabaseHelper.COLUMN_PHONE},
                    DatabaseHelper.COLUMN_PHONE + " = ?",
                    new String[]{user.getUserPhone()},
                    null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                Toast.makeText(this, "Користувач з таким номером вже існує!", Toast.LENGTH_SHORT).show();
                cursor.close();
            } else {
                cursor.close();
                db.close();
                Intent intent = new Intent(this, SignUpActivitySecond.class);
                intent.putExtra(KEY_USER, user);
                startActivity(intent);
            }
        }
    }
    private void logIn(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}