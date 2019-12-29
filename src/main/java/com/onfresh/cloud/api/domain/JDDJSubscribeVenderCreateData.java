package com.onfresh.cloud.api.domain;

import lombok.Data;

@Data
public class JDDJSubscribeVenderCreateData {
    /**
     * 到家门店编号
     */
    private String billId;
    /**
     * 商家门店编号
     */
    private String outBillId;
    /**
     * 消息状态ID(12003:新增门店消息,12004:删除门店消息,12009:修改门店消息 )
     */
    private Long statusId;
    private String timestamp;
}
