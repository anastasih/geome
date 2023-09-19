package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.Models.ViewPagerAdapter;

public class IntroductionActivity extends AppCompatActivity {

    ViewPager slideViewPager;
    ViewPagerAdapter adapter;
    LinearLayout mDotLayout;
    TextView[] dots;
    TextView buttonMiss;
    ImageView buttonBack;
    ImageView imageBackgroud;
    private int currentSlide = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        slideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicator_layout);
        buttonMiss = findViewById(R.id.buttonMiss);
        buttonBack = findViewById(R.id.buttonBack);
        imageBackgroud = findViewById(R.id.imageBackgroud);

        adapter = new ViewPagerAdapter(this, slideViewPager);
        slideViewPager.setAdapter(adapter);
        setUpindicator(0);
        slideViewPager.addOnPageChangeListener(viewListener);

        buttonMiss.setOnClickListener(this::missActivity);
        buttonBack.setOnClickListener(this::returnBack);
        buttonBack.setVisibility(View.INVISIBLE);
    }

    private void buttonCheck(){
        if (currentSlide == 0) {
            buttonBack.setVisibility(View.INVISIBLE);
        }
        else{
            buttonBack.setVisibility(View.VISIBLE);
        }
    }
    private void returnBack(View view){
        if (currentSlide > 0) {
            currentSlide--;
            slideViewPager.setCurrentItem(currentSlide);
        }
    }
    private void missActivity(View view) {
        Intent intent = new Intent(this, FirstPage.class);
        startActivity(intent);
    }
    public void setUpindicator(int position){
        dots = new TextView[6];
        mDotLayout.removeAllViews();
        for (int i = 0 ; i < dots.length ; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(0xFFE7EEFA);
            mDotLayout.addView(dots[i]);
        }
        dots[position].setTextColor(0xFF0054D2);

        currentSlide = position;
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);
            buttonCheck();

            if(position > 4){
                imageBackgroud.setImageResource(R.drawable.rectangle2_introduction2);
                buttonMiss.setText("Розпочати");
                buttonMiss.setTextColor(0xFFFFFFFF);
            }
            else{
                imageBackgroud.setImageResource(R.drawable.rectangle2_introduction);
                buttonMiss.setText("Пропустити");
                buttonMiss.setTextColor(0xFF0054D2);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };
}