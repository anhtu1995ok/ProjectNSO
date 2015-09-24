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
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;
import tunt.wa.backend.nongsanonline.models.Anh;
import tunt.wa.backend.nongsanonline.models.SanPham;
import tunt.wa.backend.nongsanonline.models.TinhThanh;
import tunt.wa.backend.nongsanonline.utils.FileUploadUtil;
import tunt.wa.backend.nongsanonline.utils.MessageUtil;

/**
 *
 * @author Thanh Tu
 */
@ManagedBean
@ViewScoped
public class SanPhamView implements Serializable{
    private SanPham sanPham;
    private ArrayList<SanPham> arrSanPham;
    private SanPham selectedSP;
    private ArrayList<SelectItem> arrTinhThanh;
    private TinhThanh tinhThanh;
    /////////////////////
    private String ten;
    private long gia;
    private String moTa;
    private UploadedFile file1;
    private UploadedFile file2;
    private UploadedFile file3;
    private String diaChi;
    private int tinhThanhId;
    //sua
    private String ngayTao;
    private String ngayCapNhat;
    private String tenTinhThanh;
    private String tenVungMien;
    private String trangThai;
    ///////
    private int activeIndexTabView = 0;
    /**
     * Creates a new instance of SanPhamView
     */
    public SanPhamView() {
        sanPham = new SanPham();
        arrSanPham = sanPham.getArrSanPham();
        tinhThanh = new TinhThanh();
        arrTinhThanh = new ArrayList<SelectItem>();
        ArrayList<TinhThanh> arrTT = tinhThanh.getArrTinhThanh();
        for(int i = 0; i<arrTT.size(); i++){
            TinhThanh gr = arrTT.get(i);
            if(gr.getParentId()==0){
                SelectItemGroup itemGroup = new SelectItemGroup(gr.getTen());
                ArrayList<SelectItem> arrItem = new ArrayList<>();
                for(int j = 0; j<arrTT.size(); j++){
                    TinhThanh item = arrTT.get(j);
                    if(item.getParentId()==gr.getId()){
                        arrItem.add(new SelectItem(item.getId(), item.getTen()));
                    }
                }
                SelectItem[] items = new SelectItem[arrItem.size()];
                arrItem.toArray(items);
                itemGroup.setSelectItems(items);
                arrTinhThanh.add(itemGroup);
            }
        }
    }

    public ArrayList<SanPham> getArrSanPham() {
        return arrSanPham;
    }

    public SanPham getSelectedSP() {
        return selectedSP;
    }

    public void setArrSanPham(ArrayList<SanPham> arrSanPham) {
        this.arrSanPham = arrSanPham;
    }

    public void setSelectedSP(SanPham selectedSP) {
        update(selectedSP);
        this.selectedSP = selectedSP;
    }

    public UploadedFile getFile1() {
        return file1;
    }

    public void setFile1(UploadedFile file1) {
        this.file1 = file1;
    }

    public UploadedFile getFile2() {
        return file2;
    }

    public void setFile2(UploadedFile file2) {
        this.file2 = file2;
    }

    public UploadedFile getFile3() {
        return file3;
    }

    public void setFile3(UploadedFile file3) {
        this.file3 = file3;
    }

    public ArrayList<SelectItem> getArrTinhThanh() {
        return arrTinhThanh;
    }

    public TinhThanh getTinhThanh() {
        return tinhThanh;
    }

    public int getTinhThanhId() {
        return tinhThanhId;
    }

    public void setTinhThanhId(int tinhThanhId) {
        this.tinhThanhId = tinhThanhId;
    }

    public String getTen() {
        return ten;
    }

