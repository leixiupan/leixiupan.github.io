package com.example.studentmgr;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityAds extends AppCompatActivity {

    private Button skipButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);

        skipButton = findViewById(R.id.btn_skip);

        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                skipButton.setText("跳过广告 （" + millisUntilFinished / 1000 + "s）");
            }

            @Override
            public void onFinish() {
                startMainActivity();
            }
        }.start();

        skipButton.setOnClickListener(v -> {
            countDownTimer.cancel();
            startMainActivity();
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(ActivityAds.this, ActivityMain.class);
        startActivity(intent);
        finish();
    }
}
