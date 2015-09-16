/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import tunt.wa.backend.nongsanonline.models.MuaBan;
import tunt.wa.backend.nongsanonline.utils.MessageUtil;

/**
 *
 * @author Thanh Tu
 */
@ManagedBean
@ViewScoped
public class MuaBanView implements Serializable{
    private MuaBan muaBan;
    private ArrayList<MuaBan> arrCanDuyet;
    private ArrayList<MuaBan> arrChapNhan;
    private ArrayList<MuaBan> arrTuChoi;
    private MuaBan selectedMB;
    
    /**
     * Creates a new instance of MuaBanView
     */
    public MuaBanView() {
        muaBan = new MuaBan();
        arrCanDuyet  = muaBan.getArrCanDuyet();
        arrChapNhan = muaBan.getArrChapNhan();
        arrTuChoi = muaBan.getArrTuChoi();
    }

    public MuaBan getMuaBan() {
        return muaBan;
    }

    public ArrayList<MuaBan> getArrCanDuyet() {
        return arrCanDuyet;
    }

    public MuaBan getSelectedMB() {
        return selectedMB;
    }

    public void setMuaBan(MuaBan muaBan) {
        this.muaBan = muaBan;
    }

    public void setArrCanDuyet(ArrayList<MuaBan> arrCanDuyet) {
        this.arrCanDuyet = arrCanDuyet;
    }

    public void setSelectedMB(MuaBan selectedMB) {
        this.selectedMB = selectedMB;
    }

    public ArrayList<MuaBan> getArrChapNhan() {
        return arrChapNhan;
    }

    public ArrayList<MuaBan> getArrTuChoi() {
        return arrTuChoi;
    }

    public void setArrChapNhan(ArrayList<MuaBan> arrChapNhan) {
        this.arrChapNhan = arrChapNhan;
    }

    public void setArrTuChoi(ArrayList<MuaBan> arrTuChoi) {
        this.arrTuChoi = arrTuChoi;
    }
    
    public void kiemDuyet(MuaBan muaBan, String kiemDuyet){
        selectedMB = muaBan;
        if(selectedMB!=null){
            MuaBan mb = new MuaBan();
            mb.setId(selectedMB.getId());
            HashMap result = mb.duyet(kiemDuyet);
            if((int) result.get("success")==1){
                selectedMB.setKiemDuyet(kiemDuyet);
                if(kiemDuyet.equals("chapnhan")){
                    arrChapNhan.add(selectedMB);
                }else{
                    arrTuChoi.add(selectedMB);
                }
                arrCanDuyet.remove(selectedMB);
                reset();
                MessageUtil.addSuccessMessage("Thao tác thành công.");
            }else{
                MessageUtil.addErrorMessage((String) result.get("error_msg"));
            }
        }else{
            MessageUtil.addErrorMessage("Lỗi hệ thống.");
        }
    }
    
    private void reset(){
        selectedMB = null;
    }
}