    public long getGia() {
        return gia;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public String getNgayCapNhat() {
        return ngayCapNhat;
    }

    public String getTenTinhThanh() {
        return tenTinhThanh;
    }

    public String getTenVungMien() {
        return tenVungMien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public void setNgayCapNhat(String ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public void setTenTinhThanh(String tenTinhThanh) {
        this.tenTinhThanh = tenTinhThanh;
    }

    public void setTenVungMien(String tenVungMien) {
        this.tenVungMien = tenVungMien;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    public int getActiveIndexTabView() {
        return activeIndexTabView;
    }

    public void setActiveIndexTabView(int activeIndexTabView) {
        this.activeIndexTabView = activeIndexTabView;
    }
    
    public void them() throws IOException{
        boolean error = false;
        if(ten==null||moTa==null||diaChi==null){
            activeIndexTabView = 1;
            error = true;
            MessageUtil.addErrorMessage("Không thể gửi lên 1 giá trị \"null\", vui lòng kiểm tra lại.");
        }
        if(ten.isEmpty()||moTa.isEmpty()||diaChi.isEmpty()||tinhThanhId<=0||gia<=0){
            error = true;
            activeIndexTabView = 1;
            MessageUtil.addErrorMessage("Có vẻ như bạn chưa điền đầy đủ thông tin.");
        }
        String path = "/resource/uploads/sanpham/";
        ArrayList<Anh> arrAnh = new ArrayList<>();
        if(!error){
            if(file1!=null){
                if(!file1.getFileName().isEmpty()){
                    String fileName1 = System.currentTimeMillis()+file1.getFileName();
                    if(FileUploadUtil.copyFile(path, fileName1, file1.getInputstream()))
                        arrAnh.add(new Anh(-1, fileName1, -1));
                }
            }
            if(file2!=null){
                if(!file2.getFileName().isEmpty()){
                    String fileName2 = System.currentTimeMillis()+file2.getFileName();
                    if(FileUploadUtil.copyFile(path, fileName2, file2.getInputstream()))
                        arrAnh.add(new Anh(-1, fileName2, -1));
                }
            }
            if(file3!=null){
                if(!file3.getFileName().isEmpty()){
                    String fileName3 = System.currentTimeMillis()+file3.getFileName();
                    if(FileUploadUtil.copyFile(path, fileName3, file3.getInputstream()))
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
            SanPham sanPham = new SanPham();
            sanPham.setTen(ten);
            sanPham.setGia(gia);
            sanPham.setDiaChi(diaChi);
            sanPham.setMota(moTa);
            sanPham.setAnh(arrAnh);
            sanPham.setTinhThanh(tinhThanhId);
            HashMap<String, Object> result = sanPham.them();
            if((int)result.get("success") == 1){
                sanPham.setId((int)result.get("id"));
                sanPham.setAnh((ArrayList<Anh>)result.get("anh"));
                sanPham.setTenTinhThanh((String) result.get("ten_tinh_thanh"));
                sanPham.setTenVungMien((String) result.get("ten_vung_mien"));
                sanPham.setNgayCapNhat((String) result.get("ngay_cap_nhat"));
                sanPham.setNgayTao((String) result.get("ngay_tao"));
                sanPham.setTrangThai((String) result.get("trang_thai"));
                arrSanPham.add(sanPham);
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
        if(selectedSP!=null){
            SanPham sanPham = new SanPham();
            sanPham.setId(selectedSP.getId());
            sanPham.setAnh(selectedSP.getAnh());
            HashMap<String, Object> result = sanPham.xoa();
            if((int)result.get("success") == 1){
                arrSanPham.remove(selectedSP);
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
        if(selectedSP!=null){
            SanPham sanPham = new SanPham();
            sanPham.setId(selectedSP.getId());
            sanPham.setTen(ten);
            sanPham.setGia(gia);
            sanPham.setDiaChi(diaChi);
            sanPham.setMota(moTa);
            sanPham.setTinhThanh(tinhThanhId);
            sanPham.setTrangThai(trangThai);
            HashMap<String, Object> result = sanPham.sua();
            if((int)result.get("success") == 1){
                selectedSP.setTen(ten);
                selectedSP.setGia(gia);
                selectedSP.setDiaChi(diaChi);
                selectedSP.setMota(moTa);
                selectedSP.setTinhThanh(tinhThanhId);
                selectedSP.setTrangThai(trangThai);
                selectedSP.setTenTinhThanh((String) result.get("ten_tinh_thanh"));
                selectedSP.setTenVungMien((String) result.get("ten_vung_mien"));
                selectedSP.setNgayCapNhat((String) result.get("ngay_cap_nhat"));
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
    
    private void reset(){
        this.ten = null;
        this.gia = 0;
        this.moTa = null;
        this.file1 = null;
        this.file2 = null;
        this.file3 = null;
        this.diaChi = null;
        this.tinhThanhId = 0;
        this.selectedSP = null;
    }
    
    private void update(SanPham sp){
        this.ten = sp.getTen();
        this.gia = sp.getGia();
        this.moTa = sp.getMota();
        this.diaChi = sp.getDiaChi();
        this.tinhThanhId = sp.getTinhThanh();
        
        this.ngayTao = sp.getNgayTao();
        this.ngayCapNhat = sp.getNgayCapNhat();
        this.tenTinhThanh = sp.getTenTinhThanh();
        this.tenVungMien = sp.getTenVungMien();
        this.trangThai = sp.getTrangThai();
    }
}
