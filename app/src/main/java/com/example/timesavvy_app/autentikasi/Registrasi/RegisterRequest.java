package com.example.timesavvy_app.autentikasi.Registrasi;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("name")
    private String name;

    @SerializedName("nama_lengkap")
    private String namaLengkap;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

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

    // Constructor
    public RegisterRequest(String name, String namaLengkap, String email, String password,
                           String nisn, String noHp, String alamat, String kota, String provinsi,
                           String kodePos, String kelas, String jurusan) {
        this.name = name;
        this.namaLengkap = namaLengkap;
        this.email = email;
        this.password = password;
        this.nisn = nisn;
        this.noHp = noHp;
        this.alamat = alamat;
        this.kota = kota;
        this.provinsi = provinsi;
        this.kodePos = kodePos;
        this.kelas = kelas;
        this.jurusan = jurusan;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
