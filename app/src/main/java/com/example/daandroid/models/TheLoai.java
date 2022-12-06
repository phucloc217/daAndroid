package com.example.daandroid.models;

import java.io.Serializable;
import java.util.ArrayList;

public class TheLoai implements Serializable {
    int MaLoai;
    String TenTheLoai;
   // ArrayList<Sach> DsSach;

    public TheLoai(int maLoai, String tenTheLoai) {
        MaLoai = maLoai;
        TenTheLoai = tenTheLoai;
       // DsSach = dsSach;
    }

    public TheLoai() {
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

//    public ArrayList<Sach> getDsSach() {
//        return DsSach;
//    }

//    public void setDsSach(ArrayList<Sach> dsSach) {
//        DsSach = dsSach;
//    }
    public String toString() {
        return
                "Mã thể loại: " + MaLoai + '\n' +
                        "Tên thể loại: " + TenTheLoai + '\n';
    }
}
