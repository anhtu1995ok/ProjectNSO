/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.views;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;
import tunt.wa.backend.nongsanonline.models.Anh;
import tunt.wa.backend.nongsanonline.models.MeoVat;
import tunt.wa.backend.nongsanonline.models.SanPham;
import tunt.wa.backend.nongsanonline.utils.FileUploadUtil;
import tunt.wa.backend.nongsanonline.utils.MessageUtil;

/**
 *
 * @author Thanh Tu
 */
@ManagedBean
@ViewScoped
public class MeoVatView implements Serializable{
    private MeoVat meoVat;
    private ArrayList<MeoVat> arrMeoVat;
    private MeoVat selectedMV;
    private int activeIndexTabView;
    ////
    private String tieuDe;
    private String noiDung;
    private String kieu;
    private UploadedFile uploadedFile1, uploadedFile2, uploadedFile3;
    
    /**
     * Creates a new instance of MeoVatView
     */
    public MeoVatView() {
        meoVat = new MeoVat();
        arrMeoVat = meoVat.getArrMeoVat();
    }

    public MeoVat getMeoVat() {
        return meoVat;
    }

    public ArrayList<MeoVat> getArrMeoVat() {
        return arrMeoVat;
    }

    public MeoVat getSelectedMV() {
        return selectedMV;
    }

    public void setMeoVat(MeoVat meoVat) {
        this.meoVat = meoVat;
    }

    public void setArrMeoVat(ArrayList<MeoVat> arrMeoVat) {
        this.arrMeoVat = arrMeoVat;
    }

