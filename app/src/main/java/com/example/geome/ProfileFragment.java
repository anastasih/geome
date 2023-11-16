package com.example.geome;

import android.content.Intent;
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
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geome.Models.AppData;
import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.User;

import java.io.File;

public class ProfileFragment extends Fragment {

    private User user, userInfo;
    private TextView user_name, settingsProfileButton, user_city, workButton;
    private ImageView userPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        initView(rootView);
        initDate();

        return rootView;
    }

    private void initDate(){
        user = AppData.getInstance().getUser();
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        userInfo = databaseHelper.getUserByPhone(user.getUserPhone());
        user_name.setText(userInfo.getUserName());
        String city = databaseHelper.getCityById(userInfo.getUserCity());
        if(city != null && city.length() > 0){
            user_city.setText("м. " + city);
        }

        String imageName = userInfo.getUserPhoto();
        if (imageName != null && imageName.length() != 0) {
            //  шлях до збереженого зображення в кеші
            File imageFile = new File(getContext().getCacheDir(), imageName);

            // Перевірка, чи файл існує
            if (imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                int desiredWidth = 126;
                int desiredHeight = 126;

                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, true);
                Bitmap roundedBitmap = getRoundedBitmap(resizedBitmap);
                userPhoto.setImageBitmap(roundedBitmap);
            }
        }
        else {
            imageName = "user";
            Drawable drawable = ContextCompat.getDrawable(getContext(), getResources().getIdentifier(imageName, "drawable", getContext().getPackageName()));
            userPhoto.setImageDrawable(drawable);
        }
    }

    private void initView(View rootView){
        user_name = rootView.findViewById(R.id.user_name);
        user_city = rootView.findViewById(R.id.user_city);
        userPhoto = rootView.findViewById(R.id.userPhoto);
        settingsProfileButton = rootView.findViewById(R.id.settingsProfileButton);
        workButton = rootView.findViewById(R.id.workButton);

        settingsProfileButton.setOnClickListener(this::settingsProfileButtonClick);
        workButton.setOnClickListener(this::workButtonClick);
    }

    public void workButtonClick(View view){
        Intent intent = new Intent(getContext(), WorkActivity.class);
        startActivity(intent);
    }

    public void settingsProfileButtonClick(View view){
        Intent intent = new Intent(getContext(), MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ProfileSettingsFragment.class.getName());
        startActivity(intent);
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