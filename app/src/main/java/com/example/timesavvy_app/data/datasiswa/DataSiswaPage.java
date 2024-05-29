package com.example.timesavvy_app.data.datasiswa;

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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timesavvy_app.R;
import com.example.timesavvy_app.data.jadwalpiket.JadwalHariAdapter;
import com.example.timesavvy_app.data.jadwalpiket.JadwalPage;
import com.example.timesavvy_app.data.jadwalpiket.JadwalPiketResponse;
import com.example.timesavvy_app.koneksi.ApiClient;
import com.example.timesavvy_app.koneksi.UserServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSiswaPage extends AppCompatActivity {

    private ImageView iconBack;
    private RecyclerView recyclerView;
    private DataSiswaAdapter adapter;
    private UserServices userServices;
    private String userToken;
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBar;
    private TextView txtNamaToolbar;
    private RelativeLayout cardDataKosong;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int idHari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_siswa_page);

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

        // get data user
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userToken = getUserToken();

        // Inisialisasi UI
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        idHari = intent.getIntExtra("id_hari", -1);
        String namaHari = intent.getStringExtra("nama_hari");

        // Setting nama Toolbar
        txtNamaToolbar = findViewById(R.id.txtNamaToolbar);
        txtNamaToolbar.setText("Jadwal Piket Hari " + namaHari);

        // inisialize userServices
        userServices = ApiClient.getUserServices();

        settingRecyclerView();
        fetchDataSiswa(idHari);

        // Refresh Layout
        swipeRefreshLayout = findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                settingRecyclerView();
                fetchDataSiswa(idHari);
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
        adapter = new DataSiswaAdapter(this, sharedPreferences, idHari);
        recyclerView.setAdapter(adapter);
    }

    // Fetch data api
    public void fetchDataSiswa(int idHari){
        String tokenUser = "Bearer " + userToken;
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        cardDataKosong.setVisibility(View.GONE);

        userServices.getJadwalPiket(tokenUser).enqueue(new Callback<JadwalPiketResponse>() {
            @Override
            public void onResponse(@NonNull Call<JadwalPiketResponse> call, @NonNull Response<JadwalPiketResponse> response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if (response.isSuccessful() && response.body() != null) {
                    List<JadwalPiketResponse.Jadwal> jadwalList = response.body().getData();
                    for (JadwalPiketResponse.Jadwal jadwal : jadwalList) {
                        if (jadwal.getId() == idHari) {
                            List<JadwalPiketResponse.Siswa> siswaList = jadwal.getSiswa();
                            adapter.setSiswaList(siswaList);

                            if (siswaList.isEmpty()) {
                                cardDataKosong.setVisibility(View.VISIBLE);
                            } else {
                                cardDataKosong.setVisibility(View.GONE);
                            }
                            break;
                        }
                    }
                } else {
                    Toast.makeText(DataSiswaPage.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JadwalPiketResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Toast.makeText(DataSiswaPage.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}