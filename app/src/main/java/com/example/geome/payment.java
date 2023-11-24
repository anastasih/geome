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
import android.widget.Toast;

import com.example.geome.Models.AppData;
import com.example.geome.Models.Booking;
import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class payment extends Fragment {
    public static final String KEY_IN_DATE = "androidx.appcompat.app.AppCompatActivity.payment.indata";
    public static final String KEY_OUT_DATE = "androidx.appcompat.app.AppCompatActivity.payment.outdata";
    public static final String KEY_NUMBER = "androidx.appcompat.app.AppCompatActivity.payment.number";
    public static final String KEY_ID_COMPANY = "androidx.appcompat.app.AppCompatActivity.payment.idcompany";
    public static final String KEY_BOOKING = "androidx.appcompat.app.AppCompatActivity.payment.booking";
    private TextView day1;
    private TextView day2;
    private TextView month1;
    private TextView month2;
    private TextView year1;
    private TextView year2;
    private TextView count1;
    private TextView count2;
    private TextView count3;
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


        return rootView;
    }

    public void Accommodation_optionsButtonClick(View view){
        User user = AppData.getInstance().getUser();
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        int userId = databaseHelper.getUserId(user.getUserPhone());
        String name = databaseHelper.getUserNameById(userId);

        Calendar calendar = Calendar.getInstance();
        calendar.set(checkInYear1, checkInMonth1, checkInDay1);
        Date date1 = calendar.getTime();

        calendar.set(checkInYear2, checkInMonth2, checkInDay2);
        Date date2 = calendar.getTime();

        Booking booking = new Booking();
        booking.setCheckInDate(date1);
        booking.setCheckOutDate(date2);
        booking.setNumGuests(Adults);
        booking.setChildren(Child);
        booking.setNumRooms(Rooms);
        booking.setSingleRoom(SoloRoom);
        if(name != null || name.length() > 0){
            booking.setGuestName(name);
        }

//        intent.putExtra(KEY_IN_DATE, date1);
//        intent.putExtra(KEY_OUT_DATE, date2);
//        intent.putExtra(KEY_NUMBER, Adults);
        Date currentDate = new Date();
        if(booking.getCheckInDate() != null && booking.getCheckOutDate() != null &&
                booking.getNumGuests() != 0 &&  booking.getNumRooms() != 0){
            if(!date1.before(currentDate) && !date2.before(currentDate)){
                Intent intent = new Intent(getContext(), Accommodation_options.class);
                intent.putExtra(KEY_ID_COMPANY, Id);
                intent.putExtra(KEY_BOOKING, booking);
                startActivity(intent);
            }
            else {
                Toast.makeText(getContext(), "Дати вже минули, не можна забронювати готель", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(getContext(), "Заповніть всі поля!", Toast.LENGTH_SHORT).show();
        }

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
        builder.setTitle("Виберіть кількість дорослих");

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
                setAdultsText3();
            }
        });

        builder.setNegativeButton("Відміна", null);

        builder.create().show();
    }

    private void showNumberPickerDialog4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Виберіть кількість дітей");

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
                setChildText4();
            }
        });

        builder.setNegativeButton("Відміна", null);

        builder.create().show();
    }

    private void showNumberPickerDialog5() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Виберіть кількість номерів");

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
                setRoomsText5();
            }
        });

        builder.setNegativeButton("Відміна", null);

        builder.create().show();
    }

    public int checkInDay1;
    public int checkInDay2;
    public int checkInMonth1;
    public int checkInMonth2;
    public int checkInYear1;
    public int checkInYear2;
    public int Adults;
    public int Child;
    public int Rooms;
    public int SoloRoom;

    private void setMonthText1() {
        month1.setText("" + months[selectedMonthIndex]);
        checkInMonth1 = selectedMonthIndex;
    }
    private void setMonthText2() {
        month2.setText("" + months[selectedMonthIndex]);
        checkInMonth2 = selectedMonthIndex;
    }
    private void setDayText1() {
        day1.setText(String.valueOf(selectedDay));
        checkInDay1 = selectedDay;
    }
    private void setDayText2() {
        day2.setText(String.valueOf(selectedDay));
        checkInDay2 = selectedDay;
    }
    private void setYearText1() {
        year1.setText(String.valueOf(selectedDay));
        checkInYear1 = selectedDay;
    }
    private void setYearText2() {
        year2.setText(String.valueOf(selectedDay));
        checkInYear2 = selectedDay;
    }

    private void setAdultsText3() {
        count1.setText(String.valueOf(selectedDay));
        Adults = selectedDay;
    }
    private void setChildText4() {
        count2.setText(String.valueOf(selectedDay));
        Child = selectedDay;
    }
    private void setRoomsText5() {
        count3.setText(String.valueOf(selectedDay));
        Rooms = selectedDay;
    }
}