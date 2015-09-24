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
import tunt.wa.backend.nongsanonline.utils.FileUploadUtil;

/**
 *
 * @author Thanh Tu
 */
public class MeoVat implements Serializable{
    private int id;
    private String tieuDe;
    private String noiDung;
    private String kieu;
    private String tenKieu;
    private String trangThai;
    private String tenTrangThai;
    private String doiTrangThai;
    private String iconTrangThai;
    private ArrayList<Anh> anh;
    private ArrayList<MeoVat> arrMeoVat;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public MeoVat() {
        connection = DBUtil.connectSQL();
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        arrMeoVat = getDSMeoVat();
    }
    
    public MeoVat(int id, String tieuDe, String noiDung,String kieu, String trangThai, ArrayList<Anh> anh) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.kieu = kieu;
        if(kieu.equals("sanxuat")){
            this.tenKieu = "Sản xuất";
        }else{
            this.tenKieu = "Chế biến";
        }
        this.trangThai = trangThai;
        if(trangThai.equals("kichhoat")){
            this.tenTrangThai = "Kích hoạt";
            this.doiTrangThai = "Tạm dừng";
            this.iconTrangThai = "ui-icon-bullet";
        }else{
            this.tenTrangThai = "Tạm dừng";
            this.doiTrangThai = "Kích hoạt";
            this.iconTrangThai = "ui-icon-radio-off";
        }
        this.anh = anh;
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

    public String getTrangThai() {
        return trangThai;
    }

    public ArrayList<Anh> getAnh() {
        return anh;
    }

