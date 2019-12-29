package com.onfresh.cloud.api.domain;


public class RequestVo {
    private JDAuthorizeToken taken;
    private String code;

    public RequestVo() {
    }

    public RequestVo(JDAuthorizeToken taken, String code) {
        this.taken = taken;
        this.code = code;
    }

    public JDAuthorizeToken getTaken() {
        return taken;
    }

    public void setTaken(JDAuthorizeToken taken) {
        this.taken = taken;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "RequestVo{" +
                "taken=" + taken +
                ", code='" + code + '\'' +
                '}';
    }
}
