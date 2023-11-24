package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.geome.Models.AppData;
import com.example.geome.Models.Booking;
import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.Payment;
import com.example.geome.Models.PaymentHistoryAdapter;
import com.example.geome.Models.User;

import java.util.List;

public class PaymentHistoryActivity extends AppCompatActivity {
    private ImageButton ImageButtonMain, ImageButtonRibbon, ImageButtonCity, ImageButtonChat,
            ImageButtonProfile;
    private User user;
    public ImageView buttonBack;
    public ListView lvHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        initDate();
        initView();
    }

    private void initDate(){
        user = AppData.getInstance().getUser();
    }

    public void initView(){
        ImageButtonMain = findViewById(R.id.ImageButtonMain);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon);
        ImageButtonCity = findViewById(R.id.ImageButtonCity);
        ImageButtonChat = findViewById(R.id.ImageButtonChat);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile);
        buttonBack = findViewById(R.id.buttonBack);
        lvHistory = findViewById(R.id.lvHistory);

        buttonBack.setOnClickListener(this::buttonBackClick);
        ImageButtonMain.setOnClickListener(this::ImageButtonMainClick);
        ImageButtonRibbon.setOnClickListener(this::ImageButtonRibbonClick);
        ImageButtonCity.setOnClickListener(this::ImageButtonCityClick);
        ImageButtonChat.setOnClickListener(this::ImageButtonChatClick);
        ImageButtonProfile.setOnClickListener(this::ImageButtonProfileClick);


        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        int userId = databaseHelper.getUserId(user.getUserPhone());
        List<Payment> cardItemList = databaseHelper.getPaymentsByUserId(userId);
        PaymentHistoryAdapter adapter = new PaymentHistoryAdapter(this, R.layout.payment_card, cardItemList);
        lvHistory.setAdapter(adapter);
    }

    private void ImageButtonProfileClick(View view) {
        Intent intent = new Intent(PaymentHistoryActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ProfileFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonChatClick(View view) {
        Intent intent = new Intent(PaymentHistoryActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ChatFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonCityClick(View view) {
        Intent intent = new Intent(PaymentHistoryActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", CityFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonRibbonClick(View view) {
        Intent intent = new Intent(PaymentHistoryActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", NewsFeedFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonMainClick(View view) {
        Intent intent = new Intent(PaymentHistoryActivity.this, HomePage.class);
        startActivity(intent);
    }
    private void buttonBackClick(View view) {
        Intent intent = new Intent(PaymentHistoryActivity.this, HomePage.class);
        startActivity(intent);
    }
}