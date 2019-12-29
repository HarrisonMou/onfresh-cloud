package com.onfresh.cloud.api.service.order.vo;

import lombok.Data;

@Data
public class OrderResultResponseVo<T> {
    private Integer code;
    private String msg;
    private T result;
    private String detail;
}
