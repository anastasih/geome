package com.example.geome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.NewFeedAdapter;
import com.example.geome.Models.NewsCard;

import java.text.DecimalFormat;
import java.util.List;

public class Reviews extends Fragment {

    public TextView ratingValueTextView;
    public RatingBar company_rating_main;
    public ProgressBar ProgressBarBar1;
    public ProgressBar ProgressBarBar2;
    public ProgressBar ProgressBarBar3;
    public ProgressBar ProgressBarBar4;
    public int Id;
    public Reviews(int Id){
        this.Id = Id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);

        ratingValueTextView = rootView.findViewById(R.id.ratingValueTextView);
        company_rating_main = rootView.findViewById(R.id.company_rating_main);
        ProgressBarBar1 = rootView.findViewById(R.id.ProgressBarBar1);
        ProgressBarBar2 = rootView.findViewById(R.id.ProgressBarBar2);
        ProgressBarBar3 = rootView.findViewById(R.id.ProgressBarBar3);
        ProgressBarBar4 = rootView.findViewById(R.id.ProgressBarBar4);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        com.example.geome.Models.Reviews reviews = databaseHelper.getRatingCompanyByCompanyId(Id);
        double weightLocation = 0.25;
        double weightService = 0.25;
        double weightAvailability = 0.25;
        double weightComfort = 0.25;

        // Обчислення загального рейтингу
        double totalRating = (reviews.getLocation() * weightLocation) + ( reviews.getService()* weightService) + (reviews.getAvailability() * weightAvailability) + (reviews.getComfort() * weightComfort);
        DecimalFormat df = new DecimalFormat("#.#");

        // Форматування числа
        String formattedValue = df.format(totalRating);
        ratingValueTextView.setText(formattedValue);
        try {
            //double companyRatingValue = Double.parseDouble(totalRating);
            company_rating_main.setRating((float) totalRating);
        } catch (NumberFormatException e) {
            // Обробка помилки, якщо не вдається перетворити рядок у число
            company_rating_main.setRating(0.0f); // Можливо, ви хочете задати значення за замовчуванням
        }

        int intValue1 = (int) reviews.getLocation();
        ProgressBarBar1.setProgress(intValue1);

        int intValue2 = (int) reviews.getService();
        ProgressBarBar2.setProgress(intValue2);

        int intValue3 = (int) reviews.getAvailability();
        ProgressBarBar3.setProgress(intValue3);

        int intValue4 = (int) reviews.getComfort();
        ProgressBarBar4.setProgress(intValue4);
        return rootView;
    }
}