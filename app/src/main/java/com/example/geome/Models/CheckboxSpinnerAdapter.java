package com.example.geome.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.geome.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckboxSpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> items;
    private boolean[] checkedItems;
    private OnCheckedChangeListener onCheckedChangeListener;

    public CheckboxSpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
        this.checkedItems = new boolean[items.size()];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_item_layout, null);
        }

        CheckBox checkBox = view.findViewById(R.id.checkBox);
        TextView textView = view.findViewById(R.id.textView);

        checkBox.setChecked(checkedItems[position]);
        textView.setText(items.get(position));

        // Встановлюємо обробник подій на чекбокс
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(position, isChecked);
            }
        });

        return view;
    }

//    public void setItemChecked(int position, boolean isChecked) {
//        checkedItems[position] = isChecked;
//    }

    public boolean[] getCheckedItems() {
        return checkedItems;
    }

    // Додайте метод для встановлення обробника подій
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    public void setItemChecked(int position, boolean isChecked) {
        checkedItems[position] = isChecked; // Оновлюємо значення "checked" для певної позиції
        notifyDataSetChanged(); // Оновлюємо відображення
    }


    public void setCheckedItems(boolean[] checkedItems) {
        this.checkedItems = checkedItems;
        notifyDataSetChanged(); // Оновлюємо відображення на основі нових значень "checked"
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(int position, boolean isChecked);
    }
}
