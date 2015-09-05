/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tunt.wa.backend.nongsanonline.beans.CookieBean;


/**
 *
 * @author Thanh Tu
 */
public class DangNhapFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
//        String username = (String) req.getSession().getAttribute("username");
//        String url = req.getRequestURI();
//        if (username != null) {
//            chain.doFilter(req, res);
//        } else {
//            String contextPath = req.getContextPath();
//            res.sendRedirect(contextPath + "/login.xhtml");
//        }
        Cookie loginCookie = CookieBean.getCookie(req, "ten_dang_nhap");
//        Cookie[] cookies = req.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("NSO_username")) {
//                    loginCookie = cookie;
//                    
//        System.err.println("tunt: cookie: "+loginCookie.getMaxAge());
//                    break;
//                }
//            }
//        }
        if (loginCookie != null) {
            chain.doFilter(req, res);
        }else{
            String contextPath = req.getContextPath();
            res.sendRedirect(contextPath + "/login.xhtml");
        }
    }

    @Override
    public void destroy() {
    }
    
}
