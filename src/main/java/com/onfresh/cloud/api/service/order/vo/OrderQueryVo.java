package com.onfresh.cloud.api.service.order.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderQueryVo {
    private Long pageNo;
    private Integer pageSize;
    private Long orderId;
}
