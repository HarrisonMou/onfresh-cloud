package com.onfresh.cloud.api.domain;

import lombok.Data;

import java.util.List;

/**
 * 订单金额拆分信息
 */
@Data
public class OassBussinessSkus {
    /**
     * 订单级别促销优惠列表
     */
    private List<OrderBussiDiscountMoney> discountlist;
}
