package com.example.geome.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
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

public class JobOfferAdapter extends ArrayAdapter<JobOffer> {

    private int resource;
    private Context context;
    private List<JobOffer> all_offers;
    private LayoutInflater inflater;
    public JobOfferAdapter(@NonNull Context context, int resource, @NonNull List<JobOffer> offers) {
        super(context, resource, offers);
        this.context = context;
        this.resource = resource;
        this.all_offers = offers;
        inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = inflater.inflate(resource, parent, false);

        ImageView icon_company = item.findViewById(R.id.icon_company);
        TextView offer_name = item.findViewById(R.id.position);
        TextView company_adress = item.findViewById(R.id.company_adress);
        TextView salary = item.findViewById(R.id.salary);
        TextView position_description = item.findViewById(R.id.position_description);

        JobOffer offers = all_offers.get(position);

        String companyLogoName = getCompanyLogoName(offers.getId_company());

        if (icon_company != null) {
            Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(companyLogoName, "drawable", context.getPackageName()));
            if (drawable != null) {
                icon_company.setImageDrawable(drawable);
            }
        }
        String offerName = offers.getOffer_name();
        offer_name.setText(offerName);

        String companyAddress = getCompanyAddress(offers.getId_company());
        company_adress.setText(companyAddress);

        String salaryOffer = offers.getSalary();
        //salary.setText(salaryOffer + "\\u20B4");
        salary.setText(salaryOffer + "₴");

        String desc = offers.getOffer_description();
        position_description.setText(desc);

        return item;
    }

    private String getCompanyLogoName(int companyId) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String companyName = dbHelper.getCompanyLogoNameById(companyId);

        return companyName;
    }

    private String getCompanyAddress(int companyId) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        CompanyDetails company = dbHelper.getCompanyDetailsById(companyId);

        String companyAddress = company.getCompanyAddress();

        return companyAddress;
    }
}
