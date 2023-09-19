package com.example.geome;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class DownloadPage extends AppCompatActivity {

    private ImageView animatedImageView;
    private ImageView animatedImageView2;
    private LinearLayout layout;

    private ProgressBar progressBar;
    private Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_page);

        layout = findViewById(R.id.layout);
        animatedImageView = findViewById(R.id.animatedImageView);
        animatedImageView2 = new ImageView(this);
        layout.addView(animatedImageView2);
        progressBar = findViewById(R.id.progressBar);
        int appLoadingTime = 5000;

        animateImage(animatedImageView);
        animateImage(animatedImageView2);
        simulateAppLoading(appLoadingTime);
    }
    private void animateImage(final ImageView imageView) {
        final int screenWidth = getResources().getDisplayMetrics().widthPixels;

        final ValueAnimator animator = ValueAnimator.ofInt(0, screenWidth);
        animator.setDuration(6000); // Тривалість анімації в мілісекундах
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE); // Безкінечне повторення

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                imageView.scrollTo(value, 0);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        animator.start();
    }

    private void simulateAppLoading(int loadingTime) {
        final int totalProgress = 100;
        final int progressInterval = 100; // Інтервал для оновлення ProgressBar
        final int numSteps = loadingTime / progressInterval;
        final int progressStep = totalProgress / numSteps;

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int progress = 0; progress <= totalProgress; progress += progressStep) {
                    final int currentProgress = progress;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(currentProgress);
                        }
                    });

                    try {
                        Thread.sleep(progressInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        openNextActivity();
                    }
                });
            }
        }).start();
    }

    private void openNextActivity() {
        Intent intent = new Intent(this, IntroductionActivity.class);
        startActivity(intent);
        finish(); // Якщо потрібно закрити поточну активність
    }
}