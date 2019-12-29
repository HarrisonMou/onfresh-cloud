package com.onfresh.cloud.api.domain;

import lombok.Data;

/**
 * 京东到家门店信息实体类
 */
@Data
public class JDDJStoreInfo {
    private String venderId;
    private String venderName;
    private String stationName;
    private String stationNo;
    private String outSystemId;
    private String phone;
    private String mobile;
    private String stationAddress;
}
