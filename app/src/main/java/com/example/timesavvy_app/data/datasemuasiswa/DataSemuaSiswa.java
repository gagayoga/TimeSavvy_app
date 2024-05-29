package com.example.timesavvy_app.data.datasemuasiswa;

import com.google.gson.annotations.SerializedName;

public class DataSemuaSiswa {
    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("nama_lengkap")
    private String nama_lengkap;
    @SerializedName("nisn")
    private String nisn;
    @SerializedName("no_hp")
    private String no_hp;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("kota")
    private String kota;
    @SerializedName("provinsi")
    private String provinsi;
    @SerializedName("kode_pos")
    private String kode_pos;
    @SerializedName("kelas")
    private String kelas;
    @SerializedName("jurusan")
    private String jurusan;

    // Constructor
    public DataSemuaSiswa(int id, String email, String name, String nama_lengkap, String nisn, String no_hp, String alamat, String kota, String provinsi, String kode_pos, String kelas, String jurusan) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nama_lengkap = nama_lengkap;
        this.nisn = nisn;
        this.no_hp = no_hp;
        this.alamat = alamat;
        this.kota = kota;
        this.provinsi = provinsi;
        this.kode_pos = kode_pos;
        this.kelas = kelas;
        this.jurusan = jurusan;
    }

    // Getter dan setter sesuai kebutuhan
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

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
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

    public String getKode_pos() {
        return kode_pos;
    }

    public void setKode_pos(String kode_pos) {
        this.kode_pos = kode_pos;
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

