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
public class MuaBan implements Serializable{
    private int id;
    private String tieuDe;
    private long gia;
    private String donVi;
    private String tenDonVi;
    private String diaChi;
    private int tinhThanhId;
    private String tenTinhThanh;
    private String tenVungMien;
    private int taoBoiId;
    private String taoBoiTen;
    private String doiTuong;
    private String tenDoiTuong;
    private String loai;
    private String tenLoai;
    private String noiDung;
    private String ngayTao;
    private String ngayHetHan;
    private String trangThai;
    private String kiemDuyet;
    private String tenTrangThai;
    private boolean coAnh = false;
    private ArrayList<Anh> anh;
    private ArrayList<MuaBan> arrCanDuyet;
    private ArrayList<MuaBan> arrChapNhan;
    private ArrayList<MuaBan> arrTuChoi;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public MuaBan() {
        connection = DBUtil.connectSQL();
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        arrCanDuyet = getDSMuaBanCanDuyet();
        arrChapNhan = getDSMuaBanChapNhan();
        arrTuChoi = getDSMuaBanTuChoi();
    }

    public MuaBan(int id, String tieuDe,long gia, String donVi, String diaChi, int tinhThanhId, String tenTinhThanh, String tenVungMien, int taoBoiId, String taoBoiTen, String doiTuong, String loai, String noiDung, String ngayTao, String ngayHetHan, String trangThai, String kiemDuyet, ArrayList<Anh> anh) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.gia = gia;
        this.donVi = donVi;
        if(donVi.equals("qua")){
            this.tenDonVi = "1 Quả";
        }else if(donVi.equals("cu")){
            this.tenDonVi = "1 Củ";
        }else if(donVi.equals("cay")){
            this.tenDonVi = "1 Cây";
        }else{
            this.tenDonVi = "1 Kilogam";
        }
        this.diaChi = diaChi;
        this.tinhThanhId = tinhThanhId;
        this.tenTinhThanh = tenTinhThanh;
        this.tenVungMien = tenVungMien;
        this.taoBoiId = taoBoiId;
        this.taoBoiTen = taoBoiTen;
        this.doiTuong = doiTuong;
        if(doiTuong.equals("congty")){
            this.tenDoiTuong = "Công ty";
        }else{
            this.tenDoiTuong = "Cá nhân";
        }
        this.loai = loai;
        if(loai.equals("canmua")){
            this.tenLoai = "Cần mua";
        }else{
            this.tenLoai = "Cần bán";
        }
        this.noiDung = noiDung;
        this.ngayTao = ngayTao;
        this.ngayHetHan = ngayHetHan;
        this.trangThai = trangThai;
        this.kiemDuyet = kiemDuyet;
        if(trangThai.equals("daban")){
            this.tenTrangThai = "Đã bán";
        }else if(trangThai.equals("dangban")){
            this.tenTrangThai = "Đang bán";
        }else{
            this.tenTrangThai = "Tạm dừng";
        }
        this.anh = anh;
        if(anh.size()>0)
            this.coAnh = true;
        else
            this.coAnh = false;
    }

    public int getId() {
        return id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public int getTinhThanhId() {
        return tinhThanhId;
    }

    public int getTaoBoiId() {
        return taoBoiId;
    }

    public String getTaoBoiTen() {
        return taoBoiTen;
    }

    public String getDoiTuong() {
        return doiTuong;
    }

    public String getLoai() {
        return loai;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public String getNgayHetHan() {
        return ngayHetHan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getKiemDuyet() {
        return kiemDuyet;
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

    public void setTinhThanhId(int tinhThanhId) {
        this.tinhThanhId = tinhThanhId;
    }

    public void setTaoBoiId(int taoBoiId) {
        this.taoBoiId = taoBoiId;
    }

    public void setTaoBoiTen(String taoBoiTen) {
        this.taoBoiTen = taoBoiTen;
    }

    public void setDoiTuong(String doiTuong) {
        if(doiTuong.equals("congty")){
            this.tenDoiTuong = "Công ty";
        }else{
            this.tenDoiTuong = "Cá nhân";
        }
        this.doiTuong = doiTuong;
    }

    public void setLoai(String loai) {
        if(loai.equals("canmua")){
            this.tenLoai = "Cần mua";
        }else{
            this.tenLoai = "Cần bán";
        }
        this.loai = loai;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public void setNgayHetHan(String ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public void setTrangThai(String trangThai) {
        if(trangThai.equals("daban")){
            this.tenTrangThai = "Đã bán";
        }else if(trangThai.equals("dangban")){
            this.tenTrangThai = "Đang bán";
        }else{
            this.tenTrangThai = "Tạm dừng";
        }
        this.trangThai = trangThai;
    }

    public void setKiemDuyet(String kiemDuyet) {
        this.kiemDuyet = kiemDuyet;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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

    public void setTenTrangThai(String tenTrangThai) {
        this.tenTrangThai = tenTrangThai;
    }

    public void setTenVungMien(String tenVungMien) {
        this.tenVungMien = tenVungMien;
    }

    public ArrayList<Anh> getAnh() {
        return anh;
    }

    public void setAnh(ArrayList<Anh> anh) {
        if(anh.size()>0)
            this.coAnh = true;
        else
            this.coAnh = false;
        this.anh = anh;
    }
    
    public String getDonVi() {
        return donVi;
    }

    public long getGia() {
        return gia;
    }

    public void setDonVi(String donVi) {
        if(donVi.equals("qua")){
            this.tenDonVi = "1 Quả";
        }else if(donVi.equals("cu")){
            this.tenDonVi = "1 Củ";
        }else if(donVi.equals("cay")){
            this.tenDonVi = "1 Cây";
        }else{
            this.tenDonVi = "1 Kilogam";
        }
        this.donVi = donVi;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public boolean isCoAnh() {
        return coAnh;
    }

    public ArrayList<MuaBan> getArrCanDuyet() {
        return arrCanDuyet;
    }

    public void setArrCanDuyet(ArrayList<MuaBan> arrCanDuyet) {
        this.arrCanDuyet = arrCanDuyet;
    }

    public ArrayList<MuaBan> getArrChapNhan() {
        return arrChapNhan;
    }

    public ArrayList<MuaBan> getArrTuChoi() {
        return arrTuChoi;
    }

    public String getTenDoiTuong() {
        return tenDoiTuong;
    }

    public String getTenDonVi() {
        return tenDonVi;
    }

    public String getTenLoai() {
        return tenLoai;
    }
    
    private ArrayList<MuaBan> getDSMuaBanCanDuyet(){
        ArrayList<MuaBan> data = new ArrayList<>();
        String sql = "SELECT a.*, b.ten as 'tentinhthanh', "
                + "(SELECT ten FROM tinhthanh WHERE id = b.parent_id) as 'tenvungmien' , c.hovaten as 'taoboiten' "
                + "FROM muaban a "
                + "INNER JOIN tinhthanh b "
                + "ON a.tinhthanh_id = b.id "
                + "INNER JOIN nguoidung c "
                + "ON a.taoboi = c.id "
                + "WHERE kiemduyet = 'chuaduyet' "
                + "ORDER BY id ASC;";
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(rs.findColumn("id"));
                String tieuDe = rs.getString(rs.findColumn("tieude"));
                long gia = rs.getLong(rs.findColumn("gia"));
                String donVi = rs.getString(rs.findColumn("donvi"));
                String diaChi = rs.getString(rs.findColumn("diachi"));
                int tinhThanhId = rs.getInt(rs.findColumn("tinhthanh_id"));
                String tenTinhThanh = rs.getString(rs.findColumn("tentinhthanh"));
                String tenVungMien = rs.getString(rs.findColumn("tenvungmien"));
                int taoBoiId = rs.getInt(rs.findColumn("taoboi"));
                String taoBoiTen = rs.getString(rs.findColumn("taoboiten"));
                String doiTuong = rs.getString(rs.findColumn("doituong"));
                String loai = rs.getString(rs.findColumn("loai"));
                String noiDung = rs.getString(rs.findColumn("noidung"));
                String ngayTao = rs.getString(rs.findColumn("ngaytao"));
                String ngayHetHan = rs.getString(rs.findColumn("ngayhethan"));
                String trangThai = rs.getString(rs.findColumn("trangthai"));
                String kiemDuyet = rs.getString(rs.findColumn("kiemduyet"));
                //danh sach anh cua san pham
                ArrayList<Anh> arrAnh = getArrAnh(id);
                //add
                MuaBan mb = new MuaBan(id, tieuDe, gia, donVi, diaChi, tinhThanhId, tenTinhThanh, tenVungMien, taoBoiId, taoBoiTen, doiTuong, loai, noiDung, ngayTao, ngayHetHan, trangThai, kiemDuyet, arrAnh);
                data.add(mb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    private ArrayList<MuaBan> getDSMuaBanChapNhan(){
        ArrayList<MuaBan> data = new ArrayList<>();
        String sql = "SELECT a.*, b.ten as 'tentinhthanh', "
                + "(SELECT ten FROM tinhthanh WHERE id = b.parent_id) as 'tenvungmien' , c.hovaten as 'taoboiten' "
                + "FROM muaban a "
                + "INNER JOIN tinhthanh b "
                + "ON a.tinhthanh_id = b.id "
                + "INNER JOIN nguoidung c "
                + "ON a.taoboi = c.id "
                + "WHERE kiemduyet = 'chapnhan' "
                + "ORDER BY id ASC;";
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(rs.findColumn("id"));
                String tieuDe = rs.getString(rs.findColumn("tieude"));
                long gia = rs.getLong(rs.findColumn("gia"));
                String donVi = rs.getString(rs.findColumn("donvi"));
                String diaChi = rs.getString(rs.findColumn("diachi"));
                int tinhThanhId = rs.getInt(rs.findColumn("tinhthanh_id"));
                String tenTinhThanh = rs.getString(rs.findColumn("tentinhthanh"));
                String tenVungMien = rs.getString(rs.findColumn("tenvungmien"));
                int taoBoiId = rs.getInt(rs.findColumn("taoboi"));
                String taoBoiTen = rs.getString(rs.findColumn("taoboiten"));
                String doiTuong = rs.getString(rs.findColumn("doituong"));
                String loai = rs.getString(rs.findColumn("loai"));
                String noiDung = rs.getString(rs.findColumn("noidung"));
                String ngayTao = rs.getString(rs.findColumn("ngaytao"));
                String ngayHetHan = rs.getString(rs.findColumn("ngayhethan"));
                String trangThai = rs.getString(rs.findColumn("trangthai"));
                String kiemDuyet = rs.getString(rs.findColumn("kiemduyet"));
                //danh sach anh cua san pham
                ArrayList<Anh> arrAnh = getArrAnh(id);
                //add
                MuaBan mb = new MuaBan(id, tieuDe, gia, donVi, diaChi, tinhThanhId, tenTinhThanh, tenVungMien, taoBoiId, taoBoiTen, doiTuong, loai, noiDung, ngayTao, ngayHetHan, trangThai, kiemDuyet, arrAnh);
                data.add(mb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    private ArrayList<MuaBan> getDSMuaBanTuChoi(){
        ArrayList<MuaBan> data = new ArrayList<>();
        String sql = "SELECT a.*, b.ten as 'tentinhthanh', "
                + "(SELECT ten FROM tinhthanh WHERE id = b.parent_id) as 'tenvungmien' , c.hovaten as 'taoboiten' "
                + "FROM muaban a "
                + "INNER JOIN tinhthanh b "
                + "ON a.tinhthanh_id = b.id "
                + "INNER JOIN nguoidung c "
                + "ON a.taoboi = c.id "
                + "WHERE kiemduyet = 'khongchapnhan' "
                + "ORDER BY id ASC;";
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(rs.findColumn("id"));
                String tieuDe = rs.getString(rs.findColumn("tieude"));
                long gia = rs.getLong(rs.findColumn("gia"));
                String donVi = rs.getString(rs.findColumn("donvi"));
                String diaChi = rs.getString(rs.findColumn("diachi"));
                int tinhThanhId = rs.getInt(rs.findColumn("tinhthanh_id"));
                String tenTinhThanh = rs.getString(rs.findColumn("tentinhthanh"));
                String tenVungMien = rs.getString(rs.findColumn("tenvungmien"));
                int taoBoiId = rs.getInt(rs.findColumn("taoboi"));
                String taoBoiTen = rs.getString(rs.findColumn("taoboiten"));
                String doiTuong = rs.getString(rs.findColumn("doituong"));
                String loai = rs.getString(rs.findColumn("loai"));
                String noiDung = rs.getString(rs.findColumn("noidung"));
                String ngayTao = rs.getString(rs.findColumn("ngaytao"));
                String ngayHetHan = rs.getString(rs.findColumn("ngayhethan"));
                String trangThai = rs.getString(rs.findColumn("trangthai"));
                String kiemDuyet = rs.getString(rs.findColumn("kiemduyet"));
                //danh sach anh cua san pham
                ArrayList<Anh> arrAnh = getArrAnh(id);
                //add
                MuaBan mb = new MuaBan(id, tieuDe, gia, donVi, diaChi, tinhThanhId, tenTinhThanh, tenVungMien, taoBoiId, taoBoiTen, doiTuong, loai, noiDung, ngayTao, ngayHetHan, trangThai, kiemDuyet, arrAnh);
                data.add(mb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    private ArrayList<Anh> getArrAnh(int muaBanId){
        ArrayList<Anh> data = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM muaban_anh WHERE muaban_id = '"+muaBanId+"';";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Anh anh = new Anh(
                    rs.getInt(rs.findColumn("id")),
                    rs.getString(rs.findColumn("url")),
                    rs.getInt(rs.findColumn("muaban_id"))
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
    
    public HashMap<String, Object> duyet(String kiemDuyet){
        if(id<=0)
            return error("Bản ghi không tồn tại.");
        if(kiemDuyet==null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        if(kiemDuyet.isEmpty())
            return error("Nội dung gửi lên không chính xác.");
        if(!kiemDuyet.equals("chapnhan") && !kiemDuyet.equals("khongchapnhan"))
            return error("Nội dung gửi lên không chính xác.");
        
        String sql = "SELECT * FROM muaban WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                sql = "UPDATE muaban SET kiemduyet=? WHERE id = ?;";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, kiemDuyet);
                preparedStatement.setInt(2, id);
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
            Logger.getLogger(MuaBan.class.getName()).log(Level.SEVERE, null, ex);
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
