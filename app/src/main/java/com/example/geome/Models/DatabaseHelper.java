package com.example.geome.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.TimeZone;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.geome.CityFragment;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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
    public static final String COLUMN_USER_PHOTO = "user_photo";
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
    //public static final String COLUMN_COM_ADDRESS = "company_address";
    public static final String COLUMN_COM_RATING = "company_rating";
    public static final String COLUMN_COM_EMAIL = "company_email";
    public static final String COLUMN_COM_PHONE = "company_phone";

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
    public static final String COLUMN_CITY_CHAT_ID = "city_id"; // Назва міста, до якого відноситься чат

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

    //таблиця для міста
    public static final String TABLE_CITY = "city";
    public static final String COLUMN_CITY_ID = "city_id";
    public static final String COLUMN_CITY_TITLE = "city_name";
    public static final String COLUMN_CITY_DESCRIPTION = "city_description";
    public static final String COLUMN_CITY_PHOTO = "city_photo";

    // для звязування таблиць компанія і місто
    public static final String TABLE_COMPANY_CITY = "company_city";
    public static final String COLUMN_COMPANY_CITY_ID = "company_city_id";
    public static final String COLUMN_COM_COMPANY_ID = "company_id"; // Зовнішній ключ для компанії
    public static final String COLUMN_COM_CITY_ID = "city_id"; // Зовнішній ключ для міста

    // для деталей про компанію
    public static final String TABLE_COMPANY_DETAILS = "company_details";
    public static final String COLUMN_COMPANY_DETAILS_ID = "company_details_id";
    public static final String COLUMN_COMPANY_DETAILS_ADDRESS = "company_address";
    public static final String COLUMN_COMPANY_DETAILS_PHOTO = "company_photo";
    public static final String COLUMN_COMPANY_CITY_ID_TABLE = "company_city_id"; // зовнішній ключ для TABLE_COMPANY_CITY

    // для вакансії
    public static final String TABLE_JOB_OFFER = "job_offer";
    public static final String COLUMN_JOB_OFFER_ID = "id";
    public static final String COLUMN_JOB_OFFER_ID_COMPANY = "id_company";
    public static final String COLUMN_JOB_OFFER_COMPANY_NAME = "company_name";
    public static final String COLUMN_JOB_OFFER_NAME = "offer_name";
    public static final String COLUMN_JOB_OFFER_DESCRIPTION = "description";
    public static final String COLUMN_JOB_OFFER_SALARY = "salary";
    public static final String COLUMN_JOB_OFFER_EMAIL = "email";
    public static final String COLUMN_JOB_OFFER_PHONE = "phone";
    public static final String COLUMN_JOB_OFFER_REQUIREMENTS = "requirements";
    public static final String COLUMN_JOB_OFFER_CATEGORY = "category";
    public static final String COLUMN_JOB_OFFER_CATEGORY_COMPANY = "category_company";
    public static final String COLUMN_JOB_OFFER_KEYWORDS = "keywords";

    // для вакансій з сервісів пошуку роботи
    public static final String TABLE_JOB_OFFER_SERVICES= "job_offer_services";
    public static final String COLUMN_JOB_OFFER_SERVICES_ID = "id";
    public static final String COLUMN_JOB_OFFER_SERVICES_COMPANY_NAME = "company_name";
    public static final String COLUMN_JOB_OFFER_SERVICES_COMPANY_ICON = "company_icon";
    public static final String COLUMN_JOB_OFFER_SERVICES_COMPANY_CATEGORY_ID = "category_id";
    public static final String COLUMN_JOB_OFFER_SERVICES_NAME = "offer_name";
    public static final String COLUMN_JOB_OFFER_SERVICES_ADDRESS = "adress";
    public static final String COLUMN_JOB_OFFER_SERVICES_CITY_ID = "city_id";
    public static final String COLUMN_JOB_OFFER_SERVICES_DESCRIPTION = "description";
    public static final String COLUMN_JOB_OFFER_SERVICES_SALARY = "salary";
    public static final String COLUMN_JOB_OFFER_SERVICES_KEYWORDS = "keywords";
    public static final String COLUMN_JOB_OFFER_SERVICES_LINK = "link";
    public static final String TABLE_HIDDEN_USER_SERVICES = "hidden_user_services";
    public static final String COLUMN_HIDDEN_USER_SERVICES_ID = "id";
    public static final String COLUMN_HIDDEN_USER_SERVICES_ID_USER = "id_user";
    public static final String COLUMN_HIDDEN_USER_SERVICES_ID_CATEGORY = "id_category";

    //відгуки на компанію
    public static final String TABLE_COMPANY_REVIEWS = "company_reviews";
    public static final String COLUMN_COMPANY_REVIEWS_ID = "id";
    public static final String COLUMN_COMPANY_REVIEWS_ID_COMPANY = "id_company";
    public static final String COLUMN_COMPANY_REVIEWS_LOCATION = "location";
    public static final String COLUMN_COMPANY_REVIEWS_SERVICE = "service";
    public static final String COLUMN_COMPANY_REVIEWS_AVAILABILITY = "availability";
    public static final String COLUMN_COMPANY_REVIEWS_COMFORT = "comfort";
    public static final String COLUMN_COMPANY_REVIEWS_ID_USER = "user_id";
    public static final String COLUMN_COMPANY_REVIEWS_ID_BOOKING = "booking_id";
    public static final String COLUMN_COMPANY_REVIEWS_USER_COMMENT = "comment";

    // відгукнутися на вакансію
    public static final String TABLE_APPLIED_VACANCY = "applied_vacancy";
    public static final String COLUMN_APPLIED_VACANCY_ID = "id";
    public static final String COLUMN_APPLIED_VACANCY_ID_VACANCY = "id_vacancy";
    public static final String COLUMN_APPLIED_VACANCY_ID_USER = "id_user";
    public static final String COLUMN_APPLIED_VACANCY_EXPERIENCE = "experience";
    public static final String COLUMN_APPLIED_VACANCY_WHY_YOU = "why_you";
    public static final String COLUMN_APPLIED_VACANCY_LANGUAGES = "languages";
    public static final String COLUMN_APPLIED_VACANCY_EMPLOYMENT = "employment";
    public static final String COLUMN_APPLIED_VACANCY_FILE_NAMES = "file_names";

    // відгуки користувачів про місто
    public static final String TABLE_USER_REVIEWS_CITY = "user_reviews_city";
    public static final String COLUMN_USER_REVIEWS_CITY_ID = "id";
    public static final String COLUMN_USER_REVIEWS_CITY_ID_CITY = "id_vacancy";
    public static final String COLUMN_USER_REVIEWS_CITY_ID_USER = "id_user";
    public static final String COLUMN_USER_REVIEWS_CITY_DATE = "date";
    public static final String COLUMN_USER_REVIEWS_CITY_COMMENT = "comment";
    public static final String COLUMN_USER_REVIEWS_CITY_RATING = "rating";
    public static final String COLUMN_USER_REVIEWS_CITY_PHOTO = "photo";

    //rooms в готелях та апартаментах
    public static final String TABLE_ROOMS = "rooms";
    public static final String COLUMN_ROOMS_ID = "id";
    public static final String COLUMN_HOTEL_ID = "hotel_id";
    public static final String COLUMN_ROOM_NUMBER = "room_number";
    public static final String COLUMN_SHORT_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_NAME_ROOM = "name_room";
    public static final String COLUMN_CAPACITY = "capacity";

    //booking
    public static final String TABLE_BOOKINGS = "booking";
    public static final String COLUMN_BOOKING_ID = "id";
    public static final String COLUMN_BOOKING_ROOM_ID = "room_id";
    public static final String COLUMN_BOOKING_GUEST_NAME = "guest_name";
    public static final String COLUMN_BOOKING_CHECKIN_DATE = "checkin_date";
    public static final String COLUMN_BOOKING_CHECKOUT_DATE = "pcheckout_daterice";
    public static final String COLUMN_BOOKING_NUM_GUESTS = "num_guests";
    public static final String COLUMN_BOOKING_HAS_CHILDREN = "children";
    public static final String COLUMN_BOOKING_CHILD_AGE = "child_age";
    public static final String COLUMN_BOOKING_NUM_ROOMS = "num_rooms";
    public static final String COLUMN_BOOKING_USER_ID = "id_user";
    public static final String COLUMN_BOOKING_TOTAL_PRICE  = "total_price";

    //payment
    public static final String TABLE_PAYMENT = "payment";
    public static final String COLUMN_PAYMENT_ID = "id";
    public static final String COLUMN_PAYMENT_ID_COMPANY = "id_company";
    public static final String COLUMN_PAYMENT_USER_ID = "user_id";
    public static final String COLUMN_PAYMENT_DATE = "payment_date";
    public static final String COLUMN_PAYMENT_TYPE_PAYMENT = "type_payment";
    public static final String COLUMN_AMOUNT = "amount";

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
                COLUMN_USER_PHOTO + " TEXT, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_ACCESS_GEO + " INTEGER, " +
                COLUMN_PRIVATE_POLICY + " INTEGER, " +
                COLUMN_USER_OFFERS + " INTEGER, " +
                COLUMN_NOTIFICATION_PROMOTIONS + " INTEGER);";
        db.execSQL(query);

        insertUser(db, "Софія Якименко", "0975821463", "uuuuuuuu", 1,
                "female", 22, true, true,true, true);
        insertUser(db, "Максим Тополя", "0975821563", "uuuuuuuu", 1,
                "male", 35, true, true,true, true);

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
        insertCategoryCompany(db, "Фотографія");
        insertCategoryCompany(db, "Спорт");

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
                //COLUMN_COM_ADDRESS + " TEXT, " +
                COLUMN_COM_EMAIL + " TEXT, " +
                COLUMN_COM_PHONE + " TEXT, " +
                COLUMN_COM_RATING + " REAL);";
        db.execSQL(companyTableQuery);

        insertCompany(db,"Ресторан CafeCarrot", 5, "cafecarrot_icon",
                "lorem", "cafecarrot@gmail.com", "147852369", 4);
        insertCompany(db,"Bolt", 9, "bolt_icon",
                "lorem", "bolt@gmail.com", "147852369",4);
        insertCompany(db,"Нічний клуб Фendo", 5, "fendo_icon",
                "lorem",  "fendo@gmail.com", "147852369",4);
        insertCompany(db,"Hotel Island", 7, "hotelisland_icon",
                "lorem",  "hotelisland@gmail.com", "147852369",4);
        insertCompany(db,"Diamond BeautyStudio", 2, "beautybeautystudio_icon",
                "Зачіска, макіяж, брови, манікюр, педикюр, догляд за шкірою, пірсинг, мелірування волосся",
                "beautybeautystudio@gmail.com", "147852369",
                4.6);

        // заклади харчування
        insertCompany(db,"Salad", 5, "salad_icon",
                "Доставка із різних закладів (бургери, картопля фрі, м’ясо гриль, десерти, напої)",
                "salad@gmail.com", "147852369",
                4.9);
        insertCompany(db,"Балувана Галя", 5, "baluvana_halya_icon",
                "Борщ, вареники, млинці, сирники, узвар",
                "baluvana_halya@gmail.com", "147852369",
                4.8);
        insertCompany(db,"McDonalds", 5, "mcdonalds_icon",
                "Бургери, картопля фрі, нагетси, картопляні діпи, напої",
                "mcdonalds@gmail.com", "147852369",
                4.5);
        insertCompany(db,"Дім млинців", 5, "dim_mlyntsiv_icon",
                "Солодкі млинці, солоні млинці, панкейки, салати, піца, сніданки (яєшня, омлет та інше)",
                "dim_mlyntsiv@gmail.com", "147852369",
                4.3);


        // супермаркети
        insertCompany(db,"АТБ", 6, "atb_icon",
                "Продукти харчування, побутові товари, свіжа випічка, відділ кулінарії",
                "atb@gmail.com", "147852369",
                4.9);
        insertCompany(db,"Метро", 6, "metro_icon",
                "Продукти харчування, побутові товари, свіжа випічка, кав’ярня",
                "metro@gmail.com", "147852369",
                4.9);
        insertCompany(db,"Ашан", 6, "ashan_icon",
                "Продукти харчування, побутові товари, випічка від партнерів, відділ кулінарії, кав’ярня",
                "ashan@gmail.com", "147852369",
                4.4);
        insertCompany(db,"Еко-маркет", 6, "eco_market_icon",
                "Продукти харчування, побутові товари, власна випічка, відділ кулінарії",
                "eco_market@gmail.com", "147852369",
                4.2);

        // готелі
        insertCompany(db,"Готель “Reikartz”", 7, "reikartz_icon",
                "Італійський ресторан, вид із вікна на історичний центр міста, безкоштовне скасування",
                "reikartz@gmail.com", "147852369",
                4.9);
        insertCompany(db,"Готель “Гайки”", 7, "gajky_icon",
                "Можна з тваринами, ресторан домашньої кухні, караоке-бар, безкоштовне скасування",
                "gajky@gmail.com", "147852369",
                4.8);
        insertCompany(db,"Hermes hotel", 7, "hermes_icon",
                "Сніданок входить у вартість, можна з тваринами, конференц-зала, ресторан",
                "hermes@gmail.com", "147852369",
                4.9);
        insertCompany(db,"Готель “DODO”", 7, "dodo_icon",
                "Цілодобовий ресторан, SPA-салон, басейн, сауна, спортивна зала",
                "dodo@gmail.com", "147852369",
                4.3);

        // апартаменти
        insertCompany(db,"Tree House", 8, "tree_house_icon",
                "Кухня, барбекю, безкоштовна парковка, власна ванна кімната, безкоштовний Wi-Fi",
                "tree_house@gmail.com", "147852369",
                4.6);
        insertCompany(db,"OLIVEA", 8, "olivea_icon",
                "Трансфер з/до автостанції, номери для некурців, безкоштовний Wi-Fi, вид на місто",
                "olivea@gmail.com", "147852369",
                4.8);
        insertCompany(db,"Sweet apartments", 8, "sweet_apartments_icon",
                "Кондиціонер, балкон, номери для некурців, можна з тваринами, пральна машина",
                "sweet_apartments@gmail.com", "147852369",
                4.0);
        insertCompany(db,"COSTAIONICA", 8, "costaionica_icon",
                "Безкоштовний Wi-Fi, безкоштовне скасування, опалення/кондиціонер, щоденне прибирання",
                "costaionica@gmail.com", "147852369",
                4.1);

        // усі послуги
        insertCompany(db,"Фотостудія LightStudio", 10, "lightstudio_icon",
                "Фотосесія з фотографом, аренда студії",
                "lightstudio@gmail.com", "147852369",
                4.8);
        insertCompany(db,"Клініка Medibor", 3, "medibor_icon",
                "Дитячий лікар, приватна хірургія, швидкі аналізи, лор-хірургія, естетична медицина",
                "medibor@gmail.com", "147852369",
                4.0);
        insertCompany(db,"YogaClub", 11, "yogaclub_icon",
                "Хатха йога, йога для початківців, айенгар йога, кундаліні йога, віньяса йога",
                "yogaclub@gmail.com", "147852369",
                4.7);

        // барбершопи
        insertCompany(db,"Барбершоп OldTime", 1, "old_time_icon",
                "Чоловіча стрижка, дитяча стрижка, стрижка машинкою, укладання волосся",
                "old_time@gmail.com", "147852369",
                4.9);
        insertCompany(db,"Барбершоп КОД", 1, "code_icon",
                "Чоловіча стрижка, стрижка машинкою, камуфлювання бороди, укладання волосся",
                "code@gmail.com", "147852369",
                4.6);
        insertCompany(db,"Барбершоп Frisor", 1, "frisor_icon",
                "Чоловіча стрижка, гоління, полубокс стрижка, теніс стрижка, камуфлювання сивини",
                "frisor@gmail.com", "147852369",
                4.2);
        insertCompany(db,"Барбершоп BlackPeppers", 1, "black_peppers_icon",
                "Чоловіча стрижка, гоління, стрижка бороди та вусів, укладання волосся, дитяча стрижка",
                "black_peppers@gmail.com", "147852369",
                4.5);

        // салони
        insertCompany(db,"INNA BILA", 2, "inna_bila_icon",
                "Оформлення брів та вій, макіяж, процедури для шкіри тіла, догляд за волоссям, догляд за руками",
                "inna_bila@gmail.com", "147852369",
                4.7);
        insertCompany(db,"Венеція", 2, "venice_icon",
                "Догляд з нігтями рук, фарбування волосся, догляд за ногами, оформлення брів та вій",
                "venice@gmail.com", "147852369",
                4.1);
        insertCompany(db,"Green Room beauty", 2, "green_room_beauty_icon",
                "Косметологія обличчя, оформлення брів та вій, реконструкція волосся, догляд за нігтями рук",
                "green_room_beauty@gmail.com", "147852369",
                4.9);


        String company_cityTableQuery = "CREATE TABLE " + TABLE_COMPANY_CITY + " (" +
                COLUMN_COMPANY_CITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COM_COMPANY_ID + " INTEGER, " +
                COLUMN_COM_CITY_ID + " INTEGER);";
        db.execSQL(company_cityTableQuery);

        // компанії для стрічки новин
        insertCompanyCity(db, 1, 1);
        insertCompanyCity(db,2, 1);
        insertCompanyCity(db,3, 1);
        insertCompanyCity(db,4, 1);
        insertCompanyCity(db,5, 1);

        // компанії
        insertCompanyCity(db,6, 1);
        insertCompanyCity(db,7, 1);
        insertCompanyCity(db,8, 1);
        insertCompanyCity(db,9, 1);
        insertCompanyCity(db,10, 1);
        insertCompanyCity(db,11, 1);
        insertCompanyCity(db,12, 1);
        insertCompanyCity(db,13, 1);
        insertCompanyCity(db,14, 1);
        insertCompanyCity(db,15, 1);
        insertCompanyCity(db,16, 1);
        insertCompanyCity(db,17, 1);
        insertCompanyCity(db,18, 1);
        insertCompanyCity(db,19, 1);
        insertCompanyCity(db,20, 1);
        insertCompanyCity(db,21, 1);
        insertCompanyCity(db,22, 1);
        insertCompanyCity(db,23, 1);
        insertCompanyCity(db,24, 1);
        insertCompanyCity(db,25, 1);
        insertCompanyCity(db,26, 1);
        insertCompanyCity(db,27, 1);
        insertCompanyCity(db,28, 1);
        insertCompanyCity(db,29, 1);
        insertCompanyCity(db,30, 1);
        insertCompanyCity(db,31, 1);

        String companyDetailsTableQuery = "CREATE TABLE " + TABLE_COMPANY_DETAILS + " (" +
                COLUMN_COMPANY_DETAILS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COMPANY_DETAILS_ADDRESS + " TEXT, " +
                COLUMN_COMPANY_DETAILS_PHOTO + " TEXT, " +
                COLUMN_COMPANY_CITY_ID_TABLE + " INTEGER);";
        db.execSQL(companyDetailsTableQuery);

        insertCompanyDetails(db, "вул. Перемоги, 12", "cafe_photo", 1);
        insertCompanyDetails(db, "Online", "bolt_photo", 2);
        insertCompanyDetails(db, "вул. Київська, 77 (ТРЦ Глобал UA)", "fendo_photo", 3);
        insertCompanyDetails(db, "проспект Незалежності, 13", "hotel_island_photo", 4);
        insertCompanyDetails(db, "вул. Покровська, 27", "beauty_studio_photo", 5);

        insertCompanyDetails(db, "Online", "salad_photo", 6);
        insertCompanyDetails(db, "вул. Перемоги, 12", "baluvana_halya_photo", 7);
        insertCompanyDetails(db, "вул. Київська, 77 (ТРЦ Глобал UA)", "mcdonalds_photo", 8);
        insertCompanyDetails(db, "вул. Велика Бердичівська, 31", "dim_mlyntsiv_photo", 9);

        insertCompanyDetails(db, "вул. Ольжича, 1", "atb_photo", 10);
        insertCompanyDetails(db, "проспект Незалежності, 55-В", "metro_photo", 11);
        insertCompanyDetails(db, "вул. Київська, 77 (ТРЦ Глобал UA)", "ashan_photo", 12);
        insertCompanyDetails(db, "вул. Отаманів Соколовських, 1", "eco_market_photo", 13);

        insertCompanyDetails(db, "Замковий майдан, 5/8", "reikartz_photo", 14);
        insertCompanyDetails(db, "Новий бульвар, 6", "gajky_photo", 15);
        insertCompanyDetails(db, "Майдан Визволення, 8а", "hermes_photo", 16);
        insertCompanyDetails(db, "проспект Незалежності, 13", "dodo_photo", 17);

        insertCompanyDetails(db, "вул. Львівська, 7", "tree_house_photo", 18);
        insertCompanyDetails(db, "вул. Покровська, 27", "olivea_photo", 19);
        insertCompanyDetails(db, "вул. Київська, 64", "sweet_apartments_photo", 20);
        insertCompanyDetails(db, "вул. Вокзальна, 14", "costaionica_photo", 21);

        insertCompanyDetails(db, "вул. Кочерги, 39", "lightstudio_photo", 22);
        insertCompanyDetails(db, "вул. Велика Бердичівська, 64", "medibor_photo", 23);
        insertCompanyDetails(db, "вул. Театральна, 19", "yogaclub_photo", 24);

        insertCompanyDetails(db, "пров. Скорульського, 2", "old_time_photo", 25);
        insertCompanyDetails(db, "вул. Львівська, 1", "code_photo", 26);
        insertCompanyDetails(db, "вул. Велика Бердичівська, 6", "frisor_photo", 27);
        insertCompanyDetails(db, "вул. Київська, 75", "black_peppers_photo", 28);

        insertCompanyDetails(db, "вул. Перемоги, 23", "inna_bila_photo", 29);
        insertCompanyDetails(db, "вул. Михайла Грушевського, 7", "venice_photo", 30);
        insertCompanyDetails(db, "вул. Хлібна, 29", "green_room_beauty_photo", 31);

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
                COLUMN_CITY_CHAT_ID + " INTEGER);";
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
//        insertCity(db, "Житомир");
//        insertCity(db, "Львів");

        String cityTableQuery = "CREATE TABLE " + TABLE_CITY + " (" +
                COLUMN_CITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CITY_TITLE + " TEXT, " +
                COLUMN_CITY_PHOTO + " TEXT, " +
                COLUMN_CITY_DESCRIPTION + " TEXT);";
        db.execSQL(cityTableQuery);

        insertCity(db, "Житомир", "city_zhytomyr", "Місто з тисячолітньою історією, батьківщина архітектора космонавтики Сергія Корольова, місто експерементів, романтики та унікальної архітектури.");
        insertCity(db, "Львів", "lviv_city", "Львів — місто, що здивовує своєю красою та атмосферою. Вулички старовинного Львова переповнені історією, яка звучить у кожному камені, будівлі та віддзеркалюється в малюнках вікон. Тут архітектура зливається зі стилістикою кав'ярень, створюючи неповторний образ міста. ");
        insertCity(db, "Київ", "kyiv_city", "Київ, столиця України, місто, що дихає історією та сучасністю. На його вулицях переплітаються тисячолітні традиції та сучасні тренди. Київ — колиска князівства та духовний центр, в якому обіймають своїми крилами велич та динаміку. ");

        String jobOfferTableQuery = "CREATE TABLE " + TABLE_JOB_OFFER + " (" +
                COLUMN_JOB_OFFER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_JOB_OFFER_ID_COMPANY + " TEXT, " +
                COLUMN_JOB_OFFER_COMPANY_NAME + " TEXT, " +
                COLUMN_JOB_OFFER_NAME + " TEXT, " +
                COLUMN_JOB_OFFER_DESCRIPTION + " TEXT, " +
                COLUMN_JOB_OFFER_SALARY + " TEXT, " +
                COLUMN_JOB_OFFER_EMAIL + " TEXT, " +
                COLUMN_JOB_OFFER_PHONE + " TEXT, " +
                COLUMN_JOB_OFFER_REQUIREMENTS + " TEXT, " +
                COLUMN_JOB_OFFER_CATEGORY + " TEXT, " +
                COLUMN_JOB_OFFER_CATEGORY_COMPANY + " TEXT, " +
                COLUMN_JOB_OFFER_KEYWORDS + " TEXT);";
        db.execSQL(jobOfferTableQuery);
        insertJobOffer(db, 12, "Ашан", "Менеджер по роботі з клієнтами",
                "Повна / часткова зайнятість. Вимоги: висока компетентність у взаємодії з клієнтами, вміння вирішувати проблеми. Буде перевагою досвід роботи в сфері юридичних послуг. ",
                17000, "ashan@gmail.com", "0974875951", "lorem", "Послуги", 4, "Віддалена робота, Крута команда, Медстрахування, Без досвіду");
        insertJobOffer(db, 15, "Гайки", "Контент-мейкер",
                "Повна / часткова зайнятість. Вимоги: висока компетентність у взаємодії з клієнтами, вміння вирішувати проблеми. Буде перевагою досвід роботи в сфері юридичних послуг. ",
                15000, "gaiki@gmail.com", "0974875951", "lorem", "Житло", 7, "Віддалена робота, Крута команда, Медстрахування, Без досвіду");
        insertJobOffer(db, 10, "АТБ", "Помічник керівника компанії",
                "Повна / часткова зайнятість. Вимоги: висока компетентність у взаємодії з клієнтами, вміння вирішувати проблеми. Буде перевагою досвід роботи в сфері юридичних послуг. ",
                22000, "atb@gmail.com", "0974875951", "lorem", "Доставка", 6, "Віддалена робота, Крута команда, Медстрахування, Без досвіду");

        insertJobOffer(db, 12, "Ашан", "Менеджер по роботі з клієнтами",
                "Повна / часткова зайнятість. Вимоги: висока компетентність у взаємодії з клієнтами, вміння вирішувати проблеми. Буде перевагою досвід роботи в сфері юридичних послуг. ",
                17000, "ashan@gmail.com", "0974875951", "lorem", "Доставка", 6, "Віддалена робота, Медстрахування, Без досвіду");
        insertJobOffer(db, 9, "Дім млинців", "Контент-мейкер",
                "Повна / часткова зайнятість. Вимоги: висока компетентність у взаємодії з клієнтами, вміння вирішувати проблеми. Буде перевагою досвід роботи в сфері юридичних послуг. ",
                15000, "dim_mlyntsiv@gmail.com", "0974875951", "lorem", "Доставка", 5, "Віддалена робота, Крута команда, Медстрахування, Без досвіду");
        insertJobOffer(db, 21, "COSTAIONICA", "Помічник керівника компанії",
                "Повна / часткова зайнятість. Вимоги: висока компетентність у взаємодії з клієнтами, вміння вирішувати проблеми. Буде перевагою досвід роботи в сфері юридичних послуг. ",
                22000, "COSTAIONICA@gmail.com", "0974875951", "lorem", "Житло", 8, "Віддалена робота, Без досвіду");

        insertJobOffer(db, 26, "Барбершоп КОД", "Контент-мейкер",
                "Повна / часткова зайнятість. Вимоги: аналітичність, дослідницьких підхід, організаційні навички, добре розвинене візуальне сприйняття, вміння швидко вчитись, позитивна реакція на зауваження, креатив...",
                11000, "code@gmail.com", "0978456974",  "lorem", "Послуги", 1,"Медстрахування, Для студентів");

        insertJobOffer(db, 29, "INNA BILA", "Менеджер по роботі з клієнтами",
                "Повна / часткова зайнятість. Вимоги: висока компетентність у взаємодії з клієнтами, вміння вирішувати проблеми. Буде перевагою досвід роботи в сфері юридичних послуг. Основні обов’яз...",
                17000, "inna_bila@gmail.com", "0978456974",  "lorem", "Послуги", 2,"Медстрахування, Для студентів");

        String jobOfferServiceTableQuery = "CREATE TABLE " + TABLE_JOB_OFFER_SERVICES + " (" +
                COLUMN_JOB_OFFER_SERVICES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_JOB_OFFER_SERVICES_COMPANY_NAME + " TEXT, " +
                COLUMN_JOB_OFFER_SERVICES_COMPANY_ICON + " TEXT, " +
                COLUMN_JOB_OFFER_SERVICES_COMPANY_CATEGORY_ID + " TEXT, " +
                COLUMN_JOB_OFFER_SERVICES_NAME + " TEXT, " +
                COLUMN_JOB_OFFER_SERVICES_ADDRESS + " TEXT, " +
                COLUMN_JOB_OFFER_SERVICES_CITY_ID + " TEXT, " +
                COLUMN_JOB_OFFER_SERVICES_DESCRIPTION + " TEXT, " +
                COLUMN_JOB_OFFER_SERVICES_SALARY + " TEXT, " +
                COLUMN_JOB_OFFER_SERVICES_KEYWORDS + " TEXT, " +
                COLUMN_JOB_OFFER_SERVICES_LINK + " TEXT);";
        db.execSQL(jobOfferServiceTableQuery);

        insertJobOfferService(db, "Трибеля", "new_company", 5, "Кухар-кондитер",
                "Повна зайнятість. Вимоги: знання класики кондитерського мистецтва різних кухонь світу, вміння працювати з пропорціями, температурою, гігієна та безпека, досвід у приготуванні авторських ...",
                "вулиця Михайлівська, 8/1", 1,18000, "Медстрахування, Крута команда, Без досвіду", "https://www.work.ua");

        insertJobOfferService(db, "Балувана Галя", "new_company", 5, "Кухар-кондитер",
                "Повна зайнятість. Вимоги: знання класики кондитерського мистецтва різних кухонь світу, вміння працювати з пропорціями, температурою, гігієна та безпека, досвід у приготуванні авторських ...",
                "вулиця Київська, 10", 1,11000, "Медстрахування, Для студентів", "https://www.work.ua");

        insertJobOfferService(db, "Час поїсти", "new_company", 5, "Кухар-кондитер",
                "Повна зайнятість. Вимоги: знання класики кондитерського мистецтва різних кухонь світу, вміння працювати з пропорціями, температурою, гігієна та безпека, досвід у приготуванні авторських ...",
                "проспект Миру, 10", 1,20000, "Медстрахування, Для людей старшого віку", "https://www.work.ua");

        insertJobOfferService(db, "Балувана Галя", "new_company", 7, "Кухар-кондитер",
                "Повна зайнятість. Вимоги: знання класики кондитерського мистецтва різних кухонь світу, вміння працювати з пропорціями, температурою, гігієна та безпека, досвід у приготуванні авторських ...",
                "вулиця Михайлівська, 10", 1,11000, "Медстрахування, Для студентів", "https://www.work.ua");

        insertJobOfferService(db, "Барбершоп КОД", "code_icon", 1, "Контент-мейкер",
                "Повна / часткова зайнятість. Вимоги: аналітичність, дослідницьких підхід, організаційні навички, добре розвинене візуальне сприйняття, вміння швидко вчитись, позитивна реакція на зауваження, креатив...",
                "вул. Львівська, 1", 1,11000, "Медстрахування, Для студентів", "https://www.work.ua");

        String hiddenServicesQuery = "CREATE TABLE " + TABLE_HIDDEN_USER_SERVICES + " (" +
                COLUMN_HIDDEN_USER_SERVICES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HIDDEN_USER_SERVICES_ID_USER + " TEXT, " +
                COLUMN_HIDDEN_USER_SERVICES_ID_CATEGORY + " TEXT);";
        db.execSQL(hiddenServicesQuery);

        String companyReviewsQuery = "CREATE TABLE " + TABLE_COMPANY_REVIEWS + " (" +
                COLUMN_COMPANY_REVIEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COMPANY_REVIEWS_ID_COMPANY + " TEXT, " +
                COLUMN_COMPANY_REVIEWS_ID_USER + " TEXT, " +
                COLUMN_COMPANY_REVIEWS_ID_BOOKING + " TEXT, " +
                COLUMN_COMPANY_REVIEWS_LOCATION + " TEXT, " +
                COLUMN_COMPANY_REVIEWS_SERVICE + " TEXT, " +
                COLUMN_COMPANY_REVIEWS_USER_COMMENT + " TEXT, " +
                COLUMN_COMPANY_REVIEWS_AVAILABILITY + " TEXT, " +
                COLUMN_COMPANY_REVIEWS_COMFORT + " TEXT);";
        db.execSQL(companyReviewsQuery);

        insertCompanyReviews(db, 1, 3, 4.5, 4, 5, 1, "");
        insertCompanyReviews(db,2, 4, 4, 5, 4.5, 1, "");
        insertCompanyReviews(db,3, 4.2, 4, 5, 4.1, 1, "");
        insertCompanyReviews(db,4, 5, 4, 4, 4, 1, "Провели відпустку в цьому готелі і взагалі залишились задоволені. Номер, в якому ми жили, був дуже просторий та комфортний. А особливо вражені були соляною кімнатою. Такий рідкісний бонус! Рекомендую!");
        insertCompanyReviews(db,5, 4, 4, 3, 4, 1, "");
        insertCompanyReviews(db,6, 4, 4.3, 4, 5, 1, "");
        insertCompanyReviews(db,7, 4.2, 4.7, 4, 4.2, 1, "");
        insertCompanyReviews(db,8, 3, 4.5, 4, 5, 1, "");
        insertCompanyReviews(db,9, 4, 4, 5, 4.5, 1, "");
        insertCompanyReviews(db,10, 4.2, 4, 5, 4.1, 1, "");
        insertCompanyReviews(db,11, 5, 4, 4, 4, 1, "");
        insertCompanyReviews(db,12, 4, 4, 3, 4, 1, "");
        insertCompanyReviews(db,13, 4, 4.3, 4, 5, 1, "");
        insertCompanyReviews(db,14, 4.2, 4.7, 4, 4.2, 1, "Ми обрали цей готель для відпустки та не пошкодували про це. Відчувається, що власники дбають про ваш комфорт. Рекомендуємо ресторан готелю, він також залишив хороше враження. Їжа була смачною та різноманітною. Оцінку знизив за відсутність паркінгу.");
        insertCompanyReviews(db,15, 3, 4.5, 4, 5, 1, "");
        insertCompanyReviews(db,16, 4, 4, 5, 4.5, 1, "");
        insertCompanyReviews(db,17, 4.2, 4, 5, 4.1, 1, "");
        insertCompanyReviews(db,18, 5, 4, 4, 4, 1, "");
        insertCompanyReviews(db,19, 4, 4, 3, 4, 1, "");
        insertCompanyReviews(db,20, 3, 4.5, 4, 5, 1, "");
        insertCompanyReviews(db,21, 4, 4, 5, 4.5, 1, "");
        insertCompanyReviews(db,22, 4.2, 4, 5, 4.1, 1, "");
        insertCompanyReviews(db,23, 5, 4, 4, 4, 1, "");
        insertCompanyReviews(db,24, 4, 4, 3, 4, 1, "");
        insertCompanyReviews(db,25, 4.2, 4.7, 4, 4.2, 1, "");
        insertCompanyReviews(db,26, 3, 4.5, 4, 5, 1, "");
        insertCompanyReviews(db,27, 4, 4, 5, 4.5, 1, "");
        insertCompanyReviews(db,28, 5, 4, 4, 4, 1, "");
        insertCompanyReviews(db,29, 3, 4.5, 4, 5, 1, "");
        insertCompanyReviews(db,30, 4.2, 4, 5, 4.1, 1, "");
        insertCompanyReviews(db,31, 4.2, 4, 5, 4.1, 1, "");

        String appliedVacanciesQuery = "CREATE TABLE " + TABLE_APPLIED_VACANCY + " (" +
                COLUMN_APPLIED_VACANCY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_APPLIED_VACANCY_ID_VACANCY + " TEXT, " +
                COLUMN_APPLIED_VACANCY_ID_USER + " TEXT, " +
                COLUMN_APPLIED_VACANCY_EXPERIENCE + " TEXT, " +
                COLUMN_APPLIED_VACANCY_WHY_YOU + " TEXT, " +
                COLUMN_APPLIED_VACANCY_LANGUAGES + " TEXT, " +
                COLUMN_APPLIED_VACANCY_EMPLOYMENT + " TEXT, " +
                COLUMN_APPLIED_VACANCY_FILE_NAMES + " TEXT);";
        db.execSQL(appliedVacanciesQuery);

        String reviewsCityQuery = "CREATE TABLE " + TABLE_USER_REVIEWS_CITY + " (" +
                COLUMN_USER_REVIEWS_CITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_REVIEWS_CITY_ID_CITY + " TEXT, " +
                COLUMN_USER_REVIEWS_CITY_ID_USER + " TEXT, " +
                COLUMN_USER_REVIEWS_CITY_DATE + " TEXT, " +
                COLUMN_USER_REVIEWS_CITY_COMMENT + " TEXT, " +
                COLUMN_USER_REVIEWS_CITY_RATING + " TEXT, " +
                COLUMN_USER_REVIEWS_CITY_PHOTO + " TEXT);";
        db.execSQL(reviewsCityQuery);

        Date currentDateNow = new Date();
        insertCityReviews(db, 2, 1, String.valueOf(currentDateNow), "Кілька днів тому потрапив до Житомира абсолютно випадково, по роботі. Це був чудовий експіріанс, мені дуже сподобалася архітектура, приємна центральна вуличка та заклади міста. Варто подумати про розвиток туризму в місті!",
                String.valueOf(5), "");
        insertCityReviews(db, 1, 1, String.valueOf(currentDateNow), "Подорож до Житомира залишила змішані враження. Визначні місця були цікавими, але декотрі архітектурні пам'ятки потребують більшої уваги до реставрації. Однак, смачні страви в місцевих ресторанах і тепла атмосфера міста роблять Житомир приємним місцем для короткої відпустки.",
                String.valueOf(4.2), "");


        String roomsQuery = "CREATE TABLE " + TABLE_ROOMS + " (" +
                COLUMN_ROOMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HOTEL_ID + " TEXT, " +
                COLUMN_ROOM_NUMBER + " TEXT, " +
                COLUMN_SHORT_DESCRIPTION + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                COLUMN_NAME_ROOM + " TEXT, " +
                COLUMN_CAPACITY + " TEXT);";
        db.execSQL(roomsQuery);

        insertRooms(db, 17, "11", "2 дивани-ліжка\n" +
                "1 ширкое двоспальне ліжко", 1100, "Стандартний 2+1 номер", 3);
        insertRooms(db, 17, "12A", "1 диван-ліжко\n" +
                "1 широке двоспальне ліжко", 700, "Люкс", 2);
        insertRooms(db, 17, "12B", "1 диван-ліжко\n" +
                "1 широке двоспальне ліжко", 950, "Покращений люкс", 2);

        String bookingQuery = "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOKING_ROOM_ID + " TEXT, " +
                COLUMN_BOOKING_GUEST_NAME + " TEXT, " +
                COLUMN_BOOKING_CHECKIN_DATE + " TEXT, " +
                COLUMN_BOOKING_CHECKOUT_DATE + " TEXT, " +
                COLUMN_BOOKING_NUM_GUESTS + " TEXT, " +
                COLUMN_BOOKING_HAS_CHILDREN + " TEXT, " +
                COLUMN_BOOKING_CHILD_AGE + " TEXT, " +
                COLUMN_BOOKING_USER_ID + " TEXT, " +
                COLUMN_BOOKING_NUM_ROOMS + " TEXT, " +
                COLUMN_BOOKING_TOTAL_PRICE + " TEXT);";
        db.execSQL(bookingQuery);

        String paymentQuery = "CREATE TABLE " + TABLE_PAYMENT + " (" +
                COLUMN_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PAYMENT_ID_COMPANY + " TEXT, " +
                COLUMN_PAYMENT_USER_ID + " TEXT, " +
                COLUMN_PAYMENT_DATE + " TEXT, " +
                COLUMN_PAYMENT_TYPE_PAYMENT + " TEXT, " +
                COLUMN_AMOUNT + " TEXT);";
        db.execSQL(paymentQuery);
    }

    public long insertRooms(SQLiteDatabase db, int idHotel, String roomNumber,
                                  String description, double price, String nameRoom, int capacity) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOTEL_ID, idHotel);
        values.put(COLUMN_ROOM_NUMBER, roomNumber);
        values.put(COLUMN_SHORT_DESCRIPTION, description);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_NAME_ROOM, nameRoom);
        values.put(COLUMN_CAPACITY, capacity);

        return db.insert(TABLE_ROOMS, null, values);
    }

