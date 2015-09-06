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
import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import tunt.wa.backend.nongsanonline.beans.CookieBean;
import tunt.wa.backend.nongsanonline.models.QuanTriVien;
import tunt.wa.backend.nongsanonline.models.TinhThanh;
import tunt.wa.backend.nongsanonline.utils.AsyncHttpClient;
import tunt.wa.backend.nongsanonline.utils.MessageUtil;

/**
 *
 * @author Thanh Tu
 */
@ManagedBean
@ViewScoped
public class TinhThanhView implements Serializable{
    private ArrayList<TinhThanh> arrTinhThanh;
    private TinhThanh selectedTT;
    private TreeNode treeNodeTT;
    private String ten;
    
    /**
     * Creates a new instance of TinhThanhView
     */
    public TinhThanhView() {
        arrTinhThanh = getDSTinhThanh();
        treeNodeTT = getTinhThanhTree();
    }

    public TinhThanh getSelectedTT() {
        return selectedTT;
    }

    public void setSelectedTT(TinhThanh selectedTT) {
        this.selectedTT = selectedTT;
    }

    public TreeNode getTreeNodeTT() {
        return treeNodeTT;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public boolean isParent(int parentId) {
        if(parentId==0){
            return true;
        }
        return false;
    }
    
    public String getMessageDelete(int parentId, String ten) {
        if(parentId==0)
            return "Bạn có thật sự muốn xóa \""+ten+"\" <br/>và các tỉnh thành thuộc \""+ten+"\"?";
        else
            return "Bạn có thật sự muốn xóa \""+ten+"\"?";
    }
    
    public int getSoTinhThanh(int id) {
        int soTT = 0;
        for(int i=0; i<arrTinhThanh.size(); i++){
            TinhThanh tt = arrTinhThanh.get(i);
            if(tt.getParentId() == id){
                soTT++;
            }
        }
        return soTT;
    }
    
    public String getStTinhThanh(int parent_id, int id) {
        if(parent_id==0){
            int soTT = 0;
            for(int i=0; i<arrTinhThanh.size(); i++){
                TinhThanh tt = arrTinhThanh.get(i);
                if(tt.getParentId() == id){
                    soTT++;
                }
            } 
            return " (số tỉnh thành: "+soTT+")";
        }
        return "";
    }
    
    public TreeNode add(ArrayList<TinhThanh> data, int parentId, TreeNode r){
        TreeNode root;
        if(r==null)
            root = new DefaultTreeNode(null, r);
        else
            root = r;
        for (int i = 0; i < data.size(); i++) {
            TinhThanh tinhThanh = data.get(i);
            if(tinhThanh.getParentId() == parentId){
                TreeNode dm1 = new DefaultTreeNode(tinhThanh, root);
                add(data, data.get(i).getId(), dm1);
            }
        }
        return root;
    }
    
    public TreeNode getTinhThanhTree(){
        if(arrTinhThanh==null)
            arrTinhThanh = getDSTinhThanh();
        TreeNode root = add(arrTinhThanh, 0, null);
        return root;
    }
    
    private ArrayList<TinhThanh> getDSTinhThanh(){
        ArrayList<TinhThanh> data = new ArrayList<>();
        String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/danhsachtinhthanh";
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
                    TinhThanh tt = new TinhThanh(o.getInt("id"), o.getString("ten"), o.getInt("parent_id"));
                    data.add(tt);
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
    
    public void themTinhThanh(){
        if(selectedTT!=null){
            if(ten!=null){
                if(!ten.isEmpty()){
                    String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/themtinhthanh";
                    HashMap<String, String> params = new HashMap<>();
                    params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
                    params.put("cid", CookieBean.getValueCookieStatic("id"));
                    params.put("ten", ten);
                    params.put("parentid", Integer.toString(selectedTT.getId()));
                    AsyncHttpClient client = new AsyncHttpClient();
                    String json = client.post(url, params);
                    try {
                        JSONObject jSONObject = new JSONObject(json);
                        int success = jSONObject.getInt("success");
                        if(success == 1){
                            arrTinhThanh.add(new TinhThanh(jSONObject.getInt("id"), ten, selectedTT.getId()));
                            treeNodeTT = getTinhThanhTree();
                            reset();
                            RequestContext rc = RequestContext.getCurrentInstance();
                            rc.execute("PF('dialogAdd').hide()");
                            MessageUtil.addSuccessMessage("Đã thêm.");
                        }else{
                            MessageUtil.addErrorMessage(jSONObject.getString("error_msg"));
                        }
                    } catch (JSONException ex) {
                        MessageUtil.addErrorMessage("Thao tác không thành công.");
                        Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else
                    MessageUtil.addErrorMessage("Có vẻ như bạn chưa nhập đầy đủ thông tin.");
            }else
                MessageUtil.addErrorMessage("Thao tác không thành công.");
        }else{
            MessageUtil.addErrorMessage("Thao tác không thành công.");
        }
    }
    
    public void themVungMien(){
        if(ten!=null){
            if(!ten.isEmpty()){
                String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/themtinhthanh";
                HashMap<String, String> params = new HashMap<>();
                params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
                params.put("cid", CookieBean.getValueCookieStatic("id"));
                params.put("ten", ten);
                params.put("parentid", "0");
                AsyncHttpClient client = new AsyncHttpClient();
                String json = client.post(url, params);
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    int success = jSONObject.getInt("success");
                    if(success == 1){
                        arrTinhThanh.add(new TinhThanh(jSONObject.getInt("id"), ten, 0));
                        treeNodeTT = getTinhThanhTree();
                        reset();
                        MessageUtil.addSuccessMessage("Đã thêm.");
                        RequestContext rc = RequestContext.getCurrentInstance();
                        rc.execute("PF('dialogAddVM').hide()");
                    }else{
                        MessageUtil.addErrorMessage(jSONObject.getString("error_msg"));
                    }
                } catch (JSONException ex) {
                    MessageUtil.addErrorMessage("Thao tác không thành công.");
                    Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else
                MessageUtil.addErrorMessage("Có vẻ như bạn chưa nhập đầy đủ thông tin.");
        }else
            MessageUtil.addErrorMessage("Thao tác không thành công.");
    }
    
    public void xoa(){
        if(selectedTT!=null){
            String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/xoatinhthanh";
            HashMap<String, String> params = new HashMap<>();
            params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
            params.put("cid", CookieBean.getValueCookieStatic("id"));
            params.put("id", Integer.toString(selectedTT.getId()));
            AsyncHttpClient client = new AsyncHttpClient();
            String json = client.post(url, params);
            try {
                JSONObject jSONObject = new JSONObject(json);
                int success = jSONObject.getInt("success");
                if(success == 1){
                    arrTinhThanh.remove(selectedTT);
                    treeNodeTT = getTinhThanhTree();
                    reset();
                    MessageUtil.addSuccessMessage("Đã xóa.");
                }else{
                    MessageUtil.addErrorMessage(jSONObject.getString("error_msg"));
                }
            } catch (JSONException ex) {
                MessageUtil.addErrorMessage("Thao tác không thành công.");
                Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            MessageUtil.addErrorMessage("Thao tác không thành công.");
        }
    }
    
    public void doiTen(){
        if(selectedTT!=null){
            String url = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/doitentinhthanh";
            HashMap<String, String> params = new HashMap<>();
            params.put("ctendangnhap", CookieBean.getValueCookieStatic("ten_dang_nhap"));
            params.put("cid", CookieBean.getValueCookieStatic("id"));
            params.put("id", Integer.toString(selectedTT.getId()));
            params.put("ten", ten.trim());
            AsyncHttpClient client = new AsyncHttpClient();
            String json = client.post(url, params);
            try {
                JSONObject jSONObject = new JSONObject(json);
                int success = jSONObject.getInt("success");
                if(success == 1){
                    selectedTT.setTen(ten.trim());
                    treeNodeTT = getTinhThanhTree();
                    reset();
                    RequestContext rc = RequestContext.getCurrentInstance();
                    rc.execute("PF('dialogUpdate').hide()");
                    MessageUtil.addSuccessMessage("Thay đổi đã được lưu lại.");
                }else{
                    MessageUtil.addErrorMessage(jSONObject.getString("error_msg"));
                }
            } catch (JSONException ex) {
                MessageUtil.addErrorMessage("Thao tác không thành công.");
                Logger.getLogger(QuanTriVien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            MessageUtil.addErrorMessage("Thao tác không thành công.");
        }
    }
    
    private void reset(){
        this.selectedTT = null;
        this.ten = "";
    }
}
