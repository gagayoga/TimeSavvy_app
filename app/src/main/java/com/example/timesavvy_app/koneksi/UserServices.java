package com.example.timesavvy_app.koneksi;

import com.example.timesavvy_app.autentikasi.Login.LoginRequest;
import com.example.timesavvy_app.autentikasi.Login.LoginResponse;
import com.example.timesavvy_app.autentikasi.Registrasi.RegisterRequest;
import com.example.timesavvy_app.autentikasi.Registrasi.RegisterResponse;
import com.example.timesavvy_app.autentikasi.Registrasi.UpdateUserRequest;
import com.example.timesavvy_app.data.crudjadwal.EditJadwalRequest;
import com.example.timesavvy_app.data.crudjadwal.EditJadwalResponse;
import com.example.timesavvy_app.data.crudjadwal.TambahJadwalRequest;
import com.example.timesavvy_app.data.crudjadwal.TambahJadwalResponse;
import com.example.timesavvy_app.data.datasemuasiswa.DataSemuaSiswa;
import com.example.timesavvy_app.data.datasemuasiswa.DataSemuaSiswaResponse;
import com.example.timesavvy_app.data.jadwalpiket.JadwalPiketResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

// Endpoint API
public interface UserServices {

    @Headers({"Accept: application/json"})

    @POST("auth/login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("users/logout")
    Call<Void> userLogout(@Header("Authorization") String token);

    // Endpoint Jadwal
    @GET("users/jadwal")
    Call<JadwalPiketResponse> getJadwalPiket(@Header("Authorization") String token);

    @POST("users/jadwal")
    Call<TambahJadwalResponse> postJadwal(@Header("Authorization") String token, @Body TambahJadwalRequest tambahJadwalRequest);

    @PATCH("users/jadwal/{idUser}")
    Call<EditJadwalResponse> postEditJadwal(@Header("Authorization") String token, @Body EditJadwalRequest editJadwalRequest, @Path("idUser") int idUser);

    @DELETE("users/jadwal/{idUser}")
    Call<Void> deleteDataJadwal(@Header("Authorization") String token, @Path("idUser") int idUser);

    // Endpoint User
    @POST("auth/register")
    Call<RegisterResponse> postDataUser(@Body RegisterRequest registerRequest);

    @PATCH("users/user/{idUser}")
    Call<RegisterResponse> postEditUser(@Header("Authorization") String token, @Body UpdateUserRequest updateUserRequest, @Path("idUser") int idUser);

    @GET("users/user")
    Call<DataSemuaSiswaResponse> getDataUser(@Header("Authorization") String token);

    @DELETE("users/user/{idUser}")
    Call<Void> deleteDataUser(@Header("Authorization") String token, @Path("idUser") int idUser);
}
