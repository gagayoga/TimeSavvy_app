package com.example.timesavvy_app.data.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timesavvy_app.R;
import com.example.timesavvy_app.autentikasi.Login.LoginPage;
import com.example.timesavvy_app.data.crudjadwal.TambahJadwalPage;
import com.example.timesavvy_app.data.datasemuasiswa.DataSemuaSiswa;
import com.example.timesavvy_app.data.datasemuasiswa.DataSemuaSiswaPage;
import com.example.timesavvy_app.data.jadwalpiket.JadwalPage;
import com.example.timesavvy_app.data.splashscreen.SplashscreenPage;
import com.example.timesavvy_app.koneksi.ApiClient;
import com.example.timesavvy_app.koneksi.UserServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textViewDateTime, txtUsername;
    private Handler handler;
    private Runnable runnable;
    private SharedPreferences sharedPreferences;
    private LinearLayout cardJadwal, cardSiswa;
    private RelativeLayout cardLogout;
    private String userToken, idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting text date
        textViewDateTime = findViewById(R.id.text_view_date_time);
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                updateDateTime();
                handler.postDelayed(this, 1000); // Update every second
            }
        };

        handler.post(runnable);

        // Navigasi ke jadwal piket
        cardJadwal = findViewById(R.id.cardJadwal);
        cardJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigasiJadwal = new Intent(MainActivity.this, JadwalPage.class);
                startActivity(navigasiJadwal);
            }
        });

        // Navigasi ke data siswa
        cardSiswa = findViewById(R.id.cardSiswa);
        cardSiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigasiDataSiswa = new Intent(MainActivity.this, DataSemuaSiswaPage.class);
                startActivity(navigasiDataSiswa);
            }
        });

        // Get Username
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String usernameUserr = sharedPreferences.getString("nama_user", "");
        idUser = sharedPreferences.getString("id_user", "");
        int UserID = Integer.parseInt(idUser);
        userToken = getUserToken();

        //Setting username
        txtUsername = findViewById(R.id.txtUsername);
        txtUsername.setText("Hallo, Selamat Datang " + usernameUserr);

        // Setting button logout
        cardLogout = findViewById(R.id.cardLogout);
        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private void updateDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());
        textViewDateTime.setText(currentDateTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private void logoutUser() {
        String token = "Bearer " + userToken;

        UserServices userServices = ApiClient.getUserServices();
        Call<Void> call = userServices.userLogout("Bearer " + token);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Clear user data from SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    String message = "Logout berhasil, silakan login kembali";
                    new android.app.AlertDialog.Builder(MainActivity.this)
                            .setTitle("Pemberitahuan")
                            .setMessage(message)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Redirect to login activity
                                    Intent intentLogout = new Intent(MainActivity.this, SplashscreenPage.class);
                                    startActivity(intentLogout);
                                    finish();
                                }
                            })
                            .setIcon(R.drawable.icon_success)
                            .show();

                } else {
                    Toast.makeText(MainActivity.this, "Failed to logout: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // get token user
    private String getUserToken() {
        return sharedPreferences.getString("user_token", "");
    }
}