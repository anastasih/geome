package com.example.geome.Models;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
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

import androidx.core.content.ContextCompat;

import com.example.geome.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        ImageView user_icon = view.findViewById(R.id.user_icon);
        TextView time_publication = view.findViewById(R.id.time_publication);

        String messageText = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTENT));
        String userName = dbHelper.getUserNameById(userId);
        String userIcon = dbHelper.getUserIconById(userId);
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formattedDate = sdf.format(currentDate);
        
        if (userIcon != null && userIcon.length() != 0) {
            //  шлях до збереженого зображення в кеші
            File imageFile = new File(context.getCacheDir(), userIcon);

            // Перевірка, чи файл існує
            if (imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

                int desiredWidth = 27;
                int desiredHeight = 27;

                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, true);
                Bitmap roundedBitmap = getRoundedBitmap(resizedBitmap);
                user_icon.setImageBitmap(roundedBitmap);
            }
        }

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
                time_publication.setText(formattedDate);
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
                time_publication.setText(formattedDate);
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

    private Bitmap getRoundedBitmap(Bitmap srcBitmap) {
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        int size = Math.min(width, height);

        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, size, size);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);

        canvas.drawRoundRect(rectF, size / 2, size / 2, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(srcBitmap, rect, rect, paint);

        return output;
    }

}
