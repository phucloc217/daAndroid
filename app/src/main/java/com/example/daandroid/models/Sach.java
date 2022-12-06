package com.example.daandroid.models;

import java.io.Serializable;
import java.sql.Blob;

public class Sach implements Serializable {
    int MaSach;
    String TenSach;
    String MoTa;
    int Gia;
    byte[] Hinh;
    TheLoai theLoai;

    public Sach(int maSach, String tenSach, String moTa, int gia, byte[] hinh, TheLoai theLoai) {
        MaSach = maSach;
        TenSach = tenSach;
        MoTa = moTa;
        Gia = gia;
        Hinh = hinh;
        this.theLoai = theLoai;
    }

    public Sach() {

    }

    public int getMaSach() {
        return MaSach;
    }

    public void setMaSach(int maSach) {
        MaSach = maSach;
    }

    public String getTenSach() {
        return TenSach;
    }

    public void setTenSach(String tenSach) {
        TenSach = tenSach;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public byte[] getHinh() {
        return Hinh;
    }

    public void setHinh(byte[] hinh) {
        Hinh = hinh;
    }

    public TheLoai getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(TheLoai theLoai) {
        this.theLoai = theLoai;
    }
}
