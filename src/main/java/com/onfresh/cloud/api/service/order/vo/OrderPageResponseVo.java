package com.onfresh.cloud.api.service.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderPageResponseVo<T> {
    private Integer pageNo;
    private Integer pageSize;
    private Integer maxPageSize;
    private Integer totalCount;
    List<T> resultList;
}
