package com.icloud.model.demand;

import java.util.Date;

/**
 * 需求信息扩展表
 * @author leiyi
 *
 */
public class DemandExt {
    private String id;

    private String demandId;

    private String skuUserId;

    private String skuUserName;

    private String skuUserPhone;

    private String skuCompany;

    private String contractPics;

    private String expressCompany;

    private String expressCode;

    private String expressPics;

    private String paymentPics;

    private Date deliveryDeadLine;

    private Date paymentTime;

    private String productPics;

    private String productPrice;

    private String totalPrice;

    private Integer productCount;
    
    private String demandTitle;
    
    private String arrivalPictures;
    
    private String unit;
    
    private String format;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId == null ? null : demandId.trim();
    }

    public String getSkuUserId() {
        return skuUserId;
    }

    public void setSkuUserId(String skuUserId) {
        this.skuUserId = skuUserId == null ? null : skuUserId.trim();
    }

    public String getSkuUserName() {
        return skuUserName;
    }

    public void setSkuUserName(String skuUserName) {
        this.skuUserName = skuUserName == null ? null : skuUserName.trim();
    }

    public String getSkuUserPhone() {
        return skuUserPhone;
    }

    public void setSkuUserPhone(String skuUserPhone) {
        this.skuUserPhone = skuUserPhone == null ? null : skuUserPhone.trim();
    }

    public String getSkuCompany() {
        return skuCompany;
    }

    public void setSkuCompany(String skuCompany) {
        this.skuCompany = skuCompany == null ? null : skuCompany.trim();
    }

    public String getContractPics() {
        return contractPics;
    }

    public void setContractPics(String contractPics) {
        this.contractPics = contractPics == null ? null : contractPics.trim();
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany == null ? null : expressCompany.trim();
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode == null ? null : expressCode.trim();
    }

    public String getExpressPics() {
        return expressPics;
    }

    public void setExpressPics(String expressPics) {
        this.expressPics = expressPics == null ? null : expressPics.trim();
    }

    public String getPaymentPics() {
        return paymentPics;
    }

    public void setPaymentPics(String paymentPics) {
        this.paymentPics = paymentPics == null ? null : paymentPics.trim();
    }

    public Date getDeliveryDeadLine() {
        return deliveryDeadLine;
    }

    public void setDeliveryDeadLine(Date deliveryDeadLine) {
        this.deliveryDeadLine = deliveryDeadLine;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getProductPics() {
        return productPics;
    }

    public void setProductPics(String productPics) {
        this.productPics = productPics == null ? null : productPics.trim();
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice == null ? null : productPrice.trim();
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice == null ? null : totalPrice.trim();
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

	public String getDemandTitle() {
		return demandTitle;
	}

	public void setDemandTitle(String demandTitle) {
		this.demandTitle = demandTitle;
	}

	public String getArrivalPictures() {
		return arrivalPictures;
	}

	public void setArrivalPictures(String arrivalPictures) {
		this.arrivalPictures = arrivalPictures;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
    
	
}