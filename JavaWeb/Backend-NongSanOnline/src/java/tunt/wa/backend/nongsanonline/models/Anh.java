/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.models;

/**
 *
 * @author Thanh Tu
 */
public class Anh {
    private int id;
    private String url;
    private int parentId;

    public Anh(int id, String url, int parentId) {
        this.id = id;
        this.url = url;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        if(url.equals("default"))
            return "/resource/images/no_ava_boy.jpg";
        else
            return "/resource/uploads/"+url;
    }
    
    public String getAddUrl() {
        return url;
    }

    public int getParentId() {
        return parentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
    
}
