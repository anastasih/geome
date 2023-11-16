package com.example.geome;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.Models.AppData;
import com.example.geome.Models.CheckboxSpinnerAdapter;
import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProfileSettingsFragment extends Fragment {
    public EditText nameEditText, phoneEditText, passwordEditText;
    public Spinner spinnerCity;
    public CheckBox checkboxLocation;
    public ImageView userPhoto, seePassword, buttonBack;
    private User user, newUser, userInfo;
    private TextView buttonNext;
    private FrameLayout addPhoto;
    public View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_settings, container, false);

        initDate();
        initView(rootView);

        return rootView;
    }
    private void initView(View rootView){
        nameEditText = rootView.findViewById(R.id.nameEditText);
        phoneEditText = rootView.findViewById(R.id.phoneEditText);
        passwordEditText = rootView.findViewById(R.id.passwordEditText);
        spinnerCity = rootView.findViewById(R.id.spinnerCity);
        checkboxLocation = rootView.findViewById(R.id.checkboxLocation);
        userPhoto = rootView.findViewById(R.id.userPhoto);
        buttonNext = rootView.findViewById(R.id.buttonNext);
        seePassword = rootView.findViewById(R.id.password_see);
        buttonBack = rootView.findViewById(R.id.buttonBack);
        addPhoto = rootView.findViewById(R.id.addPhoto);

        buttonNext.setOnClickListener(this::buttonNextClick);
        seePassword.setOnClickListener(this::toSeePassword);
        buttonBack.setOnClickListener(this::buttonBackClick);
        addPhoto.setOnClickListener(this::addPhotoClick);

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        user = AppData.getInstance().getUser();
        userInfo = dbHelper.getUserByPhone(user.getUserPhone());
        String originalString = userInfo.getUserPhone();
        String trimmedString = originalString.substring(3);
        nameEditText.setText(userInfo.getUserName());
        phoneEditText.setText(trimmedString);
        passwordEditText.setText(userInfo.getUserPassword());

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

        setEditTexes();
        setSpinnerCity();
        setCheckboxes();
    }
    private void initDate(){
        newUser = new User();
    }

    private void setEditTexes(){
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                newUser.setUserName(nameEditText.getText().toString());
            }
        });

        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                newUser.setUserPhone("380" + phoneEditText.getText().toString());
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                newUser.setUserPassword(passwordEditText.getText().toString());
            }
        });
    }
    private void setSpinnerCity(){
//        DatabaseHelper db = new DatabaseHelper(getContext());
//        Map<Integer, String> cityData = db.getCityData();
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db1 = dbHelper.getReadableDatabase();
        Map<Integer, String> cityData = dbHelper.getCityData(db1);
        List<String> cityNames = new ArrayList<>(cityData.values());
        List<Integer> cityIds = new ArrayList<>(cityData.keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cityNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter);

        int selectedCityId = userInfo.getUserCity();
        int position = cityIds.indexOf(selectedCityId);
        if (position >= 0) {
            spinnerCity.setSelection(position);
            newUser.setUserCity(position);
        }

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedCityId = cityIds.get(position);
                newUser.setUserCity(selectedCityId);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setCheckboxes(){
        checkboxLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    newUser.setAccessGeo(true);
                } else {
                    newUser.setAccessGeo(false);
                }
            }
        });
    }

    private List<Integer> categoryIds;

    private void buttonNextClick(View view) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        int id = dbHelper.getUserId(user.getUserPhone());

        if (newUser.getUserName() != null) {
            userInfo.setUserName(newUser.getUserName());
        }

        if (newUser.getUserPhone() != null) {
            userInfo.setUserPhone(newUser.getUserPhone());
        }

        if (newUser.getUserPassword() != null) {
            userInfo.setUserPassword(newUser.getUserPassword());
        }

        if (newUser.getUserCity() != 0) {
            userInfo.setUserCity(newUser.getUserCity());
        }

        if (newUser.isAccessGeo()) {
            userInfo.setAccessGeo(true);
        } else {
            userInfo.setAccessGeo(false);
        }

        if (newUser.getUserPhoto() != null) {
            userInfo.setUserPhoto(newUser.getUserPhoto());
        }

        long result = dbHelper.updateUser(id, userInfo.getUserName(), userInfo.getUserPhone(),
                userInfo.getUserPassword(), userInfo.getUserCity(), userInfo.getGender(),
                userInfo.getAge(), userInfo.isAccessGeo(), userInfo.isPrivatePolicy(),
                userInfo.isUserOffers(), userInfo.isNotificationPromotions(), userInfo.getUserPhoto());

        if (result > 0) {
            Toast.makeText(getContext(), "Запис оновлено успішно", Toast.LENGTH_SHORT).show();
            user.setUserPhone(userInfo.getUserPhone());

            Intent intent = new Intent(getContext(), MainBottomMenuActivity.class);
            intent.putExtra("fragmentName", ProfileFragment.class.getName());
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Запис не був знайдений або не оновлено", Toast.LENGTH_SHORT).show();
        }
    }

    private void toSeePassword(View view){
        if (passwordEditText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void buttonBackClick(View view){
        Intent intent = new Intent(getContext(), MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ProfileFragment.class.getName());
        startActivity(intent);
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    private void addPhotoClick(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // URI обраної фотографії
            Uri selectedImageUri = data.getData();
            ImageView imageView = rootView.findViewById(R.id.userPhoto);
            imageView.setImageURI(selectedImageUri);

            // шлях обраної фотографії
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            String fileName = new File(filePath).getName();

//            String[] parts = fileName.split("\\.");
//            String photoName = parts[0];

            newUser.setUserPhoto(fileName);

            savePhotoToCache(selectedImageUri, fileName);
        }
    }

    private void savePhotoToCache(Uri imageUri, String photoName) {
        try {
            // Отримуємо доступ до каталогу кешу
            File cacheDir = getContext().getCacheDir();

            // Створюємо новий файл для фотографії в кеші
            File photoFile = new File(cacheDir, photoName);

            // Створюємо вихідний потік для запису фотографії в файл
            FileOutputStream outputStream = new FileOutputStream(photoFile);

            // Відкриваємо вхідний потік для фотографії
            InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);

            // Копіюємо дані з вхідного потоку в вихідний
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Закриваємо потоки
            outputStream.close();
            inputStream.close();

            // Фото тепер збережено в кеші
        } catch (IOException e) {
            e.printStackTrace();
            // Виникла помилка під час збереження фотографії
        }
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