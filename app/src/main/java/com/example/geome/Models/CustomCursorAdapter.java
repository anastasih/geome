package com.example.geome.Models;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.R;

import java.io.IOException;

public class CustomCursorAdapter extends CursorAdapter {

    private int currentUserId;
    //private int layoutResId;
    private OnPlayButtonClickListener playButtonClickListener;
    private MediaPlayer mediaPlayer;

    public interface OnPlayButtonClickListener {
        void onPlayButtonClicked(String audioFilePath);
    }

    public CustomCursorAdapter(Context context, Cursor cursor, int currentUserId) {
        super(context, cursor, 0);
        this.currentUserId = currentUserId;
        //this.layoutResId = layoutResId;
    }

    public void setOnPlayButtonClickListener(OnPlayButtonClickListener listener) {
        playButtonClickListener = listener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SENDER_ID));
        int layoutResId;

        // Вибираємо макет в залежності від користувача
        if (userId == currentUserId) {
            layoutResId = R.layout.item_container_sent_message;
        } else {
            layoutResId = R.layout.item_container_received_message;
        }

        View view = inflater.inflate(layoutResId, parent, false);
        ImageView playButton = view.findViewById(R.id.playButton);
        return view;

    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
//        int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SENDER_ID));
//        DatabaseHelper dbHelper = new DatabaseHelper(context);
//
//        TextView messageTextView = view.findViewById(R.id.messageTextView);
//        TextView nameUser = view.findViewById(R.id.nameUser);
//
//        String messageText = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTENT));
//        String userName = dbHelper.getUserNameById(userId);
//        String messageType = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MESSAGE_TYPE));
//        ImageView messageImageView = view.findViewById(R.id.playButton);

        int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SENDER_ID));
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        TextView messageTextView = view.findViewById(R.id.messageTextView);
        TextView nameUser = view.findViewById(R.id.nameUser);

        String messageText = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTENT));
        String userName = dbHelper.getUserNameById(userId);
        String messageType = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MESSAGE_TYPE));
        ImageView playButton = view.findViewById(R.id.playButton);
        final String audioFilePath = messageText; // Припускаємо, що у вас в колонці `COLUMN_CONTENT` міститься шлях до аудіофайлу


        if (userId == currentUserId) {
            // Якщо повідомлення від поточного користувача, використовуйте макет для відправленого повідомлення

            view = LayoutInflater.from(context).inflate(R.layout.item_container_sent_message, null, false);

//            if ("audio".equals(messageType)) {
//                messageImageView.setVisibility(View.VISIBLE); // Показати іконку голосового запису для голосового повідомлення
//                messageTextView.setVisibility(View.GONE);
//            } else {
//                messageImageView.setVisibility(View.GONE); // Зробити іконку голосового запису невидимою для текстових повідомлень
//                messageTextView.setText(messageText);
//                messageTextView = view.findViewById(R.id.messageTextView);
//            }

            if ("audio".equals(messageType)) {
                playButton.setVisibility(View.VISIBLE); // Показати іконку голосового запису для голосового повідомлення
                messageTextView.setVisibility(View.GONE);
            } else {
                playButton.setVisibility(View.GONE); // Зробити іконку голосового запису невидимою для текстових повідомлень
                messageTextView.setText(messageText);
                messageTextView.setVisibility(View.VISIBLE); // Показати текст повідомлення
                nameUser.setVisibility(View.GONE); // Приховати ім'я користувача
            }

            //view = LayoutInflater.from(context).inflate(R.layout.item_container_sent_message, null, false);
            //messageTextView = view.findViewById(R.id.messageTextView); // Оновити посилання на TextView
            //messageTextView.setVisibility(View.VISIBLE); // Показати повідомлення
            //nameUser.setVisibility(View.GONE); // Приховати ім'я користувача
        } else {
            // Інакше використовуйте макет для отриманого повідомлення
            view = LayoutInflater.from(context).inflate(R.layout.item_container_received_message, null, false);

            if ("audio".equals(messageType)) {
                playButton.setVisibility(View.VISIBLE); // Показати іконку голосового запису для голосового повідомлення
                messageTextView.setVisibility(View.GONE);
            } else {
                playButton.setVisibility(View.GONE); // Зробити іконку голосового запису невидимою для текстових повідомлень
                messageTextView.setText(messageText);
                messageTextView.setVisibility(View.VISIBLE); // Показати текст повідомлення
                nameUser.setVisibility(View.VISIBLE); // Показати ім'я користувача
                nameUser.setText(userName);
            }

//            if ("audio".equals(messageType)) {
//                messageImageView.setVisibility(View.VISIBLE); // Показати іконку голосового запису для голосового повідомлення
//                messageTextView.setVisibility(View.GONE);
//            } else {
//                messageImageView.setVisibility(View.GONE); // Зробити іконку голосового запису невидимою для текстових повідомлень
//                messageTextView.setText(messageText);
//                messageTextView = view.findViewById(R.id.messageTextView);
//            }
//
//            //messageTextView = view.findViewById(R.id.messageTextView); // Оновити посилання на TextView
//            nameUser = view.findViewById(R.id.nameUser); // Оновити посилання на ім'я користувача TextView
//            //messageTextView.setVisibility(View.VISIBLE); // Показати повідомлення
//            nameUser.setVisibility(View.VISIBLE); // Показати ім'я користувача
//            nameUser.setText(userName);
        }

//        playButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (playButtonClickListener != null) {
//                    playButtonClickListener.onPlayButtonClicked(audioFilePath);
//                }
//
//                mediaPlayer = new MediaPlayer();
//
//                try {
//                    mediaPlayer.setDataSource(audioFilePath);
//                    mediaPlayer.prepare();
//                    mediaPlayer.start();
//                    Log.d("MyApp", "currentVoiceMessagePath " + audioFilePath);
//
//                    //Toast.makeText(getActivity(), "Відтворення", Toast.LENGTH_SHORT).show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (playButtonClickListener != null) {
//                    playButtonClickListener.onPlayButtonClicked(audioFilePath);
//                }

                mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(audioFilePath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Log.d("MyApp", "currentVoiceMessagePath " + audioFilePath);

                    //Toast.makeText(getActivity(), "Відтворення", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

}
