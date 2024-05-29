package com.example.timesavvy_app.data.datasemuasiswa;

import java.util.List;

public class DataSemuaSiswaResponse {
    private int status;
    private String message;
    private List<DataSemuaSiswa> data;

    public DataSemuaSiswaResponse(int status, String message, List<DataSemuaSiswa> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

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

    public List<DataSemuaSiswa> getData() {
        return data;
    }

    public void setData(List<DataSemuaSiswa> data) {
        this.data = data;
    }
}
