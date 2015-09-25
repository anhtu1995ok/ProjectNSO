package com.android.nso.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Trick implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String tieuDe, noiDung;
	private ArrayList<String> arrAnh;
	
	public Trick(int id, String tieuDe, String noiDung, ArrayList<String> arrAnh) {
		this.id = id;
		this.tieuDe = tieuDe;
		this.noiDung = noiDung;
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
	public String getNoiDung() {
		return noiDung;
	}
	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}
	public ArrayList<String> getArrAnh() {
		return arrAnh;
	}
	public void setArrAnh(ArrayList<String> arrAnh) {
		this.arrAnh = arrAnh;
	}

	
}
