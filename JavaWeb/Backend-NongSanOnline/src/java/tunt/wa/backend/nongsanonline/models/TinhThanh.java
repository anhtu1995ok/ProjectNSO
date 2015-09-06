/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.models;

import java.io.Serializable;

/**
 *
 * @author Thanh Tu
 */
public class TinhThanh implements Serializable{
    private int id;
    private String ten;
    private int parentId;

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
}
