package com.onfresh.cloud.api.vo;

import lombok.Data;

@Data
public class DetailResponseVo<T> {
    private Long code;
    private String msg;
    private T detail;
}
