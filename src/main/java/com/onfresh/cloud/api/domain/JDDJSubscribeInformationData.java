package com.onfresh.cloud.api.domain;

import lombok.Data;

/**
 * 京东到家 订单金额拆分接口 data部分实体类
 */
@Data
public class JDDJSubscribeInformationData {
    private Long billId;
    private Long statusId;
    private String timestamp;
}
