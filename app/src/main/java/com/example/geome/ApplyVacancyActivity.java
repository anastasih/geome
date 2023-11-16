package com.example.geome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geome.Models.AppData;
import com.example.geome.Models.AppliedVacancies;
import com.example.geome.Models.CheckboxSpinnerAdapter;
import com.example.geome.Models.DatabaseHelper;
import com.example.geome.Models.JobOffer;
import com.example.geome.Models.JobOfferAdapterServices;
import com.example.geome.Models.JobOfferServices;
import com.example.geome.Models.User;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ApplyVacancyActivity extends AppCompatActivity {
    public TextView nameVacancy, selectFiles, acceptJobOffer, nameFile;
    public EditText experienceEditText, whyEditText;
    public Spinner spinnerLanguages, spinnerEmployment;
    public ImageView buttonBack;
    public ImageButton ImageButtonMain, ImageButtonRibbon, ImageButtonCity, ImageButtonChat, ImageButtonProfile;
    public AppliedVacancies appliedVacancies;
    private List<String> selectedLanguages = new ArrayList<>();
    private List<String> selectedEmployment = new ArrayList<>();
    public int jobOfferId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_vacancy);

        initView();
        initDate();
    }

    private void initDate(){
        appliedVacancies = new AppliedVacancies();
        jobOfferId = getIntent().getIntExtra("selectedOfferIdJson", 1);
        //Toast.makeText(this, "id Job : " + jobOfferId, Toast.LENGTH_SHORT).show();
        String jobOfferJson = getIntent().getStringExtra("selectedOfferJson");
        //String selectedOffer = new Gson().fromJson(jobOfferJson, String.class);
        nameVacancy.setText(nameVacancy.getText() + "'" + jobOfferJson + "'");

    }

    private void initView(){
        nameVacancy = findViewById(R.id.nameVacancy);
        selectFiles = findViewById(R.id.selectFiles);
        experienceEditText = findViewById(R.id.experienceEditText);
        whyEditText = findViewById(R.id.whyEditText);
        spinnerLanguages = findViewById(R.id.spinnerLanguages);
        spinnerEmployment = findViewById(R.id.spinnerEmployment);
        acceptJobOffer = findViewById(R.id.acceptJobOffer);
        buttonBack = findViewById(R.id.buttonBack);
        ImageButtonMain = findViewById(R.id.ImageButtonMain);
        ImageButtonRibbon = findViewById(R.id.ImageButtonRibbon);
        ImageButtonCity = findViewById(R.id.ImageButtonCity);
        ImageButtonChat = findViewById(R.id.ImageButtonChat);
        ImageButtonProfile = findViewById(R.id.ImageButtonProfile);
        nameFile = findViewById(R.id.nameFile);

        setEditTexes();
        setSpinnerLanguages();
        setSpinnerEmployment();

        buttonBack.setOnClickListener(this::returnBack);
        ImageButtonMain.setOnClickListener(this::ImageButtonMainClick);
        ImageButtonRibbon.setOnClickListener(this::ImageButtonRibbonClick);
        ImageButtonCity.setOnClickListener(this::ImageButtonCityClick);
        ImageButtonChat.setOnClickListener(this::ImageButtonChatClick);
        ImageButtonProfile.setOnClickListener(this::ImageButtonProfileClick);
        selectFiles.setOnClickListener(this::selectFilesClick);
        acceptJobOffer.setOnClickListener(this::acceptJobOfferClick);
    }

    private void setEditTexes(){
        experienceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                appliedVacancies.setExperience(experienceEditText.getText().toString());
            }
        });
        whyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                appliedVacancies.setWhyYou(whyEditText.getText().toString());
            }
        });
    }

    public List<String> selectedlanguagesSpinner = new ArrayList<>();
    private void setSpinnerLanguages(){
        List<String> items = new ArrayList<>();
        items.add("Англійська мова");
        items.add("Німецька мова");
        items.add("Польська мова");
        items.add("Французька мова");

        LayoutInflater inflater = LayoutInflater.from(this);
        CheckboxSpinnerAdapter adapter = new CheckboxSpinnerAdapter(this, R.layout.spinner_item_layout, items, inflater, selectedlanguagesSpinner);
        spinnerLanguages.setAdapter(adapter);

        spinnerLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = items.get(position);
                adapter.toggleItem(selectedItem);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        selectedLanguages = adapter.returnSelectedItems();
    }

    public List<String> selectedEmploymentSpinner = new ArrayList<>();
    private void setSpinnerEmployment(){
        List<String> items = new ArrayList<>();
        items.add("Повний робочий день");
        items.add("Дистанційна робота");
        items.add("Частковий робочий день");
        items.add("Робота в офісі");

        LayoutInflater inflater = LayoutInflater.from(this);
        CheckboxSpinnerAdapter adapter = new CheckboxSpinnerAdapter(this, R.layout.spinner_item_layout, items, inflater, selectedEmploymentSpinner);
        spinnerEmployment.setAdapter(adapter);
        spinnerEmployment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = items.get(position);
                adapter.toggleItem(selectedItem);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        selectedEmployment = adapter.returnSelectedItems();
    }
    private void ImageButtonProfileClick(View view) {
        Intent intent = new Intent(ApplyVacancyActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ProfileFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonChatClick(View view) {
        Intent intent = new Intent(ApplyVacancyActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", ChatFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonCityClick(View view) {
        Intent intent = new Intent(ApplyVacancyActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", CityFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonRibbonClick(View view) {
        Intent intent = new Intent(ApplyVacancyActivity.this, MainBottomMenuActivity.class);
        intent.putExtra("fragmentName", NewsFeedFragment.class.getName());
        startActivity(intent);
    }
    private void ImageButtonMainClick(View view) {
        Intent intent = new Intent(ApplyVacancyActivity.this, HomePage.class);
        startActivity(intent);
    }
    private void returnBack(View view){
        onBackPressed();
    }

    private void acceptJobOfferClick(View view){
        String resultStringLanguages = String.join(", ", selectedLanguages);
        String resultStringEmployment = String.join(", ", selectedEmployment);
        String resultStringFilesNames = String.join(", ", fileList);

        appliedVacancies.setEmployment(resultStringEmployment);
        appliedVacancies.setLanguages(resultStringLanguages);
        appliedVacancies.setFilesNames(resultStringFilesNames);

        appliedVacancies.setIdVacancy(jobOfferId);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        User user = AppData.getInstance().getUser();
        int idUser = databaseHelper.getUserId(user.getUserPhone());
        appliedVacancies.setIdUser(idUser);

        if(whyEditText.getText().length() > 0 || experienceEditText.getText().length() > 0
                || !resultStringFilesNames.isEmpty() || !resultStringLanguages.isEmpty()
                || !resultStringEmployment.isEmpty()){
            DatabaseHelper db = new DatabaseHelper(ApplyVacancyActivity.this);
            long result = db.addAppliedVacancy(appliedVacancies.getIdVacancy(), appliedVacancies.getIdUser(),
                    appliedVacancies.getExperience(), appliedVacancies.getWhyYou(), appliedVacancies.getLanguages(),
                    appliedVacancies.getEmployment(), appliedVacancies.getFilesNames());
            if (result == -1) {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(this, JobOfferDetailsActivity.class);
            startActivity(intent);
        }
    }

    private static final int PICK_FILE_REQUEST_CODE = 1;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 2;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 124;
    private List<String> fileList = new ArrayList<>();
    private static final int PICK_IMAGE_REQUEST = 1;

    public static String[] storage_permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storage_permissions_33 = {
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_VIDEO
    };

    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storage_permissions_33;
        } else {
            p = storage_permissions;
        }
        return p;
    }
    private void selectFilesClick(View view) {
        Log.d("FilePicker", "selectFilesClick: Opening file picker.");
        ActivityCompat.requestPermissions(ApplyVacancyActivity.this,
                permissions(),
                1);
        openFilePicker();
    }
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Виберіть файл"), PICK_FILE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFilePicker();
            } else {
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedFileUri = data.getData();

            if (selectedFileUri != null) {
                saveFileToDesktop(selectedFileUri);
                updateFileList();
            }
        }
    }

    private void updateFileList() {
        fileList.clear();

        File desktopDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File[] files = desktopDirectory.listFiles();

        if (files != null) {
            for (File file : files) {
                fileList.add(file.getName());
                nameFile.setText(file.getName());
            }
        }
    }
    private void saveFileToDesktop(Uri fileUri) {
        try (InputStream in = getContentResolver().openInputStream(fileUri)) {
            if (in != null) {
                File desktopDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                // Отримання імені файлу з URI
                String fileName = getFileName(fileUri);

                // Створення нового файлу в каталозі робочого столу
                File destFile = new File(desktopDirectory, fileName);

                try (OutputStream out = new FileOutputStream(destFile)) {
                    byte[] buffer = new byte[1024];
                    int length;

                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }

                    Log.d("FileSave", "Файл збережено на робочому столі: " + destFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("FileSave", "Помилка збереження файлу: " + e.getMessage());
                }
            } else {
                Log.e("FileSave", "InputStream є порожнім");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("FileSave", "Помилка відкриття InputStream: " + e.getMessage());
        }
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private String getFileName(Uri fileUri) {
        String fileName = null;
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DISPLAY_NAME};
            cursor = getContentResolver().query(fileUri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                fileName = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("FilePicker", "Error getting file name: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return fileName;
    }
}