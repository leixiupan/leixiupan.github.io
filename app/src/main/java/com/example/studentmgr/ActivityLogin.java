package com.example.studentmgr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityLogin extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        progressBar = findViewById(R.id.progressBar);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String username = etUsername.getText().toString();
                        String password = etPassword.getText().toString();

                        if (username.equals("admin") && password.equals("123456")) {
                            Intent intent = new Intent(ActivityLogin.this, ActivityAds.class);
                            startActivity(intent);
                            finish();
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(ActivityLogin.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 1000);
            }
        });
    }
}