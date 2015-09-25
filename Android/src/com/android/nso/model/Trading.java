package com.android.nso.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Trading implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String tieuDe, gia, donVi, tenDonVi, diaChi, tinhThanhId, tenTinhThanh,
			tenVungMien, taoBoiId, taoBoiTen, doiTuong, tenDoiTuong, loai,
			tenLoai, noiDung, ngayTao, ngayHetHan;
	private ArrayList<String> arrAnh;
	public Trading(int id, String tieuDe, String gia, String donVi,
			String tenDonVi, String diaChi, String tinhThanhId,
			String tenTinhThanh, String tenVungMien, String taoBoiId,
			String taoBoiTen, String doiTuong, String tenDoiTuong, String loai,
			String tenLoai, String noiDung, String ngayTao, String ngayHetHan,
			ArrayList<String> arrAnh) {
		super();
		this.id = id;
		this.tieuDe = tieuDe;
		this.gia = gia;
		this.donVi = donVi;
		this.tenDonVi = tenDonVi;
		this.diaChi = diaChi;
		this.tinhThanhId = tinhThanhId;
		this.tenTinhThanh = tenTinhThanh;
		this.tenVungMien = tenVungMien;
		this.taoBoiId = taoBoiId;
		this.taoBoiTen = taoBoiTen;
		this.doiTuong = doiTuong;
		this.tenDoiTuong = tenDoiTuong;
		this.loai = loai;
		this.tenLoai = tenLoai;
		this.noiDung = noiDung;
		this.ngayTao = ngayTao;
		this.ngayHetHan = ngayHetHan;
		this.arrAnh = arrAnh;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTieuDe() {
		return tieuDe;
	}
	public void setTieuDe(String tieuDe) {
		this.tieuDe = tieuDe;
	}
	public String getGia() {
		return gia;
	}
	public void setGia(String gia) {
		this.gia = gia;
	}
	public String getDonVi() {
		return donVi;
	}
	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}
	public String getTenDonVi() {
		return tenDonVi;
	}
	public void setTenDonVi(String tenDonVi) {
		this.tenDonVi = tenDonVi;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getTinhThanhId() {
		return tinhThanhId;
	}
	public void setTinhThanhId(String tinhThanhId) {
		this.tinhThanhId = tinhThanhId;
	}
	public String getTenTinhThanh() {
		return tenTinhThanh;
	}
	public void setTenTinhThanh(String tenTinhThanh) {
		this.tenTinhThanh = tenTinhThanh;
	}
	public String getTenVungMien() {
		return tenVungMien;
	}
	public void setTenVungMien(String tenVungMien) {
		this.tenVungMien = tenVungMien;
	}
	public String getTaoBoiId() {
		return taoBoiId;
	}
	public void setTaoBoiId(String taoBoiId) {
		this.taoBoiId = taoBoiId;
	}
	public String getTaoBoiTen() {
		return taoBoiTen;
	}
	public void setTaoBoiTen(String taoBoiTen) {
		this.taoBoiTen = taoBoiTen;
	}
	public String getDoiTuong() {
		return doiTuong;
	}
	public void setDoiTuong(String doiTuong) {
		this.doiTuong = doiTuong;
	}
	public String getTenDoiTuong() {
		return tenDoiTuong;
	}
	public void setTenDoiTuong(String tenDoiTuong) {
		this.tenDoiTuong = tenDoiTuong;
	}
	public String getLoai() {
		return loai;
	}
	public void setLoai(String loai) {
		this.loai = loai;
	}
	public String getTenLoai() {
		return tenLoai;
	}
	public void setTenLoai(String tenLoai) {
		this.tenLoai = tenLoai;
	}
	public String getNoiDung() {
		return noiDung;
	}
	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}
	public String getNgayTao() {
		return ngayTao;
	}
	public void setNgayTao(String ngayTao) {
		this.ngayTao = ngayTao;
	}
	public String getNgayHetHan() {
		return ngayHetHan;
	}
	public void setNgayHetHan(String ngayHetHan) {
		this.ngayHetHan = ngayHetHan;
	}
	public ArrayList<String> getArrAnh() {
		return arrAnh;
	}
	public void setArrAnh(ArrayList<String> arrAnh) {
		this.arrAnh = arrAnh;
	}

}
