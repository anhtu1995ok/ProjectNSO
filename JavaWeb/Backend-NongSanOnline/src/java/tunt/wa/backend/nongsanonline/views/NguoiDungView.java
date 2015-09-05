/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import tunt.wa.backend.nongsanonline.beans.CookieBean;
import tunt.wa.backend.nongsanonline.models.NguoiDung;
import tunt.wa.backend.nongsanonline.models.QuanTriVien;
import tunt.wa.backend.nongsanonline.utils.AsyncHttpClient;
import tunt.wa.backend.nongsanonline.utils.MessageUtil;

/**
 *
 * @author Thanh Tu
 */
@ManagedBean
@ViewScoped
public class NguoiDungView implements Serializable{
    private ArrayList<NguoiDung> arrTempNguoiDung;
    private ArrayList<NguoiDung> arrNguoiDung;
    private NguoiDung selectedND;
    private ArrayList<NguoiDung> arrSelectedND;
    private String trangThai = "tamdung";
    /**
     * Creates a new instance of NguoiDungView
     */
    public NguoiDungView() {
        arrNguoiDung = new ArrayList<>();
        if(CookieBean.getValueCookieStatic("ten_dang_nhap")!=null && 
                CookieBean.getValueCookieStatic("id")!=null && 
                CookieBean.getValueCookieStatic("quyen")!=null){
                arrNguoiDung = getDSNguoiDung();
        }
        arrTempNguoiDung = arrNguoiDung;
    }

    public ArrayList<NguoiDung> getArrTempNguoiDung() {
        return arrTempNguoiDung;
    }

    public ArrayList<NguoiDung> getArrNguoiDung() {
        return arrNguoiDung;
    }

    public NguoiDung getSelectedND() {
        return selectedND;
    }

    public ArrayList<NguoiDung> getArrSelectedND() {
        return arrSelectedND;
    }

    public void setArrTempNguoiDung(ArrayList<NguoiDung> arrTempNguoiDung) {
        this.arrTempNguoiDung = arrTempNguoiDung;
    }

    public void setArrNguoiDung(ArrayList<NguoiDung> arrNguoiDung) {
        this.arrNguoiDung = arrNguoiDung;
    }

    public void setSelectedND(NguoiDung selectedND) {
        update(selectedND);
        this.selectedND = selectedND;
    }

    public void setArrSelectedND(ArrayList<NguoiDung> arrSelectedND) {
        this.arrSelectedND = arrSelectedND;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    public ArrayList<NguoiDung> getDSNguoiDung(){
        ArrayList<NguoiDung> data = new ArrayList<>();
        String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/danhsachnguoidung";
        HashMap<String, String> params = new HashMap<>();
        params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
        params.put("cid", CookieBean.getValueCookieStatic("id"));
        AsyncHttpClient client = new AsyncHttpClient();
        String json = client.post(url, params);
        try {
            JSONObject jSONObject = new JSONObject(json);
            int success = jSONObject.getInt("success");
            if(success == 1){
                JSONArray rows = jSONObject.getJSONArray("rows");
                for(int i = 0; i<rows.length(); i++){
                    JSONObject o = (JSONObject) rows.get(i);
                    NguoiDung nd =new NguoiDung(
                            o.getInt("id"), 
                            o.getString("ten_dang_nhap"), 
                            o.getString("ho_va_ten"), 
                            o.getString("dia_chi"), 
                            o.getString("sdt"), 
                            o.getString("email"), 
                            o.getString("anh_dai_dien"), 
                            o.getString("ma_kich_hoat"),
                            o.getString("ngay_tao"), 
                            o.getString("trang_thai"));
                    data.add(nd);
                }
            }else{
                MessageUtil.addErrorMessage(jSONObject.getString("error_msg"));
            }
        } catch (JSONException ex) {
            MessageUtil.addErrorMessage("Thao tác không thành công.");
            Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public void thayDoiTrangThai(){
        if(selectedND!=null){
            String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/thaydoitrangthainguoidung";
            HashMap<String, String> params = new HashMap<>();
            params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
            params.put("cid", CookieBean.getValueCookieStatic("id"));
            params.put("id", Integer.toString(selectedND.getId()));
            AsyncHttpClient client = new AsyncHttpClient();
            String json = client.post(url, params);
            try {
                JSONObject jSONObject = new JSONObject(json);
                int success = jSONObject.getInt("success");
                if(success == 1){
                    if(selectedND.getTrangThai().equals("kichhoat"))
                        selectedND.setTrangThai("tamdung");
                    else
                        selectedND.setTrangThai("kichhoat");
                    MessageUtil.addSuccessMessage("Thay đổi thành công.");
                }else{
                    MessageUtil.addErrorMessage(jSONObject.getString("error_msg"));
                }
            } catch (JSONException ex) {
                MessageUtil.addErrorMessage("Thao tác không thành công.");
                Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            MessageUtil.addErrorMessage("Lỗi hệ thống.");
        }
    }
    
    public void xoa(){
        if(selectedND!=null){
            String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/xoanguoidung";
            HashMap<String, String> params = new HashMap<>();
            params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
            params.put("cid", CookieBean.getValueCookieStatic("id"));
            params.put("id", ""+selectedND.getId());
            AsyncHttpClient client = new AsyncHttpClient();
            String json = client.post(url, params);
            try {
                JSONObject jSONObject = new JSONObject(json);
                int success = jSONObject.getInt("success");
                if(success == 1){
                    this.arrNguoiDung = this.arrTempNguoiDung;
                    this.arrNguoiDung.remove(selectedND);
                    this.arrTempNguoiDung = this.arrNguoiDung;
                    MessageUtil.addSuccessMessage("Đã xóa.");
                }else{
                    MessageUtil.addErrorMessage(jSONObject.getString("error_msg"));
                }
            } catch (JSONException ex) {
                MessageUtil.addErrorMessage("Thao tác không thành công.");
                Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            MessageUtil.addErrorMessage("Lỗi hệ thống.");
        }
    }
    
    public void loc(String type){
        this.arrNguoiDung = this.arrTempNguoiDung;
        ArrayList<NguoiDung> arrND = new ArrayList<>();
        if(type.equals("tamdung")||type.equals("kichhoat")){
            for(int i = 0; i<this.arrNguoiDung.size(); i++){
                NguoiDung nd = this.arrNguoiDung.get(i);
                if(nd.getTrangThai().equals(type))
                    arrND.add(nd);
            }
            this.arrNguoiDung = arrND;
        }
    }
    
    private void update(NguoiDung nguoiDung){
        this.trangThai = nguoiDung.getTrangThai();
    }
}
