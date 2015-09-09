/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.ws.nongsanonline;

import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tunt.ws.nongsanonline.utils.DBUtil;

/**
 * REST Web Service
 *
 * @author Thanh Tu
 */
@Path("backend")
public class BackendResource {

    @Context
    private ServletContext context; 
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    /**
     * Creates a new instance of BackendResource
     */
    public BackendResource() {
        try {
            connection = DBUtil.connectSQL();
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(BackendResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retrieves representation of an instance of tunt.ws.nongsanonline.BackendResource
     * @return an instance of java.lang.String
     */
    //-----------------------TEST--------------------------//
    @GET
    @Produces("application/json")
    public String getJson() {
        JSONObject obj = new JSONObject();
	obj.put("name", "demo");

	JSONArray list = new JSONArray();
	list.add("msg 1");
	list.add("msg 2");
	list.add("msg 3");

	obj.put("messages", list);
        return obj.toJSONString();
    }
    //-----------------------TEST-end--------------------------//
    //-----------------------Quản trị viên--------------------------//
    @POST
    @Path("danhsachquantrivien")
    @Produces("application/json"+";charset=utf-8")
    public String getDSQuanTriVien(
            @FormParam("ctendangnhap") String cTenDangNhap,
            @FormParam("cid") String cId){
        if(cTenDangNhap.isEmpty() || cId.isEmpty()){
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        }
        
        if(!check(cTenDangNhap, cId)){
            return error("Thông tin đăng nhập không chính xác hoặc tài khoản chưa được kích hoạt hoặc bạn không đủ quyền để truy cập.");
        }
        JSONObject result = new JSONObject();
        result.put("success", 1);
        result.put("error", 0);

        JSONArray rows = new JSONArray();
        String sql = "SELECT * FROM quantrivien WHERE id != '"+cId+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                JSONObject quantrivien = new JSONObject();
                quantrivien.put("id", rs.getInt(rs.findColumn("id")));
                quantrivien.put("ten_dang_nhap", rs.getString(rs.findColumn("tendangnhap")));
                quantrivien.put("ho_va_ten", rs.getString(rs.findColumn("hovaten")));
                quantrivien.put("anh_dai_dien", rs.getString(rs.findColumn("anhdaidien")));
                quantrivien.put("quyen", rs.getString(rs.findColumn("quyen")));
                quantrivien.put("trang_thai", rs.getString(rs.findColumn("trangthai")));
                rows.add(quantrivien);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BackendResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        result.put("total", rows.size());
        result.put("rows", rows);
        return result.toJSONString();
    }
    
    @POST
    @Path("themquantrivien")
    @Produces("application/json"+";charset=utf-8")
    public String themQuanTriVien(
            @FormParam("ctendangnhap") String cTenDangNhap, 
            @FormParam("cid") String cId,
            @FormParam("tendangnhap") String tenDangNhap, 
            @FormParam("matkhau") String matKhau,
            @FormParam("hovaten") String hoVaTen,
            @FormParam("quyen") String quyen){
        if(cTenDangNhap.isEmpty() || cId.isEmpty() || tenDangNhap.isEmpty() || matKhau.isEmpty() ||
                hoVaTen.isEmpty() || quyen.isEmpty() || 
                (!quyen.equals("master") && !quyen.equals("admin")))
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        
        if(!check(cTenDangNhap, cId)){
            return error("Thông tin đăng nhập không chính xác hoặc tài khoản chưa được kích hoạt hoặc bạn không đủ quyền để truy cập.");
        }

        String sql = "SELECT * FROM quantrivien WHERE LOWER(tendangnhap) = LOWER('"+tenDangNhap+"');";
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
               return error("Tài khoản đã tồn tại.");
            }
            sql = "INSERT INTO quantrivien VALUES(LOWER(N'"+tenDangNhap+"'),  CONVERT(VARCHAR(32), HashBytes('MD5', '"+matKhau+"'), 2), N'"+hoVaTen+"', 'default', '"+quyen+"', 'kichhoat')";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows!=0){
                rs = preparedStatement.getGeneratedKeys();
                rs.next();

                JSONObject result = new JSONObject();
                result.put("success", 1);
                result.put("error", 0);
                result.put("id", rs.getInt(1));
                return result.toJSONString(); 
            }else{
                return error("Sảy ra lỗi.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BackendResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error("Sảy ra lỗi.");
    }
    
    @POST
    @Path("xoaquantrivien")
    @Produces("application/json"+";charset=utf-8")
    public String xoaQuanTriVien(
            @FormParam("ctendangnhap") String cTenDangNhap, 
            @FormParam("cid") String cId,
            @FormParam("id") String id){
        if(cTenDangNhap.isEmpty() || cId.isEmpty() || id.isEmpty())
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        
        if(!check(cTenDangNhap, cId)){
            return error("Thông tin đăng nhập không chính xác hoặc tài khoản chưa được kích hoạt hoặc bạn không đủ quyền để truy cập.");
        }
        
        if(Integer.parseInt(id)<=0)
            return error("Bản ghi không tồn tại.");
        
        String sql = "SELECT * FROM quantrivien WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            int row = 0;
            if(rs.next()){
                if(rs.getString(rs.findColumn("tendangnhap")).equals("master")){
                    return error("Không thể xóa tài khoản này.");
                }
                sql = "DELETE FROM quantrivien WHERE id = '"+id+"';";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows!=0){
                    JSONObject result = new JSONObject();
                    result.put("success", 1);
                    result.put("error", 0);
                    return result.toJSONString(); 
                }else{
                    return error("Sảy ra lỗi.");
                }
            }else{
               return error("Bản ghi không tồn tại.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BackendResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error("Sảy ra lỗi.");
    }

    @POST
    @Path("suaquantrivien")
    @Produces("application/json"+";charset=utf-8")
    public String suaQuanTriVien(
            @FormParam("ctendangnhap") String cTenDangNhap, 
            @FormParam("cid") String cId,
            @FormParam("id") String id, 
            @FormParam("matkhau") String matKhau,
            @FormParam("hovaten") String hoVaTen,
            @FormParam("quyen") String quyen,
            @FormParam("trangthai") String trangThai){
        if(cTenDangNhap.isEmpty() || cId.isEmpty() || id.isEmpty() ||
                hoVaTen.isEmpty() || quyen.isEmpty() || (!quyen.equals("master") && !quyen.equals("admin")) ||
                trangThai.isEmpty() && (!trangThai.equals("kichhoat")||!trangThai.equals("tamdung")))
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        
        if(!check(cTenDangNhap, cId)){
            return error("Thông tin đăng nhập không chính xác hoặc tài khoản chưa được kích hoạt hoặc bạn không đủ quyền để truy cập.");
        }

        String sql = "SELECT * FROM quantrivien WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            int row = 0;
            while (rs.next()) {
                row++;
            }
            if(row>0){
                String add = "";
                String mk = "";
                if(!matKhau.isEmpty())
                    mk = "matkhau = CONVERT(VARCHAR(32), HashBytes('MD5', '"+matKhau+"'), 2),";
                sql = "UPDATE quantrivien SET "+mk+" hovaten = N'"+hoVaTen+"', "+add+" quyen = '"+quyen+"', trangthai = '"+trangThai+"' WHERE id = '"+id+"';";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows!=0){
                    JSONObject result = new JSONObject();
                    result.put("success", 1);
                    result.put("error", 0);
                    return result.toJSONString(); 
                }else{
                    return error("Sảy ra lỗi.");
                }
            }else
               return error("Bản ghi không tồn tại."); 
        } catch (SQLException ex) {
            Logger.getLogger(BackendResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error("Sảy ra lỗi.");
    }
    
    @POST
    @Path("dangnhap")
    @Produces("application/json"+";charset=utf-8")
    public String dangnhap(
            @FormParam("tendangnhap") String tenDangNhap, 
            @FormParam("matkhau") String matKhau){
        if(tenDangNhap.isEmpty() || matKhau.isEmpty())
            return error("Tên đăng nhập và mật khẩu không được để rỗng.");
        
        String sql = "SELECT * FROM quantrivien WHERE LOWER(tendangnhap) = LOWER('"+tenDangNhap+"') AND matkhau = CONVERT(VARCHAR(32), HashBytes('MD5', '"+matKhau+"'), 2);";
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                if(rs.getString(rs.findColumn("trangthai")).equals("kichhoat")){
                    JSONObject result = new JSONObject();
                    result.put("success", 1);
                    result.put("error", 0);
                    result.put("id", rs.getInt(rs.findColumn("id")));
                    result.put("ten_dang_nhap", rs.getString(rs.findColumn("tendangnhap")));
                    result.put("ho_va_ten", rs.getString(rs.findColumn("hovaten")));
                    result.put("quyen", rs.getString(rs.findColumn("quyen")));
                    result.put("anh_dai_dien", rs.getString(rs.findColumn("anhdaidien")));
                    result.put("trang_thai", rs.getString(rs.findColumn("trangthai")));
                    return result.toJSONString(); 
                }else{
                    return error("Tài khoản chưa được kích hoạt.");
                }
            }else{
                return error("Tài khoản hoặc mật khẩu không chính xác.");
            }
        }catch (SQLException ex){
        }
        return error("Sảy ra lỗi."); 
    }
    
    @POST
    @Path("thongtinquantrivien")
    @Produces("application/json"+";charset=utf-8")
    public String thongTinQuanTriVien(
            @FormParam("ctendangnhap") String cTenDangNhap, 
            @FormParam("cid") String cId,
            @FormParam("id") String id){
        if(cTenDangNhap.isEmpty() || cId.isEmpty())
            return error("Tên đăng nhập và mật khẩu không được để rỗng.");
        
        if(Integer.parseInt(id)<=0)
            return error("Bản ghi không tồn tại.");
        
        if(check2(cTenDangNhap, cId)){
            String sql = "SELECT * FROM quantrivien WHERE id = '"+id+"';";
            try {
                ResultSet rs = statement.executeQuery(sql);
                if(rs.next()){
                    JSONObject result = new JSONObject();
                    result.put("success", 1);
                    result.put("error", 0);
                    result.put("id", rs.getInt(rs.findColumn("id")));
                    result.put("ten_dang_nhap", rs.getString(rs.findColumn("tendangnhap")));
                    result.put("ho_va_ten", rs.getString(rs.findColumn("hovaten")));
                    result.put("quyen", rs.getString(rs.findColumn("quyen")));
                    result.put("anh_dai_dien", rs.getString(rs.findColumn("anhdaidien")));
                    result.put("trang_thai", rs.getString(rs.findColumn("trangthai")));
                    return result.toJSONString();
                }else{
                    return error("Không tìm thấy bản ghi có id "+id+".");
                }
            }catch (SQLException ex){
            }
        }
        return error("Sảy ra lỗi."); 
    }
    
    @POST
    @Path("doimatkhau")
    @Produces("application/json"+";charset=utf-8")
    public String doiMatKhau(
            @FormParam("ctendangnhap") String cTenDangNhap, 
            @FormParam("cid") String cId,
            @FormParam("matkhaucu") String matKhauCu,
            @FormParam("matkhaumoi") String matKhauMoi){
        if(cTenDangNhap == null || cId == null || matKhauCu == null || matKhauMoi == null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        
        if(cTenDangNhap.isEmpty() || cId.isEmpty() || matKhauCu.isEmpty() || matKhauMoi.isEmpty())
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        
        if(Integer.parseInt(cId)<=0)
            return error("Bản ghi không tồn tại.");
        
        if(check2(cTenDangNhap, cId)){
            String sql = "SELECT * FROM quantrivien WHERE id = '"+cId+"' AND matkhau = CONVERT(VARCHAR(32), HashBytes('MD5', '"+matKhauCu+"'), 2);";
            try {
                ResultSet rs = statement.executeQuery(sql);
                if(rs.next()){
                    sql = "UPDATE quantrivien SET matkhau = CONVERT(VARCHAR(32), HashBytes('MD5', '"+matKhauMoi+"'), 2) WHERE id = '"+cId+"';";
                    preparedStatement = connection.prepareStatement(sql);
                    int affectedRows = preparedStatement.executeUpdate();
                    if(affectedRows!=0){
                        JSONObject result = new JSONObject();
                        result.put("success", 1);
                        result.put("error", 0);
                        return result.toJSONString(); 
                    }else{
                        return error("Sảy ra lỗi.");
                    }
                }else{
                    return error("Mật khẩu cũ không chính xác.");
                }
            }catch (SQLException ex){
            }
        }
        return error("Sảy ra lỗi."); 
    }
    
    @POST
    @Path("thaydoithongtin")
    @Produces("application/json"+";charset=utf-8")
    public String thayDoiThongTin(
            @FormParam("ctendangnhap") String cTenDangNhap, 
            @FormParam("cid") String cId,
            @FormParam("hovaten") String hoVaten){
        if(cTenDangNhap == null || cId == null || hoVaten == null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        
        if(cTenDangNhap.isEmpty() || cId.isEmpty() || hoVaten.isEmpty())
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        
        if(Integer.parseInt(cId)<=0)
            return error("Bản ghi không tồn tại.");
        
        if(check2(cTenDangNhap, cId)){
            String sql = "UPDATE quantrivien SET hovaten = N'"+hoVaten+"' WHERE id ='"+cId+"'; ";
            try {
                preparedStatement = connection.prepareStatement(sql);
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows!=0){
                    JSONObject result = new JSONObject();
                    result.put("success", 1);
                    result.put("error", 0);
                    return result.toJSONString(); 
                }else{
                    return error("Sảy ra lỗi.");
                }
            }catch (SQLException ex){
            }
        }
        return error("Sảy ra lỗi."); 
    }
    //-----------------------Quản trị viên - END--------------------------//
    //-----------------------Người dùng--------------------------//
    @POST
    @Path("danhsachnguoidung")
    @Produces("application/json"+";charset=utf-8")
    public String getDSNguoiDung(
            @FormParam("ctendangnhap") String cTenDangNhap, 
            @FormParam("cid") String cId){
        if(cTenDangNhap == null || cId == null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        
        if(cTenDangNhap.isEmpty() || cId.isEmpty())
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        
        if(check2(cTenDangNhap, cId)){
            JSONObject result = new JSONObject();
            result.put("success", 1);
            result.put("error", 0);

            JSONArray rows = new JSONArray();
            String sql = "SELECT * FROM nguoidung ORDER BY tendangnhap DESC;";
            try {
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    JSONObject nguoidung = new JSONObject();
                    nguoidung.put("id", rs.getInt(rs.findColumn("id")));
                    nguoidung.put("ten_dang_nhap", rs.getString(rs.findColumn("tendangnhap")));
                    nguoidung.put("ho_va_ten", rs.getString(rs.findColumn("hovaten")));
                    nguoidung.put("dia_chi", rs.getString(rs.findColumn("diachi")));
                    nguoidung.put("sdt", rs.getString(rs.findColumn("sdt")));
                    nguoidung.put("email", rs.getString(rs.findColumn("email")));
                    nguoidung.put("anh_dai_dien", rs.getString(rs.findColumn("anhdaidien")));
                    nguoidung.put("ma_kich_hoat", rs.getString(rs.findColumn("makichhoat")));
                    nguoidung.put("ngay_tao", rs.getString(rs.findColumn("ngaytao")));
                    nguoidung.put("trang_thai", rs.getString(rs.findColumn("trangthai")));
                    rows.add(nguoidung);
                }
            } catch (SQLException ex) {
                Logger.getLogger(BackendResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            result.put("total", rows.size());
            result.put("rows", rows);
            return result.toJSONString();
        }
        return error("Sảy ra lỗi.");
    }
    
    @POST
    @Path("thaydoitrangthainguoidung")
    @Produces("application/json"+";charset=utf-8")
    public String thayDoiTrangThaiND(
            @FormParam("ctendangnhap") String cTenDangNhap, 
            @FormParam("cid") String cId,
            @FormParam("id") String id){
        if(cTenDangNhap == null || cId == null || id == null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        
        if(cTenDangNhap.isEmpty() || cId.isEmpty() || id.isEmpty())
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        
        if(Integer.parseInt(id)<=0)
            return error("Bản ghi không tồn tại.");
        
        if(!check2(cTenDangNhap, cId))
            return error("Thông tin đăng nhập không chính xác hoặc tài khoản chưa được kích hoạt hoặc bạn không đủ quyền để truy cập.");
        
        String sql = "SELECT * FROM nguoidung WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                String trangthai = "";
                if(rs.getString(rs.findColumn("trangthai")).equals("kichhoat")){
                    trangthai = "tamdung";
                }else{
                    trangthai = "kichhoat";
                }
                sql = "UPDATE nguoidung SET trangthai = '"+trangthai+"' WHERE id = '"+rs.getInt(rs.findColumn("id"))+"';";
                preparedStatement = connection.prepareStatement(sql);
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows!=0){
                    JSONObject result = new JSONObject();
                    result.put("success", 1);
                    result.put("error", 0);
                    return result.toJSONString(); 
                }else{
                    return error("Sảy ra lỗi.");
                }
            }else{
                return error("Bản ghi không tồn tại.");
            }
        }catch (SQLException ex){
        }
        return error("Sảy ra lỗi."); 
    }
    
    @POST
    @Path("xoanguoidung")
    @Produces("application/json"+";charset=utf-8")
    public String xoaNguoiDung(
            @FormParam("ctendangnhap") String cTenDangNhap, 
            @FormParam("cid") String cId,
            @FormParam("id") String id){
        if(cTenDangNhap == null || cId == null || id == null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        
        if(cTenDangNhap.isEmpty() || cId.isEmpty() || id.isEmpty())
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        
        if(!check2(cTenDangNhap, cId)){
            return error("Thông tin đăng nhập không chính xác hoặc tài khoản chưa được kích hoạt hoặc bạn không đủ quyền để truy cập.");
        }
        
        if(Integer.parseInt(id)<=0)
            return error("Bản ghi không tồn tại.");
        
        String sql = "SELECT * FROM nguoidung WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            int row = 0;
            if(rs.next()){
                sql = "DELETE FROM nguoidung WHERE id = '"+rs.getInt(rs.findColumn("id"))+"';";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows!=0){
                    JSONObject result = new JSONObject();
                    result.put("success", 1);
                    result.put("error", 0);
                    return result.toJSONString(); 
                }else{
                    return error("Sảy ra lỗi.");
                }
            }else{
               return error("Bản ghi không tồn tại.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BackendResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error("Sảy ra lỗi.");
    }
    //-----------------------Người dùng - END--------------------------//
    //-----------------------Tỉnh thành--------------------------//
    @POST
    @Path("danhsachtinhthanh")
    @Produces("application/json"+";charset=utf-8")
    public String getDSTinhThanh(
            @FormParam("ctendangnhap") String cTenDangNhap, 
            @FormParam("cid") String cId){
        if(cTenDangNhap == null || cId == null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        
        if(cTenDangNhap.isEmpty() || cId.isEmpty())
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        
        if(check2(cTenDangNhap, cId)){
            JSONObject result = new JSONObject();
            result.put("success", 1);
            result.put("error", 0);

            JSONArray rows = new JSONArray();
            String sql = "SELECT * FROM tinhthanh ORDER BY ten ASC;";
            try {
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    JSONObject tinhthanh = new JSONObject();
                    tinhthanh.put("id", rs.getInt(rs.findColumn("id")));
                    tinhthanh.put("ten", rs.getString(rs.findColumn("ten")));
                    tinhthanh.put("parent_id", rs.getString(rs.findColumn("parent_id")));
                    rows.add(tinhthanh);
                }
            } catch (SQLException ex) {
                Logger.getLogger(BackendResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            result.put("total", rows.size());
            result.put("rows", rows);
            return result.toJSONString();
        }
        return error("Sảy ra lỗi.");
    }
    
    @POST
    @Path("themtinhthanh")
    @Produces("application/json"+";charset=utf-8")
    public String themTinhThanh(
            @FormParam("ctendangnhap") String cTenDangNhap, 
            @FormParam("cid") String cId,
            @FormParam("ten") String ten,
            @FormParam("parentid") String parentId){
        if(cTenDangNhap == null || cId == null || ten == null || parentId == null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        
        if(cTenDangNhap.isEmpty() || cId.isEmpty() || ten.isEmpty() || parentId.isEmpty())
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        
        if(!check2(cTenDangNhap, cId)){
            return error("Thông tin đăng nhập không chính xác hoặc tài khoản chưa được kích hoạt hoặc bạn không đủ quyền để truy cập.");
        }
        
        String sql = "SELECT * FROM tinhthanh WHERE LOWER(ten) = LOWER(N'"+ten+"');";
        try {
            ResultSet rs = statement.executeQuery(sql);
            int row = 0;
            if(rs.next()){
                return error("Vùng miền, tỉnh thành đã tồn tại.");
            }else{
                sql = "INSERT INTO tinhthanh VALUES(N'"+ten+"', '"+parentId+"');";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows!=0){
                    rs = preparedStatement.getGeneratedKeys();
                    rs.next();
                    
                    JSONObject result = new JSONObject();
                    result.put("success", 1);
                    result.put("error", 0);
                    result.put("id", rs.getInt(1));
                    
                    return result.toJSONString(); 
                }else{
                    return error("Sảy ra lỗi.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BackendResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error("Sảy ra lỗi.");
    }
    
    @POST
    @Path("xoatinhthanh")
    @Produces("application/json"+";charset=utf-8")
    public String xoaTinhThanh(
            @FormParam("ctendangnhap") String cTenDangNhap, 
            @FormParam("cid") String cId,
            @FormParam("id") String id){
        if(cTenDangNhap == null || cId == null || id == null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        
        if(cTenDangNhap.isEmpty() || cId.isEmpty() || id.isEmpty())
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        
        if(Integer.parseInt(id)<=0)
            return error("Bản ghi không tồn tại.");
        
        if(!check2(cTenDangNhap, cId)){
            return error("Thông tin đăng nhập không chính xác hoặc tài khoản chưa được kích hoạt hoặc bạn không đủ quyền để truy cập.");
        }
        
        String sql = "SELECT * FROM tinhthanh WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            int row = 0;
            if(rs.next()){
                if(rs.getInt(rs.findColumn("parent_id"))==0){
                    sql = "DELETE FROM tinhthanh WHERE parent_id = '"+rs.getInt(rs.findColumn("id"))+"';";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.executeUpdate();
                }
                sql = "DELETE FROM tinhthanh WHERE id = '"+rs.getInt(rs.findColumn("id"))+"';";
                preparedStatement = connection.prepareStatement(sql);
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows!=0){
                    JSONObject result = new JSONObject();
                    result.put("success", 1);
                    result.put("error", 0);
                    return result.toJSONString(); 
                }else{
                    return error("Sảy ra lỗi.");
                }
            }else{
                return error("Bản ghi không tồn tại.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BackendResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error("Sảy ra lỗi.");
    }
    
    @POST
    @Path("doitentinhthanh")
    @Produces("application/json"+";charset=utf-8")
    public String doiTenTinhThanh(
            @FormParam("ctendangnhap") String cTenDangNhap, 
            @FormParam("cid") String cId,
            @FormParam("id") String id,
            @FormParam("ten") String ten){
        if(cTenDangNhap == null || cId == null || id == null || ten == null)
            return error("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        
        if(cTenDangNhap.isEmpty() || cId.isEmpty() || id.isEmpty() || ten.isEmpty())
            return error("Thông tin gửi lên không chính xác, vui lòng kiểm tra lại.");
        
        if(Integer.parseInt(id)<=0)
            return error("Bản ghi không tồn tại.");
        
        if(!check2(cTenDangNhap, cId)){
            return error("Thông tin đăng nhập không chính xác hoặc tài khoản chưa được kích hoạt hoặc bạn không đủ quyền để truy cập.");
        }
        
        String sql = "SELECT * FROM tinhthanh WHERE id = '"+id+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                sql = "SELECT * FROM tinhthanh WHERE LOWER(ten) = LOWER(N'"+ten+"');";
                rs = statement.executeQuery(sql);
                if(rs.next())
                    return error("Đã tồn tại.");
                sql = "UPDATE tinhthanh SET ten = N'"+ten+"' WHERE id = '"+id+"';";
                preparedStatement = connection.prepareStatement(sql);
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows!=0){
                    JSONObject result = new JSONObject();
                    result.put("success", 1);
                    result.put("error", 0);
                    return result.toJSONString(); 
                }else{
                    return error("Sảy ra lỗi.");
                }
            }else{
                return error("Bản ghi không tồn tại.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BackendResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error("Sảy ra lỗi.");
    }
    
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json"+";charset=utf-8")
    public String upload(FormDataMultiPart form){
        if(form!=null){
            FormDataBodyPart file = form.getField("file");
            if(file!=null){
                String fileName = file.getContentDisposition().getFileName();
                if(!fileName.toLowerCase().endsWith(".png")&&!fileName.toLowerCase().endsWith(".jpg")&&!fileName.toLowerCase().endsWith(".jpeg")&&!fileName.toLowerCase().endsWith(".gif"))
                    return error("Tệp tin tải lên không đúng định dạng, định dạng cho phép .jpg, .jpeg, .png, .gif");
                
                InputStream fileInputStream = file.getValueAs(InputStream.class);
                if(copyFile(fileName, fileInputStream)){
                    JSONObject success = new JSONObject();
                    success.put("success", 1);
                    success.put("error", 0);
                    return success.toJSONString();
                }else
                    return error("Không thành công.");
            }
        }
        return error("Sảy ra lỗi.");
    }
    //-----------------------Tỉnh thành - END--------------------------//
    //-----------------------Utils--------------------------//
    private String error(String error_msg){
        JSONObject error = new JSONObject();
        error.put("success", 0);
        error.put("error", 1);
        error.put("error_msg", error_msg);
        return error.toJSONString();
    }
    
    private boolean check(String cTenDangNhap, String cId){
        String sql = "SELECT * FROM quantrivien WHERE LOWER(tendangnhap) = LOWER('"+cTenDangNhap+"') AND id = '"+cId+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                if(rs.getString(rs.findColumn("trangthai")).equals("kichhoat")){
                    if(rs.getString(rs.findColumn("quyen")).equals("master"))
                        return true;
                }
            }
        }catch (SQLException ex){
            return false;
        }
        return false;
    }
    
    private boolean check2(String cTenDangNhap, String cId){
        String sql = "SELECT * FROM quantrivien WHERE LOWER(tendangnhap) = LOWER('"+cTenDangNhap+"') AND id = '"+cId+"';";
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                if(rs.getString(rs.findColumn("trangthai")).equals("kichhoat")){
                    return true;
                }
            }
        }catch (SQLException ex){
            return false;
        }
        return false;
    }
    //sao chep file vao he thong
    public boolean copyFile(String fileName, InputStream in) {
        String destination = context.getRealPath("/")+"\\..\\..\\..\\Backend-NongSanOnline\\build\\web\\resource\\uploads";
        try {
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination+"/"+ fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    //-----------------------Utils - END--------------------------//
}
