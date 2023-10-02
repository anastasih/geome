package com.example.geome;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.NewFeedAdapter;
import com.example.geome.Models.NewsCard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NewsFeedFragment extends Fragment {
    private ListView lvNews;
    private EditText search_news;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);

        lvNews = rootView.findViewById(R.id.lvNews);
        search_news = rootView.findViewById(R.id.search_news);

        setEditText();

        List<NewsCard> cardItemList = getCardItemsFromDatabase();
        NewFeedAdapter adapter = new NewFeedAdapter(getActivity(), R.layout.news_card, cardItemList);
        lvNews.setAdapter(adapter);

        return rootView;
    }
    public void setEditText() {
        search_news.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keywords = search_news.getText().toString();
                DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                List<Map<String, Object>> searchResults = dbHelper.searchNewsByKeywords(keywords);

                List<NewsCard> newsCards = new ArrayList<>();

                for (Map<String, Object> newsItem : searchResults) {
                    int newsId = (int) newsItem.get("newsId");
                    int companyId = (int) newsItem.get("companyId");
                    String newsPhoto = (String) newsItem.get("newsPhoto");
                    String description = (String) newsItem.get("description");
                    String companyName = (String) newsItem.get("companyName");
                    Date date = (Date) newsItem.get("date");

                    NewsCard newsCard = new NewsCard(newsId, companyId, newsPhoto, description, date);
                    newsCards.add(newsCard);
                }

                NewFeedAdapter searchAdapter = new NewFeedAdapter(getContext(), R.layout.news_card, newsCards);
                lvNews.setAdapter(searchAdapter);
            }
        });
    }

    private List<NewsCard> getCardItemsFromDatabase() {
        List<NewsCard> cardItemList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
            db = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_NEWS_FEED;
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    int companyId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_COMPANY));
                    String photo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NEWS_FEED_PHOTO));
                    String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NEWS_FEED_DESCRIPTION));

                    String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NEWS_FEED_TIME));
                    String dateFormatPattern = "yyyy-MM-dd HH:mm:ss";
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.US);
                        Date date = dateFormat.parse(time);
                        NewsCard newsCard = new NewsCard(companyId, photo, description, date);
                        cardItemList.add(newsCard);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return cardItemList;
    }
}