package com.tyler.book_monitor.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.tyler.book_monitor.R;
import com.tyler.book_monitor.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;

    private LottieAnimationView lavSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        lavSplash = findViewById(R.id.lav_splash);

        lavSplash.animate().setDuration(SPLASH_TIME_OUT);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}