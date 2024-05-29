package com.example.timesavvy_app.data.crudjadwal;

import com.google.gson.annotations.SerializedName;

public class EditJadwalRequest {

    @SerializedName("id_user")
    private String idUser;

    @SerializedName("id_jadwal")
    private String idJadwal;

    public EditJadwalRequest(String idUser, String idJadwal) {
        this.idUser = idUser;
        this.idJadwal = idJadwal;
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
}
