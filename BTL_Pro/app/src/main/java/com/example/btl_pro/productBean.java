package com.example.btl_pro;

public class productBean {
    public Integer productId;
    public String namePro;
    public String namePer;
    public String phone;
    public Integer cost;
    public String srcImage;
    public boolean status;

    public productBean() {
        // empty
    }

    public productBean(Integer productId, String namePro, String namePer, String phone, Integer cost, String srcImage, boolean status) {
        this.productId = productId;
        this.namePro = namePro;
        this.namePer = namePer;
        this.phone = phone;
        this.cost = cost;
        this.srcImage = srcImage;
        this.status = status;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getNamePro() {
        return namePro;
    }

    public void setNamePro(String namePro) {
        this.namePro = namePro;
    }

    public String getNamePer() {
        return namePer;
    }

    public void setNamePer(String namePer) {
        this.namePer = namePer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSrcImage() {
        return srcImage;
    }

    public void setSrcImage(String srcImage) {
        this.srcImage = srcImage;
    }
}
