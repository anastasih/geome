package com.example.geome.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.geome.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckboxSpinnerAdapter extends ArrayAdapter<String> {
    private final LayoutInflater inflater;
    private List<String> selectedItems;

    public CheckboxSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects, LayoutInflater inflater, List<String> selectedItems) {
        super(context, resource, objects);
        this.inflater = inflater;
        this.selectedItems = selectedItems;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(final int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_item_layout, parent, false);

        CheckBox checkBox = row.findViewById(R.id.checkBox);
        TextView textView = row.findViewById(R.id.textView);

        final String selectedItem = getItem(position);

        if (selectedItem != null) {
            textView.setText(selectedItem);

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked && !selectedItems.contains(selectedItem)) {
                    selectedItems.add(selectedItem);
                } else if (!isChecked && selectedItems.contains(selectedItem)) {
                    selectedItems.remove(selectedItem);
                }
            });

            checkBox.setChecked(selectedItems.contains(selectedItem));
        }

        return row;
    }

    public void toggleItem(String item) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item);
        } else {
            selectedItems.add(item);
        }
        notifyDataSetChanged(); // Повідомляємо адаптеру про зміни
    }

    public List<String> returnSelectedItems(){
        return selectedItems;
    }
}