package com.example.geome;
import com.example.geome.Models.AppData;
import com.example.geome.Models.CustomCursorAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private CursorAdapter adapter;
    private ListView chatListView;
    private EditText messageEditText;
    private ImageView sendButton;
    private ImageView recordButton;
    private ImageView playButton;
    private User user;
    private MediaRecorder mediaRecorder;
    private String outputFile;
    private MediaPlayer mediaPlayer;
    private boolean isRecording = false;
    private String currentVoiceMessagePath;
    private int userCity;

    private String audioPath;
    private static final int REQUEST_AUDIO_PERMISSION = 200; // Замість 200 ви можете використовувати будь-яке унікальне ціле число

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Встановлення режиму відображення клавіатури
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
//        if (getArguments() != null) {
//            user = (User) getArguments().getSerializable(LogInActivity.KEY_USERPROFILE);
//        }

        user = AppData.getInstance().getUser();


        dbHelper = new DatabaseHelper(getActivity());

        database = dbHelper.getReadableDatabase();

        chatListView = rootView.findViewById(R.id.chatListView);
        messageEditText = rootView.findViewById(R.id.messageEditText);
        sendButton = rootView.findViewById(R.id.sendButton);

        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                // Викликається після зміни тексту
                String messageText = s.toString().trim();
//                ImageView recordButton = rootView.findViewById(R.id.recordButton);
//                ImageView sendButton = rootView.findViewById(R.id.sendButton);

                if (messageText.isEmpty()) {
                    // Якщо текст порожній, відображаємо кнопку recordButton і приховуємо sendButton
                    //recordButton.setVisibility(View.VISIBLE);
                    sendButton.setVisibility(View.GONE);
                } else {
                    // Якщо в тексті є символи, відображаємо кнопку sendButton і приховуємо recordButton
                    sendButton.setVisibility(View.VISIBLE);
                    //recordButton.setVisibility(View.GONE);
                }
            }
        });

        userCity = dbHelper.getCityForUser(user.getUserPhone());
        final int chatId = getChatIdForCity(userCity);

        if (chatId != -1) {

            if (adapter == null) {
                Cursor cursor = getMessagesForChat(chatId);
                adapter = new CustomCursorAdapter(getActivity(), cursor, dbHelper.getUserId(user.getUserPhone()));
                chatListView.setAdapter(adapter);
            }

//            Cursor cursor = getMessagesForChat(chatId);
//
//            adapter = new CustomCursorAdapter(getActivity(), cursor, dbHelper.getUserId(user.getUserPhone()));
//            //adapter = new CustomCursorAdapter(getActivity(), cursor, dbHelper.getUserId(user.getUserPhone()), R.layout.item_container_sent_message);
//
//            chatListView.setAdapter(adapter);
        }

        rootView.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageEditText.getText().toString();
                if (!messageText.isEmpty()) {
                    int userId = dbHelper.getUserId(user.getUserPhone());
                    int chatId = getChatIdForCity(userCity);

                    if (chatId == -1) {
                        // Чат не існує, створюємо новий чат
                        chatId = dbHelper.createChat(userCity);
                    }

                    if (chatId != -1) {
                        long result = dbHelper.insertMessage(chatId, userId, "text", messageText);

                        if (result != -1) {
                            // Оновіть список повідомлень
                            updateMessagesList(chatId);
                            messageEditText.setText("");
                        } else {
                            Toast.makeText(getActivity(), "Помилка при вставці повідомлення", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Помилка при створенні чату", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return rootView;
    }

    private int getChatIdForCity(int cityId) {
        // Отримуємо ідентифікатор чату за назвою міста
        return dbHelper.getChatIdByCityName(cityId);
    }

    private Cursor getMessagesForChat(int chatId) {
        // Отримуємо список повідомлень для даного чату (міста)
        return dbHelper.getMessagesForChat(chatId);
    }

//    private void updateMessagesList(int chatId) {
//        Cursor cursor = dbHelper.getMessagesForChat(chatId);
//
//        String[] fromColumns = {DatabaseHelper.COLUMN_CONTENT};
//        int[] toViews = {R.id.messageTextView}; // TextView для відображення повідомлень
//
//        adapter.changeCursor(cursor);
//    }

    private void updateMessagesList(int chatId) {
        Cursor cursor = dbHelper.getMessagesForChat(chatId);

        String[] fromColumns = {DatabaseHelper.COLUMN_CONTENT};
        int[] toViews = {R.id.messageTextView}; // TextView для відображення повідомлень

        if (adapter != null) {
            // Перевірте, чи адаптер не є null перед викликом changeCursor
            adapter.changeCursor(cursor);
        } else {
            // Якщо адаптер не ініціалізований, створіть новий адаптер
            adapter = new CustomCursorAdapter(getActivity(), cursor, dbHelper.getUserId(user.getUserPhone()));
            chatListView.setAdapter(adapter);
        }
    }

}