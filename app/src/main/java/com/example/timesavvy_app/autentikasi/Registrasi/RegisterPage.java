package com.example.timesavvy_app.autentikasi.Registrasi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timesavvy_app.R;
import com.example.timesavvy_app.data.datasemuasiswa.DataSemuaSiswaPage;
import com.example.timesavvy_app.koneksi.ApiClient;
import com.example.timesavvy_app.koneksi.UserServices;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPage extends AppCompatActivity {

    private ImageView iconBack;
    private TextInputEditText txtNamaLengkap, txtUsername, txtEmail, txtPassword, txtNomorTelepon, txtNisn, txtAlamat, txtKota, txtProvinsi, txtKodePos;
    private MaterialButton btnRegister;
    private Spinner spinnerKelas, spinnerJurusan;
    private TextInputLayout layoutPassword;
    private TextView txtNamaToolbar;
    private String userToken;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        // Setting icon back
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

        // Initialize views
        txtNamaLengkap = findViewById(R.id.txtNamaLengkap);
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtNomorTelepon = findViewById(R.id.txtNomorTelepon);
        txtNisn = findViewById(R.id.txtNisn);
        txtAlamat = findViewById(R.id.txtAlamat);
        txtKota = findViewById(R.id.txtKota);
        txtProvinsi = findViewById(R.id.txtProvinsi);
        txtKodePos = findViewById(R.id.txtKodePos);
        spinnerKelas = findViewById(R.id.spinnerKelas);
        spinnerJurusan = findViewById(R.id.spinnerJurusan);
        btnRegister = findViewById(R.id.buttonMasuk);
        layoutPassword = findViewById(R.id.layout_password);
        txtNamaToolbar = findViewById(R.id.txtNamaToolbar);

        // setting inputan user
        Intent intent = getIntent();
        if (intent.hasExtra("siswaId")) {
            // This is edit mode, fill the form with the existing data
            txtNamaLengkap.setText(intent.getStringExtra("namaLengkap"));
            txtUsername.setText(intent.getStringExtra("username"));
            txtEmail.setText(intent.getStringExtra("email"));
            txtNisn.setText(intent.getStringExtra("nisn"));
            txtNomorTelepon.setText(intent.getStringExtra("noHp"));
            txtAlamat.setText(intent.getStringExtra("alamat"));
            txtKota.setText(intent.getStringExtra("kota"));
            txtProvinsi.setText(intent.getStringExtra("provinsi"));
            txtKodePos.setText(intent.getStringExtra("kodePos"));

            // Set spinner values
            // Assuming you have a method to set spinner value
            setSpinnerValue(spinnerKelas, intent.getStringExtra("kelas"));
            setSpinnerValue(spinnerJurusan, intent.getStringExtra("jurusan"));

            btnRegister.setText("Simpan Perubahan Data");

            String usernameUser = intent.getStringExtra("username").toUpperCase();

            layoutPassword.setVisibility(View.GONE);
            txtNamaToolbar.setText("Pembaruan Data Siswa " + usernameUser);

            Log.d("Hallo", String.valueOf(intent.getIntExtra("siswaId", 0)));
            final int finalUserID = intent.getIntExtra("siswaId", 0);
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editUser(finalUserID);
                }
            });
        } else {
            // This is add mode
            btnRegister.setText("Tambah Data");
            txtNamaToolbar.setText("Tambah Data Siswa ");
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postRegister();
                }
            });
        }
    }

    // get token user
    private String getUserToken() {
        return sharedPreferences.getString("user_token", "");
    }

    // Setting spinner
    private void setSpinnerValue(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    // Register post
    public void postRegister(){
        // Get data from input fields
        // Get data from input fields
        String name = txtUsername.getText().toString();
        String namaLengkap = txtNamaLengkap.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String nisn = txtNisn.getText().toString();
        String noHp = txtNomorTelepon.getText().toString();
        String alamat = txtAlamat.getText().toString();
        String kota = txtKota.getText().toString();
        String provinsi = txtProvinsi.getText().toString();
        String kodePos = txtKodePos.getText().toString();
        String kelas = spinnerKelas.getSelectedItem().toString();
        String jurusan = spinnerJurusan.getSelectedItem().toString();

        RegisterRequest registerRequest = new RegisterRequest(name, namaLengkap, email, password, nisn, noHp, alamat, kota, provinsi, kodePos, kelas, jurusan);

        UserServices userServices = ApiClient.getUserServices();
        Call<RegisterResponse> call = userServices.postDataUser(registerRequest);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = "Tambah data siswa berhasil, silakan refresh lagi untuk melihat datanya.";

                    // Membuat AlertDialog
                    new AlertDialog.Builder(RegisterPage.this)
                            .setTitle("Tambah Siswa Berhasil")
                            .setMessage(message)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Kembali ke halaman sebelumnya
                                    if (getApplicationContext() instanceof DataSemuaSiswaPage) {
                                        ((DataSemuaSiswaPage) getApplicationContext()).refreshData();
                                    }
                                    finish();
                                }
                            })
                            .setIcon(R.drawable.icon_success)
                            .show();
                } else {
                    try {
                        // Log the error body
                        Log.e("RegisterPage", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(RegisterPage.this, "Register failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d("RegisterPage", t.getMessage());
                Toast.makeText(RegisterPage.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Edit User
    private void editUser(int idUser) {
        // Get data from input fields
        String name = txtUsername.getText().toString();
        String namaLengkap = txtNamaLengkap.getText().toString();
        String email = txtEmail.getText().toString();
        String nisn = txtNisn.getText().toString();
        String noHp = txtNomorTelepon.getText().toString();
        String alamat = txtAlamat.getText().toString();
        String kota = txtKota.getText().toString();
        String provinsi = txtProvinsi.getText().toString();
        String kodePos = txtKodePos.getText().toString();
        String kelas = spinnerKelas.getSelectedItem().toString();
        String jurusan = spinnerJurusan.getSelectedItem().toString();
        String tokenUser = "Bearer " + userToken;

        UpdateUserRequest updateUserRequest = new UpdateUserRequest(email, name, namaLengkap, nisn, noHp, alamat, kota, provinsi, kodePos, kelas, jurusan);

        UserServices userServices = ApiClient.getUserServices();
        Call<RegisterResponse> call = userServices.postEditUser(tokenUser, updateUserRequest, idUser);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = "Pembaruan data siswa berhasil, silakan refresh data untuk melihat pembaruan data yang di simpan.";
                    new AlertDialog.Builder(RegisterPage.this)
                            .setTitle("Edit Siswa Berhasil")
                            .setMessage(message)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Refresh data in the previous activity if needed
                                    if (getApplicationContext() instanceof DataSemuaSiswaPage) {
                                        ((DataSemuaSiswaPage) getApplicationContext()).refreshData();
                                    }
                                    finish();
                                }
                            })
                            .setIcon(R.drawable.icon_success)
                            .show();
                } else {
                    try {
                        Log.e("RegisterPage", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(RegisterPage.this, "Edit failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d("RegisterPage", t.getMessage());
                Toast.makeText(RegisterPage.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}