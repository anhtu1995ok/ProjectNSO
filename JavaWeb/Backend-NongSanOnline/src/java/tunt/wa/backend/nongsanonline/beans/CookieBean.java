/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Thanh Tu
 */
@ManagedBean
@ViewScoped
public class CookieBean implements Serializable{
    private static String prefix = "NSO_";
    /**
     * Creates a new instance of CookieBean
     */
    public CookieBean() {
    }
    
    public static Cookie getCookie(String name){
        name = prefix+name;
        Cookie loginCookie = null;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    loginCookie = cookie;
                    break;
                }
            }
        }
        return loginCookie;
    }
    
    public static Cookie getCookie(HttpServletRequest request, String name){
        name = prefix+name;
        Cookie loginCookie = null;
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    loginCookie = cookie;
                    break;
                }
            }
        }
        return loginCookie;
    }
    
    public static Cookie addCookie(String name, String value, int time){
        name = prefix+name;
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(time);
        cookie.setPath("/"); 
        cookie.setHttpOnly(true); 
        response.addCookie(cookie);
        return cookie;
    }
    
    public static void removeCookie(){
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
//                if(cookie.getName().startsWith(prefix)){
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
//                }
            }
        }
    }
    
    public String getUsernameLogin() {
        Cookie loginCookie = getCookie("ten_dang_nhap");
        String username = loginCookie.getValue();
        return username;
    }
    public String getIdUserLogin() {
        Cookie loginCookie = getCookie("id");
        String id = loginCookie.getValue();
        return id;
    }
    
    public String getValueCookie(String name){
        Cookie cookie = getCookie(name);
        String result = cookie.getValue();
        return result;
    }
    
    public static String getValueCookieStatic(String name){
        Cookie cookie = getCookie(name);
        if(cookie!=null)
            return cookie.getValue();
        else
            return null;
    }
}
