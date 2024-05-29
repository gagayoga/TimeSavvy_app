package com.example.timesavvy_app.data.crudjadwal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timesavvy_app.R;
import com.example.timesavvy_app.data.datasemuasiswa.DataSemuaSiswa;
import com.example.timesavvy_app.data.datasemuasiswa.DataSemuaSiswaResponse;
import com.example.timesavvy_app.data.jadwalpiket.JadwalPiketResponse;
import com.example.timesavvy_app.data.main.MainActivity;
import com.example.timesavvy_app.koneksi.ApiClient;
import com.example.timesavvy_app.koneksi.UserServices;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahJadwalPage extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String userToken, idUser, alamatUrl;
    private Spinner spinnerHari, spinnerSiswa;
    private List<DataSemuaSiswa> userSiswaList;
    private TextView textViewSiswaDetail, txtToolbar;
    private MaterialButton buttonTambahJadwal;
    private List<String> hariIdList = new ArrayList<>();
    private ImageView iconBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal_page);

        // get data user
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userToken = getUserToken();

        // Get data user
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String usernameUserr = sharedPreferences.getString("nama_user", "");
        idUser = sharedPreferences.getString("id_user", "");
        userToken = getUserToken();

        // Initialize
        spinnerHari = findViewById(R.id.spinnerHari);
        spinnerSiswa =  findViewById(R.id.spinnerSiswa);
        textViewSiswaDetail = findViewById(R.id.textViewSiswaDetail);
        buttonTambahJadwal = findViewById(R.id.buttonTambahJadwal);
        txtToolbar = findViewById(R.id.txtNamaToolbar);

        // icon Back
        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getDataHari();
        getDataSiswa();


        //get Data Intent
        // Mendapatkan intent
        Intent intent = getIntent();

        // Mendapatkan id_siswa dan jadwal_id dari intent
        int idSiswa = intent.getIntExtra("id_siswa", 0);
        int jadwalId = intent.getIntExtra("jadwal_id", 0);
        alamatUrl = intent.getStringExtra("alamat");

        // Hide or show views based on alamatUrl value
        if (alamatUrl != null && alamatUrl.equals("dataSiswa")) {
            // Hide spinnerSiswa
            spinnerSiswa.setVisibility(View.GONE);
            textViewSiswaDetail.setVisibility(View.GONE);
            buttonTambahJadwal.setText("Simpan Perubahan Data");

            // Set nama lengkap from intent
            String namaSiswa = intent.getStringExtra("nama_siswa");
            String namaLengkap = String.valueOf(idSiswa) + " | " + namaSiswa;

            // Show TextInputLayout for entering nama lengkap
            TextInputLayout textInputLayoutNamaLengkap = findViewById(R.id.layout_namaLengkap);
            textInputLayoutNamaLengkap.setVisibility(View.VISIBLE);
            txtToolbar.setText("Pembaruan Data Jadwal " + namaSiswa);

            TextInputEditText txtNamaLengkap = findViewById(R.id.txtNamaLengkap);
            txtNamaLengkap.setText(namaLengkap);

            int selectedHariPosition = hariIdList.indexOf(String.valueOf(jadwalId));
            spinnerHari.setSelection(selectedHariPosition);
        }else{
            spinnerSiswa.setVisibility(View.VISIBLE);
            textViewSiswaDetail.setVisibility(View.VISIBLE);
            TextInputLayout textInputLayoutNamaLengkap = findViewById(R.id.layout_namaLengkap);
            textInputLayoutNamaLengkap.setVisibility(View.GONE);
            buttonTambahJadwal.setText("Tambah Data Jadwal");
            txtToolbar.setText("Tambah Data Jadwal");
        }

        // Tambahkan listener untuk menampilkan data lengkap siswa saat salah satu siswa dipilih
        spinnerSiswa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSiswaList != null && position >= 0 && position < userSiswaList.size()) {
                    // Ambil data siswa yang dipilih
                    DataSemuaSiswa selectedSiswa = userSiswaList.get(position);

                    // Tampilkan data lengkap siswa di bawah Spinner spinnerSiswa
                    String siswaDetail = "Nama: " + selectedSiswa.getNama_lengkap() + "\n" +
                            "Email: " + selectedSiswa.getEmail() + "\n" +
                            "NISN: " + selectedSiswa.getNisn() + "\n" +
                            "Telepon: " + selectedSiswa.getNo_hp() + "\n" +
                            "Alamat: " + selectedSiswa.getAlamat() + ", " +
                            selectedSiswa.getKota() + ", " +
                            selectedSiswa.getProvinsi() + ", " +
                            selectedSiswa.getKode_pos() + "\n" +
                            "Kelas: " + selectedSiswa.getKelas() + " " + selectedSiswa.getJurusan();

                    textViewSiswaDetail.setText(siswaDetail);
                } else {
                    // Tampilkan pesan bahwa tidak ada siswa yang dipilih
                    textViewSiswaDetail.setText("Tidak ada siswa yang dipilih");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buttonTambahJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(buttonTambahJadwal.getText().equals("Tambah Data Jadwal")){
                   tambahJadwal();
               }else if(buttonTambahJadwal.getText().equals("Simpan Perubahan Data")){
                   editJadwal(Integer.parseInt(idUser));
               }else{

               }
            }
        });
    }

    // get token user
    private String getUserToken() {
        return sharedPreferences.getString("user_token", "");
    }

    public void getDataHari() {
        String token = "Bearer " + userToken;
        UserServices userServices = ApiClient.getUserServices();
        Call<JadwalPiketResponse> callJadwal = userServices.getJadwalPiket(token);
        callJadwal.enqueue(new Callback<JadwalPiketResponse>() {
            @Override
            public void onResponse(Call<JadwalPiketResponse> call, Response<JadwalPiketResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JadwalPiketResponse jadwalPiketResponse = response.body();

                    // Ambil data hari dari respons jadwal piket dan setel ke Spinner spinnerHari
                    List<JadwalPiketResponse.Jadwal> jadwalList = jadwalPiketResponse.getData();
                    List<String> hariList = new ArrayList<>();

                    for (JadwalPiketResponse.Jadwal jadwal : jadwalList) {
                        hariList.add(jadwal.getHari() + " | " + jadwal.getId());
                        hariIdList.add(String.valueOf(jadwal.getId())); // Menambahkan ID hari ke dalam list
                    }

                    ArrayAdapter<String> hariAdapter = new ArrayAdapter<>(TambahJadwalPage.this, android.R.layout.simple_spinner_item, hariList);
                    hariAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerHari.setAdapter(hariAdapter);

                    // Tambahkan listener untuk mengambil ID hari saat item dipilih
                    spinnerHari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedHariId = hariIdList.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                } else {
                    Toast.makeText(TambahJadwalPage.this, "Failed to get data. Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JadwalPiketResponse> call, Throwable t) {
                Toast.makeText(TambahJadwalPage.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getDataSiswa(){
        // Panggil API untuk mendapatkan data user
        String token = "Bearer " + userToken;
        UserServices userServices = ApiClient.getUserServices();
        Call<DataSemuaSiswaResponse> callUser = userServices.getDataUser(token);
        callUser.enqueue(new Callback<DataSemuaSiswaResponse>() {
            @Override
            public void onResponse(Call<DataSemuaSiswaResponse> call, Response<DataSemuaSiswaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DataSemuaSiswaResponse dataSemuaSiswaResponse = response.body();

                    // Ambil data user dari respons data user dan setel ke Spinner spinnerSiswa
                    userSiswaList = dataSemuaSiswaResponse.getData(); // Variabel userSiswaList harus dideklarasikan secara global
                    List<String> userSiswaNames = new ArrayList<>();
                    for (DataSemuaSiswa siswa : userSiswaList) {
                        userSiswaNames.add(siswa.getNama_lengkap() + " | " + siswa.getEmail());
                    }
                    ArrayAdapter<String> userSiswaAdapter = new ArrayAdapter<>(TambahJadwalPage.this, android.R.layout.simple_spinner_item, userSiswaNames);
                    userSiswaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSiswa.setAdapter(userSiswaAdapter);
                } else {
                    Toast.makeText(TambahJadwalPage.this, "Failed to get data. Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataSemuaSiswaResponse> call, Throwable t) {
                Toast.makeText(TambahJadwalPage.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tambahJadwal() {
        // Mendapatkan token otorisasi dari SharedPreferences atau sumber lainnya
        String token = "Bearer " + userToken;
        UserServices userServices = ApiClient.getUserServices();

        // Mendapatkan ID siswa dan hari dari spinner
        int selectedSiswaPosition = spinnerSiswa.getSelectedItemPosition();
        int selectedHariPosition = spinnerHari.getSelectedItemPosition();

        // Periksa apakah posisi spinner valid
        if (selectedSiswaPosition != AdapterView.INVALID_POSITION && selectedHariPosition != AdapterView.INVALID_POSITION) {
            // Ambil ID siswa dari posisi yang dipilih di spinner
            int idSiswa = userSiswaList.get(selectedSiswaPosition).getId();
            String siswaId = String.valueOf(idSiswa);

            // Ambil ID hari dari posisi yang dipilih di spinner
            String hariId = hariIdList.get(selectedHariPosition);

            // Ambil hari dari posisi yang dipilih di spinner
            String hari = spinnerHari.getSelectedItem().toString();
            String waktu = "-";

            // Membuat objek TambahJadwalRequest dengan data yang diperoleh
            TambahJadwalRequest request = new TambahJadwalRequest(siswaId, hariId, waktu);

            // Memanggil endpoint untuk melakukan post jadwal
            userServices.postJadwal(token, request).enqueue(new Callback<TambahJadwalResponse>() {
                @Override
                public void onResponse(Call<TambahJadwalResponse> call, Response<TambahJadwalResponse> response) {
                    if (response.isSuccessful()) {
                        // Post berhasil, tanggapan dari server dapat diakses di response.body()
                        TambahJadwalResponse jadwalResponse = response.body();
                        if (jadwalResponse != null) {
                            finish();
                            Toast.makeText(getApplicationContext(), "Tambah jadwal berhasil", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Gagal melakukan post, tanggapan dari server dapat diakses di response.errorBody()
                        Toast.makeText(TambahJadwalPage.this, "Gagal menambah jadwal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TambahJadwalResponse> call, Throwable t) {
                    // Terjadi kegagalan koneksi atau kesalahan lainnya
                    Toast.makeText(TambahJadwalPage.this, "Koneksi gagal, coba lagi", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Jika posisi spinner tidak valid, tampilkan pesan kesalahan
            Toast.makeText(this, "Pilih siswa dan hari terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    private void editJadwal(int idUser) {
        // Mendapatkan token otorisasi dari SharedPreferences atau sumber lainnya
        String token = "Bearer " + userToken;
        UserServices userServices = ApiClient.getUserServices();

        // Mendapatkan ID hari dari spinner
        int selectedHariPosition = spinnerHari.getSelectedItemPosition();

        // Periksa apakah posisi spinner valid
        if (selectedHariPosition != AdapterView.INVALID_POSITION) {
            // Ambil ID hari dari posisi yang dipilih di spinner
            String hariId = hariIdList.get(selectedHariPosition);

            // Membuat objek EditJadwalRequest dengan data yang diperoleh
            EditJadwalRequest request = new EditJadwalRequest(String.valueOf(idUser), hariId);

            // Memanggil endpoint untuk melakukan edit jadwal
            userServices.postEditJadwal(token, request, idUser).enqueue(new Callback<EditJadwalResponse>() {
                @Override
                public void onResponse(Call<EditJadwalResponse> call, Response<EditJadwalResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        EditJadwalResponse editJadwalResponse = response.body();
                        Toast.makeText(getApplicationContext(), editJadwalResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish(); // Tutup aktivitas setelah berhasil mengedit jadwal
                    } else {
                        Toast.makeText(TambahJadwalPage.this, "Gagal mengedit jadwal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<EditJadwalResponse> call, Throwable t) {
                    Toast.makeText(TambahJadwalPage.this, "Koneksi gagal, coba lagi", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Jika posisi spinner tidak valid, tampilkan pesan kesalahan
            Toast.makeText(this, "Pilih hari terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

}