    public void setSelectedMV(MeoVat selectedMV) {
        update(selectedMV);
        this.selectedMV = selectedMV;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public String getKieu() {
        return kieu;
    }

    public UploadedFile getUploadedFile1() {
        return uploadedFile1;
    }

    public UploadedFile getUploadedFile2() {
        return uploadedFile2;
    }

    public UploadedFile getUploadedFile3() {
        return uploadedFile3;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public void setKieu(String kieu) {
        this.kieu = kieu;
    }

    public void setUploadedFile1(UploadedFile uploadedFile1) {
        this.uploadedFile1 = uploadedFile1;
    }

    public void setUploadedFile2(UploadedFile uploadedFile2) {
        this.uploadedFile2 = uploadedFile2;
    }

    public void setUploadedFile3(UploadedFile uploadedFile3) {
        this.uploadedFile3 = uploadedFile3;
    }

    public int getActiveIndexTabView() {
        return activeIndexTabView;
    }

    public void setActiveIndexTabView(int activeIndexTabView) {
        this.activeIndexTabView = activeIndexTabView;
    }
    
    public void them() throws IOException{
        boolean error = false;
        if(tieuDe==null||noiDung==null||kieu==null){
            activeIndexTabView = 1;
            error = true;
            MessageUtil.addErrorMessage("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        }
        if(tieuDe.isEmpty()||noiDung.isEmpty()||(!kieu.equals("sanxuat")&&!kieu.equals("chebien"))){
            error = true;
            activeIndexTabView = 1;
            MessageUtil.addErrorMessage("Có vẻ như bạn chưa điền đầy đủ thông tin.");
        }
        
        String path = "/resource/uploads/meovat/";
        ArrayList<Anh> arrAnh = new ArrayList<>();
        if(!error){
            if(uploadedFile1!=null){
                if(!uploadedFile1.getFileName().isEmpty()){
                    String fileName1 = System.currentTimeMillis()+uploadedFile1.getFileName();
                    if(FileUploadUtil.copyFile(path, fileName1, uploadedFile1.getInputstream()))
                        arrAnh.add(new Anh(-1, fileName1, -1));
                }
            }
            if(uploadedFile2!=null){
                if(!uploadedFile2.getFileName().isEmpty()){
                    String fileName2 = System.currentTimeMillis()+uploadedFile2.getFileName();
                    if(FileUploadUtil.copyFile(path, fileName2, uploadedFile2.getInputstream()))
                        arrAnh.add(new Anh(-1, fileName2, -1));
                }
            }
            if(uploadedFile3!=null){
                if(!uploadedFile3.getFileName().isEmpty()){
                    String fileName3 = System.currentTimeMillis()+uploadedFile3.getFileName();
                    if(FileUploadUtil.copyFile(path, fileName3, uploadedFile3.getInputstream()))
                        arrAnh.add(new Anh(-1, fileName3, -1));
                }
            }
        }
        if(arrAnh.size()<=0){
            error = true;
            activeIndexTabView = 1;
            MessageUtil.addErrorMessage("Sản phẩm phải có ít nhất 1 ảnh.");
        }
        if(!error){
            MeoVat meoVat = new MeoVat();
            meoVat.setTieuDe(tieuDe);
            meoVat.setNoiDung(noiDung);
            meoVat.setKieu(kieu);
            meoVat.setTrangThai("kichhoat");
            meoVat.setAnh(arrAnh);
            HashMap<String, Object> result = meoVat.them();
            if((int)result.get("success") == 1){
                meoVat.setId((int)result.get("id"));
                meoVat.setAnh((ArrayList<Anh>)result.get("anh"));
                arrMeoVat.add(meoVat);
                MessageUtil.addSuccessMessage("Thêm thành công.");
                reset();
                activeIndexTabView = 0;
            }else{
                activeIndexTabView = 1;
                MessageUtil.addErrorMessage((String)result.get("error_msg"));
            }
        }
    }
      
    public void xoa(){
        if(selectedMV!=null){
            MeoVat meoVat = new MeoVat();
            meoVat.setId(selectedMV.getId());
            meoVat.setAnh(selectedMV.getAnh());
            HashMap<String, Object> result = meoVat.xoa();
            if((int)result.get("success") == 1){
                arrMeoVat.remove(selectedMV);
                reset();
                MessageUtil.addSuccessMessage("Đã xóa.");
            }else{
               MessageUtil.addSuccessMessage((String)result.get("error_msg")); 
            }
        }else{
            MessageUtil.addSuccessMessage("Lỗi hệ thống.");
        }
    }
    
    public void sua(){
        if(selectedMV!=null){
            MeoVat meoVat = new MeoVat();
            meoVat.setId(selectedMV.getId());
            meoVat.setTieuDe(tieuDe);
            meoVat.setNoiDung(noiDung);
            meoVat.setKieu(kieu);
            HashMap<String, Object> result = meoVat.sua();
            if((int)result.get("success") == 1){
                selectedMV.setTieuDe(tieuDe);
                selectedMV.setNoiDung(noiDung);
                selectedMV.setKieu(kieu);
                reset();
                RequestContext rc = RequestContext.getCurrentInstance();
                rc.execute("PF('dialogUpdateItem').hide()");
                MessageUtil.addSuccessMessage("Thay đổi đã được lưu lại.");
            }else{
               MessageUtil.addErrorMessage((String)result.get("error_msg")); 
            }
        }else{
            MessageUtil.addErrorMessage("Lỗi hệ thống"); 
        }
    }
    
    public void thayDoiTrangThai(){
        if(selectedMV!=null){
            MeoVat meoVat = new MeoVat();
            meoVat.setId(selectedMV.getId());
            HashMap<String, Object> result = meoVat.thayDoiTrangThai();
            if((int)result.get("success") == 1){
                selectedMV.setTrangThai((String) result.get("trang_thai"));
                reset();
                MessageUtil.addSuccessMessage("Thao tác thành công.");
            }else{
               MessageUtil.addSuccessMessage((String)result.get("error_msg")); 
            }
        }else{
            MessageUtil.addSuccessMessage("Lỗi hệ thống.");
        }
    }
    
    private void reset(){
        this.tieuDe = null;
        this.kieu = null;
        this.noiDung = null;
        this.selectedMV = null;
    }
    
    private void update(MeoVat meoVat){
        this.tieuDe = meoVat.getTieuDe();
        this.noiDung = meoVat.getNoiDung();
        this.kieu = meoVat.getKieu();
    }
}
