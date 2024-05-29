package com.example.timesavvy_app.data.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.timesavvy_app.autentikasi.Login.LoginPage;
import com.example.timesavvy_app.data.main.MainActivity;
import com.example.timesavvy_app.R;

public class SplashscreenPage extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private String userToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreenpage);

        // Setting splashscreen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();
            }
        }, 3000);

        // Get data user dari penyimpanan lokal
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userToken = getUserToken();
    }

    // Fungsi get data token user
    private String getUserToken() {
        return sharedPreferences.getString("user_token", "");
    }

    // Validasi user login
    private void checkUser(){
        if (userToken.isEmpty()) {
            Intent intentLogin = new Intent(SplashscreenPage.this, LoginPage.class);
            startActivity(intentLogin);
            finish();
        } else {
            Toast.makeText(SplashscreenPage.this, "Selamat Datang Kembali, Anda sudah login", Toast.LENGTH_SHORT).show();
            Intent intentHome = new Intent(SplashscreenPage.this, MainActivity.class);
            startActivity(intentHome);
            finish();
        }
    }
}