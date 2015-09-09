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
import java.util.logging.Level;
import java.util.logging.Logger;
import tunt.wa.backend.nongsanonline.utils.DBUtil;

/**
 *
 * @author Thanh Tu
 */
public class TinhThanh implements Serializable{
    private int id;
    private String ten;
    private int parentId;
    private ArrayList<TinhThanh> arrTinhThanh;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public TinhThanh() {
        connection = DBUtil.connectSQL();
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        arrTinhThanh = getDSTinhThanh();
    }

    public TinhThanh(int id, String ten, int parentId) {
        this.id = id;
        this.ten = ten;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public int getParentId() {
        return parentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public ArrayList<TinhThanh> getArrTinhThanh() {
        return arrTinhThanh;
    }

    public void setArrTinhThanh(ArrayList<TinhThanh> arrTinhThanh) {
        this.arrTinhThanh = arrTinhThanh;
    }
    
    private ArrayList<TinhThanh> getDSTinhThanh(){
        ArrayList<TinhThanh> data = new ArrayList<>();
        String sql = "SELECT * FROM tinhthanh ORDER BY parent_id ASC;";
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(rs.findColumn("id"));
                String ten = rs.getString(rs.findColumn("ten"));
                int parentId = rs.getInt(rs.findColumn("parent_id"));
                //add san pham vao array
                TinhThanh tt = new TinhThanh(id, ten, parentId);
                data.add(tt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TinhThanh.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
}
