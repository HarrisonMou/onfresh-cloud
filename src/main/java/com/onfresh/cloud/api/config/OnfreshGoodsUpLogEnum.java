package com.onfresh.cloud.api.config;

public enum OnfreshGoodsUpLogEnum {
    SUCCESS(1, "成功"),
    FAILED(2, "失败"),
    UPDATE_SUCCESS(3, "更新成功"), //商品或图片同步到京东到家失败后更新商品成功
    UPDATE_FAILED(4, "更新失败");  //商品或图片同步到京东到家失败后更新商品失败

    private Integer state;
    private String msg;

    OnfreshGoodsUpLogEnum(Integer state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
