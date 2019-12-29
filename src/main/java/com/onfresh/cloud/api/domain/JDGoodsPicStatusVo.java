package com.onfresh.cloud.api.domain;

public class JDGoodsPicStatusVo {
    private String id;
    private Long skuId;
    private Integer isMain;
    private Integer imgType;
    private String sourceImgUrl;
    private Integer skuImgSort;
    private Integer handleStatus;
    private String handleStatusStr;
    private String handleRemark;
    private String handleErrLog;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    public Integer getImgType() {
        return imgType;
    }

    public void setImgType(Integer imgType) {
        this.imgType = imgType;
    }

    public String getSourceImgUrl() {
        return sourceImgUrl;
    }

    public void setSourceImgUrl(String sourceImgUrl) {
        this.sourceImgUrl = sourceImgUrl;
    }

    public Integer getSkuImgSort() {
        return skuImgSort;
    }

    public void setSkuImgSort(Integer skuImgSort) {
        this.skuImgSort = skuImgSort;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getHandleStatusStr() {
        return handleStatusStr;
    }

    public void setHandleStatusStr(String handleStatusStr) {
        this.handleStatusStr = handleStatusStr;
    }

    public String getHandleRemark() {
        return handleRemark;
    }

    public void setHandleRemark(String handleRemark) {
        this.handleRemark = handleRemark;
    }

    public String getHandleErrLog() {
        return handleErrLog;
    }

    public void setHandleErrLog(String handleErrLog) {
        this.handleErrLog = handleErrLog;
    }
}
