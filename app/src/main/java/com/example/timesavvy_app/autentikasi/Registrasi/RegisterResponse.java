package com.example.timesavvy_app.autentikasi.Registrasi;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private UserData data;

    public static class UserData {
        @SerializedName("id")
        private int id;

        @SerializedName("email")
        private String email;

        @SerializedName("name")
        private String name;

        @SerializedName("nama_lengkap")
        private String namaLengkap;

        @SerializedName("nisn")
        private String nisn;

        @SerializedName("no_hp")
        private String noHp;

        @SerializedName("alamat")
        private String alamat;

        @SerializedName("kota")
        private String kota;

        @SerializedName("provinsi")
        private String provinsi;

        @SerializedName("kode_pos")
        private String kodePos;

        @SerializedName("kelas")
        private String kelas;

        @SerializedName("jurusan")
        private String jurusan;

        // Getters and Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNamaLengkap() {
            return namaLengkap;
        }

        public void setNamaLengkap(String namaLengkap) {
            this.namaLengkap = namaLengkap;
        }

        public String getNisn() {
            return nisn;
        }

        public void setNisn(String nisn) {
            this.nisn = nisn;
        }

        public String getNoHp() {
            return noHp;
        }

        public void setNoHp(String noHp) {
            this.noHp = noHp;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getKota() {
            return kota;
        }

        public void setKota(String kota) {
            this.kota = kota;
        }

        public String getProvinsi() {
            return provinsi;
        }

        public void setProvinsi(String provinsi) {
            this.provinsi = provinsi;
        }

        public String getKodePos() {
            return kodePos;
        }

        public void setKodePos(String kodePos) {
            this.kodePos = kodePos;
        }

        public String getKelas() {
            return kelas;
        }

        public void setKelas(String kelas) {
            this.kelas = kelas;
        }

        public String getJurusan() {
            return jurusan;
        }

        public void setJurusan(String jurusan) {
            this.jurusan = jurusan;
        }
    }

    // Getters and Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}
