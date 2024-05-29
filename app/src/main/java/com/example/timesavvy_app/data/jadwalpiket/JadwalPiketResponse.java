package com.example.timesavvy_app.data.jadwalpiket;

import java.util.List;

public class JadwalPiketResponse {
    private int status;
    private String message;
    private List<Jadwal> data;

    // Getters and Setters
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<Jadwal> getData() { return data; }
    public void setData(List<Jadwal> data) { this.data = data; }

    public static class Jadwal {
        private int id;
        private String hari;
        private List<Siswa> siswa;

        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getHari() { return hari; }
        public void setHari(String hari) { this.hari = hari; }

        public List<Siswa> getSiswa() { return siswa; }
        public void setSiswa(List<Siswa> siswa) { this.siswa = siswa; }
    }

    public static class Siswa {
        private int id;
        private String email;
        private String name;
        private String nama_lengkap;
        private String nisn;
        private String no_hp;
        private String alamat;
        private String kota;
        private String provinsi;
        private String kode_pos;
        private String kelas;
        private String jurusan;

        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getNama_lengkap() { return nama_lengkap; }
        public void setNama_lengkap(String nama_lengkap) { this.nama_lengkap = nama_lengkap; }

        public String getNisn() { return nisn; }
        public void setNisn(String nisn) { this.nisn = nisn; }

        public String getNo_hp() { return no_hp; }
        public void setNo_hp(String no_hp) { this.no_hp = no_hp; }

        public String getAlamat() { return alamat; }
        public void setAlamat(String alamat) { this.alamat = alamat; }

        public String getKota() { return kota; }
        public void setKota(String kota) { this.kota = kota; }

        public String getProvinsi() { return provinsi; }
        public void setProvinsi(String provinsi) { this.provinsi = provinsi; }

        public String getKode_pos() { return kode_pos; }
        public void setKode_pos(String kode_pos) { this.kode_pos = kode_pos; }

        public String getKelas() { return kelas; }
        public void setKelas(String kelas) { this.kelas = kelas; }

        public String getJurusan() { return jurusan; }
        public void setJurusan(String jurusan) { this.jurusan = jurusan; }
    }

}
