package com.onfresh.cloud.api.vo;


public class ApiResponseVo<T> extends BaseResponseVo<T> {

    public ApiResponseVo(Integer code, String msg, T data) {
        super(code, msg, data);
    }

    public static <A> ApiResponseVo<A> ok(A data){
        return new ApiResponseVo(0, "success", data);
    }
}
