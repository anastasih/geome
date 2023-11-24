package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.Models.AppData;
import com.example.geome.Models.Booking;
import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.Payment;
import com.example.geome.Models.PaymentHistoryAdapter;
import com.example.geome.Models.RoomsAdapter;
import com.example.geome.Models.User;
import com.example.geome.Models.Reviews;

import java.util.List;

public class MakeReviewActivity extends AppCompatActivity {
    private ImageButton ImageButtonMain, ImageButtonRibbon, ImageButtonCity, ImageButtonChat,
            ImageButtonProfile;
    private User user;
    public ImageView buttonBack;
    private RatingBar ratingBarLocation, ratingBarComfort, ratingBarAccessibility, ratingBarService;
    private EditText editTextComment;
    public TextView buttonMakeReview;
    public Reviews review;
    public Booking booking;
    public int idCompany;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_review);

        initDate();
        initView();
    }

    private void initDate(){
        user = AppData.getInstance().getUser();
        Intent intent = getIntent();
        booking = (Booking) intent.getSerializableExtra(RoomsAdapter.KEY_BOOKING);
        idCompany = (Integer) intent.getSerializableExtra(RoomsAdapter.KEY_ID_COMPANY);
    }

    public void initView(){
        ImageButtonMain = findViewById(R.id.ImageButtonMain);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon);
        ImageButtonCity = findViewById(R.id.ImageButtonCity);
        ImageButtonChat = findViewById(R.id.ImageButtonChat);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile);
        buttonBack = findViewById(R.id.buttonBack);
        ratingBarLocation = findViewById(R.id.ratingBarLocation);
        ratingBarComfort = findViewById(R.id.ratingBarComfort);
        ratingBarAccessibility = findViewById(R.id.ratingBarAccessibility);
        ratingBarService = findViewById(R.id.ratingBarService);
        editTextComment = findViewById(R.id.editTextComment);
        buttonMakeReview = findViewById(R.id.buttonMakeReview);

        buttonBack.setOnClickListener(this::buttonBackClick);
        ImageButtonMain.setOnClickListener(this::ImageButtonMainClick);
        ImageButtonRibbon.setOnClickListener(this::ImageButtonRibbonClick);
        ImageButtonCity.setOnClickListener(this::ImageButtonCityClick);
        ImageButtonChat.setOnClickListener(this::ImageButtonChatClick);
        ImageButtonProfile.setOnClickListener(this::ImageButtonProfileClick);
        buttonMakeReview.setOnClickListener(this::buttonMakeReviewClick);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        int userId = databaseHelper.getUserId(user.getUserPhone());
        review.setUserId(userId);
//        List<Payment> cardItemList = databaseHelper.getPaymentsByUserId(userId);
//        PaymentHistoryAdapter adapter = new PaymentHistoryAdapter(this, R.layout.payment_card, cardItemList);
//        lvHistory.setAdapter(adapter);

        ratingBarLocation.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                review.setLocation(rating);
            }
        });
        ratingBarComfort.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                review.setComfort(rating);
            }
        });
        ratingBarAccessibility.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                review.setAvailability(rating);
            }
        });
        ratingBarService.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                review.setService(rating);
            }
        });
        editTextComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                review.setUserComment(editTextComment.getText().toString());
            }
        });
    }
    private void buttonMakeReviewClick(View view) {
        DatabaseHelper databaseHelper = new DatabaseHelper(MakeReviewActivity.this);
        long result = databaseHelper.addReview(idCompany, review.getLocation(), review.getService(),
                review.getAvailability(), review.getComfort(), review.getUserComment(),
                review.getUserId(), review.getBookingId());
        if (result == -1) {
            Toast.makeText(MakeReviewActivity.this, "Failed", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(MakeReviewActivity.this, "Add", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MakeReviewActivity.this, ServiceHistoryActivity.class);
            startActivity(intent);
        }
    }
    private void ImageButtonProfileClick(View view) {
        Intent intent = new Intent(MakeReviewActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ProfileFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonChatClick(View view) {
        Intent intent = new Intent(MakeReviewActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ChatFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonCityClick(View view) {
        Intent intent = new Intent(MakeReviewActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", CityFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonRibbonClick(View view) {
        Intent intent = new Intent(MakeReviewActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", NewsFeedFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonMainClick(View view) {
        Intent intent = new Intent(MakeReviewActivity.this, HomePage.class);
        startActivity(intent);
    }
    private void buttonBackClick(View view) {
        Intent intent = new Intent(MakeReviewActivity.this, HomePage.class);
        startActivity(intent);
    }
}