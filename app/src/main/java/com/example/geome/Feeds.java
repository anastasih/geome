package com.example.geome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.NewFeedAdapter;
import com.example.geome.Models.NewsCard;

import java.util.List;

public class Feeds extends Fragment {
    private ListView lvNews;
    public int Id;
    public Feeds(int Id){
        this.Id = Id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feeds, container, false);
        lvNews = rootView.findViewById(R.id.lvNews);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
//        Bundle arguments = getArguments();
//        String value = "0";
//        if (arguments != null) {
//            value = arguments.getString("IdCompany");
//        }
//        int idCompany = Integer.parseInt(value);
        List<NewsCard> cardItemList = databaseHelper.getNewsByCompanyId(Id);
        NewFeedAdapter adapter = new NewFeedAdapter(getActivity(), R.layout.news_card, cardItemList);
        lvNews.setAdapter(adapter);
//        initDate();
//        initView(rootView);
        return rootView;
    }
}