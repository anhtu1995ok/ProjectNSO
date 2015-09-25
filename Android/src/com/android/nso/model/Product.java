package com.android.nso.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.android.nso.R;
import com.android.nso.adapter.ProductAdapter;
import com.android.nso.utils.Time;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;

public class Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String ten, gia, moTa, ngayTao, ngayCapNhat, diaChi, tinhThanhId,
			tenTinhThanh, tenVungMien;
	private ArrayList<String> arrAnh;

	public Product(int id, String ten, String gia, String moTa, String ngayTao,
			String ngayCapNhat, String diaChi, String tinhThanhId,
			String tenTinhThanh, String tenVungMien, ArrayList<String> arrAnh) {
		this.id = id;
		this.ten = ten;
		this.gia = gia;
		this.moTa = moTa;
		this.ngayTao = ngayTao;
		this.ngayCapNhat = ngayCapNhat;
		this.diaChi = diaChi;
		this.tinhThanhId = tinhThanhId;
		this.tenTinhThanh = tenTinhThanh;
		this.tenVungMien = tenVungMien;
		this.arrAnh = arrAnh;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getGia() {
		return gia;
	}

	public void setGia(String gia) {
		this.gia = gia;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public String getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(String ngayTao) {
		this.ngayTao = ngayTao;
	}

	public String getNgayCapNhat() {
		return ngayCapNhat;
	}

	public void setNgayCapNhat(String ngayCapNhat) {
		this.ngayCapNhat = ngayCapNhat;
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

	public ArrayList<String> getArrAnh() {
		return arrAnh;
	}

	public void setArrAnh(ArrayList<String> arrAnh) {
		this.arrAnh = arrAnh;
	}

}
