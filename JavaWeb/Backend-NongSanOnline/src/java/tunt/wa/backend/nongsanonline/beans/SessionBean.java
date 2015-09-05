/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thanh Tu
 */
@ManagedBean
@SessionScoped
public class SessionBean implements Serializable{

    public static HttpSession newSession(boolean value) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(value);
        return session;
    }

    public String getUsernameLogin() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String username = (String) session.getAttribute("username");
        return username;
    }
    public String getIdUserLogin() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String id = (String) session.getAttribute("id");
        return id;
    }
}
