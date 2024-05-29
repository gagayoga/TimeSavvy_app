package com.example.timesavvy_app.data.jadwalpiket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.timesavvy_app.R;
import com.example.timesavvy_app.data.crudjadwal.TambahJadwalPage;
import com.example.timesavvy_app.koneksi.ApiClient;
import com.example.timesavvy_app.koneksi.UserServices;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalPage extends AppCompatActivity {

    private ImageView iconBack, imageError;
    private RecyclerView recyclerView;
    private JadwalHariAdapter adapter;
    private UserServices userServices;
    private SharedPreferences sharedPreferences;
    private String userToken, idUser;
    private ProgressBar progressBar;
    private MaterialButton btnJadwal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_page);

        //Setting back icon
        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // get data user
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userToken = getUserToken();

        // Inisialisasi UI
        progressBar = findViewById(R.id.progressBar);
        imageError = findViewById(R.id.imageError);

        // btn jadwal
        btnJadwal = findViewById(R.id.btnJadwal);
        btnJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentJadwal = new Intent(JadwalPage.this, TambahJadwalPage.class);
                startActivity(intentJadwal);
            }
        });

        // inisialize userServices
        userServices = ApiClient.getUserServices();

        settingRecyclerView();
        fetchDataJadwalHari();
    }

    // get token user
    private String getUserToken() {
        return sharedPreferences.getString("user_token", "");
    }

    // Setting recycler view
    public void settingRecyclerView(){
        recyclerView = findViewById(R.id.recyclerviewJadwal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JadwalHariAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    // Fetch data api
    public void fetchDataJadwalHari(){
        String tokenUser = "Bearer " + userToken;
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        userServices.getJadwalPiket(tokenUser).enqueue(new Callback<JadwalPiketResponse>() {
            @Override
            public void onResponse(@NonNull Call<JadwalPiketResponse> call, @NonNull Response<JadwalPiketResponse> response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if (response.isSuccessful() && response.body() != null) {
                    List<JadwalPiketResponse.Jadwal> jadwalList = response.body().getData();
                    adapter.setDayList(jadwalList);
                } else {
                    imageError.setVisibility(View.VISIBLE);
                    Toast.makeText(JadwalPage.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JadwalPiketResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                imageError.setVisibility(View.VISIBLE);
                Toast.makeText(JadwalPage.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}