package com.example.timesavvy_app.data.datasiswa;

import android.content.Context;
import android.content.DialogInterface;
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
import com.example.timesavvy_app.data.crudjadwal.TambahJadwalPage;
import com.example.timesavvy_app.data.datasemuasiswa.DataSemuaSiswaPage;
import com.example.timesavvy_app.data.jadwalpiket.JadwalPiketResponse;
import com.example.timesavvy_app.koneksi.ApiClient;
import com.example.timesavvy_app.koneksi.UserServices;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSiswaAdapter extends RecyclerView.Adapter<DataSiswaAdapter.SiswaViewHolder> {

    private List<JadwalPiketResponse.Siswa> siswaList;
    private Context context;
    private SharedPreferences sharedPreferences;
    private int jadwalId;

    public DataSiswaAdapter(Context context, SharedPreferences sharedPreferences, int jadwalId) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
        this.jadwalId = jadwalId;
    }

    public void setSiswaList(List<JadwalPiketResponse.Siswa> siswaList) {
        this.siswaList = siswaList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_data_siswa, parent, false);
        return new SiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiswaViewHolder holder, int position) {
        JadwalPiketResponse.Siswa siswa = siswaList.get(position);
        holder.txtNama.setText(siswa.getNama_lengkap());
        holder.txtEmail.setText("Email: " + siswa.getEmail());
        holder.txtNisn.setText("NISN: " + siswa.getNisn());
        holder.txtTelepon.setText("Telepon: " + siswa.getNo_hp());
        holder.txtAlamat.setText("Alamat: " + siswa.getAlamat() + ", " + siswa.getKota() + ", " + siswa.getProvinsi() + ", " + siswa.getKode_pos());
        holder.txtJurusan.setText("Kelas: " + siswa.getKelas() + " " + siswa.getJurusan());

        // Get user data from SharedPreferences
        String emailUser = sharedPreferences.getString("email_user", "");

        // Check if current student's data matches user's data
        if (emailUser.equals(siswa.getEmail())) {
            // Set background to gradient_background if data matches
            holder.cardJadwal.setBackgroundResource(R.drawable.gradient_background_pressed);
            holder.txtNama.setTextColor(R.color.primary);
            holder.txtEmail.setTextColor(R.color.primary);
            holder.txtNisn.setTextColor(R.color.primary);
            holder.txtTelepon.setTextColor(R.color.primary);
            holder.txtAlamat.setTextColor(R.color.primary);
            holder.txtJurusan.setTextColor(R.color.primary);
            holder.avatar.setImageResource(R.drawable.avatar1);
        } else {
            // Set default background color
            holder.cardJadwal.setBackgroundResource(R.drawable.gradient_background_pressed);
        }

        holder.cardJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(siswa, jadwalId);
            }
        });
    }


    @Override
    public int getItemCount() {
        return siswaList.size();
    }

    public static class SiswaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama, txtEmail, txtNisn, txtTelepon, txtAlamat, txtJurusan;
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

    private void showAlertDialog(JadwalPiketResponse.Siswa siswa, int jadwalId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.custom_alert, null);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        TextView titleTextView = dialogView.findViewById(R.id.titleTextView);
        TextView dataSiswaTextView = dialogView.findViewById(R.id.dataSiswa);
        MaterialButton btnDelete = dialogView.findViewById(R.id.btnDelete);
        MaterialButton btnEdit = dialogView.findViewById(R.id.btnEdit);

        String namaUser = siswa.getNama_lengkap().toUpperCase();
        titleTextView.setText("Data Siswa " + namaUser);
        String siswaData = ("Nama : " + siswa.getNama_lengkap() + "\n"
                + "Email : " + siswa.getEmail() + "\n"
                + "NISN : " + siswa.getNisn() + "\n"
                + "Telepon : " + siswa.getNo_hp() + "\n"
                + "Alamat : " + siswa.getAlamat() + ", " + siswa.getKota() + ", " + siswa.getProvinsi() + ", " + siswa.getKode_pos() + "\n"
                + "Kelas : " + siswa.getKelas() + " " + siswa.getJurusan());

        dataSiswaTextView.setText(siswaData);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(siswa.getId_pivot(), alertDialog, siswa.getNama_lengkap());
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to TambahJadwalPage and pass data siswa and jadwal ID
                Intent intent = new Intent(context, TambahJadwalPage.class);

                // Pass data siswa
                intent.putExtra("id_siswa", siswa.getId());
                intent.putExtra("nama_siswa", siswa.getNama_lengkap());

                // Pass jadwal ID
                intent.putExtra("jadwal_id", jadwalId);
                intent.putExtra("alamat", "dataSiswa");

                // Start the activity
                context.startActivity(intent);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void deleteUser(int idUser, AlertDialog alertDialog, String namaLengkap) {
        String token = sharedPreferences.getString("user_token", "");
        UserServices userServices = ApiClient.getUserServices();
        Call<Void> call = userServices.deleteDataJadwal("Bearer " + token, idUser);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    String message = "Hapus data siswa atas nama " + namaLengkap + " berhasil di hapus.";
                    new android.app.AlertDialog.Builder(context)
                            .setTitle("Hapus Data Siswa")
                            .setMessage(message)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Refresh data in the previous activity if needed
                                    if (context instanceof DataSiswaPage) {
                                        ((DataSiswaPage) context).refreshData();
                                    }
                                    alertDialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.icon_success)
                            .show();
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
