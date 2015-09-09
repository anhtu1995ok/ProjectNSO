/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.utils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import java.io.File;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author Thanh Tu
 */
public class WebserviceUtil {
    public static String SERVICE_URL = "http://localhost:8084/Webservice-NongSanOnline/webresources/backend/";
    private String name;
    private File file;
    private MultivaluedMap params;
    private String path;
    
    public WebserviceUtil() {
        
    }
    
    public String post(String path, MultivaluedMap params){
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(SERVICE_URL).path(path);
            ClientResponse response;
            if(params!=null&&file!=null&&name!=null){
                FormDataMultiPart multiPart = new FormDataMultiPart();
                multiPart.bodyPart(new FileDataBodyPart(name, file, MediaType.APPLICATION_OCTET_STREAM_TYPE));
                response = webResource.queryParams(params).type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, multiPart);
            }else if(params!=null){
                response = webResource.queryParams(params).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                   .post(ClientResponse.class);
            }else if(file!=null&&name!=null){
                FormDataMultiPart multiPart = new FormDataMultiPart();
                multiPart.bodyPart(new FileDataBodyPart(name, file, MediaType.APPLICATION_OCTET_STREAM_TYPE));
                response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, multiPart);
            }else{
                return null;
            }
            return response.getEntity(String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String post(){
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(SERVICE_URL).path(path);
            ClientResponse response;
            if(this.params!=null&&this.file!=null&&this.name!=null){
                FormDataMultiPart multiPart = new FormDataMultiPart();
                multiPart.bodyPart(new FileDataBodyPart(this.name, this.file, MediaType.APPLICATION_OCTET_STREAM_TYPE));
                response = webResource.queryParams(this.params).type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, multiPart);
            }else if(this.params!=null){
                response = webResource.queryParams(this.params).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                   .post(ClientResponse.class);
            }else if(this.file!=null&&this.name!=null){
                FormDataMultiPart multiPart = new FormDataMultiPart();
                multiPart.bodyPart(new FileDataBodyPart(this.name, this.file, MediaType.APPLICATION_OCTET_STREAM_TYPE));
                response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, multiPart);
            }else{
                return null;
            }
            return response.getEntity(String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String get(String path){
        try{
            Client client = Client.create();
            WebResource webResource = client.resource(SERVICE_URL).path(path);
            ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
            return response.getEntity(String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void setFileParam(String name, File file){
        this.name = name;
        this.file = file;
    }

    public void setParams(MultivaluedMap params) {
        this.params = params;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
}
