package com.example.geome.Models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.geome.R;

import java.util.List;

public class CompanyListAdapter extends ArrayAdapter<Company> {
    private int resource;
    private Context context;
    private List<Company> companies;

    private LayoutInflater inflater;

    public CompanyListAdapter(@NonNull Context context, int resource, @NonNull List<Company> companies) {
        super(context, resource, companies);
        this.context = context;
        this.resource = resource;
        this.companies = companies;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//        if (view == null) {
//            LayoutInflater inflater = LayoutInflater.from(context);
//            view = inflater.inflate(R.layout.delivery_card, null);
//        }
        //View view = inflater.inflate(resource, parent, false);

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(resource, parent, false);
        }

        ImageView photo_card = view.findViewById(R.id.photo_card);
        ImageButton Companyphoto = view.findViewById(R.id.ImageButtonCompanyphoto);
        TextView company_name = view.findViewById(R.id.company_name);
        TextView company_address = view.findViewById(R.id.company_address);
        TextView company_description = view.findViewById(R.id.company_description);
        RatingBar companyRating = view.findViewById(R.id.company_rating);
        TextView Rating = view.findViewById(R.id.rating);

        Company company = companies.get(position);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        CompanyDetails companyDetails = databaseHelper.getCompanyDetailsById(company.getCompanyId());
        Log.d("Тег", "companyDetails.getCompanyPhoto() = " + companyDetails.getCompanyPhoto());
        String photo = companyDetails.getCompanyPhoto();

        if (photo != null) {
            Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(photo, "drawable", context.getPackageName()));
            if (drawable != null) {
                Log.d("Тег", "drawable = " + drawable);
                photo_card.setImageDrawable(drawable);
            }
        }

//        if (ImageButtonCompanyphoto != null) {
//            int drawableId = context.getResources().getIdentifier(company.getCompanyPhoto(), "drawable", context.getPackageName());
//            if (drawableId != 0) { // Перевірте, чи ідентифікатор знайдено (не дорівнює 0)
//                ImageButtonCompanyphoto.setImageResource(drawableId);
//            }
//        }

        Log.d("Тег", "company.getCompanyPhoto() = " + company.getCompanyPhoto());

        if (Companyphoto != null) {
            Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(company.getCompanyPhoto(), "drawable", context.getPackageName()));
            if (drawable != null) {
                Companyphoto.setImageDrawable(drawable);
            }
        }
        company_name.setText(company.getCompanyName());
        company_address.setText(companyDetails.getCompanyAddress());
        company_description.setText(company.getCompanyDescription());

        Reviews reviews = databaseHelper.getRatingCompanyByCompanyId(company.getCompanyId());
        double weightLocation = 0.25;
        double weightService = 0.25;
        double weightAvailability = 0.25;
        double weightComfort = 0.25;

        // Обчислення загального рейтингу
        double totalRating = (reviews.getLocation() * weightLocation) + ( reviews.getService()* weightService) + (reviews.getAvailability() * weightAvailability) + (reviews.getComfort() * weightComfort);

        try {
            //double companyRatingValue = Double.parseDouble(company.getCompanyRating());
            companyRating.setRating((float) totalRating);
        } catch (NumberFormatException e) {
            // Обробка помилки, якщо не вдається перетворити рядок у число
            companyRating.setRating(0.0f); // Можливо, ви хочете задати значення за замовчуванням
        }
        Rating.setText(String.valueOf(totalRating));

        return view;
    }
}
