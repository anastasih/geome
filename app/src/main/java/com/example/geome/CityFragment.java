package com.example.geome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.geome.Models.AppData;
import com.example.geome.Models.Company;
import com.example.geome.Models.CompanyDetails;
import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CityFragment extends Fragment {
    public DatabaseHelper dbHelper;
    private User user;
    public TextView empty;
    //public TextView empty2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_city, container, false);
        View rootView = inflater.inflate(R.layout.fragment_city, container, false);

//        dbHelper = new DatabaseHelper(getActivity());
//        user = AppData.getInstance().getUser();
//        int idCity = dbHelper.getCityForUser(user.getUserPhone());
//        List<Company> companies = dbHelper.getCompaniesByCityId(idCity);
//        empty = rootView.findViewById(R.id.empty);
//        //empty2 = rootView.findViewById(R.id.empty2);
//        List<Company> filteredCompanies = getCompaniesByCategoryAndCity(idCity, 6);
//        for (Company com: filteredCompanies) {
//            empty.setText(empty.getText() + com.companyName);
//            //CompanyDetails companiesDetails = dbHelper.getCompanyDetailsById(com.companyId);
//            //empty2.setText(empty2.getText() + companiesDetails.companyAddress);
//        }


        return rootView;
    }

    public List<Company> getCompaniesByCategoryAndCity(int idCity, int idCategory) {
        List<Company> allCompanies = dbHelper.getCompaniesByCityId(idCity);
        List<Company> filteredCompanies = new ArrayList<>();

        for (Company company : allCompanies) {
            int companyCategoryId = Integer.parseInt(company.getIdCategory());
            if (companyCategoryId == idCategory) {
                filteredCompanies.add(company);
            }
        }

        return filteredCompanies;
    }

}