package com.onfresh.cloud.api.service.order.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JDDJOrder {
    private Long id;
    private Long orderId;
    private Integer srcInnerType;
    private Integer orderType;
    private Integer orderStatus;
    private LocalDateTime orderStatusTime;
    private LocalDateTime orderStartTime;
    private LocalDateTime orderPurchaseTime;
    private Integer orderAgingType;
    private LocalDateTime pickDeadline;
    private LocalDateTime orderCancelTime;
    private String orderCancelRemark;
    private String orgCode;
    private String buyerFullName;
    private String buyerFullAddress;
    private String buyerTelephone;
    private String buyerMobile;
    private String lastFourDigitsOfBuyerMobile;
    private String deliveryStationNo;
    private String deliveryStationNoIsv;
    private String deliveryStationName;
    private String deliveryCarrierNo;
    private String deliveryCarrierName;
    private Integer orderPayType;
    private Integer payChannel;
    private LocalDateTime orderPreStartDeliveryTime;
    private LocalDateTime orderPreEndDeliveryTime;
}
