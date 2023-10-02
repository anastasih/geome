package com.example.geome.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.geome.R;

import java.util.List;

public class NewFeedAdapter extends ArrayAdapter<NewsCard> {

    private int resource;
    private Context context;
    private List<NewsCard> all_news;
    private LayoutInflater inflater;

    public NewFeedAdapter(@NonNull Context context, int resource, @NonNull List<NewsCard> news) {
        super(context, resource, news);
        this.context = context;
        this.resource = resource;
        this.all_news = news;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = inflater.inflate(resource, parent, false);

        ImageView icon_news = item.findViewById(R.id.icon_news);
        TextView company_name_news = item.findViewById(R.id.company_name_news);
        TextView time_publication_news = item.findViewById(R.id.time_publication_news);
        ImageView photo_news = item.findViewById(R.id.photo_news);
        TextView news_description = item.findViewById(R.id.news_description);

        NewsCard news = all_news.get(position);

        String companyLogoName = getCompanyLogoName(news.getId_company());

        if (icon_news != null) {
            Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(companyLogoName, "drawable", context.getPackageName()));
            if (drawable != null) {
                icon_news.setImageDrawable(drawable);
            }
        }

        String companyName = getCompanyName(news.getId_company());
        company_name_news.setText(companyName);

        time_publication_news.setText(String.valueOf(news.getPublication_time()));

        String imageName = news.getImage();
        if (imageName.length() != 0) {
            Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(imageName, "drawable", context.getPackageName()));
            photo_news.setImageDrawable(drawable);
            photo_news.setVisibility(View.VISIBLE);
        } else {
            photo_news.setVisibility(View.GONE);
        }

        if (news_description != null) {
            news_description.setText(news.getDescription());
        }


        return item;
    }
    private String getCompanyName(int companyId) {
         DatabaseHelper dbHelper = new DatabaseHelper(getContext());
         SQLiteDatabase db = dbHelper.getReadableDatabase();
         String companyName = dbHelper.getCompanyNameById(companyId);

         return companyName;
    }
    private String getCompanyLogoName(int companyId) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String companyName = dbHelper.getCompanyLogoNameById(companyId);

        return companyName;
    }
}
