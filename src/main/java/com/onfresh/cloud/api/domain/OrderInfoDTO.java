package com.onfresh.cloud.api.domain;

import lombok.Data;

import java.util.List;

/**
 * 京东到家订单拆分返回实体类（只列举重要信息）
 */
@Data
public class OrderInfoDTO {
    /**
     * 订单主信息
     */
    private OrderMainDTO orderMain;
    /**
     * 订单金额拆分信息
     */
    private List<OassBussinessSkus> oassBussinessSkus;
}
