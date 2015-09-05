/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Thanh Tu
 */
@ManagedBean
@ViewScoped
public class UploadFileBean implements Serializable{

    private ExternalContext extContext=FacesContext.getCurrentInstance().getExternalContext();
    private String prefix = "/resource/uploads/";
    private String url;
    private ArrayList<String> images;
    /**
     * Creates a new instance of UploadFileBean
     */
    public UploadFileBean() {
    }

    public String getUrl() {
        return prefix+url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
    
    public void upload(FileUploadEvent event) {  
        images = new ArrayList<>();
        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }  
 
    public void copyFile(String fileName, InputStream in) {
        String destination = extContext.getRealPath("/resource/uploads/");
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
            this.url = fileName;
            images.add(prefix+fileName);
            System.out.println("New file created! Images size:"+images.size());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
