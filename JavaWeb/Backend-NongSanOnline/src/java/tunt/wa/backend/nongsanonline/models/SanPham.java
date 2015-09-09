/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.models;

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
public class SanPham {
    private int id;
    private String ten;
    private long gia;
    private String mota;
    private String ngayTao;
    private String ngayCapNhat;
    private String diaChi;
    private int tinhThanh;
    private String tenTinhThanh;
    private String tenVungMien;
    private String trangThai;
    private String tenTrangThai;
    private ArrayList<Anh> anh;
    private ArrayList<SanPham> arrSanPham;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public SanPham() {
        connection = DBUtil.connectSQL();
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        arrSanPham = getDSSanPham();
    }

    public SanPham(int id, String ten, long gia, String mota, String ngayTao, String ngayCapNhat, String diaChi, int tinhThanh, String tenTinhThanh, String tenVungMien, String trangThai, ArrayList<Anh> anh) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.mota = mota;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
        this.diaChi = diaChi;
        this.tinhThanh = tinhThanh;
        this.tenTinhThanh = tenTinhThanh;
        this.tenVungMien = tenVungMien;
        this.trangThai = trangThai;
        if(trangThai.equals("kichhoat"))
            this.tenTrangThai = "Kích hoạt";
        else
            this.tenTrangThai = "Tạm dừng";
        this.anh = anh;
    }

    public int getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public long getGia() {
        return gia;
    }

    public String getMota() {
        return mota;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public String getNgayCapNhat() {
        return ngayCapNhat;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public int getTinhThanh() {
        return tinhThanh;
    }

    public String getTenTinhThanh() {
        return tenTinhThanh;
    }

    public String getTenVungMien() {
        return tenVungMien;
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

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public void setNgayCapNhat(String ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setTinhThanh(int tinhThanh) {
        this.tinhThanh = tinhThanh;
    }

    public void setTenTinhThanh(String tenTinhThanh) {
        this.tenTinhThanh = tenTinhThanh;
    }

    public void setTenVungMien(String tenVungMien) {
        this.tenVungMien = tenVungMien;
    }

    public void setTrangThai(String trangThai) {
        if(trangThai.equals("kichhoat"))
            this.tenTrangThai = "Kích hoạt";
        else
            this.tenTrangThai = "Tạm dừng";
        this.trangThai = trangThai;
    }

    public void setTenTrangThai(String tenTrangThai) {
        this.tenTrangThai = tenTrangThai;
    }

    public void setAnh(ArrayList<Anh> anh) {
        this.anh = anh;
    }

    public ArrayList<Anh> getAnh() {
        return anh;
    }

    public ArrayList<SanPham> getArrSanPham() {
        return arrSanPham;
    }

    public void setArrSanPham(ArrayList<SanPham> arrSanPham) {
        this.arrSanPham = arrSanPham;
    }
    
    private ArrayList<SanPham> getDSSanPham(){
        ArrayList<SanPham> data = new ArrayList<>();
        String sql = "SELECT a.*, b.ten as 'tentinhthanh', (SELECT ten FROM tinhthanh WHERE id = b.parent_id) as 'tenvungmien' FROM sanpham a INNER JOIN tinhthanh b ON a.tinhthanh_id = b.id ORDER BY a.ten ASC;";
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(rs.findColumn("id"));
                String ten = rs.getString(rs.findColumn("ten"));
                int gia = rs.getInt(rs.findColumn("gia"));
                String moTa = rs.getString(rs.findColumn("mota"));
                String ngayTao = rs.getString(rs.findColumn("ngaytao"));
                String ngayCapNhat = rs.getString(rs.findColumn("ngaycapnhat"));
                String diaChi = rs.getString(rs.findColumn("diachi"));
                int tinhThanh = rs.getInt(rs.findColumn("tinhthanh_id"));
                String tenTinhThanh = rs.getString(rs.findColumn("tentinhthanh"));
                String tenVungMien = rs.getString(rs.findColumn("tenvungmien"));
                String trangThai = rs.getString(rs.findColumn("trangthai"));
                //danh sach anh cua san pham
                ArrayList<Anh> arrAnh = getArrAnh(id);
                //add san pham vao array
                SanPham sp = new SanPham(id, ten, gia, moTa, ngayTao, ngayCapNhat, diaChi, tinhThanh, tenTinhThanh, tenVungMien, trangThai, arrAnh);
                data.add(sp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    private ArrayList<Anh> getArrAnh(int sanPhamId){
        ArrayList<Anh> data = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM sanpham_anh WHERE sanpham_id = '"+sanPhamId+"';";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Anh anh = new Anh(
                    rs.getInt(rs.findColumn("id")),
                    rs.getString(rs.findColumn("url")),
                    rs.getInt(rs.findColumn("sanpham_id"))
                );
                //if(data.size()==0)
                    //data.add(new Anh(-1, "default", sanPhamId));
                data.add(anh);
            }
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public HashMap<String, Object> them(){
        if(ten==null||mota==null||diaChi==null||anh==null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        if(ten.isEmpty()||mota.isEmpty()||diaChi.isEmpty())
            return error("Có vẻ như bạn chưa điền đầy đủ thông tin.");
        if(tinhThanh<=0)
            return error("Bạn chưa chọn tỉnh thành.");
        if(gia<=0)
            return error("Giá sản phẩm phải lớn hơn 0.");
        if(anh.size()<=0)
            return error("Sản phẩm phải có ít nhất 1 ảnh.");
        
        String sql = "INSERT INTO sanpham VALUES(N'"+ten+"', '"+gia+"', N'"+mota+"', GETDATE(), GETDATE(), N'"+diaChi+"', '"+tinhThanh+"', 'kichhoat');";
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows!=0){
                ResultSet rs = preparedStatement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                ArrayList<Anh> arrAnh = new ArrayList<>();
                for(int i = 0; i<anh.size(); i++){
                    sql = "INSERT INTO sanpham_anh VALUES (N'"+anh.get(i).getAddUrl()+"', '"+id+"')";
                    preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    affectedRows = preparedStatement.executeUpdate();
                    if(affectedRows!=0){
                        rs = preparedStatement.getGeneratedKeys();
                        rs.next();
                        arrAnh.add(new Anh(rs.getInt(1), anh.get(i).getAddUrl(), id));
                    }
                }
                
                sql = "SELECT a.*, b.ten as 'tentinhthanh', (SELECT ten FROM tinhthanh WHERE id = b.parent_id) as 'tenvungmien' FROM sanpham a INNER JOIN tinhthanh b ON a.tinhthanh_id = b.id WHERE a.id='"+id+"'";
                rs = statement.executeQuery(sql);
                if(rs.next()){
                    HashMap<String, Object> success = new HashMap<>();
                    success.put("error", 0);
                    success.put("success", 1);
                    success.put("id", id);
                    success.put("ngay_tao", rs.getString(rs.findColumn("ngaytao")));
                    success.put("ngay_cap_nhat", rs.getString(rs.findColumn("ngaycapnhat")));
                    success.put("ten_tinh_thanh", rs.getString(rs.findColumn("tentinhthanh")));
                    success.put("ten_vung_mien", rs.getString(rs.findColumn("tenvungmien")));
                    success.put("trang_thai", rs.getString(rs.findColumn("trangthai")));
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
    
    public HashMap<String, Object> xoa(){
        if(id<=0)
            return error("Bản ghi không tồn tại.");
        
        String sql = "SELECT * FROM sanpham WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                if(anh!=null){
                    for (int i=0; i<anh.size(); i++) {
                        FileUploadUtil.deleteFile(anh.get(i).getAddUrl());
                    }
                }
                sql = "DELETE FROM sanpham_anh WHERE sanpham_id = '"+rs.getInt(rs.findColumn("id"))+"'";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
                
                sql = "DELETE FROM sanpham WHERE id = '"+rs.getInt(rs.findColumn("id"))+"';";
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
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error("Lỗi hệ thống.");
    }
    
    public HashMap<String, Object> sua(){
        if(id<=0)
            return error("Bản ghi không tồn tại.");
        if(ten==null||mota==null||diaChi==null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        if(ten.isEmpty()||mota.isEmpty()||diaChi.isEmpty())
            return error("Có vẻ như bạn chưa điền đầy đủ thông tin.");
        if(tinhThanh<=0)
            return error("Bạn chưa chọn tỉnh thành.");
        if(gia<=0)
            return error("Giá sản phẩm phải lớn hơn 0.");
        
        String sql = "SELECT * FROM sanpham WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                sql = "UPDATE sanpham SET ten=?, gia=?, mota=?, ngaycapnhat=GETDATE(), diachi=?, tinhthanh_id=? WHERE id = '"+rs.getInt(rs.findColumn("id"))+"';";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, ten);
                preparedStatement.setLong(2, gia);
                preparedStatement.setString(3, mota);
                preparedStatement.setString(4, diaChi);
                preparedStatement.setInt(5, tinhThanh);
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows!=0){
                    sql = "SELECT a.*, b.ten as 'tentinhthanh', (SELECT ten FROM tinhthanh WHERE id = b.parent_id) as 'tenvungmien' FROM sanpham a INNER JOIN tinhthanh b ON a.tinhthanh_id = b.id WHERE a.id='"+id+"'";
                    rs = statement.executeQuery(sql);
                    if(rs.next()){
                        HashMap<String, Object> success = new HashMap<>();
                        success.put("success", 1);
                        success.put("error", 0);
                        success.put("ngay_cap_nhat", rs.getString(rs.findColumn("ngaycapnhat")));
                        success.put("ten_tinh_thanh", rs.getString(rs.findColumn("tentinhthanh")));
                        success.put("ten_vung_mien", rs.getString(rs.findColumn("tenvungmien")));
                        return success; 
                    }
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
