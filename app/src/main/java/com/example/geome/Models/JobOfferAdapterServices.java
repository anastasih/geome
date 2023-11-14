package com.example.geome.Models;

import android.content.Context;
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

public class JobOfferAdapterServices extends ArrayAdapter<JobOfferServices> {
    private int resource;
    private Context context;
    private List<JobOfferServices> all_offers;
    private LayoutInflater inflater;
    public JobOfferAdapterServices(@NonNull Context context, int resource, @NonNull List<JobOfferServices> offers) {
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

        JobOfferServices offers = all_offers.get(position);

        String companyLogoName = offers.getIcon();

        if (icon_company != null) {
            Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(companyLogoName, "drawable", context.getPackageName()));
            if (drawable != null) {
                icon_company.setImageDrawable(drawable);
            }
        }
        String offerName = offers.getOffer_name();
        offer_name.setText(offerName);

        String companyAddress = offers.getAddress();
        company_adress.setText(companyAddress);

        String salaryOffer = offers.getSalary();
        //salary.setText(salaryOffer + "\\u20B4");
        salary.setText(salaryOffer + "₴");

        String desc = offers.getOffer_description();
        position_description.setText(desc);

        return item;
    }
}
