package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.Models.AppData;
import com.example.geome.Models.Booking;
import com.example.geome.Models.Company;
import com.example.geome.Models.CompanyListAdapter;
import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.RoomsAdapter;
import com.example.geome.Models.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Reservation extends AppCompatActivity {

    private TextView buttonPay;
    public Booking booking;
    private TextView data1;
    private TextView data2;
    private TextView adults;
    private TextView children;
    private TextView room;
    private TextView value;
    private TextView TotalValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent intent = getIntent();
        booking = (Booking) intent.getSerializableExtra(RoomsAdapter.KEY_BOOKING);
        int idCompany = (Integer) intent.getSerializableExtra(RoomsAdapter.KEY_ID_COMPANY);

        //Toast.makeText(this, "name = " + booking.getCheckOutDate(), Toast.LENGTH_SHORT).show();

        buttonPay = findViewById(R.id.buttonPay);
        data1 = findViewById(R.id.data1);
        data2 = findViewById(R.id.data2);
        adults = findViewById(R.id.adults);
        children = findViewById(R.id.children);
        room = findViewById(R.id.room);
        value = findViewById(R.id.value);
        TotalValue = findViewById(R.id.TotalValue);

        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", new Locale("uk"));
        String formattedDate = sdf.format(booking.getCheckInDate());
        data1.setText(formattedDate);
        formattedDate = sdf.format(booking.getCheckOutDate());
        data2.setText(formattedDate);

        long differenceInMillis = booking.getCheckOutDate().getTime() - booking.getCheckInDate().getTime();
        // Переведення мілісекунд в дні
        int daysDifference = (int) (differenceInMillis / (1000 * 60 * 60 * 24));

        adults.setText(String.valueOf(booking.getNumGuests()));
        children.setText(String.valueOf(booking.getChildren()));
        room.setText(String.valueOf(booking.getNumRooms()));
        value.setText(String.valueOf(daysDifference * booking.getTotalPrice()));
        TotalValue.setText(String.valueOf(daysDifference * booking.getTotalPrice()));
        booking.setTotalPrice(daysDifference * booking.getTotalPrice());

        User user = AppData.getInstance().getUser();
        DatabaseHelper databaseHelper = new DatabaseHelper(Reservation.this);
        int userId = databaseHelper.getUserId(user.getUserPhone());
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(Reservation.this);
                long result = databaseHelper.addBooking(String.valueOf(booking.getRoomId()) ,
                        booking.getGuestName(),
                        String.valueOf(booking.getCheckInDate()),
                        String.valueOf(booking.getCheckOutDate()),
                        booking.getNumGuests(),
                        booking.getChildren(),
                        booking.getChildAge(),
                        booking.getNumRooms(),
                        booking.getTotalPrice(),
                        userId);
                if (result == -1) {
                    Toast.makeText(Reservation.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(Reservation.this, "Add", Toast.LENGTH_SHORT).show();
                    String recipientEmail = "geome2503@gmail.com";
                    String subject = "Підтвердження бронювання";
                    String body = "Ваше бронювання підтверджено. Дякуємо за вибір нашого готелю!";

                    sendEmail(Reservation.this, recipientEmail, subject, body);

                    User user = AppData.getInstance().getUser();
                    databaseHelper = new DatabaseHelper(Reservation.this);
                    int userId = databaseHelper.getUserId(user.getUserPhone());

                    Date currentDate = new Date();
                    long result2 = databaseHelper.addPayment(String.valueOf(idCompany), String.valueOf(userId),
                            String.valueOf(currentDate),
                            "", booking.getTotalPrice());
                    if (result2 == -1) {
                        Toast.makeText(Reservation.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Reservation.this, "Add", Toast.LENGTH_SHORT).show();
                    }
                }
                showReservationDialog();
            }
        });
    }

    public static void sendEmail(Context context, String recipientEmail, String subject, String body) {
        final String username = "anastasi.gulko@gmail.com";
        final String password = "zptlredtkcuigtra";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            new SendEmailTask(context, recipientEmail, subject, body, username, password).execute();
        } else {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();
        }
    }

    private static class SendEmailTask extends AsyncTask<Void, Void, Void> {
        private final Context context;
        private final String recipientEmail;
        private final String subject;
        private final String body;
        private final String username;
        private final String password;

        public SendEmailTask(Context context, String recipientEmail, String subject, String body, String username, String password) {
            this.context = context;
            this.recipientEmail = recipientEmail;
            this.subject = subject;
            this.body = body;
            this.username = username;
            this.password = password;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Session session = createSession(username, password);

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
                Log.d("Email Sending", "Повідомлення відправлено успішно.");
            } catch (MessagingException e) {
                Log.e("Email Sending", "Помилка відправки електронного листа", e);
            }
            return null;
        }
    }

    private static Session createSession(final String username, final String password) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

//    public static void sendEmail(String recipientEmail, String subject, String body) {
//        final String username = "geome2503@gmail.com"; // Ваша електронна адреса Gmail
//        final String password = "geo128746A"; // Ваш пароль
//
//        // Налаштування властивостей
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com"); // або інший SMTP-сервер
//        props.put("mail.smtp.port", "587");
//
//        // Створення сеансу
//        Session session = Session.getInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//
//        try {
//            // Створення об'єкта MimeMessage
//            Message message = new MimeMessage(session);
//
//            // Встановлення відправника
//            message.setFrom(new InternetAddress(username));
//
//            // Встановлення отримувача
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
//
//            // Встановлення теми
//            message.setSubject(subject);
//
//            // Встановлення тексту повідомлення
//            message.setText(body);
//
//            // Відправлення повідомлення
//            Transport.send(message);
//
//            Log.d("hello " , "message = " + "Повідомлення відправлено успішно.");
//            //System.out.println("Повідомлення відправлено успішно.");
//
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void showReservationDialog() {
        ReservationDialogFragment dialogFragment = new ReservationDialogFragment(booking);
        dialogFragment.show(getSupportFragmentManager(), "ReservationDialog");
    }
}