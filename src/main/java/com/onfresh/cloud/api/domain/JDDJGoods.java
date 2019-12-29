package com.onfresh.cloud.api.domain;

public class JDDJGoods {
    private String outSkuId;
    private Long categoryId;
    private Long brandId;
    private String skuName;
    private Long skuPrice;
    private Float weight;
    private String[] images;
    private Long[] shopCategories = new Long[]{4969531L};
    private Integer fixedStatus = 2;
    private Boolean isSale = true;
    private String upcCode;

    public String getOutSkuId() {
        return outSkuId;
    }

    public void setOutSkuId(String outSkuId) {
        this.outSkuId = outSkuId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(Long skuPrice) {
        this.skuPrice = skuPrice;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public Long[] getShopCategories() {
        return shopCategories;
    }

    public void setShopCategories(Long[] shopCategories) {
        this.shopCategories = shopCategories;
    }

    public Integer getFixedStatus() {
        return fixedStatus;
    }

    public void setFixedStatus(Integer fixedStatus) {
        this.fixedStatus = fixedStatus;
    }

    public Boolean getSale() {
        return isSale;
    }

    public void setSale(Boolean sale) {
        isSale = sale;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }
}
