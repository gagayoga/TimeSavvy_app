package com.example.timesavvy_app.data.datasemuasiswa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timesavvy_app.R;
import com.example.timesavvy_app.autentikasi.Registrasi.RegisterPage;
import com.example.timesavvy_app.koneksi.ApiClient;
import com.example.timesavvy_app.koneksi.UserServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSemuaSiswaPage extends AppCompatActivity {

    private ImageView iconBack, imageError;
    private RecyclerView recyclerView;
    private DataSemuaSiswaAdapter adapter;
    private UserServices userServices;
    private String userToken;
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBar;
    private TextView txtNamaToolbar;
    private RelativeLayout cardDataKosong;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<DataSemuaSiswa> userList;
    private LinearLayout fabUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_semua_siswa_page);

        // Card Data kosong
        cardDataKosong = findViewById(R.id.cardDataKosong);

        //Setting back icon
        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageError = findViewById(R.id.imageError);

        // Settings floating button
        fabUser = findViewById(R.id.floatingUser);
        fabUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddUser = new Intent(DataSemuaSiswaPage.this, RegisterPage.class);
                startActivity(intentAddUser);
            }
        });

        // get data user
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userToken = getUserToken();

        // Inisialisasi UI
        progressBar = findViewById(R.id.progressBar);

        // inisialize userServices
        userServices = ApiClient.getUserServices();

        refreshData();

        // Refresh Layout
        swipeRefreshLayout = findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    // get token user
    private String getUserToken() {
        return sharedPreferences.getString("user_token", "");
    }

    // Setting recycler view
    public void settingRecyclerView(){
        recyclerView = findViewById(R.id.recyclerviewJadwal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        adapter = new DataSemuaSiswaAdapter(userList, this, sharedPreferences);
        recyclerView.setAdapter(adapter);
    }

    // Fetch data api
    public void fetchDataSiswa() {
        String tokenUser = "Bearer " + userToken;
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        cardDataKosong.setVisibility(View.GONE);

        userServices.getDataUser(tokenUser).enqueue(new Callback<DataSemuaSiswaResponse>() {
            @Override
            public void onResponse(@NonNull Call<DataSemuaSiswaResponse> call, @NonNull Response<DataSemuaSiswaResponse> response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if (response.isSuccessful() && response.body() != null) {
                    DataSemuaSiswaResponse dataResponse = response.body();
                    if (dataResponse.getStatus() == 200) {
                        userList.addAll(dataResponse.getData());
                        adapter.notifyDataSetChanged();
                        // Tambahkan logika untuk menampilkan cardDataKosong jika data kosong
                        if (userList.isEmpty()) {
                            cardDataKosong.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(DataSemuaSiswaPage.this, dataResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    imageError.setVisibility(View.VISIBLE);
                    Toast.makeText(DataSemuaSiswaPage.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataSemuaSiswaResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                imageError.setVisibility(View.VISIBLE);
                Toast.makeText(DataSemuaSiswaPage.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshData() {
        settingRecyclerView();
        fetchDataSiswa();
    }
}
