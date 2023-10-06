package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.User;

public class SignUpActivityThird extends AppCompatActivity {
    TextView buttonFinishRegistration;
    CheckBox checkboxAgreement;
    private User user;
    ImageView buttonBack;
    TextView buttonLogIn;
    private CheckBox checkboxResidence;
    private CheckBox checkboxRest;
    private CheckBox checkboxBeauty;
    private CheckBox checkboxFood;
    private CheckBox checkboxDelivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_third);

        user = new User();
        initView();
    }
    private void initView() {
        Intent intent = getIntent();
        user = (User)getIntent()
                .getSerializableExtra(SignUpActivity.KEY_USER);

        buttonFinishRegistration = findViewById(R.id.buttonFinishRegistration);
        checkboxAgreement = findViewById(R.id.checkboxAgreement);
        buttonBack = findViewById(R.id.buttonBack);
        buttonLogIn = findViewById(R.id.buttonLogIn);
        checkboxResidence = findViewById(R.id.checkboxResidence);
        checkboxRest = findViewById(R.id.checkboxRest);
        checkboxBeauty = findViewById(R.id.checkboxBeauty);
        checkboxFood = findViewById(R.id.checkboxFood);
        checkboxDelivery = findViewById(R.id.checkboxDelivery);

        buttonFinishRegistration.setOnClickListener(this::openNext);
        buttonBack.setOnClickListener(this::returnBack);
        buttonLogIn.setOnClickListener(this::logIn);

        setCheckbox();

        // Обробка подій для вибору категорій
        checkboxResidence.setOnCheckedChangeListener((buttonView, isChecked) -> updateCategories("Проживання", isChecked));
        checkboxRest.setOnCheckedChangeListener((buttonView, isChecked) -> updateCategories("Відпочинок та розваги", isChecked));
        checkboxBeauty.setOnCheckedChangeListener((buttonView, isChecked) -> updateCategories("Послуги краси", isChecked));
        checkboxFood.setOnCheckedChangeListener((buttonView, isChecked) -> updateCategories("Заклади харчування", isChecked));
        checkboxDelivery.setOnCheckedChangeListener((buttonView, isChecked) -> updateCategories("Доставка продуктів", isChecked));
    }

    // Оновлює список категорій користувача
    private void updateCategories(String category, boolean isChecked) {
        if (isChecked) {
            // Додати категорію до списку
            if (!user.getUserCategories().contains(category)) {
                user.getUserCategories().add(category);
            }
        } else {
            // Видалити категорію зі списку
            user.getUserCategories().remove(category);
        }
    }
    private void setCheckbox(){
        user.setNotificationPromotions(true);
        checkboxAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user.setNotificationPromotions(true);
                } else {
                    user.setNotificationPromotions(false);
                }
            }
        });
    }
    private void returnBack(View view){
        onBackPressed();
    }
    private void openNext(View view) {
        if(user.getUserCategories().size() == 0){
            Toast.makeText(this, "Categories are empty!", Toast.LENGTH_SHORT).show();
        }
        else{
            DatabaseHelper db = new DatabaseHelper(SignUpActivityThird.this);
            long result = db.addUser(user.getUserName(), user.getUserPhone(), user.getUserPassword(),
                    user.getUserCity(), user.getGender(), user.getAge(), user.isAccessGeo(),
                    user.isPrivatePolicy(), user.isUserOffers(),
                    user.isNotificationPromotions());
            if (result == -1) {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                int userId = db.getUserId(user.getUserPhone());
                //Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();

                for (String category : user.getUserCategories()) {
                    int categoryId = db.getCategoryId(category);
                    db.addUserCategory(userId, categoryId);
                }
            }

            Intent intent = new Intent(this, SignUpActivityFinish.class);
            startActivity(intent);
        }
    }
    private void logIn(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}