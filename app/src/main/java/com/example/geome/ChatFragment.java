package com.example.geome;
import com.example.geome.Models.CustomCursorAdapter;

import android.app.AlertDialog;
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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private String userCity;

    private static final int REQUEST_AUDIO_PERMISSION = 200; // Замість 200 ви можете використовувати будь-яке унікальне ціле число

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        dbHelper = new DatabaseHelper(getActivity());
        if(dbHelper != null) {
            Toast.makeText(getActivity(), "get Activity", Toast.LENGTH_SHORT).show();
        }

        database = dbHelper.getReadableDatabase();

        chatListView = rootView.findViewById(R.id.chatListView);
        messageEditText = rootView.findViewById(R.id.messageEditText);
        recordButton = rootView.findViewById(R.id.recordButton);
        sendButton = rootView.findViewById(R.id.sendButton);
        playButton = rootView.findViewById(R.id.playButton);

        recordButton.setVisibility(View.VISIBLE);
        sendButton.setVisibility(View.GONE);
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
                    recordButton.setVisibility(View.VISIBLE);
                    sendButton.setVisibility(View.GONE);
                } else {
                    // Якщо в тексті є символи, відображаємо кнопку sendButton і приховуємо recordButton
                    sendButton.setVisibility(View.VISIBLE);
                    recordButton.setVisibility(View.GONE);
                }
            }
        });


        userCity = dbHelper.getCityForUser(user.getUserPhone());
        final int chatId = getChatIdForCity(userCity);

        if (chatId != -1) {
            Cursor cursor = getMessagesForChat(chatId);

            adapter = new CustomCursorAdapter(getActivity(), cursor, dbHelper.getUserId(user.getUserPhone()));
            //adapter = new CustomCursorAdapter(getActivity(), cursor, dbHelper.getUserId(user.getUserPhone()), R.layout.item_container_sent_message);

            chatListView.setAdapter(adapter);
        }

        rootView.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageEditText.getText().toString();
                if (!messageText.isEmpty()) {
                    int userId = dbHelper.getUserId(user.getUserPhone());
                    int chatId = getChatIdForCity(userCity);

                    if (chatId != -1) {
                        long result = dbHelper.insertMessage(chatId, userId, "text", messageText);

                        if (result != -1) {
                            // Оновіть список повідомлень
                            updateMessagesList(chatId);
                            messageEditText.setText("");
                        } else {
                            Toast.makeText(getActivity(), "Помилка при вставці повідомлення", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        // Додайте обробник для кнопки запису голосового повідомлення
//        rootView.findViewById(R.id.recordButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startRecording();
//            }
//        });

        rootView.findViewById(R.id.recordButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //checkRecordAudioPermission();

                //permission();

                requestAudioPermission();

//                if (!isRecording) {
//                    startRecording(); // Починаємо запис
//                    Toast.makeText(getActivity(), "Запис почато", Toast.LENGTH_SHORT).show();
//                    isRecording = true;
//                } else {
//                    stopRecording();  // Зупиняємо запис
//                    isRecording = false;
//                }
                //isRecording = !isRecording; // Змінюємо стан запису

                // Додаємо цей код до вашої активності або фрагмента, де ви запускаєте запис аудіо
//                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
//                        ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION);
//                } else {
//                    // Вже є дозволи, можна продовжувати запис аудіо
//                    //startRecording();
//
////                    Log.d("MyApp", "Це повідомлення для відлагодження");
//
//                    if (!isRecording) {
//
//                        startRecording(); // Починаємо запис
//                        Toast.makeText(getActivity(), "Запис почато", Toast.LENGTH_SHORT).show();
//                    } else {
//                        stopRecording();  // Зупиняємо запис
//                    }
//                    isRecording = !isRecording; // Змінюємо стан запису
//
//                }



//                if (!isRecording) {
//
//                    startRecording(); // Починаємо запис
//                } else {
//                    stopRecording();  // Зупиняємо запис
//                }
//                isRecording = !isRecording; // Змінюємо стан запису
            }
        });


        // Додайте обробник для кнопки відтворення голосового повідомлення
//        rootView.findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playRecording();
//            }
//        });

//        rootView.findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentVoiceMessagePath != null) {
//                    playRecording(currentVoiceMessagePath); // Відтворюємо голосове повідомлення
//                } else {
//                    Toast.makeText(getActivity(), "Голосове повідомлення не знайдено", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        if (adapter != null) {
//            int itemCount = adapter.getCount();
//
//            for (int i = 0; i < itemCount; i++) {
//                // Отримуємо посилання на кореневий View елемента контейнера
//                View itemRootView = adapter.getView(i, null, chatListView);
//
//                // Отримуємо посилання на playButton у кожному елементі контейнера
//                ImageView playButton = itemRootView.findViewById(R.id.playButton);
//
//                // Встановлюємо обробник подій для playButton
//                if (playButton != null) {
//                    playButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            // Обробка кліку на кнопку відтворення
//                            // Можете викликати метод playRecording з відповідним шляхом то запису голосового повідомлення
//
//                            Log.d("MyApp", "currentVoiceMessagePath " + currentVoiceMessagePath);
//
//                            if (currentVoiceMessagePath != null) {
//                    playRecording(currentVoiceMessagePath); // Відтворюємо голосове повідомлення
//                } else {
//                    Toast.makeText(getActivity(), "Голосове повідомлення не знайдено", Toast.LENGTH_SHORT).show();
//                }
//                        }
//                    });
//                }
//            }
//        }

        return rootView;
    }



    private void permission() {
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) ==
//                PackageManager.PERMISSION_DENIED) {
            Log.d("MyApp", "getActivity " + getActivity());
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_AUDIO_PERMISSION);
            //Log.d("MyApp", "permission " + true);
        //}
    }

    // Поле класу
    private String timeStamp;

    private void requestAudioPermission() {
        // Перевірка, чи дозвіл вже наданий
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            // Якщо дозвіл не наданий, викликати запит дозволу
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_AUDIO_PERMISSION);
        } else {
            // Дозвіл вже наданий, ви можете розпочати запис аудіо
            //startRecording();

            if (isRecording == false) {
                startRecording(); // Починаємо запис
                Toast.makeText(getActivity(), "Запис почато", Toast.LENGTH_SHORT).show();
                isRecording = true;
            } else if(isRecording == true) {
                stopRecording();  // Зупиняємо запис
                isRecording = false;
            }

        }
    }

    // Обробник результатів запиту дозволу
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Користувач надав дозвіл на запис аудіо
                // Тут ви можете викликати метод для початку запису аудіо
                startRecording();
            } else {
                // Користувач відмовив у дозволі на запис аудіо
                // Тут ви можете повідомити користувача або виконати інші дії
                Toast.makeText(getActivity(), "Дозвіл на запис аудіо відхилений", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        //mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());


        int userId = dbHelper.getUserId(user.getUserPhone());
        int chatId = getChatIdForCity(userCity);

        if (chatId != -1) {

            File audioFolder = new File(getActivity().getCacheDir(), "audio");
            if (!audioFolder.exists()) {
                audioFolder.mkdirs();
            }

            // Отримайте шлях для збереження аудіофайлу
            String audioFileName = "voice_message_" + timeStamp + ".3gp";
            File audioFile = new File(audioFolder, audioFileName);
            outputFile = audioFile.getAbsolutePath();

            try {
                if (!audioFile.createNewFile()) {
                    // Помилка створення аудіофайлу, обробіть це відповідним чином
                    Log.e("MyApp", "Не вдалося створити аудіофайл");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("MyApp", "шлях файлу " + outputFile);

            //outputFile = getActivity().getCacheDir().getAbsolutePath() + "/voice_message_" + timeStamp + ".3gp";
            mediaRecorder.setOutputFile(outputFile);

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                Toast.makeText(getActivity(), "Запис розпочато", Toast.LENGTH_SHORT).show();
                currentVoiceMessagePath = outputFile;
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Помилка під час підготовки або запуску запису", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Помилка під час закриття запису", Toast.LENGTH_SHORT).show();
            }
            mediaRecorder = null;
            Toast.makeText(getActivity(), "Запис завершено", Toast.LENGTH_SHORT).show();

            // Зберігаємо шлях голосового повідомлення в базі даних
//            if (currentVoiceMessagePath != null) {
                int userId = dbHelper.getUserId(user.getUserPhone());
                int chatId = getChatIdForCity(userCity);
//
//                if (chatId != -1) {
//                    // Створіть папку для зберігання голосових повідомлень, якщо вона не існує
//                    File audioFolder = new File(getActivity().getCacheDir(), "audio");
//                    if (!audioFolder.exists()) {
//                        audioFolder.mkdirs();
//                    }
//
//                    // Отримайте шлях для збереження аудіофайлу
//                    String audioFileName = "voice_message_" + timeStamp + ".3gp";
//                    File audioFile = new File(audioFolder, audioFileName);
//                    String audioFilePath = audioFile.getAbsolutePath();

                    // Скопіюйте файл до нового місця зі збереженням
//                    if (copyFile(currentVoiceMessagePath, audioFilePath)) {
//                        // Видаліть тимчасовий файл
//                        File tempFile = new File(currentVoiceMessagePath);
//                        if (tempFile.exists()) {
//                            tempFile.delete();
//                        }

                        // Вставте інформацію про голосове повідомлення в базу даних
                        long result = dbHelper.insertMessage(chatId, userId, "audio", outputFile);

                        if (result != -1) {
                            // Оновіть список повідомлень
                            updateMessagesList(chatId);
                        } else {
                            Toast.makeText(getActivity(), "Помилка при вставці голосового повідомлення", Toast.LENGTH_SHORT).show();
                        }
                    //}
                //}
            //}
        }
    }


//    private void startRecording() {
//        mediaRecorder = new MediaRecorder();
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
////        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
////        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
////        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//
//        outputFile = getActivity().getCacheDir().getAbsolutePath() + "/voice_message_" + timeStamp + ".3gp";
//
//        //outputFile = getActivity().getExternalCacheDir().getAbsolutePath() + "/voice_message.3gp";
//        mediaRecorder.setOutputFile(outputFile);
//
//        try {
//            mediaRecorder.prepare();
//            mediaRecorder.start();
//            Toast.makeText(getActivity(), "Запис розпочато", Toast.LENGTH_SHORT).show();
//            currentVoiceMessagePath = outputFile;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void stopRecording() {
//        if (mediaRecorder != null) {
//            try {
//                mediaRecorder.stop();
//                mediaRecorder.release();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            mediaRecorder = null;
//            Toast.makeText(getActivity(), "Запис завершено", Toast.LENGTH_SHORT).show();
//
//            // Зберігаємо шлях голосового повідомлення в базі даних
//            if (currentVoiceMessagePath != null) {
//                int userId = dbHelper.getUserId(user.getUserPhone());
//                int chatId = getChatIdForCity(userCity);
//
//                if (chatId != -1) {
//                    // Створіть папку для зберігання голосових повідомлень, якщо вона не існує
//                    File audioFolder = new File(getActivity().getCacheDir(), "audio");
//                    if (!audioFolder.exists()) {
//                        audioFolder.mkdirs();
//                    }
//
//                    // Отримайте шлях для збереження аудіофайлу
//                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                    String audioFileName = "voice_message_" + timeStamp + ".3gp";
//                    File audioFile = new File(audioFolder, audioFileName);
//                    String audioFilePath = audioFile.getAbsolutePath();
//
//                    // Скопіюйте файл до нового місця зі збереженням
//                    if (copyFile(currentVoiceMessagePath, audioFilePath)) {
//                        // Видаліть тимчасовий файл
//                        File tempFile = new File(currentVoiceMessagePath);
//                        if (tempFile.exists()) {
//                            tempFile.delete();
//                        }
//
//                        // Вставте інформацію про голосове повідомлення в базу даних
//                        long result = dbHelper.insertMessage(chatId, userId, "audio", audioFilePath);
//
//                        if (result != -1) {
//                            // Оновіть список повідомлень
//                            updateMessagesList(chatId);
//                        } else {
//                            Toast.makeText(getActivity(), "Помилка при вставці голосового повідомлення", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            }
//        }
//    }

    private boolean copyFile(String sourcePath, String destinationPath) {
        try {
            FileInputStream in = new FileInputStream(sourcePath);
            FileOutputStream out = new FileOutputStream(destinationPath);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


//    private void stopRecording() {
//        if (mediaRecorder != null) {
//            mediaRecorder.stop();
//            mediaRecorder.release();
//            mediaRecorder = null;
//            Toast.makeText(getActivity(), "Запис завершено", Toast.LENGTH_SHORT).show();
//
//            // Зберігаємо шлях голосового повідомлення в базі даних
//            if (currentVoiceMessagePath != null) {
//                int userId = dbHelper.getUserId(user.getUserPhone());
//                int chatId = getChatIdForCity(userCity);
//
//                if (chatId != -1) {
//                    long result = dbHelper.insertMessage(chatId, userId, "audio", currentVoiceMessagePath);
//
//                    if (result != -1) {
//                        // Оновіть список повідомлень
//                        updateMessagesList(chatId);
//                    } else {
//                        Toast.makeText(getActivity(), "Помилка при вставці голосового повідомлення", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }
//    }

//    private void playRecording() {
//        mediaPlayer = new MediaPlayer();
//
//        try {
//            mediaPlayer.setDataSource(outputFile);
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//            Toast.makeText(getActivity(), "Відтворення", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private void playRecording(String audioFilePath) {
//        mediaPlayer = new MediaPlayer();
//
//        try {
//            mediaPlayer.setDataSource(audioFilePath);
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//            Toast.makeText(getActivity(), "Відтворення", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaRecorder != null) {
            mediaRecorder.release();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
    private int getChatIdForCity(String cityName) {
        // Отримуємо ідентифікатор чату за назвою міста
        return dbHelper.getChatIdByCityName(cityName);
    }

    private Cursor getMessagesForChat(int chatId) {
        // Отримуємо список повідомлень для даного чату (міста)
        return dbHelper.getMessagesForChat(chatId);
    }

    private void updateMessagesList(int chatId) {
        Cursor cursor = dbHelper.getMessagesForChat(chatId);

        String[] fromColumns = {DatabaseHelper.COLUMN_CONTENT};
        int[] toViews = {R.id.messageTextView}; // TextView для відображення повідомлень

        adapter.changeCursor(cursor);
    }
}