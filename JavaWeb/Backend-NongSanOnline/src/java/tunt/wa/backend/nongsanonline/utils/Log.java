/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.utils;

/**
 *
 * @author Thanh Tu
 */
public class Log {
    public static void d(Class c, String tag, String value){
        System.out.println(c.getName()+"-[" + tag + "] "+value);
    }
    public static void e(Class c, String tag, String value){
        System.err.println(c.getName()+"[" + tag + "] "+value);
    }
}
