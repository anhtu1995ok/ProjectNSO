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
import org.primefaces.event.FileUploadEvent;
import tunt.wa.backend.nongsanonline.models.Anh;
import tunt.wa.backend.nongsanonline.models.TinTuc;
import tunt.wa.backend.nongsanonline.utils.FileUploadUtil;
import tunt.wa.backend.nongsanonline.utils.MessageUtil;

/**
 *
 * @author Thanh Tu
 */
@ManagedBean
@ViewScoped
public class TinTucView implements Serializable{
    private TinTuc tinTuc;
    private ArrayList<TinTuc> arrTinTuc;
    private ArrayList<Anh> arrAnh;
    private TinTuc selectedTT;
    /////////////
    private String tieuDe;
    private String noiDung;
    private String moTa;
    /////////////
    private int activeIndexTabView = 0;
    /**
     * Creates a new instance of TinTucView
     */
    public TinTucView() {
        tinTuc = new TinTuc();
        arrTinTuc = tinTuc.getArrTinTuc();
        arrAnh = tinTuc.getArrAnh();
    }

    public TinTuc getTinTuc() {
        return tinTuc;
    }

    public ArrayList<TinTuc> getArrTinTuc() {
        return arrTinTuc;
    }

    public TinTuc getSelectedTT() {
        return selectedTT;
    }

    public void setTinTuc(TinTuc tinTuc) {
        this.tinTuc = tinTuc;
    }

    public void setArrTinTuc(ArrayList<TinTuc> arrTinTuc) {
        this.arrTinTuc = arrTinTuc;
    }

    public void setSelectedTT(TinTuc selectedTT) {
        update(selectedTT);
        this.selectedTT = selectedTT;
    }

    public int getActiveIndexTabView() {
        return activeIndexTabView;
    }

    public void setActiveIndexTabView(int activeIndexTabView) {
        this.activeIndexTabView = activeIndexTabView;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public ArrayList<Anh> getArrAnh() {
        return arrAnh;
    }

    public void setArrAnh(ArrayList<Anh> arrAnh) {
        this.arrAnh = arrAnh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    public void them(){
        TinTuc tinTuc = new TinTuc();
        tinTuc.setTieuDe(tieuDe);
        tinTuc.setNoiDung(noiDung);
        tinTuc.setMoTa(moTa);
        HashMap<String, Object> result = tinTuc.them();
        if((int) result.get("success") == 1){
            tinTuc.setId((int)result.get("id"));
            tinTuc.setTrangThai("kichhoat");
            tinTuc.setNgayTao((String)result.get("ngay_tao"));
            tinTuc.setMoTa((String)result.get("mo_ta"));
            arrTinTuc.add(tinTuc);
            reset();
            MessageUtil.addSuccessMessage("Đã thêm.");
            activeIndexTabView = 0;
        }else{
            MessageUtil.addSuccessMessage((String)result.get("error_msg"));
        }
    }
    
    public void sua(){
        if(selectedTT!=null){
            TinTuc tinTuc = new TinTuc();
            tinTuc.setId(selectedTT.getId());
            tinTuc.setTieuDe(tieuDe);
            tinTuc.setNoiDung(noiDung);
            tinTuc.setMoTa(moTa);
            HashMap<String, Object> result = tinTuc.sua();
            if((int) result.get("success") == 1){
                selectedTT.setTieuDe(tieuDe);
                selectedTT.setNoiDung(noiDung);
                selectedTT.setMoTa(moTa);
                reset();
                RequestContext rc = RequestContext.getCurrentInstance();
                rc.execute("PF('dialogUpdateItem').hide()");
                MessageUtil.addSuccessMessage("Thay đổi đã được lưu lại.");
            }else{
                MessageUtil.addSuccessMessage((String)result.get("error_msg"));
            }
        }else{
            MessageUtil.addSuccessMessage("Lỗi hệ thống.");
        }
    }
    
    public void thayDoiTrangThai(){
        if(selectedTT!=null){
            TinTuc tinTuc = new TinTuc();
            tinTuc.setId(selectedTT.getId());
            HashMap<String, Object> result = tinTuc.thayDoiTrangThai();
            if((int) result.get("success") == 1){
                selectedTT.setTrangThai((String)result.get("trang_thai"));
                reset();
                MessageUtil.addSuccessMessage("Thay đổi thành công.");
            }else{
                MessageUtil.addSuccessMessage((String)result.get("error_msg"));
            }
        }else{
            MessageUtil.addSuccessMessage("Lỗi hệ thống.");
        }
    }
    
    public void xoa(){
        if(selectedTT!=null){
            TinTuc tinTuc = new TinTuc();
            tinTuc.setId(selectedTT.getId());
            HashMap<String, Object> result = tinTuc.xoa();
            if((int) result.get("success") == 1){
                arrTinTuc.remove(selectedTT);
                reset();
                MessageUtil.addSuccessMessage("Đã xóa.");
            }else{
                MessageUtil.addSuccessMessage((String)result.get("error_msg"));
            }
        }else{
            MessageUtil.addSuccessMessage("Lỗi hệ thống.");
        }
    }
    
    public void taiLen(FileUploadEvent event){
        try {
            if(!event.getFile().getFileName().isEmpty()){
                String fileName = System.currentTimeMillis()+event.getFile().getFileName();
                if(FileUploadUtil.copyFile("/resource/uploads/tintuc/", fileName, event.getFile().getInputstream())){
                    Anh anh = new Anh(-1, fileName, -1);
                    HashMap<String, Object> result = tinTuc.themAnh(anh);
                    if((int)result.get("success")==1){
                        MessageUtil.addSuccessMessage(event.getFile().getFileName()+" đã được tải lên.");
                        anh.setId((int)result.get("id"));
                        arrAnh.add(0, anh);
                    }else{
                        MessageUtil.addErrorMessage(event.getFile().getFileName()+" tải lên không thành công.");
                    }
                }else{
                    MessageUtil.addErrorMessage("Sảy ra lỗi.");
                }
            }else{
                MessageUtil.addErrorMessage("Sảy ra lỗi.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void reset(){
        this.tieuDe = null;
        this.noiDung = null;
        this.moTa = null;
        this.selectedTT = null;
    }
    
    private void update(TinTuc tinTuc){
        this.tieuDe = tinTuc.getTieuDe();
        this.noiDung = tinTuc.getNoiDung();
        this.moTa = tinTuc.getMoTa();
    }
}