//    public List<Room> getAvailableRoomsByIdCompany(int idCompany, Date checkinDate, Date checkoutDate) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        List<Room> availableRooms = new ArrayList<>();
//
//        String query = "SELECT * FROM " + TABLE_ROOMS +
//                " WHERE " + COLUMN_HOTEL_ID + " = ? AND " +
//                COLUMN_ROOMS_ID + " NOT IN (SELECT " + COLUMN_BOOKING_ROOM_ID + " FROM " + TABLE_BOOKINGS +
//                " WHERE (" +
//                "(" + COLUMN_BOOKING_CHECKIN_DATE + " < ? AND " + COLUMN_BOOKING_CHECKOUT_DATE + " > ?) OR " +
//                "(" + COLUMN_BOOKING_CHECKIN_DATE + " >= ? AND " + COLUMN_BOOKING_CHECKIN_DATE + " < ?) OR " +
//                "(" + COLUMN_BOOKING_CHECKOUT_DATE + " > ? AND " + COLUMN_BOOKING_CHECKOUT_DATE + " <= ?)" +
//                "))";
//
//        Cursor cursor = db.rawQuery(query, new String[]{
//                String.valueOf(idCompany),
//                String.valueOf(checkoutDate.getTime()), String.valueOf(checkinDate.getTime()),
//                String.valueOf(checkinDate.getTime()), String.valueOf(checkoutDate.getTime()),
//                String.valueOf(checkinDate.getTime()), String.valueOf(checkoutDate.getTime())
//        });
//
//        if (cursor.moveToFirst()) {
//            do {
//                int idRoom = cursor.getInt(cursor.getColumnIndex(COLUMN_ROOMS_ID));
//                int hotelId = cursor.getInt(cursor.getColumnIndex(COLUMN_HOTEL_ID));
//                String roomNumber = cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_NUMBER));
//                String description = cursor.getString(cursor.getColumnIndex(COLUMN_SHORT_DESCRIPTION));
//                double price = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
//                String nameRoom = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ROOM));
//                int capacity = cursor.getInt(cursor.getColumnIndex(COLUMN_CAPACITY));
//
//                Room room = new Room(idRoom, hotelId, roomNumber, description, price, nameRoom, capacity);
//
//                availableRooms.add(room);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return availableRooms;
//    }
    public List<Room> getAvailableRoomsByIdCompany(int idCompany, Date date1, Date date2, int numGuests) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Room> availableRooms = new ArrayList<>();

        String formattedDate1 = formatDateForDatabase(date1);
        String formattedDate2 = formatDateForDatabase(date2);

        String query = "SELECT * FROM " + TABLE_ROOMS + " r " +
                "LEFT JOIN " + TABLE_BOOKINGS + " b ON r." + COLUMN_ROOMS_ID + " = b." + COLUMN_BOOKING_ROOM_ID +
                " WHERE r." + COLUMN_HOTEL_ID + " = ? " +
                "AND (b." + COLUMN_BOOKING_CHECKIN_DATE + " > ? OR b." + COLUMN_BOOKING_CHECKOUT_DATE + " < ? " +
                "OR b." + COLUMN_BOOKING_ID + " IS NULL)";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idCompany), formattedDate2, formattedDate1});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Перевіряємо, чи номер не зайнятий
                if (cursor.isNull(cursor.getColumnIndex(COLUMN_BOOKING_ID))) {
                    if(cursor.getInt(cursor.getColumnIndex(COLUMN_CAPACITY)) >= numGuests){
                        Room room = new Room(
                                cursor.getInt(cursor.getColumnIndex(COLUMN_ROOMS_ID)),
                                cursor.getInt(cursor.getColumnIndex(COLUMN_HOTEL_ID)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_NUMBER)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_SHORT_DESCRIPTION)),
                                Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE))),
                                cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ROOM)),
                                cursor.getInt(cursor.getColumnIndex(COLUMN_CAPACITY))
                        );
                        availableRooms.add(room);
                    }
