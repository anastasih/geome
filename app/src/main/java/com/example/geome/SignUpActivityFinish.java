package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;
import android.text.Html;

public class SignUpActivityFinish extends AppCompatActivity {

    TextView textViewCongratulations;
    TextView textViewHowWorks;
    TextView textViewUser;
    TextView textViewMap;
    TextView textViewTape;
    TextView textViewCity;
    TextView textViewChat;
    TextView buttonNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_finish);

        initView();
    }

    private void initView() {
        textViewCongratulations = findViewById(R.id.textView);
        textViewHowWorks = findViewById(R.id.textView2);
        textViewUser = findViewById(R.id.textView3);
        textViewMap = findViewById(R.id.textView4);
        textViewTape = findViewById(R.id.textView5);
        textViewCity = findViewById(R.id.textView6);
        textViewChat = findViewById(R.id.textView7);
        buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(this::logIn);

        String fullText = "Вітаємо, <br>реєстрацію <font color='#0054D2'>завершено!</font>";
        textViewCongratulations.setText(Html.fromHtml(fullText));

        SpannableString spannableString = new SpannableString("Тепер ви можете повноцінно користуватися нашим сервісом.\n\nЯк все працює?");
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldSpan, 56, 70, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewHowWorks.setText(spannableString);

        spannableString = new SpannableString(
                "Профіль. Переглядайте заплановані візити,  будуйте маршрути, збирайте бонуси, створюйте бізнес!");
        boldSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldSpan, 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewUser.setText(spannableString);

        spannableString = new SpannableString(
                "Мапа. Переглядайте заклади на мапі та дізнавайтеся більше про популярні місця!");
        boldSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewMap.setText(spannableString);

        spannableString = new SpannableString(
                "Стрічка. Переглядайте дописи від компаній, представлених на сервісі, та не пропустіть цікаві пропозиції!");
        boldSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldSpan, 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewTape.setText(spannableString);

        spannableString = new SpannableString(
                "Місто. Дізнавайтесь усе про місто, в якому знаходитесь та його визначні місця!");
        boldSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldSpan, 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewCity.setText(spannableString);

        spannableString = new SpannableString(
                "Чат. Спілкуйтеся з іншими користувачами у  місті та використовуйте їх досвід для вдалого планування!");
        boldSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewChat.setText(spannableString);
    }

    private void logIn(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}