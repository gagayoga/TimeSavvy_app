
package com.example.timesavvy_app.autentikasi.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.timesavvy_app.R;
import com.example.timesavvy_app.data.main.MainActivity;
import com.example.timesavvy_app.koneksi.ApiClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {

    private TextInputLayout layoutEmail, layoutPassword;
    private TextInputEditText txtEmail, txtPassword;
    private MaterialButton buttonMasuk;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        // Initialize variabel
        layoutEmail = findViewById(R.id.layout_email);
        layoutPassword = findViewById(R.id.layout_password);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        // Setting hint inputan
        txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    layoutEmail.setHint("");
                } else {
                    if (txtEmail.getText().toString().isEmpty()) {
                        layoutEmail.setHint("TimeSavvy1Jaya");
                    }
                }
            }
        });

        txtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    layoutPassword.setHint("");
                } else {
                    if (txtPassword.getText().toString().isEmpty()) {
                        layoutPassword.setHint("TimeSavvy1Jaya");
                    }
                }
            }
        });
        // ----------------------->> End Setting hint inputan <<---------------------

        // ButtonMasuk
        buttonMasuk = findViewById(R.id.buttonMasuk);
        buttonMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInputan();
            }
        });

        // Get data di penyimpanan lokal
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
    }

    // Validasi inputan user
    public void validateInputan(){
        String inputanEmail, inputanPassword;
        inputanEmail = txtEmail.getText().toString().trim();
        inputanPassword = txtPassword.getText().toString().trim();

        if(TextUtils.isEmpty(inputanEmail)){
            Toast.makeText(LoginPage.this, "Email tidak boleh kosong, silakan inputkan email!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(inputanPassword)){
            Toast.makeText(LoginPage.this, "Password tidak boleh kosong, silakan inputkan password!", Toast.LENGTH_SHORT).show();
        }else{
            isLogin(inputanEmail, inputanPassword);
        }
    }

    // Login API
    public void isLogin(String email, String password){
        String emailUser = email;
        String passwordUser = password;

        LoginRequest loginrequest = new LoginRequest();
        loginrequest.setEmail(emailUser);
        loginrequest.setPassword(passwordUser);

        Call<LoginResponse> loginResponseCall = ApiClient.getUserServices().userLogin(loginrequest);

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    LoginResponse loginResponse = response.body();

                    String namaUser = loginResponse.getData().getName();
                    String emailUser = loginResponse.getData().getEmail();
                    int idUser = loginResponse.getData().getId();
                    String token = loginResponse.getData().getToken();

                    // Menyimpan token ke SharedPreferences
                    saveUserToken(token, namaUser, emailUser, idUser);

                    Toast.makeText(LoginPage.this, "Login Successfully, Selamat Datang Kembali " + namaUser, Toast.LENGTH_SHORT).show();
                    Intent loginSucces = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(loginSucces);
                    finish();
                }else{
                    try {
                        String errorBody = response.errorBody().string();
                        Gson gson = new Gson();
                        LoginErrorResponse errorResponse = gson.fromJson(errorBody, LoginErrorResponse.class);

                        String errorMessage = errorResponse.getMessage();
                        List<String> emailErrors = errorResponse.getErrors().getEmail();

                        StringBuilder errorMessages = new StringBuilder(errorMessage);
                        if (emailErrors != null && !emailErrors.isEmpty()) {
                            errorMessages.append("\nEmail: ").append(emailErrors.get(0));
                        }

                        Toast.makeText(LoginPage.this, errorMessages.toString(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(LoginPage.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginPage.this, "Login failed, please cek koneksi internet Anda.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //  Untuk menyimpan data User
    private void saveUserToken(String token, String namaUser, String emailUser, int idUser) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_token", token);
        editor.putString("nama_user", namaUser);
        editor.putString("email_user", emailUser);
        editor.putString("id_user", Integer.toString(idUser));
        editor.apply();
    }
}