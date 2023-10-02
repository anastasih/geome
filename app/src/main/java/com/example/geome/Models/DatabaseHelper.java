package com.example.geome.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    // для створення категорій компаній
    public static final String TABLE_COMPANY_CATEGORY = "company_category";
    public static final String COLUMN_COM_CAT_ID = "id";
    public static final String COLUMN_COM_CAT_NAME = "category_name";

    //для створення компанії
    public static final String TABLE_COMPANY = "company";
    public static final String COLUMN_COM_ID = "id";
    public static final String COLUMN_COM_NAME = "company_name";
    public static final String COLUMN_COM_ID_CATEGORY = "id_category";
    public static final String COLUMN_COM_PHOTO = "company_photo";
    public static final String COLUMN_COM_DESCRIPTION = "company_description";
    public static final String COLUMN_COM_ADDRESS = "company_address";
    public static final String COLUMN_COM_RATING = "company_rating";

    //для стрічки новин і пропозицій (нижнє меню екрану)
    public static final String TABLE_NEWS_FEED = "news_feed";
    public static final String COLUMN_NEWS_FEED_ID = "id";
    public static final String COLUMN_ID_COMPANY = "id_company";
    public static final String COLUMN_NEWS_FEED_PHOTO = "photo";
    public static final String COLUMN_NEWS_FEED_DESCRIPTION = "description";
    public static final String COLUMN_NEWS_FEED_TIME = "time";

    // Таблиця для чатів
    public static final String TABLE_CHAT = "chat";
    public static final String COLUMN_CHAT_ID = "chat_id";
    public static final String COLUMN_CITY_NAME = "city_name"; // Назва міста, до якого відноситься чат

    // Таблиця для повідомлень
    public static final String TABLE_MESSAGES = "messages";
    public static final String COLUMN_MESSAGE_ID = "message_id";
    public static final String COLUMN_CHAT_ID_FK = "chat_id"; // Зовнішній ключ до таблиці чатів
    public static final String COLUMN_SENDER_ID = "user_id"; // ID користувача, який відправив повідомлення
    //public static final String COLUMN_MESSAGE_TEXT = "message_text";
    public static final String COLUMN_MESSAGE_TYPE = "message_type"; // Тип повідомлення (текстове або голосове)
    public static final String COLUMN_CONTENT = "content"; // Зміст повідомлення (текст або шлях до голосового файлу)
    public static final String COLUMN_MESSAGE_TIME = "message_time";
    //public static final String COLUMN_AUDIO_PATH = "audio_path";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // створення таблички user
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

        // створення таблички category
        String categoryTableQuery = "CREATE TABLE " + TABLE_CATEGORIES + " (" +
                COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY_NAME + " TEXT);";
        db.execSQL(categoryTableQuery);
        insertCategory(db, "Проживання");
        insertCategory(db, "Відпочинок та розваги");
        insertCategory(db, "Послуги краси");
        insertCategory(db, "Заклади харчування");
        insertCategory(db, "Доставка продуктів");

        // створення таблички user_category
        String userCategoryTableQuery = "CREATE TABLE " + TABLE_USER_CATEGORY + " (" +
                COLUMN_UC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_UC_USER_ID + " INTEGER, " +
                COLUMN_UC_CATEGORY_ID + " INTEGER);";
        db.execSQL(userCategoryTableQuery);

        // створення таблички company_category
        String companyCategoryTableQuery = "CREATE TABLE " + TABLE_COMPANY_CATEGORY + " (" +
                COLUMN_COM_CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COM_CAT_NAME + " TEXT);";
        db.execSQL(companyCategoryTableQuery);
        insertCategoryCompany(db, "Барбершоп");
        insertCategoryCompany(db, "Б'юті-салон");
        insertCategoryCompany(db, "Приватна медицина");
        insertCategoryCompany(db, "Адвокатські компанії");
        insertCategoryCompany(db, "Заклад харчування");
        insertCategoryCompany(db, "Супермаркет");
        insertCategoryCompany(db, "Готель");
        insertCategoryCompany(db, "Апартаменти");
        insertCategoryCompany(db, "Таксі");

        // створення таблички news_feed
        String newsFeedTableQuery = "CREATE TABLE " + TABLE_NEWS_FEED + " (" +
                COLUMN_NEWS_FEED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID_COMPANY + " INTEGER, " +
                COLUMN_NEWS_FEED_PHOTO + " TEXT, " +
                COLUMN_NEWS_FEED_DESCRIPTION + " TEXT, " +
                COLUMN_NEWS_FEED_TIME + " TEXT);";
        db.execSQL(newsFeedTableQuery);

        // створення таблички company
        String companyTableQuery = "CREATE TABLE " + TABLE_COMPANY + " (" +
                COLUMN_COM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COM_NAME + " TEXT, " +
                COLUMN_COM_ID_CATEGORY + " INTEGER, " +
                COLUMN_COM_PHOTO + " TEXT, " +
                COLUMN_COM_DESCRIPTION + " TEXT, " +
                COLUMN_COM_ADDRESS + " TEXT, " +
                COLUMN_COM_RATING + " REAL);";
        db.execSQL(companyTableQuery);

        insertCompany(db,"Ресторан CafeCarrot", 5, "cafecarrot_icon",
                "lorem", "", 4);
        insertCompany(db,"Bolt", 9, "bolt_icon",
                "lorem", "", 4);
        insertCompany(db,"Нічний клуб Фendo", 5, "fendo_icon",
                "lorem", "", 4);
        insertCompany(db,"Hotel Island", 7, "hotelisland_icon",
                "lorem", "", 4);
        insertCompany(db,"Diamond BeautyStudio", 2, "beautybeautystudio_icon",
                "lorem", "", 4);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(currentDate);


        insertNewsFeed(db,1, "", "Сьогодні ввечері проводимо " +
                "відкритий мікрофон для усіх охочих. Запрошуємо мешканців та гостей міста розказати " +
                "свою історію, пожартувати або організувати музичний виступ. Буде круто! :)",
                currentTime);
        insertNewsFeed(db,2, "", "А ви знали, що Bolt дарує " +
                        "знижки усім, хто вперше приїхав до Житомира та ще не користувався нашим " +
                        "сервісом? Ви можете замовити таксі або їжу, запрошуємо!",
                currentTime);
        insertNewsFeed(db,3, "fendo_news", "Приготуйтеся до незабутньої " +
                        "ночі з DJ BEE, який запалить танцпол своїми гарячими міксами! Приходьте " +
                        "разом з друзями та близькими - це буде вечір, що залишиться в пам'яті " +
                        "на довго. А ще, долучайтесь до благодійної ініціативи, оскільки 25% " +
                        "від вартості квитка будуть передані на підтримку наших захисників ЗСУ. " +
                        "Весело проведемо час та зробимо разом добру справу! До зустрічі в " +
                        "нашому клубі після 21:00! \uD83C\uDFB5\uD83C\uDF89\uD83E\uDD42\uD83C\uDDFA\uD83C\uDDE6",
                currentTime);
        insertNewsFeed(db,4, "", "Лише до кінця тижня: ранні " +
                        "бронювання дають можливість отримати безкоштовну вечерю в перший день " +
                        "заїзду. Не пропустіть цю можливість!",
                currentTime);
        insertNewsFeed(db,5, "diamondstudio_news", "\uD83D\uDC8E Раді представити Вам " +
                        "найблискучішу акцію цього сезону від Diamond BeautyStudio! \uD83D\uDC8E\n" +
                        "Сяйте разом з нами, адже ми даруємо вам унікальну можливість отримати " +
                        "розкішний макіяж та стильну зачіску - за ціною однієї з цих послуг! " +
                        "Завітайте до нашого салону краси та насолоджуйтесь професійним " +
                        "доглядом від наших талановитих стилістів.\n" +
                        "Чекаємо на вас з нетерпінням! Поспішайте, кількість місць обмежена. " +
                        "Нехай ваша краса світиться разом із нами - Diamond BeautyStudio. \uD83D\uDC84\uD83D\uDC87\u200D♀️\uD83D\uDC85",
                currentTime);


        // Таблиця для чатів
        String chatTableQuery = "CREATE TABLE " + TABLE_CHAT + " (" +
                COLUMN_CHAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CITY_NAME + " TEXT);";
        db.execSQL(chatTableQuery);

        // Таблиця для повідомлень
        String messagesTableQuery = "CREATE TABLE " + TABLE_MESSAGES + " (" +
                COLUMN_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CHAT_ID_FK + " INTEGER, " +
                COLUMN_SENDER_ID + " INTEGER, " +
                COLUMN_MESSAGE_TYPE + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_MESSAGE_TIME + " TEXT);";
        db.execSQL(messagesTableQuery);

        // Заповнення таблиці чатів даними про міста
        insertCity(db, "Житомир");
        insertCity(db, "Львів");
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

    public String getUserNameById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_NAME + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " = ?"; // Змінено COLUMN_NAME на COLUMN_ID

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)}); // Змінено тип параметра на String та передано id у вигляді рядка

        String userName = null; // Змінено тип результату на String

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_NAME);
            if (columnIndex != -1) {
                userName = cursor.getString(columnIndex); // Змінено отримання значення на getString
            }
        }

        cursor.close();
        return userName;
    }
    private long insertCategory(SQLiteDatabase db, String categoryName) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CATEGORY_NAME, categoryName);
        return db.insert(TABLE_CATEGORIES, null, cv);
    }

    private long insertCategoryCompany(SQLiteDatabase db, String categoryName) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_COM_CAT_NAME, categoryName);
        return db.insert(TABLE_COMPANY_CATEGORY, null, cv);
    }

    public long insertCompany(SQLiteDatabase db,String companyName, int categoryId, String companyPhoto,
                              String companyDescription, String companyAddress,
                              double companyRating) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_COM_NAME, companyName);
        cv.put(COLUMN_COM_ID_CATEGORY, categoryId);
        cv.put(COLUMN_COM_PHOTO, companyPhoto);
        cv.put(COLUMN_COM_DESCRIPTION, companyDescription);
        cv.put(COLUMN_COM_ADDRESS, companyAddress);
        cv.put(COLUMN_COM_RATING, companyRating);
        return db.insert(TABLE_COMPANY, null, cv);
    }

    public long insertNewsFeed(SQLiteDatabase db, int companyId, String newsFeedPhoto, String newsFeedDescription,
                               String newsFeedTime) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID_COMPANY, companyId);
        cv.put(COLUMN_NEWS_FEED_PHOTO, newsFeedPhoto);
        cv.put(COLUMN_NEWS_FEED_DESCRIPTION, newsFeedDescription);
        cv.put(COLUMN_NEWS_FEED_TIME, newsFeedTime);
        return db.insert(TABLE_NEWS_FEED, null, cv);
    }

    public String getCompanyNameById(int companyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_COM_NAME + " FROM " + TABLE_COMPANY +
                " WHERE " + COLUMN_COM_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(companyId)});

        String companyName = null;

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_COM_NAME);
            if (columnIndex != -1) {
                companyName = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        return companyName;
    }

    public String getCompanyLogoNameById(int companyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_COM_PHOTO + " FROM " + TABLE_COMPANY +
                " WHERE " + COLUMN_COM_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(companyId)});

        String companyLogoName = null;

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_COM_PHOTO);
            if (columnIndex != -1) {
                companyLogoName = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        return companyLogoName;
    }

    //для пошуку новин за ключовими словами
    public List<Map<String, Object>> searchNewsByKeywords(String keywords) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Map<String, Object>> newsList = new ArrayList<>();

        String query = "SELECT nf.*, c." + COLUMN_COM_NAME + " AS company_name " +
                "FROM " + TABLE_NEWS_FEED + " nf " +
                "INNER JOIN " + TABLE_COMPANY + " c ON nf." + COLUMN_ID_COMPANY + " = c." + COLUMN_COM_ID +
                " WHERE nf." + COLUMN_NEWS_FEED_DESCRIPTION + " LIKE ?" +
                " OR c." + COLUMN_COM_NAME + " LIKE ?";


        Cursor cursor = db.rawQuery(query, new String[]{"%" + keywords + "%", "%" + keywords + "%"});

        if (cursor.moveToFirst()) {
            do {
                int newsId = cursor.getInt(cursor.getColumnIndex(COLUMN_NEWS_FEED_ID));
                int companyId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_COMPANY));
                String newsPhoto = cursor.getString(cursor.getColumnIndex(COLUMN_NEWS_FEED_PHOTO));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_NEWS_FEED_DESCRIPTION));
                String companyName = cursor.getString(cursor.getColumnIndex("company_name")); // Отримуємо назву компанії
                String time = cursor.getString(cursor.getColumnIndex(COLUMN_NEWS_FEED_TIME));
                try {
                    String dateFormatPattern = "yyyy-MM-dd HH:mm:ss";
                    SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.US);
                    Date date = dateFormat.parse(time);

                    // Створюємо Map для об'єднання даних про новини та компанію
                    Map<String, Object> newsItem = new HashMap<>();
                    newsItem.put("newsId", newsId);
                    newsItem.put("companyId", companyId);
                    newsItem.put("newsPhoto", newsPhoto);
                    newsItem.put("description", description);
                    newsItem.put("companyName", companyName);
                    newsItem.put("date", date);

                    newsList.add(newsItem);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return newsList;
    }

    private long insertCity(SQLiteDatabase db, String cityName) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CITY_NAME, cityName);
        return db.insert(TABLE_CHAT, null, cv);
    }

    // метод для отримання ідентифікатора чату за назвою міста
    public int getChatIdByCityName(String cityName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_CHAT_ID + " FROM " + TABLE_CHAT +
                " WHERE " + COLUMN_CITY_NAME + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{cityName});

        int chatId = -1; // Значення за замовчуванням

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_CHAT_ID);
            if (columnIndex != -1) {
                chatId = cursor.getInt(columnIndex);
            }
        }

        cursor.close();
        return chatId;
    }

    // метод для отримання повідомлень для даного чату (міста)
