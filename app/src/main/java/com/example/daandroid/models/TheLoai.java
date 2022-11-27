package com.example.daandroid.models;

import java.util.ArrayList;

public class TheLoai {
    int MaLoai;
    String TenTheLoai;
    ArrayList<Sach> DsSach;

    public TheLoai(int maLoai, String tenTheLoai, ArrayList<Sach> dsSach) {
        MaLoai = maLoai;
        TenTheLoai = tenTheLoai;
        DsSach = dsSach;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }

    public String getTenTheLoai() {
        return TenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        TenTheLoai = tenTheLoai;
    }

    public ArrayList<Sach> getDsSach() {
        return DsSach;
    }

    public void setDsSach(ArrayList<Sach> dsSach) {
        DsSach = dsSach;
    }
}
