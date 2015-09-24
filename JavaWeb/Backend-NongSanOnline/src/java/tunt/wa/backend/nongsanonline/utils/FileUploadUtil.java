/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Thanh Tu
 */
public class FileUploadUtil {
    public static boolean copyFile(String fileName, InputStream in) {
        ExternalContext extContext=FacesContext.getCurrentInstance().getExternalContext();
        String destination = extContext.getRealPath("/resource/uploads/");
        System.out.println("extContext: "+extContext);
        System.out.println("destination: "+destination);
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
    
    public static boolean copyFile(String path, String fileName, InputStream in) {
        ExternalContext extContext=FacesContext.getCurrentInstance().getExternalContext();
        String destination = extContext.getRealPath(path);
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
    
    public static boolean deleteFile(String fileName){
        ExternalContext extContext=FacesContext.getCurrentInstance().getExternalContext();
        String destination = extContext.getRealPath("/resource/uploads/");
        System.out.println("extContext: "+extContext);
        System.out.println("destination: "+destination);
        try {
            File file = new File(destination+"/"+fileName);
            if(file.delete())
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean deleteFile(String path, String fileName){
        ExternalContext extContext=FacesContext.getCurrentInstance().getExternalContext();
        String destination = extContext.getRealPath(path);
        try {
            File file = new File(destination+"/"+fileName);
            if(file.delete())
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }
}
