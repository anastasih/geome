package com.example.geome.Models;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.geome.R;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    private ViewPager slideViewPager;
    int images[] = {
            R.drawable.slider1,
            R.drawable.slider2,
            R.drawable.slider3,
            R.drawable.slider4,
            R.drawable.slider5,
            R.drawable.slider6,
    };

    int headings[] = {
            R.string.heading_one,
            R.string.heading_two,
            R.string.heading_three,
            R.string.heading_four,
            R.string.heading_five,
            R.string.heading_six,
    };

    int description[] = {
            R.string.description_one,
            R.string.description_two,
            R.string.description_three,
            R.string.description_four,
            R.string.description_five,
            R.string.description_six,
    };

    public ViewPagerAdapter(Context context, ViewPager viewPager){
        this.context = context;
        this.slideViewPager = viewPager;
    }

    @Override
    public int getCount() {
        return  headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();

        int screenHeightInDp = 0;

        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);

            // Отримуємо кофіцієнт масштабу екрана (dp to pixel ratio)
            float density = context.getResources().getDisplayMetrics().density;

            // Переконвертовуємо висоту в dp
            int screenHeightInPixels = displayMetrics.heightPixels;
            screenHeightInDp = (int) (screenHeightInPixels / density);

            //return screenHeightInDp;
        }


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView slidetitleimage = (ImageView) view.findViewById(R.id.slider);
        TextView slideHeading = (TextView) view.findViewById(R.id.textSlider);
        TextView slideDesciption = (TextView) view.findViewById(R.id.descriptionSlider);

        slidetitleimage.setImageResource(images[position]);
        slideHeading.setText(headings[position]);
        slideDesciption.setText(description[position]);

        ImageView buttonNext = view.findViewById(R.id.buttonNext);
        TextView knewMore = view.findViewById(R.id.knewMore);

        //knewMore.setText(String.valueOf(screenHeightInDp));

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextSlide = slideViewPager.getCurrentItem() + 1;
                if (nextSlide < getCount()) {
                    slideViewPager.setCurrentItem(nextSlide);
                }
            }
        });

        if (position == getCount() - 1) {
            buttonNext.setVisibility(View.INVISIBLE);
            knewMore.setVisibility(View.VISIBLE);
        } else {
            buttonNext.setVisibility(View.VISIBLE);
            knewMore.setVisibility(View.INVISIBLE);
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
