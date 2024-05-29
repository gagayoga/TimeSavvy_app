package com.example.timesavvy_app.data.crudjadwal;

import java.util.List;

public class EditJadwalResponse {
    private int status;
    private String message;
    private Data data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        private int id;
        private String hari;
        private List<Siswa> siswa;

        public int getId() {
            return id;
        }

        public String getHari() {
            return hari;
        }

        public List<Siswa> getSiswa() {
            return siswa;
        }
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

        public int getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getNama_lengkap() {
            return nama_lengkap;
        }

        public String getNisn() {
            return nisn;
        }

        public String getNo_hp() {
            return no_hp;
        }

        public String getAlamat() {
            return alamat;
        }

        public String getKota() {
            return kota;
        }

        public String getProvinsi() {
            return provinsi;
        }

        public String getKode_pos() {
            return kode_pos;
        }

        public String getKelas() {
            return kelas;
        }

        public String getJurusan() {
            return jurusan;
        }
    }
}
