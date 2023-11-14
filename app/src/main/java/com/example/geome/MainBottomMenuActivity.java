package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.geome.Models.AppData;
import com.example.geome.Models.FragmentReplacer;
import com.example.geome.Models.User;

public class MainBottomMenuActivity extends AppCompatActivity implements FragmentReplacer {
    private ImageButton ImageButtonMain, ImageButtonRibbon, ImageButtonCity, ImageButtonChat, ImageButtonProfile;
    private FrameLayout fragmentContainer;
    private User user;
    public  int newWidth = 60;
    public int newHeight = 60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom_menu);

        user = AppData.getInstance().getUser();

        ImageButtonMain = findViewById(R.id.ImageButtonMain);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon);
        ImageButtonCity = findViewById(R.id.ImageButtonCity);
        ImageButtonChat = findViewById(R.id.ImageButtonChat);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile);
        fragmentContainer = findViewById(R.id.fragmentContainer);

        String fragmentName = getIntent().getStringExtra("fragmentName");

        if (fragmentName != null) {
            try {
                Class<?> fragmentClass = Class.forName(fragmentName);
                Fragment fragment = (Fragment) fragmentClass.newInstance();
                replaceFragment(fragment);

                if(fragmentName.contains("NewsFeedFragment")){
                    Drawable drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                            getResources().getIdentifier("news_feed_main", "drawable", getPackageName()));
                    drawable.setBounds(0, 0, newWidth, newHeight);
                    ImageButtonRibbon.setImageDrawable(drawable);

                    drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                            getResources().getIdentifier("home_not_main", "drawable", getPackageName()));
                    drawable.setBounds(0, 0, newWidth, newHeight);
                    ImageButtonMain.setImageDrawable(drawable);
                }
                else if(fragmentName.contains("CityFragment")){
                    Drawable drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                            getResources().getIdentifier("city_main", "drawable", getPackageName()));
                    drawable.setBounds(0, 0, newWidth, newHeight);
                    ImageButtonCity.setImageDrawable(drawable);

                    drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                            getResources().getIdentifier("home_not_main", "drawable", getPackageName()));
                    drawable.setBounds(0, 0, newWidth, newHeight);
                    ImageButtonMain.setImageDrawable(drawable);
                }
                else if(fragmentName.contains("ChatFragment")){
                    Drawable drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                            getResources().getIdentifier("chat_main", "drawable", getPackageName()));
                    drawable.setBounds(0, 0, newWidth, newHeight);
                    ImageButtonChat.setImageDrawable(drawable);

                    drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                            getResources().getIdentifier("home_not_main", "drawable", getPackageName()));
                    drawable.setBounds(0, 0, newWidth, newHeight);
                    ImageButtonMain.setImageDrawable(drawable);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        ImageButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainBottomMenuActivity.this, HomePage.class);
                startActivity(intent);
            }
        });
        ImageButtonRibbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("news_feed_main", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonRibbon.setImageDrawable(drawable);

                Drawable drawable2 = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("home_not_main", "drawable", getPackageName()));
                drawable2.setBounds(0, 0, newWidth, newHeight);
                ImageButtonMain.setImageDrawable(drawable2);

                drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("city", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonCity.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("chat", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonChat.setImageDrawable(drawable);

                replaceFragment(new NewsFeedFragment());
            }
        });

        ImageButtonCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("city_main", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonCity.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("home_not_main", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonMain.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("strichka", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonRibbon.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("chat", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonChat.setImageDrawable(drawable);

                replaceFragment(new CityFragment());
            }
        });

        ImageButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("chat_main", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonChat.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("home_not_main", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonMain.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("strichka", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonRibbon.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("city", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonCity.setImageDrawable(drawable);
                replaceFragment(new ChatFragment());
            }
        });

        ImageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("chat", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonChat.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("home_not_main", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonMain.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("strichka", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonRibbon.setImageDrawable(drawable);

                drawable = ContextCompat.getDrawable(MainBottomMenuActivity.this,
                        getResources().getIdentifier("city", "drawable", getPackageName()));
                drawable.setBounds(0, 0, newWidth, newHeight);
                ImageButtonCity.setImageDrawable(drawable);
                replaceFragment(new ProfileFragment());
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
