package com.example.timesavvy_app.data.crudjadwal;

import com.google.gson.annotations.SerializedName;

public class TambahJadwalRequest {
    @SerializedName("id_user")
    private String idUser;

    @SerializedName("id_jadwal")
    private String idJadwal;

    @SerializedName("waktu")
    private String waktu;

    public TambahJadwalRequest(String idUser, String idJadwal, String waktu) {
        this.idUser = idUser;
        this.idJadwal = idJadwal;
        this.waktu = waktu;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(String idJadwal) {
        this.idJadwal = idJadwal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