//                    Room room = new Room(
//                        cursor.getInt(cursor.getColumnIndex(COLUMN_ROOMS_ID)),
//                        cursor.getInt(cursor.getColumnIndex(COLUMN_HOTEL_ID)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_NUMBER)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_SHORT_DESCRIPTION)),
//                        Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE))),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ROOM)),
//                        cursor.getInt(cursor.getColumnIndex(COLUMN_CAPACITY))
//                );
//                    availableRooms.add(room);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        return availableRooms;
    }

    private String formatDateForDatabase(Date date) {
        // Форматуємо дату в строку за допомогою SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Перетворюємо об'єкт Date у строку за зазначеним форматом
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            // Обробка помилок форматування дати
            return null;
        }
    }

//    public List<Booking> getCompletedBookingsForUser(int userId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        List<Booking> completedBookings = new ArrayList<>();
//
//        String currentDate = formatDateForDatabase(new Date());
//
////        String query = "SELECT * FROM " + TABLE_BOOKINGS +
////                " WHERE " + COLUMN_BOOKING_USER_ID + " = ?" +
////                " AND " + COLUMN_BOOKING_CHECKOUT_DATE + " < ?";
//
//        String query = "SELECT * FROM " + TABLE_BOOKINGS +
//                " WHERE " + COLUMN_BOOKING_USER_ID + " = ?" +
//                " AND " + COLUMN_BOOKING_CHECKOUT_DATE + " < datetime(?, 'localtime')";
//
//
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), currentDate});
//
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_ID));
//                int roomId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_ROOM_ID));
//                String guestName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_GUEST_NAME));
//                String checkInDateStr = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHECKIN_DATE));
//                String checkOutDateStr = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHECKOUT_DATE));
//                double totalPrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_BOOKING_TOTAL_PRICE));
//                int numGuests = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_NUM_GUESTS));
//                int children = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_HAS_CHILDREN));
//                String childAge = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHILD_AGE));
//                int numRooms = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_NUM_ROOMS));
//
//                Date checkInDate = convertStringToDate(checkInDateStr);
//                Date checkOutDate = convertStringToDate(checkOutDateStr);
//
//                Booking booking = new Booking(id, roomId, guestName, checkInDate, checkOutDate,
//                        totalPrice, numGuests, children, childAge, numRooms, 0, userId);
//
//                completedBookings.add(booking);
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//
//        return completedBookings;
//    }
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public List<Booking> getCompletedBookingsForUser(int userId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        List<Booking> completedBookings = new ArrayList<>();
//
//        // Отримання поточної дати у правильному форматі для порівняння в базі даних SQLite
//        //String currentDate = formatDateForDatabase(new Date());
//        Date currentDateDate = new Date();
//        String currentDate = String.valueOf(currentDateDate);
//
//        // SQL-запит із використанням функції datetime для порівняння дат в SQLite
//        String query = "SELECT * FROM " + TABLE_BOOKINGS +
//                " WHERE " + COLUMN_BOOKING_USER_ID + " = ?" +
//                " AND strftime('%Y-%m-%d %H:%M:%S', " + COLUMN_BOOKING_CHECKOUT_DATE + ") < datetime(?, 'localtime')";
//
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), currentDate});
//
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_ID));
//                int roomId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_ROOM_ID));
//                String guestName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_GUEST_NAME));
//                String checkInDateStr = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHECKIN_DATE));
//                String checkOutDateStr = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHECKOUT_DATE));
//                double totalPrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_BOOKING_TOTAL_PRICE));
//                int numGuests = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_NUM_GUESTS));
//                int children = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_HAS_CHILDREN));
//                String childAge = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHILD_AGE));
//                int numRooms = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_NUM_ROOMS));
//
//                Date checkInDate = convertStringToDate(checkInDateStr);
//                Date checkOutDate = convertStringToDate(checkOutDateStr);
//
//                Booking booking = new Booking(id, roomId, guestName, checkInDate, checkOutDate,
//                        totalPrice, numGuests, children, childAge, numRooms, 0, userId);
//
//                completedBookings.add(booking);
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//
//        return completedBookings;
//    }

    private Date convertStringToDate2(String dateString) {
        // Форматуємо дату в строку за допомогою SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);

        try {
            // Перетворюємо об'єкт Date у строку за зазначеним форматом
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            // Обробка помилок форматування дати
            return null;
        }
    }


    public List<Booking> getCompletedBookingsForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Booking> completedBookings = new ArrayList<>();

        // Отримання поточної дати для порівняння
        Date currentDate = new Date();

        // SQL-запит для вибору завершених бронювань
        String query = "SELECT * FROM " + TABLE_BOOKINGS +
                " WHERE " + COLUMN_BOOKING_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_ID));
                int roomId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_ROOM_ID));
                String guestName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_GUEST_NAME));
                String checkInDateStr = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHECKIN_DATE));
                String checkOutDateStr = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHECKOUT_DATE));
                double totalPrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_BOOKING_TOTAL_PRICE));
                int numGuests = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_NUM_GUESTS));
                int children = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_HAS_CHILDREN));
                String childAge = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHILD_AGE));
                int numRooms = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_NUM_ROOMS));

                Date checkInDate = convertStringToDate2(checkInDateStr);
                Date checkOutDate = convertStringToDate2(checkOutDateStr);

                // Порівняння дат
                if (checkOutDate != null && checkOutDate.before(currentDate)) {
                    // Створення об'єкта Booking
                    Booking booking = new Booking(id, roomId, guestName, checkInDate, checkOutDate,
                            totalPrice, numGuests, children, childAge, numRooms, 0, userId);

                    completedBookings.add(booking);
                }

            } while (cursor.moveToNext());
            cursor.close();
        }

        return completedBookings;
    }


    public int getCompanyByIdBooking(int id_booking) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + TABLE_ROOMS + "." + COLUMN_HOTEL_ID +
                " FROM " + TABLE_ROOMS +
                " JOIN " + TABLE_BOOKINGS + " ON " + TABLE_ROOMS + "." + COLUMN_ROOMS_ID +
                " = " + TABLE_BOOKINGS + "." + COLUMN_BOOKING_ROOM_ID +
                " WHERE " + TABLE_BOOKINGS + "." + COLUMN_BOOKING_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id_booking)});

        int companyId = 0;

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_HOTEL_ID);
            if (columnIndex != -1) {
                companyId = cursor.getInt(columnIndex);
            }
        }

        cursor.close();
        return companyId;
    }



    public List<Booking> getPaymentsForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Booking> completedBookings = new ArrayList<>();

        String currentDate = formatDateForDatabase(new Date());

        String query = "SELECT * FROM " + TABLE_BOOKINGS +
                " WHERE " + COLUMN_BOOKING_USER_ID + " = ?" +
                " AND " + COLUMN_BOOKING_CHECKOUT_DATE + " < ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), currentDate});

        if (cursor != null && cursor.moveToFirst()) {
            do {

                int roomId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_ROOM_ID));
                String guestName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_GUEST_NAME));
                String checkInDateStr = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHECKIN_DATE));
                String checkOutDateStr = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHECKOUT_DATE));
                double totalPrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_BOOKING_TOTAL_PRICE));
                int numGuests = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_NUM_GUESTS));
                int children = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_HAS_CHILDREN));
                String childAge = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHILD_AGE));
                int numRooms = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_NUM_ROOMS));

                Date checkInDate = convertStringToDate(checkInDateStr);
                Date checkOutDate = convertStringToDate(checkOutDateStr);

                Booking booking = new Booking(roomId, guestName, checkInDate, checkOutDate,
                        totalPrice, numGuests, children, childAge, numRooms, 0);

                completedBookings.add(booking);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return completedBookings;
    }
    public long addPayment(String idCompany, String idUser, String date, String typePayment,
                           double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PAYMENT_ID_COMPANY, idCompany);
        cv.put(COLUMN_PAYMENT_USER_ID, idUser);
        cv.put(COLUMN_PAYMENT_DATE, date);
        cv.put(COLUMN_PAYMENT_TYPE_PAYMENT, typePayment);
        cv.put(COLUMN_AMOUNT, amount);

        long result = db.insert(TABLE_PAYMENT, null, cv);

        return result;
    }
    public long addBooking(String roomID, String guestName, String checkInDate, String checkOutDate,
                           int numGuests, int children, String childAge,
                        int numRooms, double totalPrice, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BOOKING_ROOM_ID, roomID);
        cv.put(COLUMN_BOOKING_GUEST_NAME, guestName);
        cv.put(COLUMN_BOOKING_CHECKIN_DATE, checkInDate);
        cv.put(COLUMN_BOOKING_CHECKOUT_DATE, checkOutDate);
        cv.put(COLUMN_BOOKING_NUM_GUESTS, numGuests);
        cv.put(COLUMN_BOOKING_HAS_CHILDREN, children);
        cv.put(COLUMN_BOOKING_CHILD_AGE, childAge);
        cv.put(COLUMN_BOOKING_NUM_ROOMS, numRooms);
        cv.put(COLUMN_BOOKING_TOTAL_PRICE, totalPrice);
        cv.put(COLUMN_BOOKING_USER_ID, userId);

        long result = db.insert(TABLE_BOOKINGS, null, cv);

        return result;
    }
    public long insertCityReviews(SQLiteDatabase db, int idUser, int idCity,
                                     String date, String comment, String rating, String photo) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_REVIEWS_CITY_ID_CITY, idCity);
        values.put(COLUMN_USER_REVIEWS_CITY_ID_USER, idUser);
        values.put(COLUMN_USER_REVIEWS_CITY_DATE, date);
        values.put(COLUMN_USER_REVIEWS_CITY_COMMENT, comment);
        values.put(COLUMN_USER_REVIEWS_CITY_RATING, rating);
        values.put(COLUMN_USER_REVIEWS_CITY_PHOTO, photo);

        return db.insert(TABLE_USER_REVIEWS_CITY, null, values);
    }
