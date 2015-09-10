/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.models;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import tunt.wa.backend.nongsanonline.utils.DBUtil;

/**
 *
 * @author Thanh Tu
 */
public class TinTuc implements Serializable{
    private int id;
    private String tieuDe;
    private String noiDung;
    private String moTa;
    private String ngayTao;
    private String trangThai;
    private String tenTrangThai;
    private String doiTrangThai;
    private String iconTrangThai;
    private ArrayList<TinTuc> arrTinTuc;
    private ArrayList<Anh> arrAnh;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public TinTuc() {
        connection = DBUtil.connectSQL();
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        arrTinTuc = getDSTinTuc();
        arrAnh = getDSAnh();
    }

    public TinTuc(int id, String tieuDe, String noiDung, String moTa, String ngayTao, String trangThai) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.moTa = moTa;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        if(trangThai.equals("kichhoat")){
            this.tenTrangThai = "Kích hoạt";
            this.iconTrangThai = "ui-icon-bullet";
            this.doiTrangThai = "Tạm dừng";
        }else{
            this.tenTrangThai = "Tạm dừng";
            this.iconTrangThai = "ui-icon-radio-off";
            this.doiTrangThai = "Kích hoạt";
        }
    }

    public int getId() {
        return id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getTenTrangThai() {
        return tenTrangThai;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public void setTrangThai(String trangThai) {
        if(trangThai.equals("kichhoat")){
            this.tenTrangThai = "Kích hoạt";
            this.iconTrangThai = "ui-icon-bullet";
            this.doiTrangThai = "Tạm dừng";
        }else{
            this.tenTrangThai = "Tạm dừng";
            this.iconTrangThai = "ui-icon-radio-off";
            this.doiTrangThai = "Kích hoạt";
        }
        this.trangThai = trangThai;
    }

    public void setTenTrangThai(String tenTrangThai) {
        this.tenTrangThai = tenTrangThai;
    }

    public ArrayList<TinTuc> getArrTinTuc() {
        return arrTinTuc;
    }

    public void setArrTinTuc(ArrayList<TinTuc> arrTinTuc) {
        this.arrTinTuc = arrTinTuc;
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

    public ArrayList<Anh> getArrAnh() {
        return arrAnh;
    }

    public void setArrAnh(ArrayList<Anh> arrAnh) {
        this.arrAnh = arrAnh;
    }
    
    private ArrayList<TinTuc> getDSTinTuc(){
        ArrayList<TinTuc> data = new ArrayList<>();
        String sql = "SELECT * FROM tintuc ORDER BY ngaytao DESC";
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(rs.findColumn("id"));
                String tieuDe = rs.getString(rs.findColumn("tieude"));
                String noiDung = rs.getString(rs.findColumn("noidung"));
                String moTa = rs.getString(rs.findColumn("mota"));
                String ngayTao = rs.getString(rs.findColumn("ngaytao"));
                String trangThai = rs.getString(rs.findColumn("trangthai"));
                //add san pham vao array
                TinTuc tt = new TinTuc(id, tieuDe, noiDung, moTa, ngayTao, trangThai);
                data.add(tt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public HashMap<String, Object> them(){
        if(tieuDe==null||noiDung==null||moTa==null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        if(tieuDe.isEmpty()||noiDung.isEmpty()||moTa.isEmpty())
            return error("Có vẻ như bạn chưa điền đầy đủ thông tin.");
        
        String sql = "INSERT INTO tintuc VALUES(?, ?, ?, GETDATE(), 'kichhoat');";
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, tieuDe);
            preparedStatement.setString(2, noiDung);
            preparedStatement.setString(3, moTa);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows!=0){
                ResultSet rs = preparedStatement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                
                sql = "SELECT * FROM tintuc WHERE id = '"+id+"'";
                rs = statement.executeQuery(sql);
                if(rs.next()){
                    HashMap<String, Object> success = new HashMap<>();
                    success.put("error", 0);
                    success.put("success", 1);
                    success.put("id", id);
                    success.put("ngay_tao", rs.getString(rs.findColumn("ngaytao")));
                    success.put("mo_ta", rs.getString(rs.findColumn("mota")));
                    return success; 
                }
            }else{
                return error("Sảy ra lỗi.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error("Lỗi hệ thống.");
    }
    
    public HashMap<String, Object> sua(){
        if(id<=0)
            return error("Bản ghi không tồn tại.");
        if(tieuDe==null||noiDung==null||moTa==null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        if(tieuDe.isEmpty()||noiDung.isEmpty()||moTa.isEmpty())
            return error("Có vẻ như bạn chưa điền đầy đủ thông tin.");
        
        String sql = "SELECT * FROM tintuc WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                sql = "UPDATE tintuc SET tieude=?, noidung=?, mota=? WHERE id = '"+rs.getInt(rs.findColumn("id"))+"';";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, tieuDe);
                preparedStatement.setString(2, noiDung);
                preparedStatement.setString(3, moTa);
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows!=0){
                    HashMap<String, Object> success = new HashMap<>();
                    success.put("success", 1);
                    success.put("error", 0);
                    return success; 
                }else{
                    return error("Sảy ra lỗi.");
                }
            }else{
               return error("Bản ghi không tồn tại.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return error("Lỗi hệ thống.");
    }
    
    public HashMap<String, Object> thayDoiTrangThai(){
        if(id<=0)
            return error("Bản ghi không tồn tại.");
        
        String sql = "SELECT * FROM tintuc WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                String trangThai = "kichhoat";
                if(rs.getString(rs.findColumn("trangthai")).equals("kichhoat")){
                    trangThai = "tamdung";
                }
                sql = "UPDATE tintuc SET trangthai=? WHERE id = '"+rs.getInt(rs.findColumn("id"))+"';";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, trangThai);
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows!=0){
                    HashMap<String, Object> success = new HashMap<>();
                    success.put("success", 1);
                    success.put("error", 0);
                    success.put("trang_thai", trangThai);
                    return success; 
                }else{
                    return error("Sảy ra lỗi.");
                }
            }else{
               return error("Bản ghi không tồn tại.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return error("Lỗi hệ thống.");
    }
    
    public HashMap<String, Object> xoa(){
        if(id<=0)
            return error("Bản ghi không tồn tại.");
        
        String sql = "SELECT * FROM tintuc WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                String trangThai = "kichhoat";
                if(rs.getString(rs.findColumn("trangthai")).equals("kichhoat")){
                    trangThai = "tamdung";
                }
                sql = "DELETE FROM tintuc WHERE id = ?;";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, rs.getInt(rs.findColumn("id")));
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows!=0){
                    HashMap<String, Object> success = new HashMap<>();
                    success.put("success", 1);
                    success.put("error", 0);
                    return success; 
                }else{
                    return error("Sảy ra lỗi.");
                }
            }else{
               return error("Bản ghi không tồn tại.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return error("Lỗi hệ thống.");
    }
    
    private ArrayList<Anh> getDSAnh(){
        ArrayList<Anh> data = new ArrayList<>();
        String sql = "SELECT * FROM tintuc_anh ORDER BY id DESC";
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(rs.findColumn("id"));
                String url = rs.getString(rs.findColumn("url"));
                //add san pham vao array
                Anh a = new Anh(id, url, -1);
                data.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public HashMap<String, Object> themAnh(Anh anh){
        if(anh==null)
            return error("Tải ảnh lên không thành công.");
        if(anh.getAddUrl()==null)
            return error("Tải ảnh lên không thành công.");
        if(anh.getAddUrl().isEmpty())
            return error("Tải ảnh lên không thành công.");
        
        String sql = "INSERT INTO tintuc_anh VALUES(?, '');";
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, anh.getAddUrl());
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows!=0){
                ResultSet rs = preparedStatement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                HashMap<String, Object> success = new HashMap<>();
                success.put("error", 0);
                success.put("success", 1);
                success.put("id", id);
                return success; 
            }else{
                return error("Sảy ra lỗi.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error("Sảy ra lỗi.");
    }
    
    private HashMap<String, Object> error(String error_msg){
        HashMap<String, Object> error = new HashMap<>();
        error.put("error", 1);
        error.put("success", 0);
        error.put("error_msg", error_msg);
        return error;
    }
}
