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
public class NguoiDung implements Serializable{
    private int id;
    private String tenDangNhap;
    private String matKhau;
    private String hoVaTen;
    private String diaChi;
    private String sdt;
    private String email;
    private String anhDaiDien;
    private String maKichHoat;
    private String ngayTao;
    private String trangThai;
    private String tenTrangThai;
    private String doiTrangThai;
    private String iconTrangThai;

    public NguoiDung() {
    }

    public NguoiDung(int id, String tenDangNhap, String hoVaTen, String diaChi, String sdt, String email, String anhDaiDien, String maKichHoat, String ngayTao, String trangThai) {
        this.id = id;
        this.tenDangNhap = tenDangNhap;
        this.hoVaTen = hoVaTen;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.anhDaiDien = anhDaiDien;
        this.maKichHoat = maKichHoat;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        if(trangThai.equals("kichhoat")){
            this.tenTrangThai = "Kích hoạt";
        }else{
            this.tenTrangThai = "Tạm dừng";
        }
        if(trangThai.equals("kichhoat")){
            this.iconTrangThai = "ui-icon-bullet";
        }else{
            this.iconTrangThai = "ui-icon-radio-off";
        }
        if(trangThai.equals("kichhoat")){
            this.doiTrangThai = "Tạm dừng";
        }else{
            this.doiTrangThai = "Kích hoạt";
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

    public String getDiaChi() {
        return diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public String getEmail() {
        return email;
    }

    public String getMaKichHoat() {
        return maKichHoat;
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

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMaKichHoat(String maKichHoat) {
        this.maKichHoat = maKichHoat;
    }

    public void setTrangThai(String trangThai) {
        if(trangThai.equals("kichhoat")){
            this.tenTrangThai = "Kích hoạt";
        }else{
            this.tenTrangThai = "Tạm dừng";
        }
        if(trangThai.equals("kichhoat")){
            this.doiTrangThai = "Tạm dừng";
        }else{
            this.doiTrangThai = "Kích hoạt";
        }
        if(trangThai.equals("kichhoat")){
            this.iconTrangThai = "ui-icon-bullet";
        }else{
            this.iconTrangThai = "ui-icon-radio-off";
        }
        this.trangThai = trangThai;
    }

    public String getAnhDaiDien() {
        if(anhDaiDien.equals("") || anhDaiDien.endsWith("default"))
            return "/resource/images/no_ava_boy.jpg";
        return anhDaiDien;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTenTrangThai() {
        return tenTrangThai;
    }

    public void setTenTrangThai(String tenTrangThai) {
        this.tenTrangThai = tenTrangThai;
    }

    public String getDoiTrangThai() {
        return doiTrangThai;
    }

    public void setDoiTrangThai(String doiTrangThai) {
        this.doiTrangThai = doiTrangThai;
    }

    public String getIconTrangThai() {
        return iconTrangThai;
    }

    public void setIconTrangThai(String iconTrangThai) {
        this.iconTrangThai = iconTrangThai;
    }
    
}
