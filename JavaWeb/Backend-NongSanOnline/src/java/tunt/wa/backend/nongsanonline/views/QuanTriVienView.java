/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.views;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import tunt.wa.backend.nongsanonline.beans.CookieBean;
import tunt.wa.backend.nongsanonline.models.QuanTriVien;
import tunt.wa.backend.nongsanonline.utils.AsyncHttpClient;
import tunt.wa.backend.nongsanonline.utils.MessageUtil;

/**
 *
 * @author Thanh Tu
 */
@ManagedBean
@ViewScoped
public class QuanTriVienView implements Serializable{
    private String tenDangNhap;
    private String matKhau;
    private boolean nhoMatKhau = false;
    private QuanTriVien quanTriVien;
    private boolean master = false;
    //
    private ArrayList<QuanTriVien> arrTempQuanTriVien;
    private ArrayList<QuanTriVien> arrQuanTriVien;
    private QuanTriVien selectedQTV;
    private ArrayList<QuanTriVien> arrSelectedQTV;
    private QuanTriVien loginQTV;
    //them
    private String reMatKhau;
    private String hoVaTen;
    private String quyen;
    private String trangThai;
    //
    private String matKhauCu;
    /**
     * Creates a new instance of QuanTriVienBean
     */
    public QuanTriVienView() {
        quanTriVien = new QuanTriVien();
        arrQuanTriVien = new ArrayList<>();
        if(CookieBean.getValueCookieStatic("ten_dang_nhap")!=null && 
                CookieBean.getValueCookieStatic("id")!=null && 
                CookieBean.getValueCookieStatic("quyen")!=null){
            loginQTV = getThongTinQuanTriVien(CookieBean.getValueCookieStatic("id"));
            if(CookieBean.getValueCookieStatic("quyen").equals("master")){
                master = true;
                arrQuanTriVien = getDSQuanTriVien();
            }
        }
        arrTempQuanTriVien = arrQuanTriVien;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public boolean getNhoMatKhau() {
        return nhoMatKhau;
    }

    public void setNhoMatKhau(boolean nhoMatKhau) {
        this.nhoMatKhau = nhoMatKhau;
    }

    public ArrayList<QuanTriVien> getArrQuanTriVien() {
        return arrQuanTriVien;
    }

    public QuanTriVien getSelectedQTV() {
        return selectedQTV;
    }

    public QuanTriVien getQuanTriVien() {
        return quanTriVien;
    }

    public void setQuanTriVien(QuanTriVien quanTriVien) {
        this.quanTriVien = quanTriVien;
    }

    public void setArrQuanTriVien(ArrayList<QuanTriVien> arrQuanTriVien) {
        this.arrQuanTriVien = arrQuanTriVien;
    }

    public void setSelectedQTV(QuanTriVien selectedQTV) {
        update(selectedQTV);
        this.selectedQTV = selectedQTV;
    }

    public ArrayList<QuanTriVien> getArrSelectedQTV() {
        return arrSelectedQTV;
    }

    public void setArrSelectedQTV(ArrayList<QuanTriVien> arrSelectedQTV) {
        this.arrSelectedQTV = arrSelectedQTV;
    }

    public String getReMatKhau() {
        return reMatKhau;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public String getQuyen() {
        return quyen;
    }

    public void setReMatKhau(String reMatKhau) {
        this.reMatKhau = reMatKhau;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public QuanTriVien getLoginQTV() {
        return loginQTV;
    }

    public void setLoginQTV(QuanTriVien loginQTV) {
        this.loginQTV = loginQTV;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public String getMatKhauCu() {
        return matKhauCu;
    }

    public void setMatKhauCu(String matKhauCu) {
        this.matKhauCu = matKhauCu;
    }
    
    public ArrayList<QuanTriVien> getDSQuanTriVien(){
        ArrayList<QuanTriVien> data = new ArrayList<>();
        String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/danhsachquantrivien";
        HashMap<String, String> params = new HashMap<>();
        params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
        params.put("cid", CookieBean.getValueCookieStatic("id"));
        AsyncHttpClient client = new AsyncHttpClient();
        String json = client.post(url, params);
//        try {
//            Client client = Client.create();
//
//            WebResource webResource = client
//               .resource(url);
//
//            MultivaluedMap<String, String> params = new MultivaluedMapImpl();
//            params.add("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
//            params.add("cid", CookieBean.getValueCookieStatic("id"));
//
//            ClientResponse response = webResource.queryParams(params).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
//               .post(ClientResponse.class);
//
//            if (response.getStatus() != 200) {
//                    throw new RuntimeException("Failed : HTTP error code : "
//                         + response.getStatus());
//            }
//            String json = response.getEntity(String.class);
//            System.out.println("json: "+json);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            JSONObject jSONObject = new JSONObject(json);
            int success = jSONObject.getInt("success");
            if(success == 1){
                JSONArray rows = jSONObject.getJSONArray("rows");
                for(int i = 0; i<rows.length(); i++){
                    JSONObject o = (JSONObject) rows.get(i);
                    QuanTriVien qtv = new QuanTriVien(o.getInt("id"), o.getString("ten_dang_nhap"), o.getString("ho_va_ten"), o.getString("quyen"), o.getString("trang_thai"));
                    data.add(qtv);
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
    
    public String dangNhap(){
        if(!tenDangNhap.trim().isEmpty() && !matKhau.isEmpty() ){
            String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/dangnhap";
            HashMap<String, String> params = new HashMap<>();
            params.put("tendangnhap", tenDangNhap.trim().toLowerCase());
            params.put("matkhau", matKhau);
            AsyncHttpClient client = new AsyncHttpClient();
            String json = client.post(url, params);
            try {
                JSONObject jSONObject = new JSONObject(json);
                int success = jSONObject.getInt("success");
                if(success == 1){
                    int time = -1;
                    if(nhoMatKhau){
                        time = 60*60;
                    }else{
                        time = -1;
                    }
                    CookieBean.addCookie("ten_dang_nhap", jSONObject.getString("ten_dang_nhap"), time);
                    CookieBean.addCookie("id", jSONObject.getString("id"), time);
                    CookieBean.addCookie("quyen", jSONObject.getString("quyen"), time);
                    return "login/trang_chu.xhtml?faces-redirect=true";
                }else{
                    MessageUtil.addErrorMessage((String) jSONObject.get("error_msg"));
                }
            } catch (JSONException ex) {
                MessageUtil.addErrorMessage("Thao tác không thành công.");
                Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            MessageUtil.addWarnMessage("Bạn chưa nhập tên đăng nhập hoặc mật khẩu.");
        }
        return null;
    }
    
    public void dangXuat(){
        CookieBean.removeCookie();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(QuanTriVienView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void xoa(){
        if(selectedQTV!=null){
            String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/xoaquantrivien";
            HashMap<String, String> params = new HashMap<>();
            params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
            params.put("cid", CookieBean.getValueCookieStatic("id"));
            params.put("id", ""+selectedQTV.getId());
            AsyncHttpClient client = new AsyncHttpClient();
            String json = client.post(url, params);
            try {
                JSONObject jSONObject = new JSONObject(json);
                int success = jSONObject.getInt("success");
                if(success == 1){
                    this.arrQuanTriVien = this.arrTempQuanTriVien;
                    this.arrQuanTriVien.remove(selectedQTV);
                    this.arrTempQuanTriVien = this.arrQuanTriVien;
                    MessageUtil.addSuccessMessage("Đã xóa.");
                }else{
                    MessageUtil.addErrorMessage(jSONObject.getString("error_msg"));
                }
            } catch (JSONException ex) {
                MessageUtil.addErrorMessage("Thao tác không thành công.");
                Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void them(){
        if(hoVaTen!=null&&tenDangNhap!=null&&matKhau!=null&&quyen!=null&&reMatKhau!=null){
            if(!hoVaTen.isEmpty()&&!tenDangNhap.isEmpty()&&!matKhau.isEmpty()&&!quyen.isEmpty()&&!reMatKhau.isEmpty()){
                if(reMatKhau.equals(matKhau)){
                    String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/themquantrivien";
                    HashMap<String, String> params = new HashMap<>();
                    params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
                    params.put("cid", CookieBean.getValueCookieStatic("id"));
                    params.put("tendangnhap", tenDangNhap);
                    params.put("matkhau", matKhau);
                    params.put("hovaten", hoVaTen);
                    params.put("quyen", quyen);
                    params.put("anhdaidien", "");
                    AsyncHttpClient client = new AsyncHttpClient();
                    String json = client.post(url, params);
                    try {
                        JSONObject jSONObject = new JSONObject(json);
                        int success = jSONObject.getInt("success");
                        if(success == 1){
                            this.arrQuanTriVien = this.arrTempQuanTriVien;
                            this.arrQuanTriVien.add(new QuanTriVien(jSONObject.getInt("id"), tenDangNhap, hoVaTen, quyen, "kichhoat"));
                            this.arrTempQuanTriVien = this.arrQuanTriVien;
                            reset();
                            MessageUtil.addSuccessMessage("Thêm thành công.");
                        }else{
                            MessageUtil.addErrorMessage(jSONObject.getString("error_msg"));
                        }
                    } catch (JSONException ex) {
                        MessageUtil.addErrorMessage("Thao tác không thành công.");
                        Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    MessageUtil.addWarnMessage("Hai mật khẩu không giống nhau.");
                }
            }else{
                MessageUtil.addWarnMessage("Có vẻ như bạn chưa điền đầy đủ thông tin.");
            }
        }else{
            MessageUtil.addWarnMessage("Có vẻ như bạn chưa điền đầy đủ thông tin.");
        }
    }
    
    public void sua(){
        if(hoVaTen!=null&&trangThai!=null&&quyen!=null){
            if(!hoVaTen.isEmpty()&&!trangThai.isEmpty()&&!quyen.isEmpty()){
                if(matKhau!=null&&!matKhau.isEmpty()){
                    if(reMatKhau!=null&&matKhau.equals(reMatKhau)){
                        String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/suaquantrivien";
                        HashMap<String, String> params = new HashMap<>();
                        params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
                        params.put("cid", CookieBean.getValueCookieStatic("id"));
                        params.put("id", ""+selectedQTV.getId());
                        params.put("matkhau", matKhau);
                        params.put("hovaten", hoVaTen);
                        params.put("quyen", quyen);
                        params.put("anhdaidien", "");
                        params.put("trangthai", trangThai);
                        
                        AsyncHttpClient client = new AsyncHttpClient();
                        String json = client.post(url, params);
                        try {
                            JSONObject jSONObject = new JSONObject(json);
                            int success = jSONObject.getInt("success");
                            if(success == 1){
                                selectedQTV.setHoVaTen(hoVaTen);
                                selectedQTV.setTrangThai(trangThai);
                                selectedQTV.setQuyen(quyen);
                                reset();
                                RequestContext rc = RequestContext.getCurrentInstance();
                                        rc.execute("PF('dialogUpdateItem').hide()");
                                MessageUtil.addSuccessMessage("Thay đổi thành công.");
                            }else{
                                MessageUtil.addErrorMessage(jSONObject.getString("error_msg"));
                            }
                        } catch (JSONException ex) {
                            MessageUtil.addErrorMessage("Thao tác không thành công.");
                            Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        MessageUtil.addWarnMessage("Hai mật khẩu không giống nhau.");
                    }  
                }else{
                    String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/suaquantrivien";
                    HashMap<String, String> params = new HashMap<>();
                    params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
                    params.put("cid", CookieBean.getValueCookieStatic("id"));
                    params.put("id", ""+selectedQTV.getId());
                    params.put("matkhau", "");
                    params.put("hovaten", hoVaTen);
                    params.put("quyen", quyen);
                    params.put("anhdaidien", "");
                    params.put("trangthai", trangThai);
                    AsyncHttpClient client = new AsyncHttpClient();
                    String json = client.post(url, params);
                    try {
                        JSONObject jSONObject = new JSONObject(json);
                        int success = jSONObject.getInt("success");
                        if(success == 1){
                            selectedQTV.setHoVaTen(hoVaTen);
                            selectedQTV.setTrangThai(trangThai);
                            selectedQTV.setQuyen(quyen);
                            reset();
                            RequestContext rc = RequestContext.getCurrentInstance();
                                    rc.execute("PF('dialogUpdateItem').hide()");
                            MessageUtil.addSuccessMessage("Thay đổi thành công.");
                        }else{
                            MessageUtil.addSuccessMessage(jSONObject.getString("error_msg"));
                        }
                    } catch (JSONException ex) {
                        MessageUtil.addSuccessMessage("Thao tác không thành công.");
                        Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }else{
                MessageUtil.addWarnMessage("Có vẻ như bạn chưa điền đầy đủ thông tin.");
            }
        }else{
            MessageUtil.addWarnMessage("Có vẻ như bạn chưa điền đầy đủ thông tin.");
        }
    }
    
    public void doiMatKhau(){
        if(matKhauCu != null && matKhau !=null && reMatKhau!= null){
            if(!matKhauCu.isEmpty() && !matKhau.isEmpty() && !reMatKhau.isEmpty()){
                if(matKhau.equals(reMatKhau)){
                    String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/doimatkhau";
                    HashMap<String, String> params = new HashMap<>();
                    params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
                    params.put("cid", CookieBean.getValueCookieStatic("id"));
                    params.put("matkhaucu", matKhauCu);
                    params.put("matkhaumoi", matKhau);
                    AsyncHttpClient client = new AsyncHttpClient();
                    String json = client.post(url, params);
                    try {
                        JSONObject jSONObject = new JSONObject(json);
                        int success = jSONObject.getInt("success");
                        if(success == 1){
                            reset();
                            RequestContext rc = RequestContext.getCurrentInstance();
                            rc.execute("PF('dialogChangePassword').hide()");
                            MessageUtil.addSuccessMessage("Thay đổi thành công.");
                        }else{
                            MessageUtil.addErrorMessage(jSONObject.getString("error_msg"));
                        }
                    } catch (JSONException ex) {
                        MessageUtil.addErrorMessage("Thao tác không thành công.");
                        Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else
                    MessageUtil.addWarnMessage("Hai mật khẩu mới không giống nhau.");
            }else
                MessageUtil.addWarnMessage("Có vẻ như bạn chưa nhập đầy đủ thông tin.");
        }else
            MessageUtil.addErrorMessage("Sảy ra lỗi.");
    }
    
    public void thayDoiThongTin(){
        if(hoVaTen != null){
            if(!hoVaTen.trim().isEmpty()){
                hoVaTen = hoVaTen.trim();
                String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/thaydoithongtin";
                HashMap<String, String> params = new HashMap<>();
                params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
                params.put("cid", CookieBean.getValueCookieStatic("id"));
                params.put("hovaten", hoVaTen);
                AsyncHttpClient client = new AsyncHttpClient();
                String json = client.post(url, params);
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    int success = jSONObject.getInt("success");
                    if(success == 1){
                        loginQTV.setHoVaTen(hoVaTen);
                        reset();
                        RequestContext rc = RequestContext.getCurrentInstance();
                        rc.execute("PF('dialogUpdate').hide()");
                        MessageUtil.addSuccessMessage("Thay đổi thành công.");
                    }else{
                        MessageUtil.addErrorMessage(jSONObject.getString("error_msg"));
                    }
                } catch (JSONException ex) {
                    MessageUtil.addErrorMessage("Thao tác không thành công.");
                    Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else
                MessageUtil.addWarnMessage("Có vẻ như bạn chưa nhập đầy đủ thông tin.");
        }else
            MessageUtil.addErrorMessage("Sảy ra lỗi.");
    }
    
    private QuanTriVien getThongTinQuanTriVien(String id){
        String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/thongtinquantrivien";
        HashMap<String, String> params = new HashMap<>();
        params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
        params.put("cid", CookieBean.getValueCookieStatic("id"));
        params.put("id", id);
        AsyncHttpClient client = new AsyncHttpClient();
        String json = client.post(url, params);
        try {
            JSONObject jSONObject = new JSONObject(json);
            int success = jSONObject.getInt("success");
            if(success == 1){
                return new QuanTriVien(
                        jSONObject.getInt("id"), 
                        jSONObject.getString("ten_dang_nhap"), 
                        jSONObject.getString("ho_va_ten"),
                        jSONObject.getString("quyen"), 
                        jSONObject.getString("trang_thai")
                    );
            }else{
                MessageUtil.addSuccessMessage(jSONObject.getString("error_msg"));
            }
        } catch (JSONException ex) {
            MessageUtil.addSuccessMessage("Thao tác không thành công.");
            Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void loc(String type){
        this.arrQuanTriVien  = this.arrTempQuanTriVien;
        ArrayList<QuanTriVien> arrQTV = new ArrayList<>();
        if(type.equals("master")||type.equals("admin")){
            for(int i = 0; i<this.arrQuanTriVien.size(); i++){
                QuanTriVien qtv = this.arrQuanTriVien.get(i);
                if(qtv.getQuyen().equals(type))
                    arrQTV.add(qtv);
            }
            this.arrQuanTriVien = arrQTV;
        }else if(type.equals("tamdung")||type.equals("kichhoat")){
            for(int i = 0; i<this.arrQuanTriVien.size(); i++){
                QuanTriVien qtv = this.arrQuanTriVien.get(i);
                if(qtv.getTrangThai().equals(type))
                    arrQTV.add(qtv);
            }
            this.arrQuanTriVien = arrQTV;
        }
    }
    
    public void reset(){
        this.selectedQTV = null;
        this.hoVaTen = null;
        this.tenDangNhap = null;
        this.reMatKhau = null;
        this.matKhau = null;
        this.quyen = null;
        this.trangThai = null;
        this.matKhauCu = null;
    }
    
    private void update(QuanTriVien qtv){
        this.hoVaTen = qtv.getHoVaTen();
        this.tenDangNhap = qtv.getTenDangNhap();
        this.quyen = qtv.getQuyen();
        this.trangThai = qtv.getTrangThai();
    }
}