    public ArrayList<MeoVat> getArrMeoVat() {
        return arrMeoVat;
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

    public void setTrangThai(String trangThai) {
        if(trangThai.equals("kichhoat")){
            this.tenTrangThai = "Kích hoạt";
            this.doiTrangThai = "Tạm dừng";
            this.iconTrangThai = "ui-icon-bullet";
        }else{
            this.tenTrangThai = "Tạm dừng";
            this.doiTrangThai = "Kích hoạt";
            this.iconTrangThai = "ui-icon-radio-off";
        }
        this.trangThai = trangThai;
    }

    public void setAnh(ArrayList<Anh> anh) {
        this.anh = anh;
    }

    public void setArrMeoVat(ArrayList<MeoVat> arrMeoVat) {
        this.arrMeoVat = arrMeoVat;
    }

    public String getKieu() {
        return kieu;
    }

    public void setKieu(String kieu) {
        if(kieu.equals("sanxuat")){
            this.tenKieu = "Sản xuất";
        }else{
            this.tenKieu = "Chế biến";
        }
        this.kieu = kieu;
    }

    public String getDoiTrangThai() {
        return doiTrangThai;
    }

    public String getIconTrangThai() {
        return iconTrangThai;
    }

    public String getTenKieu() {
        return tenKieu;
    }

    public String getTenTrangThai() {
        return tenTrangThai;
    }

    public void setTenKieu(String tenKieu) {
        this.tenKieu = tenKieu;
    }

    public void setTenTrangThai(String tenTrangThai) {
        this.tenTrangThai = tenTrangThai;
    }

    public void setDoiTrangThai(String doiTrangThai) {
        this.doiTrangThai = doiTrangThai;
    }

    public void setIconTrangThai(String iconTrangThai) {
        this.iconTrangThai = iconTrangThai;
    }
    
    private ArrayList<MeoVat> getDSMeoVat(){
        ArrayList<MeoVat> data = new ArrayList<>();
        String sql = "SELECT * FROM meovat ORDER BY tieude ASC;";
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(rs.findColumn("id"));
                String tieuDe = rs.getString(rs.findColumn("tieude"));
                String noiDung = rs.getString(rs.findColumn("noidung"));
                String kieu = rs.getString(rs.findColumn("kieu"));
                String trangThai = rs.getString(rs.findColumn("trangthai"));
                //danh sach anh cua san pham
                ArrayList<Anh> arrAnh = getArrAnh(id);
                //add san pham vao array
                MeoVat mv = new MeoVat(id, tieuDe, noiDung, kieu, trangThai, arrAnh);
                data.add(mv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MeoVat.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    private ArrayList<Anh> getArrAnh(int meoVatId){
        ArrayList<Anh> data = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM meovat_anh WHERE meovat_id = '"+meoVatId+"';";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Anh anh = new Anh(
                    rs.getInt(rs.findColumn("id")),
                    rs.getString(rs.findColumn("url")),
                    rs.getInt(rs.findColumn("meovat_id"))
                );
                data.add(anh);
            }
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(MeoVat.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public HashMap<String, Object> them(){
        if(tieuDe==null||noiDung==null||kieu==null||anh==null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        if(tieuDe.isEmpty()||noiDung.isEmpty()||kieu.isEmpty())
            return error("Có vẻ như bạn chưa điền đầy đủ thông tin.");
        if(!kieu.equals("sanxuat")&&!kieu.equals("chebien"))
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        if(anh.size()<=0)
            return error("Sản phẩm phải có ít nhất 1 ảnh.");
        
        String sql = "INSERT INTO meovat VALUES(?, ?, ?, 'kichhoat')";
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, tieuDe);
            preparedStatement.setString(2, noiDung);
            preparedStatement.setString(3, kieu);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows!=0){
                ResultSet rs = preparedStatement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                ArrayList<Anh> arrAnh = new ArrayList<>();
                for(int i = 0; i<anh.size(); i++){
                    sql = "INSERT INTO meovat_anh VALUES (?, ?)";
                    preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, anh.get(i).getAddUrl());
                    preparedStatement.setInt(2, id);
                    affectedRows = preparedStatement.executeUpdate();
                    if(affectedRows!=0){
                        System.out.println("id : "+id+"/ url: "+anh.get(i).getAddUrl());
                        rs = preparedStatement.getGeneratedKeys();
                        rs.next();
                        arrAnh.add(new Anh(rs.getInt(1), anh.get(i).getAddUrl(), id));
                    }
                }
                
                sql = "SELECT * FROM meovat WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                rs = preparedStatement.executeQuery();
                if(rs.next()){
                    HashMap<String, Object> success = new HashMap<>();
                    success.put("error", 0);
                    success.put("success", 1);
                    success.put("id", id);
                    success.put("anh", arrAnh);
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
        if(tieuDe==null||noiDung==null||kieu==null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        if(tieuDe.isEmpty()||noiDung.isEmpty()||kieu.isEmpty())
            return error("Có vẻ như bạn chưa điền đầy đủ thông tin.");
        if(!kieu.equals("sanxuat")&&!kieu.equals("chebien"))
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        
        String sql = "SELECT * FROM meovat WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                sql = "UPDATE meovat SET tieude=?, noidung=?, kieu=? WHERE id = '"+rs.getInt(rs.findColumn("id"))+"';";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, tieuDe);
                preparedStatement.setString(2, noiDung);
                preparedStatement.setString(3, kieu);
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
            Logger.getLogger(MeoVat.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return error("Lỗi hệ thống.");
    }
    
    public HashMap<String, Object> xoa(){
        if(id<=0)
            return error("Bản ghi không tồn tại.");
        
        String sql = "SELECT * FROM meovat WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                if(anh!=null){
                    for (int i=0; i<anh.size(); i++) {
                        FileUploadUtil.deleteFile("/resource/uploads/meovat/", anh.get(i).getAddUrl());
                    }
                }
                sql = "DELETE FROM meovat_anh WHERE meovat_id = '"+rs.getInt(rs.findColumn("id"))+"'";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
                
                sql = "DELETE FROM meovat WHERE id = '"+rs.getInt(rs.findColumn("id"))+"';";
                preparedStatement = connection.prepareStatement(sql);
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
            Logger.getLogger(MeoVat.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error("Lỗi hệ thống.");
    }
    
    public HashMap<String, Object> thayDoiTrangThai(){
        if(id<=0)
            return error("Bản ghi không tồn tại.");
        
        String sql = "SELECT * FROM meovat WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                String trangThai = "kichhoat";
                if(rs.getString(rs.findColumn("trangthai")).equals("kichhoat")){
                    trangThai = "tamdung";
                }
                sql = "UPDATE meovat SET trangthai=? WHERE id = '"+rs.getInt(rs.findColumn("id"))+"';";
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
    
    private HashMap<String, Object> error(String error_msg){
        HashMap<String, Object> error = new HashMap<>();
        error.put("error", 1);
        error.put("success", 0);
        error.put("error_msg", error_msg);
        return error;
    }
}
