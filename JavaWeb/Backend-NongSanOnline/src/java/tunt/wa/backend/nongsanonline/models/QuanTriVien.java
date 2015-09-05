/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.models;

import java.io.Serializable;

/**
 *
 * @author Thanh Tu
 */
public class QuanTriVien implements Serializable{
    private int id;
    private String tenDangNhap;
    private String matKhau;
    private String hoVaTen;
    private String quyen;
    private String trangThai;
    private String tenQuyen;
    private String tenTrangThai;
    
    public QuanTriVien() {
    }
    
    public QuanTriVien(int id, String tenDangNhap, String hoVaTen, String quyen, String trangThai) {
        this.id = id;
        this.tenDangNhap = tenDangNhap;
        this.hoVaTen = hoVaTen;
        this.quyen = quyen;
        this.trangThai = trangThai;
        if(trangThai.equals("kichhoat")){
            this.tenTrangThai = "Kích hoạt";
        }else{
            this.tenTrangThai = "Tạm dừng";
        }
        if(quyen.equals("master")){
            this.tenQuyen = "Master";
        }else{
            this.tenQuyen = "Admin";
        }
    }

    public int getId() {
        return id;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public String getQuyen() {
        return quyen;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public void setQuyen(String quyen) {
        if(quyen.equals("master")){
            this.tenQuyen = "Master";
        }else{
            this.tenQuyen = "Admin";
        }
        this.quyen = quyen;
    }

    public void setTrangThai(String trangThai) {
        if(trangThai.equals("kichhoat")){
            this.tenTrangThai = "Kích hoạt";
        }else{
            this.tenTrangThai = "Tạm dừng";
        }
        this.trangThai = trangThai;
    }

    public String getTenTrangThai() {
        return tenTrangThai;
    }

    public String getTenQuyen() {
        return tenQuyen;
    }
    
}