//    public Cursor getMessagesForChat(int chatId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM " + TABLE_MESSAGES +
//                " WHERE " + COLUMN_CHAT_ID_FK + " = ?";
//
//        return db.rawQuery(query, new String[]{String.valueOf(chatId)});
//    }

    // Оновлений метод для отримання повідомлень для чату
    public Cursor getMessagesForChat(int chatId) {
        SQLiteDatabase db = this.getReadableDatabase();
        //String[] columns = {COLUMN_MESSAGE_ID, COLUMN_MESSAGE_TYPE, COLUMN_CONTENT}; // Додайте COLUMN_ID
        //String[] columns = {COLUMN_MESSAGE_TYPE, COLUMN_CONTENT}; // Додайте COLUMN_AUDIO_PATH
        //String[] columns = {COLUMN_MESSAGE_ID + " AS _id", COLUMN_MESSAGE_TYPE, COLUMN_CONTENT}; // Add COLUMN_MESSAGE_ID with alias _id

        String[] columns = {COLUMN_MESSAGE_ID + " AS _id", COLUMN_SENDER_ID, COLUMN_MESSAGE_TYPE, COLUMN_CONTENT};


        String selection = COLUMN_CHAT_ID_FK + " = ?";
        String[] selectionArgs = {String.valueOf(chatId)};

        return db.query(TABLE_MESSAGES, columns, selection, selectionArgs, null, null, null);
    }

    // Оновлений метод для вставки нового повідомлення
    public long insertMessage(int chatId, int senderId, String message_type, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CHAT_ID_FK, chatId);
        cv.put(COLUMN_SENDER_ID, senderId);
        cv.put(COLUMN_MESSAGE_TYPE, message_type);
        cv.put(COLUMN_CONTENT, content);
        cv.put(COLUMN_MESSAGE_TIME, getCurrentTimestamp());

        long result = db.insert(TABLE_MESSAGES, null, cv);
        db.close();
        return result;
    }



    // метод для отримання повідомлень для даного чату (міста)
//    public Cursor getMessagesForChat(int chatId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT " + COLUMN_MESSAGE_TEXT + " AS _id, * FROM " + TABLE_MESSAGES +
//                " WHERE " + COLUMN_CHAT_ID_FK + " = ?";
//
//        return db.rawQuery(query, new String[]{String.valueOf(chatId)});
//    }
//
//    // Метод для вставки нового повідомлення
//    public long insertMessage(int chatId, int senderId, String messageText) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_CHAT_ID_FK, chatId);
//        cv.put(COLUMN_SENDER_ID, senderId);
//        cv.put(COLUMN_MESSAGE_TEXT, messageText);
//        cv.put(COLUMN_MESSAGE_TIME, getCurrentTimestamp()); // Отримайте поточний час як строку
//
//        long result = db.insert(TABLE_MESSAGES, null, cv);
//        db.close();
//        return result;
//    }

    // Отримання поточного часу у форматі "yyyy-MM-dd HH:mm:ss"
    private String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    // пошук міста за номером користувача
    public String getCityForUser(String userPhone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_CITY + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_PHONE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userPhone});
        String cityName = null;

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_CITY);
            if (columnIndex != -1) {
                cityName = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        return cityName;
    }


}