//    public List<CityReviews> getCityReviewsByIdCity(int id) {
//        List<CityReviews> listReviews = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM " + TABLE_USER_REVIEWS_CITY +
//                " WHERE " + COLUMN_USER_REVIEWS_CITY_ID_CITY + " = ?";
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
//
//        if (cursor.moveToFirst()) {
//            do{
//                int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_REVIEWS_CITY_ID_USER));
//                int cityId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_REVIEWS_CITY_ID_CITY));
//                String comment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_REVIEWS_CITY_COMMENT));
//                String rating = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_REVIEWS_CITY_RATING));
//                String photo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_REVIEWS_CITY_PHOTO));
//                String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_REVIEWS_CITY_DATE));
//                String dateFormatPattern = "yyyy-MM-dd HH:mm:ss";
//                try {
//                    SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.US);
//                    Date date = dateFormat.parse(time);
//                    CityReviews cityReviews = new CityReviews(userId, cityId, date, comment, rating, photo);
//                    listReviews.add(cityReviews);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return listReviews;
//    }


    public long insertCompanyReviews(SQLiteDatabase db, int companyId, double location,
                                     double service, double availability, double comfort, int userId, String comment) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPANY_REVIEWS_ID_COMPANY, companyId);
        values.put(COLUMN_COMPANY_REVIEWS_LOCATION, location);
        values.put(COLUMN_COMPANY_REVIEWS_SERVICE, service);
        values.put(COLUMN_COMPANY_REVIEWS_AVAILABILITY, availability);
        values.put(COLUMN_COMPANY_REVIEWS_COMFORT, comfort);
        values.put(COLUMN_COMPANY_REVIEWS_ID_USER, userId);
        values.put(COLUMN_COMPANY_REVIEWS_USER_COMMENT, comment);

        return db.insert(TABLE_COMPANY_REVIEWS, null, values);
    }
    public List<Integer> getCategoriesForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Integer> categories = new ArrayList<>();

        String query = "SELECT " + COLUMN_HIDDEN_USER_SERVICES_ID_CATEGORY +
                " FROM " + TABLE_HIDDEN_USER_SERVICES +
                " WHERE " + COLUMN_HIDDEN_USER_SERVICES_ID_USER + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int categoryId = cursor.getInt(cursor.getColumnIndex(COLUMN_HIDDEN_USER_SERVICES_ID_CATEGORY));
                categories.add(categoryId);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return categories;
    }


    public long insertJobOfferService(SQLiteDatabase db, String companyName, String companyIcon,
                                      int categoryId, String jobOfferName, String description, String address,
                                      int cityId, int salary, String keywords, String link) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_JOB_OFFER_SERVICES_COMPANY_NAME, companyName);
        values.put(COLUMN_JOB_OFFER_SERVICES_COMPANY_ICON, companyIcon);
        values.put(COLUMN_JOB_OFFER_SERVICES_COMPANY_CATEGORY_ID, categoryId);
        values.put(COLUMN_JOB_OFFER_SERVICES_NAME, jobOfferName);
        values.put(COLUMN_JOB_OFFER_SERVICES_DESCRIPTION, description);
        values.put(COLUMN_JOB_OFFER_SERVICES_ADDRESS, address);
        values.put(COLUMN_JOB_OFFER_SERVICES_CITY_ID, cityId);
        values.put(COLUMN_JOB_OFFER_SERVICES_SALARY, salary);
        values.put(COLUMN_JOB_OFFER_SERVICES_KEYWORDS, keywords);
        values.put(COLUMN_JOB_OFFER_SERVICES_LINK, link);

        return db.insert(TABLE_JOB_OFFER_SERVICES, null, values);
    }

    public long insertJobOffer(SQLiteDatabase db, int companyId, String companyName,
                               String jobOfferName, String description, int salary,
                               String email, String phone, String requirements,
                               String category, int categoryCompany, String keywords) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_JOB_OFFER_ID_COMPANY, companyId);
        values.put(COLUMN_JOB_OFFER_COMPANY_NAME, companyName);
        values.put(COLUMN_JOB_OFFER_NAME, jobOfferName);
        values.put(COLUMN_JOB_OFFER_DESCRIPTION, description);
        values.put(COLUMN_JOB_OFFER_SALARY, salary);
        values.put(COLUMN_JOB_OFFER_EMAIL, email);
        values.put(COLUMN_JOB_OFFER_PHONE, phone);
        values.put(COLUMN_JOB_OFFER_REQUIREMENTS, requirements);
        values.put(COLUMN_JOB_OFFER_CATEGORY, category);
        values.put(COLUMN_JOB_OFFER_CATEGORY_COMPANY, categoryCompany);
        values.put(COLUMN_JOB_OFFER_KEYWORDS, keywords);

        return db.insert(TABLE_JOB_OFFER, null, values);
    }

    public long insertCompanyCity(SQLiteDatabase db, int companyId, int cityId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COM_COMPANY_ID, companyId);
        values.put(COLUMN_COM_CITY_ID, cityId);

        return db.insert(TABLE_COMPANY_CITY, null, values);
    }

    public long insertCompanyDetails(SQLiteDatabase db, String address, String photo, int companyCityId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPANY_DETAILS_ADDRESS, address);
        values.put(COLUMN_COMPANY_CITY_ID_TABLE, companyCityId);
        values.put(COLUMN_COMPANY_DETAILS_PHOTO, photo);

        return db.insert(TABLE_COMPANY_DETAILS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addUser(String name, String phone, String password, int city, String gender, int age, boolean accessGeo,
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

    public String getUserIconById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_USER_PHOTO + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " = ?"; // Змінено COLUMN_NAME на COLUMN_ID

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)}); // Змінено тип параметра на String та передано id у вигляді рядка

        String userIcon = null; // Змінено тип результату на String

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_USER_PHOTO);
            if (columnIndex != -1) {
                userIcon = cursor.getString(columnIndex); // Змінено отримання значення на getString
            }
        }

        cursor.close();
        return userIcon;
    }
    private long insertCategory(SQLiteDatabase db, String categoryName) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CATEGORY_NAME, categoryName);
        return db.insert(TABLE_CATEGORIES, null, cv);
    }

    public long insertUser(SQLiteDatabase db, String name, String phone, String password, int city, String gender, int age, boolean accessGeo,
                        boolean privatePolicy, boolean userOffers, boolean notificationPromotions) {
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

        return db.insert(TABLE_NAME, null, cv);
    }

    private long insertCategoryCompany(SQLiteDatabase db, String categoryName) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_COM_CAT_NAME, categoryName);
        return db.insert(TABLE_COMPANY_CATEGORY, null, cv);
    }

    public long insertCompany(SQLiteDatabase db,String companyName, int categoryId, String companyPhoto,
                              String companyDescription, String email, String phone,
                              //String companyAddress,
                              double companyRating) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_COM_NAME, companyName);
        cv.put(COLUMN_COM_ID_CATEGORY, categoryId);
        cv.put(COLUMN_COM_PHOTO, companyPhoto);
        cv.put(COLUMN_COM_DESCRIPTION, companyDescription);
        cv.put(COLUMN_COM_EMAIL, email);
        cv.put(COLUMN_COM_PHONE, phone);
        //cv.put(COLUMN_COM_ADDRESS, companyAddress);
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

    private long insertCity(SQLiteDatabase db, String cityName, String photo, String description) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CITY_TITLE, cityName);
        cv.put(COLUMN_CITY_PHOTO, photo);
        cv.put(COLUMN_CITY_DESCRIPTION, description);
        return db.insert(TABLE_CITY, null, cv);


