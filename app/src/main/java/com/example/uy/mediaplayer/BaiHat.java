package com.example.uy.mediaplayer;

/**
 * Created by UY on 6/6/2017.
 */

public class BaiHat {
    private String TenBaiHat;
    private int File;

    public BaiHat(String tenBaiHat, int file) {
        TenBaiHat = tenBaiHat;
        File = file;
    }

    public String getTenBaiHat() {
        return TenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        TenBaiHat = tenBaiHat;
    }

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }
}
