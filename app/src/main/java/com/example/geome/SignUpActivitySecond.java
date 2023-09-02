package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.Models.User;

public class SignUpActivitySecond extends AppCompatActivity {
    TextView buttonSecondNext;
    TextView buttonFemale;
    TextView buttonMale;
    EditText ageEditText;
    CheckBox checkboxInfo;
    private User user;
    ImageView buttonBack;
    TextView buttonLogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_second);

        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(SignUpActivity.KEY_USER);

        buttonSecondNext = findViewById(R.id.buttonSecondNext);
        buttonFemale = findViewById(R.id.buttonFemale);
        buttonMale = findViewById(R.id.buttonMale);
        ageEditText = findViewById(R.id.ageEditText);
        checkboxInfo = findViewById(R.id.checkboxInfo);
        buttonBack = findViewById(R.id.buttonBack);
        buttonLogIn = findViewById(R.id.buttonLogIn);

        setEditText();
        setCheckbox();

        buttonSecondNext.setOnClickListener(this::openNext);
        buttonFemale.setOnClickListener(this::setFemale);
        buttonMale.setOnClickListener(this::setMale);
        buttonBack.setOnClickListener(this::returnBack);
        buttonLogIn.setOnClickListener(this::logIn);
    }
    private void setCheckbox(){
        user.setUserOffers(true);
        checkboxInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user.setUserOffers(true);
                } else {
                    user.setUserOffers(false);
                }
            }
        });
    }
    private void setEditText(){
        ageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String ageString = ageEditText.getText().toString();
                    int userAge = Integer.parseInt(ageString);
                    if (userAge <= 0 || userAge > 120) {
                        Toast.makeText(SignUpActivitySecond.this, "Неправильний діапазон віку", Toast.LENGTH_SHORT).show();
                    } else {
                        user.setAge(userAge);
                    }
                }
                catch (Exception ex){}
            }
        });
    }
    private void returnBack(View view){
        onBackPressed();
    }
    private void openNext(View view) {
        if(user.getGender() == null){
            Toast.makeText(this, "Gender is not checked", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(this, SignUpActivityThird.class);
            intent.putExtra(SignUpActivity.KEY_USER, user);//ctrl + alc + C
            startActivity(intent);
        }
    }
    private void setFemale(View view){
        user.setGender("female");
    }
    private void setMale(View view){
        user.setGender("male");
    }
    private void logIn(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}