//        cv.put(COLUMN_CITY_NAME, cityName);
//        return db.insert(TABLE_CHAT, null, cv);
    }

    public String getCityDescriptionById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_CITY_DESCRIPTION + " FROM " + TABLE_CITY +
                " WHERE " + COLUMN_CITY_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        String description = null;

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_CITY_DESCRIPTION);
            if (columnIndex != -1) {
                description = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        return description;
    }

    public String getCityPhotoById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_CITY_PHOTO + " FROM " + TABLE_CITY +
                " WHERE " + COLUMN_CITY_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        String photo = null;

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_CITY_PHOTO);
            if (columnIndex != -1) {
                photo = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        return photo;
    }

    // метод для отримання ідентифікатора чату за назвою міста
    public int getChatIdByCityName(int cityId) {
        //public int getChatIdByCityId(String cityName) {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT " + COLUMN_CHAT_ID + " FROM " + TABLE_CHAT +
                    " WHERE " + COLUMN_CITY_CHAT_ID + " = ?";

            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(cityId)});

            int chatId = -1; // Значення за замовчуванням

            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(COLUMN_CHAT_ID);
                if (columnIndex != -1) {
                    chatId = cursor.getInt(columnIndex);
                }
            }

            cursor.close();
            return chatId;
        //}



//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT " + COLUMN_CHAT_ID + " FROM " + TABLE_CHAT +
//                " WHERE " + COLUMN_CITY_NAME + " = ?";
//
//        Cursor cursor = db.rawQuery(query, new String[]{cityName});
//
//        int chatId = -1; // Значення за замовчуванням
//
//        if (cursor.moveToFirst()) {
//            int columnIndex = cursor.getColumnIndex(COLUMN_CHAT_ID);
//            if (columnIndex != -1) {
//                chatId = cursor.getInt(columnIndex);
//            }
//        }
//
//        cursor.close();
//        return chatId;
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

    // створення чату для конкретного міста
    public int createChat(int cityId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY_CHAT_ID, cityId);

        long rowId = db.insert(TABLE_CHAT, null, values);

        if (rowId > Integer.MAX_VALUE) {
            return -1;
        } else {
            return (int) rowId;
        }
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
    public int getCityForUser(String userPhone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_CITY + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_PHONE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userPhone});
        int cityName = 0;

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_CITY);
            if (columnIndex != -1) {
                cityName = cursor.getInt(columnIndex);
            }
        }

        cursor.close();
        return cityName;
    }

    public Map<Integer, String> getCityData(SQLiteDatabase db) {
        //SQLiteDatabase db = this.getReadableDatabase();
        Map<Integer, String> cityData = new HashMap<>();

        String query = "SELECT " + COLUMN_CITY_ID + ", " + COLUMN_CITY_TITLE + " FROM " + TABLE_CITY;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int cityId = cursor.getInt(cursor.getColumnIndex(COLUMN_CITY_ID));
                String cityName = cursor.getString(cursor.getColumnIndex(COLUMN_CITY_TITLE));
                cityData.put(cityId, cityName);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return cityData;
    }

    public String getCityById(int cityId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String cityName = null;

        String query = "SELECT " + COLUMN_CITY_TITLE + " FROM " + TABLE_CITY + " WHERE " + COLUMN_CITY_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(cityId)});

        if (cursor.moveToFirst()) {
            cityName = cursor.getString(cursor.getColumnIndex(COLUMN_CITY_TITLE));
        }

        cursor.close();
        return cityName;
    }


    public List<Company> getCompaniesByCityId(int cityId) {
        List<Company> companies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + TABLE_COMPANY + ".* " +
                "FROM " + TABLE_COMPANY + " " +
                "INNER JOIN " + TABLE_COMPANY_CITY + " ON " +
                TABLE_COMPANY + "." + COLUMN_COM_ID + " = " + TABLE_COMPANY_CITY + "." + COLUMN_COM_COMPANY_ID + " " +
                "WHERE " + TABLE_COMPANY_CITY + "." + COLUMN_COM_CITY_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(cityId)});

        if (cursor.moveToFirst()) {
            do {
                int companyId = cursor.getInt(cursor.getColumnIndex(COLUMN_COM_ID));
                String companyName = cursor.getString(cursor.getColumnIndex(COLUMN_COM_NAME));
                String idCategory = cursor.getString(cursor.getColumnIndex(COLUMN_COM_ID_CATEGORY));
                String companyPhoto = cursor.getString(cursor.getColumnIndex(COLUMN_COM_PHOTO));
                String companyDescription = cursor.getString(cursor.getColumnIndex(COLUMN_COM_DESCRIPTION));
                String companyRating = cursor.getString(cursor.getColumnIndex(COLUMN_COM_RATING));
                String companyEmail = cursor.getString(cursor.getColumnIndex(COLUMN_COM_EMAIL));
                String companyPhone = cursor.getString(cursor.getColumnIndex(COLUMN_COM_PHONE));

                Company company = new Company(companyId, companyName, idCategory, companyPhoto, companyDescription, companyRating, companyEmail, companyPhone);
                companies.add(company);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return companies;
    }
    public CompanyDetails getCompanyDetailsById(int companyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String cityIdQuery = "SELECT " + COLUMN_COMPANY_CITY_ID + " FROM " + TABLE_COMPANY_CITY +
                " WHERE " + COLUMN_COM_COMPANY_ID + " = ?";
        Cursor cityIdCursor = db.rawQuery(cityIdQuery, new String[]{String.valueOf(companyId)});
        int companyCityId = -1;

        if (cityIdCursor.moveToFirst()) {
            companyCityId = cityIdCursor.getInt(0);
        }
        cityIdCursor.close();
        String query = "SELECT * FROM " + TABLE_COMPANY_DETAILS +
                " WHERE " + COLUMN_COMPANY_CITY_ID_TABLE + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(companyCityId)});
        CompanyDetails companyDetails = null;
        if (cursor.moveToFirst()) {
            String companyAddress = cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY_DETAILS_ADDRESS));
            String companyPhoto = cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY_DETAILS_PHOTO));
            String company_city = cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY_CITY_ID_TABLE));

            companyDetails = new CompanyDetails(companyAddress, companyPhoto, company_city);
        }

        cursor.close();
        return companyDetails;
    }

    //для пошуку вакансій за ключовими словами
    public List<Map<String, Object>> searchJobOffersByKeywords(String keywords) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Map<String, Object>> offersList = new ArrayList<>();

        String query = "SELECT * " +
                "FROM " + TABLE_JOB_OFFER +
                " WHERE " + COLUMN_JOB_OFFER_NAME + " LIKE ?" +
                " OR " + COLUMN_JOB_OFFER_COMPANY_NAME + " LIKE ?";

        String[] selectionArgs = new String[]{"%" + keywords + "%", "%" + keywords + "%"};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> offerItem = new HashMap<>();
                int offerId = cursor.getInt(cursor.getColumnIndex(COLUMN_JOB_OFFER_ID));
                int companyId = cursor.getInt(cursor.getColumnIndex(COLUMN_JOB_OFFER_ID_COMPANY));
                String offerName = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_OFFER_NAME));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_OFFER_DESCRIPTION));
                String salary = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_OFFER_SALARY));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_OFFER_EMAIL));
                String phone = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_OFFER_PHONE));
                String requirements = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_OFFER_REQUIREMENTS));
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_OFFER_CATEGORY));
                String companyName = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_OFFER_COMPANY_NAME));
                String categoryCompany = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_OFFER_CATEGORY_COMPANY));
                String keywordsCompany = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_OFFER_KEYWORDS));

                offerItem.put("Id", offerId);
                offerItem.put("companyId", companyId);
                offerItem.put("companyName", companyName);
                offerItem.put("offerName", offerName);
                offerItem.put("description", description);
                offerItem.put("salary", salary);
                offerItem.put("email", email);
                offerItem.put("phone", phone);
                offerItem.put("requirements", requirements);
                offerItem.put("category", category);
                offerItem.put("categoryCompany", categoryCompany);
                offerItem.put("keywords", keywordsCompany);

                offersList.add(offerItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return offersList;
    }
    public Map<Integer, String> getCompanyCategoryData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<Integer, String> cityData = new HashMap<>();

        String query = "SELECT " + COLUMN_COM_CAT_ID + ", " + COLUMN_COM_CAT_NAME + " FROM " + TABLE_COMPANY_CATEGORY;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int categoryId = cursor.getInt(cursor.getColumnIndex(COLUMN_COM_CAT_ID));
                String categoryName = cursor.getString(cursor.getColumnIndex(COLUMN_COM_CAT_NAME));
                cityData.put(categoryId, categoryName);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return cityData;
    }

    public User getUserByPhone(String phone) {
        User user = null;

        // Ось ваш SQL-запит для отримання інформації про користувача за номером телефона
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PHONE + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{phone});

        if (cursor.moveToFirst()) {
            // Отримуємо дані про користувача з рядка результату запиту
            user = new User();
            user.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
            user.setUserPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
            user.setUserPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
            user.setUserCity(cursor.getInt(cursor.getColumnIndex(COLUMN_CITY)));
            user.setUserPhoto(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHOTO)));
            user.setAge(cursor.getInt(cursor.getColumnIndex(COLUMN_AGE)));
            user.setAccessGeo(cursor.getInt(cursor.getColumnIndex(COLUMN_ACCESS_GEO)) == 1);
            user.setPrivatePolicy(cursor.getInt(cursor.getColumnIndex(COLUMN_PRIVATE_POLICY)) == 1);
            user.setUserOffers(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_OFFERS)) == 1);
            user.setNotificationPromotions(cursor.getInt(cursor.getColumnIndex(COLUMN_NOTIFICATION_PROMOTIONS)) == 1);
        }

        cursor.close();
        db.close();

        return user;
    }

    public long updateUser(int userId, String newName, String newPhone, String newPassword,
                           int newCity, String newGender, int newAge, boolean newAccessGeo,
                           boolean newPrivatePolicy, boolean newUserOffers, boolean newNotificationPromotions,
                           String newPhoto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, newName);
        values.put(COLUMN_PHONE, newPhone);
        values.put(COLUMN_PASSWORD, newPassword);
        values.put(COLUMN_CITY, newCity);
        values.put(COLUMN_GENDER, newGender);
        values.put(COLUMN_AGE, newAge);
        values.put(COLUMN_ACCESS_GEO, newAccessGeo);
        values.put(COLUMN_PRIVATE_POLICY, newPrivatePolicy);
        values.put(COLUMN_USER_OFFERS, newUserOffers);
        values.put(COLUMN_NOTIFICATION_PROMOTIONS, newNotificationPromotions);
        values.put(COLUMN_USER_PHOTO, newPhoto);

        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(userId)});
    }

    //hidden category
    public long addCategoryToUser(int userId, int categoryId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_HIDDEN_USER_SERVICES_ID_USER, userId);
        cv.put(COLUMN_HIDDEN_USER_SERVICES_ID_CATEGORY, categoryId);

        long result = db.insert(TABLE_HIDDEN_USER_SERVICES, null, cv);

        return result;
    }
    public void removeCategoryFromUser(int userId, int categoryId) {
        String deleteQuery = "DELETE FROM " + TABLE_HIDDEN_USER_SERVICES +
                " WHERE " + COLUMN_HIDDEN_USER_SERVICES_ID_USER + " = " + userId +
                " AND " + COLUMN_HIDDEN_USER_SERVICES_ID_CATEGORY + " = " + categoryId;

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(deleteQuery);
        db.close();
    }

    public void updateUserCategories(int userId, List<Integer> selectedCategoryIds) {
        SQLiteDatabase db = this.getWritableDatabase();

         //Спочатку видаліть всі існуючі записи про категорії для користувача
        String deleteQuery = "DELETE FROM " + TABLE_HIDDEN_USER_SERVICES +
                " WHERE " + COLUMN_HIDDEN_USER_SERVICES_ID_USER + " = " + userId;
        db.execSQL(deleteQuery);

        // Додайте нові записи для вибраних категорій
        for (int categoryId : selectedCategoryIds) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_HIDDEN_USER_SERVICES_ID_USER, userId);
            cv.put(COLUMN_HIDDEN_USER_SERVICES_ID_CATEGORY, categoryId);
            db.insert(TABLE_HIDDEN_USER_SERVICES, null, cv);
        }
    }

    public List<NewsCard> getNewsByCompanyId(int companyId) {
        List<NewsCard> itemList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NEWS_FEED + " WHERE " + COLUMN_ID_COMPANY + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(companyId)});

        if (cursor.moveToFirst()) {
            do {
                NewsCard newsCard = new NewsCard();
                int idCompany = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID_COMPANY)));
                newsCard.setId_company(idCompany);
                newsCard.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_NEWS_FEED_PHOTO)));
                newsCard.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_NEWS_FEED_DESCRIPTION)));
                String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NEWS_FEED_TIME));
                String dateFormatPattern = "yyyy-MM-dd HH:mm:ss";
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.US);
                    Date date = dateFormat.parse(time);
                    newsCard.setPublication_time(date);
                    itemList.add(newsCard);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return itemList;
    }

    public Reviews getRatingCompanyByCompanyId(int companyId) {
        String selectQuery = "SELECT * FROM " + TABLE_COMPANY_REVIEWS + " WHERE " + COLUMN_COMPANY_REVIEWS_ID_COMPANY + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(companyId)});
        Reviews reviews = null;
        if (cursor.moveToFirst()) {
            double locationRating = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY_REVIEWS_LOCATION)));
            double serviceRating = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY_REVIEWS_SERVICE)));
            double availabilityRating = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY_REVIEWS_AVAILABILITY)));
            double comfortRating = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY_REVIEWS_COMFORT)));
            String comment = cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY_REVIEWS_USER_COMMENT));
            int userId = cursor.getInt(cursor.getColumnIndex(COLUMN_COMPANY_REVIEWS_ID_USER));
            int bookingId = cursor.getInt(cursor.getColumnIndex(COLUMN_COMPANY_REVIEWS_ID_BOOKING));
            reviews = new Reviews(companyId, locationRating, serviceRating, availabilityRating,
                    comfortRating, comment, userId, bookingId);
           }

        cursor.close();
        db.close();

        return reviews;
    }

    public Company getCompanyById(int Id) {
        String selectQuery = "SELECT * FROM " + TABLE_COMPANY + " WHERE " + COLUMN_COM_ID + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});
        Company company = null;
        if (cursor.moveToFirst()) {
            int companyId = cursor.getInt(cursor.getColumnIndex(COLUMN_COM_ID));
            String companyName = cursor.getString(cursor.getColumnIndex(COLUMN_COM_NAME));
            String IdCategory = cursor.getString(cursor.getColumnIndex(COLUMN_COM_ID_CATEGORY));
            String CompanyPhoto = cursor.getString(cursor.getColumnIndex(COLUMN_COM_PHOTO));
            String CompanyDescription = cursor.getString(cursor.getColumnIndex(COLUMN_COM_DESCRIPTION));
            String CompanyRating = cursor.getString(cursor.getColumnIndex(COLUMN_COM_RATING));
            String Email = cursor.getString(cursor.getColumnIndex(COLUMN_COM_EMAIL));
            String Phone = cursor.getString(cursor.getColumnIndex(COLUMN_COM_PHONE));

            company = new Company(companyId, companyName, IdCategory, CompanyPhoto, CompanyDescription,CompanyRating,Email, Phone);
        }

        cursor.close();
        db.close();

        return company;
    }
    public long addAppliedVacancy(int idVacancy, int idUser, String experience, String whyYou,
                                  String languages, String employment, String fileNames) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_APPLIED_VACANCY_ID_VACANCY, idVacancy);
        cv.put(COLUMN_APPLIED_VACANCY_ID_USER, idUser);
        cv.put(COLUMN_APPLIED_VACANCY_EXPERIENCE, experience);
        cv.put(COLUMN_APPLIED_VACANCY_WHY_YOU, whyYou);
        cv.put(COLUMN_APPLIED_VACANCY_LANGUAGES, languages);
        cv.put(COLUMN_APPLIED_VACANCY_EMPLOYMENT, employment);
        cv.put(COLUMN_APPLIED_VACANCY_FILE_NAMES, fileNames);

        long result = db.insert(TABLE_APPLIED_VACANCY, null, cv);

        return result;
    }
    public List<Company> getCompanyByKeywordsAndCity(int cityId, String keywords) {
        List<Company> sortedList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

//        String query = "SELECT " + TABLE_COMPANY + ".* " +
//                "FROM " + TABLE_COMPANY + " " +
//                "INNER JOIN " + TABLE_COMPANY_CITY + " ON " +
//                TABLE_COMPANY + "." + COLUMN_COM_ID + " = " + TABLE_COMPANY_CITY + "." + COLUMN_COM_COMPANY_ID + " " +
//                "WHERE " + TABLE_COMPANY_CITY + "." + COLUMN_COM_CITY_ID + " = ? AND " +
//                TABLE_COMPANY + "." + COLUMN_COM_NAME + " = ?";

        String query = "SELECT " + TABLE_COMPANY + ".* " +
                "FROM " + TABLE_COMPANY + " " +
                "INNER JOIN " + TABLE_COMPANY_CITY + " ON " +
                TABLE_COMPANY + "." + COLUMN_COM_ID + " = " + TABLE_COMPANY_CITY + "." + COLUMN_COM_COMPANY_ID + " " +
                "WHERE " + TABLE_COMPANY_CITY + "." + COLUMN_COM_CITY_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(cityId)});
