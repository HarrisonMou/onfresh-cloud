package com.onfresh.cloud.api.domain;

import lombok.Data;

/**
 * 订单创建监听 请求参数实体类
 */
@Data
public class JDDJSubscribeOrderCreateData {
    /**
     * 消息单据ID
     */
    private String billId;
    /**
     * 	消息状态ID（32000 如果商家自动接单，状态含义为等待出库（31000）；如果商家手动接单，状态含义为待处理（41000），接收到消息后，商家需要调用商家确认接单接口，商家确认接单且订单状态变为等待出库（31000）。）
     */
    private String statusId;
}
