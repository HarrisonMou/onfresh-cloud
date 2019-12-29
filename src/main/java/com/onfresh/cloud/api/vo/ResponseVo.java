package com.onfresh.cloud.api.vo;

import org.springframework.data.domain.Page;

public class ResponseVo<T> {
    private Integer code;
    private Integer errno;
    private String errMsg;
    private Boolean isSuccess;
    private T data;

    public ResponseVo(Integer code, Integer errno, String errMsg, Boolean isSuccess, T data) {
        this.code = code;
        this.errMsg = errMsg;
        this.isSuccess = isSuccess;
        this.data = data;
        this.errno = errno;
    }

    public static <A> ResponseVo<A> result(Integer code, Integer errno, String errMsg, Boolean isSuccess, A data){
        return new ResponseVo(code, errno, errMsg, isSuccess, data);
    }

    public static <A> ResponseVo<A> success(A data){
        return result(200, 0, "请求成功", true, data);
    }

    public static ResponseVo pageSuccess(Page page){
        return result(200, 0, "请求成功", true, page);
    }

    public static <A> ResponseVo<A> failed(A data){
        return result(500, -1, "请求失败", false, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }
}