//        String query = "SELECT " + TABLE_COMPANY + ".* " +             "FROM " + TABLE_COMPANY + " " +             "INNER JOIN " + TABLE_COMPANY_CITY + " ON " +             TABLE_COMPANY + "." + COLUMN_COM_ID + " = " + TABLE_COMPANY_CITY + "." + COLUMN_COM_COMPANY_ID + " " +             "WHERE " + TABLE_COMPANY_CITY + "." + COLUMN_COM_CITY_ID + " = ? AND " +             "(" + TABLE_COMPANY + "." + COLUMN_COM_NAME + " LIKE ? OR " +             TABLE_COMPANY + "." + COLUMN_COM_DESCRIPTION + " LIKE ?)";
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(cityId), "%" + keywords + "%", "%" + keywords + "%"});
//        String[] selectionArgs = new String[]{String.valueOf(cityId), "%" + keywords + "%", "%" + keywords + "%"};

        if (cursor.moveToFirst()) {
            do {
                int companyId = cursor.getInt(cursor.getColumnIndex(COLUMN_COM_ID));
                String companyName = cursor.getString(cursor.getColumnIndex(COLUMN_COM_NAME));
                String idCategory = cursor.getString(cursor.getColumnIndex(COLUMN_COM_ID_CATEGORY));
                String companyPhoto = cursor.getString(cursor.getColumnIndex(COLUMN_COM_PHOTO));
                String companyDescription = cursor.getString(cursor.getColumnIndex(COLUMN_COM_DESCRIPTION));
                String companyRating = cursor.getString(cursor.getColumnIndex(COLUMN_COM_RATING));
                String companyEmail = cursor.getString(cursor.getColumnIndex(COLUMN_COM_EMAIL));
                String companyPhone = cursor.getString(cursor.getColumnIndex(COLUMN_COM_PHONE));

                if(companyName.toLowerCase().contains(keywords.toLowerCase())){
                    Company company = new Company(companyId, companyName, idCategory, companyPhoto, companyDescription, companyRating, companyEmail, companyPhone);
                    sortedList.add(company);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return sortedList;
    }

    public List<Payment> getPaymentsByUserId(int userId) {
        List<Payment> payments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_PAYMENT +
                " WHERE " + COLUMN_PAYMENT_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int paymentId = cursor.getInt(cursor.getColumnIndex(COLUMN_PAYMENT_ID));
                int companyId = cursor.getInt(cursor.getColumnIndex(COLUMN_PAYMENT_ID_COMPANY));
                Log.d("hello", "payment Company = " + companyId);
                String paymentDateString = cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_DATE));
                Date paymentDate = convertStringToDate2(paymentDateString);
                Log.d("hello", "payment Date = " + paymentDate);
                String typePayment = cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_TYPE_PAYMENT));
                double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT));

                Payment payment = new Payment(paymentId, userId, companyId, paymentDate, typePayment, amount);
                payments.add(payment);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return payments;
    }

    public List<Booking> getUpcomingBookingsForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Booking> upcomingBookings = new ArrayList<>();

        // Отримання поточної дати для порівняння
        Date currentDate = new Date();

        // SQL-запит для вибору майбутніх бронювань
        String query = "SELECT * FROM " + TABLE_BOOKINGS +
                " WHERE " + COLUMN_BOOKING_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_ID));
                int roomId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_ROOM_ID));
                String guestName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_GUEST_NAME));
                String checkInDateStr = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHECKIN_DATE));
                String checkOutDateStr = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHECKOUT_DATE));
                double totalPrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_BOOKING_TOTAL_PRICE));
                int numGuests = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_NUM_GUESTS));
                int children = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_HAS_CHILDREN));
                String childAge = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_CHILD_AGE));
                int numRooms = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_NUM_ROOMS));

                Date checkInDate = convertStringToDate2(checkInDateStr);
                Date checkOutDate = convertStringToDate2(checkOutDateStr);

                // Порівняння дат
                if (checkOutDate != null && checkInDate.after(currentDate)) {
                    // Створення об'єкта Booking
                    Booking booking = new Booking(id, roomId, guestName, checkInDate, checkOutDate,
                            totalPrice, numGuests, children, childAge, numRooms, 0, userId);

                    upcomingBookings.add(booking);
                }

            } while (cursor.moveToNext());
            cursor.close();
        }

        return upcomingBookings;
    }
    public long addReview(int idCompany, double location, double service, double availability,
                          double comfort, String userComment, int userId, int bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_COMPANY_REVIEWS_ID_COMPANY, idCompany);
        cv.put(COLUMN_COMPANY_REVIEWS_ID_USER, userId);
        cv.put(COLUMN_COMPANY_REVIEWS_LOCATION, location);
        cv.put(COLUMN_COMPANY_REVIEWS_SERVICE, service);
        cv.put(COLUMN_COMPANY_REVIEWS_AVAILABILITY, availability);
        cv.put(COLUMN_COMPANY_REVIEWS_COMFORT, comfort);
        cv.put(COLUMN_COMPANY_REVIEWS_USER_COMMENT, userComment);
        cv.put(COLUMN_COMPANY_REVIEWS_ID_BOOKING, bookingId);

        long result = db.insert(TABLE_COMPANY_REVIEWS, null, cv);

        return result;
    }

}
