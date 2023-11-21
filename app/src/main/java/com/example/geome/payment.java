package com.example.geome;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class payment extends Fragment {
    private TextView day1;
    private TextView day2;
    private TextView month1;
    private TextView month2;
    private TextView year1;
    private TextView year2;
    private TextView count1;
    private TextView count2;
    private TextView count3;
    private TextView count4;
    private TextView button_accommodation_options;
    private String[] months = {"Січень", "Лютий", "Березень", "Квітень", "Травень", "Червень",
            "Липень", "Серпень", "Вересень", "Жовтень", "Листопад", "Грудень"};
    private int selectedMonthIndex = 0; // Значення за замовчуванням
    ImageView vector1;
    ImageView vector2;
    ImageView vector3;
    ImageView vector4;
    ImageView vector5;
    ImageView vector6;
    ImageView vector7;
    ImageView vector8;
    ImageView vector9;
    ImageView vector10;
    private int selectedDay = 1;
    public int Id;
    public payment(int Id){
        this.Id = Id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);

        day1 = rootView.findViewById(R.id.day1);
        day2 = rootView.findViewById(R.id.day2);

        month1 = rootView.findViewById(R.id.month1);
        month2 = rootView.findViewById(R.id.month2);

        year1 = rootView.findViewById(R.id.year1);
        year2 = rootView.findViewById(R.id.year2);

        count1 = rootView.findViewById(R.id.count1);
        count2 = rootView.findViewById(R.id.count2);
        count3 = rootView.findViewById(R.id.count3);
        count4 = rootView.findViewById(R.id.count4);


        button_accommodation_options = rootView.findViewById(R.id.button_accommodation_options);
        button_accommodation_options.setOnClickListener(this::Accommodation_optionsButtonClick);

        vector1 = rootView.findViewById(R.id.vector1);
        vector2 = rootView.findViewById(R.id.vector2);
        vector3 = rootView.findViewById(R.id.vector3);
        vector4 = rootView.findViewById(R.id.vector4);
        vector5 = rootView.findViewById(R.id.vector5);
        vector6 = rootView.findViewById(R.id.vector6);
        vector7 = rootView.findViewById(R.id.vector7);
        vector8 = rootView.findViewById(R.id.vector8);
        vector9 = rootView.findViewById(R.id.vector9);
        vector10 = rootView.findViewById(R.id.vector10);

        vector1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Відображення діалогового вікна для вибору числа
                showNumberPickerDialog();
            }
        });

        vector2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Відображення діалогового вікна для вибору місяця
                showMonthPickerDialog();
            }
        });

        vector3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Відображення діалогового вікна для вибору числа
                showYearPickerDialog();
            }
        });

        vector4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Відображення діалогового вікна для вибору числа
                showNumberPickerDialog2();
            }
        });

        vector5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Відображення діалогового вікна для вибору місяця
                showMonthPickerDialog2();
            }
        });

        vector6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Відображення діалогового вікна для вибору числа
                showYearPickerDialog2();
            }
        });

        vector7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Відображення діалогового вікна для вибору числа
                showNumberPickerDialog3();
            }
        });

        vector8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Відображення діалогового вікна для вибору числа
                showNumberPickerDialog4();
            }
        });

        vector9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Відображення діалогового вікна для вибору числа
                showNumberPickerDialog5();
            }
        });

        vector10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Відображення діалогового вікна для вибору числа
                showNumberPickerDialog6();
            }
        });



        return rootView;
    }

    public void Accommodation_optionsButtonClick(View view){
        Intent intent = new Intent(getContext(), Accommodation_options.class);
        startActivity(intent);
    }
    // Функція для відображення діалогового вікна для вибору числа

    private void showMonthPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Виберіть місяць");

        builder.setSingleChoiceItems(months, selectedMonthIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Оновлення обраного місяця при виборі
                selectedMonthIndex = i;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Викликати функцію встановлення тексту у TextView
                setMonthText1();
            }
        });

        builder.setNegativeButton("Відміна", null);

        builder.create().show();
    }

    // Функція для встановлення тексту у TextView

    private void showNumberPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Виберіть день");

        // Масив днів від 1 до 31
        final String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }

        builder.setSingleChoiceItems(days, selectedDay - 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Оновлення обраного дня при виборі
                selectedDay = i + 1;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Викликати функцію встановлення тексту у TextView
                setDayText1();
            }
        });

        builder.setNegativeButton("Відміна", null);

        builder.create().show();
    }

    private void showYearPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Виберіть рік");

        // Масив років від 2023 до 2027
        final String[] years = new String[5];
        for (int i = 2023; i < 2028; i++) {
            years[i - 2023] = String.valueOf(i);
        }

        builder.setSingleChoiceItems(years, selectedDay - 2023, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Оновлення обраного року при виборі
                selectedDay = Integer.parseInt(years[i]);
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Викликати функцію встановлення тексту у TextView
                setYearText1();
            }
        });

        builder.setNegativeButton("Відміна", null);

        builder.create().show();
    }

    private void showMonthPickerDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Виберіть місяць");

        builder.setSingleChoiceItems(months, selectedMonthIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Оновлення обраного місяця при виборі
                selectedMonthIndex = i;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Викликати функцію встановлення тексту у TextView
                setMonthText2();
            }
        });

        builder.setNegativeButton("Відміна", null);

        builder.create().show();
    }

    // Функція для встановлення тексту у TextView

    private void showNumberPickerDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Виберіть день");

        // Масив днів від 1 до 31
        final String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }

        builder.setSingleChoiceItems(days, selectedDay - 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Оновлення обраного дня при виборі
                selectedDay = i + 1;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Викликати функцію встановлення тексту у TextView
                setDayText2();
            }
        });

        builder.setNegativeButton("Відміна", null);

        builder.create().show();
    }

    private void showYearPickerDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Виберіть рік");

        // Масив років від 2023 до 2027
        final String[] years = new String[5];
        for (int i = 2023; i < 2028; i++) {
            years[i - 2023] = String.valueOf(i);
        }

        builder.setSingleChoiceItems(years, selectedDay - 2023, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Оновлення обраного року при виборі
                selectedDay = Integer.parseInt(years[i]);
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Викликати функцію встановлення тексту у TextView
                setYearText2();
            }
        });

        builder.setNegativeButton("Відміна", null);

        builder.create().show();
    }




    private void showNumberPickerDialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Виберіть день");

        // Масив днів від 1 до 31
        final String[] days = new String[10];
        for (int i = 0; i < 10; i++) {
            days[i] = String.valueOf(i + 1);
        }

        builder.setSingleChoiceItems(days, selectedDay - 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Оновлення обраного дня при виборі
                selectedDay = i + 1;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Викликати функцію встановлення тексту у TextView
                setDayText3();
            }
        });

        builder.setNegativeButton("Відміна", null);

        builder.create().show();
    }

    private void showNumberPickerDialog4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Виберіть день");

        // Масив днів від 1 до 31
        final String[] days = new String[10];
        for (int i = 0; i < 10; i++) {
            days[i] = String.valueOf(i + 1);
        }

        builder.setSingleChoiceItems(days, selectedDay - 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Оновлення обраного дня при виборі
                selectedDay = i + 1;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Викликати функцію встановлення тексту у TextView
                setDayText4();
            }
        });

        builder.setNegativeButton("Відміна", null);

        builder.create().show();
    }

    private void showNumberPickerDialog5() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Виберіть день");

        // Масив днів від 1 до 31
        final String[] days = new String[10];
        for (int i = 0; i < 10; i++) {
            days[i] = String.valueOf(i + 1);
        }

        builder.setSingleChoiceItems(days, selectedDay - 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Оновлення обраного дня при виборі
                selectedDay = i + 1;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Викликати функцію встановлення тексту у TextView
                setDayText5();
            }
        });

        builder.setNegativeButton("Відміна", null);

        builder.create().show();
    }

    private void showNumberPickerDialog6() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Виберіть день");

        // Масив днів від 1 до 31
        final String[] days = new String[10];
        for (int i = 0; i < 10; i++) {
            days[i] = String.valueOf(i + 1);
        }

        builder.setSingleChoiceItems(days, selectedDay - 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Оновлення обраного дня при виборі
                selectedDay = i + 1;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Викликати функцію встановлення тексту у TextView
                setDayText6();
            }
        });

        builder.setNegativeButton("Відміна", null);

        builder.create().show();
    }
    private void setMonthText1() {
        month1.setText("" + months[selectedMonthIndex]);
    }
    private void setMonthText2() {
        month2.setText("" + months[selectedMonthIndex]);
    }
    private void setDayText1() {
        day1.setText(String.valueOf(selectedDay));
    }
    private void setDayText2() {
        day2.setText(String.valueOf(selectedDay));
    }
    private void setDayText3() {
        count1.setText(String.valueOf(selectedDay));
    }
    private void setDayText4() {
        count2.setText(String.valueOf(selectedDay));
    }
    private void setDayText5() {
        count3.setText(String.valueOf(selectedDay));
    }
    private void setDayText6() {
        count4.setText(String.valueOf(selectedDay));
    }
    private void setYearText1() {
        year1.setText(String.valueOf(selectedDay));
    }
    private void setYearText2() {
        year2.setText(String.valueOf(selectedDay));
    }


}