package com.example.timesavvy_app.data.datasemuasiswa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timesavvy_app.R;
import com.example.timesavvy_app.autentikasi.Registrasi.RegisterPage;
import com.example.timesavvy_app.koneksi.ApiClient;
import com.example.timesavvy_app.koneksi.UserServices;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSemuaSiswaAdapter extends RecyclerView.Adapter<DataSemuaSiswaAdapter.SiswaViewHolder> {

    private List<DataSemuaSiswa> siswaList;
    private Context context;
    private SharedPreferences sharedPreferences;

    public DataSemuaSiswaAdapter(List<DataSemuaSiswa> siswaList, Context context, SharedPreferences sharedPreferences) {
        this.siswaList = siswaList;
        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public SiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_data_siswa, parent, false);
        return new SiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiswaViewHolder holder, int position) {
        DataSemuaSiswa siswa = siswaList.get(position);
        holder.txtNama.setText(siswa.getNama_lengkap());
        holder.txtEmail.setText("Email: " + siswa.getEmail());
        holder.txtNisn.setText("NISN: " + siswa.getNisn());
        holder.txtTelepon.setText("Telepon: " + siswa.getNo_hp());
        holder.txtAlamat.setText("Alamat: " + siswa.getAlamat() + ", " + siswa.getKota() + ", " + siswa.getProvinsi() + ", " + siswa.getKode_pos());
        holder.txtJurusan.setText("Kelas: " + siswa.getKelas() + " " + siswa.getJurusan());

        // Get user data from SharedPreferences
        String namaUser = sharedPreferences.getString("nama_user", "");
        String emailUser = sharedPreferences.getString("email_user", "");
        String nisnUser = sharedPreferences.getString("id_user", "");

        // Check if current student's data matches user's data
        if (emailUser.equals(siswa.getEmail()) ) {
            // Set background to gradient_background if data matches
            holder.cardJadwal.setBackgroundResource(R.drawable.gradient_white_card);
            holder.txtNama.setTextColor(Color.WHITE);
            holder.txtEmail.setTextColor(Color.WHITE);
            holder.txtNisn.setTextColor(Color.WHITE);
            holder.txtTelepon.setTextColor(Color.WHITE);
            holder.txtAlamat.setTextColor(Color.WHITE);
            holder.txtJurusan.setTextColor(Color.WHITE);
            holder.avatar.setImageResource(R.drawable.avatar1);
        } else {
            // Set default background color
            holder.cardJadwal.setBackgroundResource(R.drawable.gradient_background_pressed);
        }

        // Setting card di klik
        // Set OnClickListener for the card
        holder.cardJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(siswa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return siswaList.size();
    }

    public static class SiswaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama, txtEmail, txtNisn, txtTelepon, txtAlamat, txtKelas, txtJurusan;
        RelativeLayout cardJadwal;
        ImageView avatar;

        public SiswaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtNisn = itemView.findViewById(R.id.txtNisn);
            txtTelepon = itemView.findViewById(R.id.txtTelepon);
            txtAlamat = itemView.findViewById(R.id.txtAlamat);
            txtJurusan = itemView.findViewById(R.id.txtJurusam);
            cardJadwal = itemView.findViewById(R.id.cardJadwal);
            avatar = itemView.findViewById(R.id.avatarSiswa);
        }
    }

    private void showAlertDialog(DataSemuaSiswa siswa) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.custom_alert, null);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        TextView titleTextView = dialogView.findViewById(R.id.titleTextView);
        TextView dataSiswaTextView = dialogView.findViewById(R.id.dataSiswa);
        MaterialButton btnDelete = dialogView.findViewById(R.id.btnDelete);
        MaterialButton btnEdit = dialogView.findViewById(R.id.btnEdit);

        String namaUser = siswa.getName().toUpperCase();
        titleTextView.setText("Data Siswa " + namaUser);
        String siswaData = ("Nama : " + siswa.getNama_lengkap() + "\n"
                + "Email : " + siswa.getEmail() + "\n"
                + "IDUSER : " + String.valueOf(siswa.getId()) + "\n"
                + "NISN : " + siswa.getNisn() + "\n"
                + "Telepon : " + siswa.getNo_hp() + "\n"
                + "Alamat : " + siswa.getAlamat() + ", " + siswa.getKota() + ", " + siswa.getProvinsi() + ", " + siswa.getKode_pos() + "\n"
                + "Kelas : " + siswa.getKelas() + " " + siswa.getJurusan()).toUpperCase();

        dataSiswaTextView.setText(siswaData);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the user
                deleteUser(siswa.getId(), alertDialog, siswa.getNama_lengkap());
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Periksa apakah objek siswa tidak null dan id tidak null
                Log.d("Debug", "Siswa object: " + siswa);

                if (siswa != null && siswa.getId() != 0) {
                    // Redirect to Edit User page
                    Intent intent = new Intent(context, RegisterPage.class);
                    intent.putExtra("siswaId", siswa.getId());
                    intent.putExtra("namaLengkap", siswa.getNama_lengkap());
                    intent.putExtra("username", siswa.getName());
                    intent.putExtra("email", siswa.getEmail());
                    intent.putExtra("nisn", siswa.getNisn());
                    intent.putExtra("noHp", siswa.getNo_hp());
                    intent.putExtra("alamat", siswa.getAlamat());
                    intent.putExtra("kota", siswa.getKota());
                    intent.putExtra("provinsi", siswa.getProvinsi());
                    intent.putExtra("kodePos", siswa.getKode_pos());
                    intent.putExtra("kelas", siswa.getKelas());
                    intent.putExtra("jurusan", siswa.getJurusan());
                    intent.putExtra("asalHalaman", "EditData");
                    context.startActivity(intent);
                    alertDialog.dismiss();
                } else {
                    Log.e("Error", "Objek siswa adalah null atau ID siswa adalah 0");
                }
            }
        });

        alertDialog.show();
    }

    private void deleteUser(int idUser, AlertDialog alertDialog, String namaLengkap) {
        String token = sharedPreferences.getString("user_token", "");
        UserServices userServices = ApiClient.getUserServices();
        Call<Void> call = userServices.deleteDataUser("Bearer " + token, idUser);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Delete User " + namaLengkap + " Berhasil.", Toast.LENGTH_SHORT).show();
                    // Refresh the data in the activity
                    if (context instanceof DataSemuaSiswaPage) {
                        ((DataSemuaSiswaPage) context).refreshData();
                    }
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Failed to delete user: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("DELETE_USER", "Failed to delete user: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("DELETE_USER", "Error: " + t.getMessage());
            }
        });
    }
}
