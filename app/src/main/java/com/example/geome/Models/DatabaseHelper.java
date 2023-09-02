package com.example.geome.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "test1.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "user_name";
    public static final String COLUMN_PHONE = "user_phone";
    public static final String COLUMN_PASSWORD = "user_password";
    public static final String COLUMN_CITY = "user_city";
    public static final String COLUMN_GENDER = "user_gender";
    public static final String COLUMN_AGE = "user_age";
    public static final String COLUMN_ACCESS_GEO = "access_geo";
    public static final String COLUMN_PRIVATE_POLICY = "private_policy";
    public static final String COLUMN_USER_OFFERS = "user_offers";
    public static final String COLUMN_NOTIFICATION_PROMOTIONS = "notification_promotions";

    // New table for categories
    public static final String TABLE_CATEGORIES = "categories";
    public static final String COLUMN_CATEGORY_ID = "_category_id";
    public static final String COLUMN_CATEGORY_NAME = "category_name";

    // створення категорій для окремого користувача (при реєстрації)
    public static final String TABLE_USER_CATEGORY = "user_category";
    public static final String COLUMN_UC_ID = "id";
    public static final String COLUMN_UC_USER_ID = "id_user";
    public static final String COLUMN_UC_CATEGORY_ID = "id_category";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_CITY + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_ACCESS_GEO + " INTEGER, " +
                COLUMN_PRIVATE_POLICY + " INTEGER, " +
                COLUMN_USER_OFFERS + " INTEGER, " +
                COLUMN_NOTIFICATION_PROMOTIONS + " INTEGER);";
        db.execSQL(query);

        String categoryTableQuery = "CREATE TABLE " + TABLE_CATEGORIES + " (" +
                COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY_NAME + " TEXT);";

        db.execSQL(categoryTableQuery);

        // Insert predefined categories
        insertCategory(db, "Проживання");
        insertCategory(db, "Відпочинок та розваги");
        insertCategory(db, "Послуги краси");
        insertCategory(db, "Заклади харчування");
        insertCategory(db, "Доставка продуктів");

        String userCategoryTableQuery = "CREATE TABLE " + TABLE_USER_CATEGORY + " (" +
                COLUMN_UC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_UC_USER_ID + " INTEGER, " +
                COLUMN_UC_CATEGORY_ID + " INTEGER);";
        db.execSQL(userCategoryTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addUser(String name, String phone, String password, String city, String gender, int age, boolean accessGeo,
                 boolean privatePolicy, boolean userOffers, boolean notificationPromotions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_CITY, city);
        cv.put(COLUMN_GENDER, gender);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_ACCESS_GEO, accessGeo);
        cv.put(COLUMN_PRIVATE_POLICY, privatePolicy);
        cv.put(COLUMN_USER_OFFERS, userOffers);
        cv.put(COLUMN_NOTIFICATION_PROMOTIONS, notificationPromotions);


        long result = db.insert(TABLE_NAME, null, cv);

        return result;
    }

    public long addUserCategory(int userId, int categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_UC_USER_ID, userId);
        cv.put(COLUMN_UC_CATEGORY_ID, categoryId);

        long result = db.insert(TABLE_USER_CATEGORY, null, cv);

        return result;
    }
    public int getCategoryId(String categoryName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_CATEGORY_ID + " FROM " + TABLE_CATEGORIES +
                " WHERE " + COLUMN_CATEGORY_NAME + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{categoryName});

        int categoryId = -1; // Default value

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_CATEGORY_ID);
            if (columnIndex != -1) {
                categoryId = cursor.getInt(columnIndex);
            }
        }

        cursor.close();
        return categoryId;
    }

    public int getUserId(String userPhone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_PHONE + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{userPhone});

        int userId = -1; // Default value

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_ID);
            if (columnIndex != -1) {
                userId = cursor.getInt(columnIndex);
            }
        }

        cursor.close();
        return userId;
    }
    private long insertCategory(SQLiteDatabase db, String categoryName) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CATEGORY_NAME, categoryName);
        return db.insert(TABLE_CATEGORIES, null, cv);
    }

}
