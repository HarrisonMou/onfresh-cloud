package com.onfresh.cloud.api.domain;

import lombok.Data;

import java.util.List;

/**
 * 订单主信息
 */
@Data
public class OrderMainDTO {
    /**
     *配送门店编号
     */
    private String deliveryStationNo;

    private String produceStationNo;
    /**
     *订单商品销售价总金额 单位分
     */
    private Long orderTotalMoney;
    /**
     * 包含需要查询订单优惠List列表
     */
    private List<OrderDiscountDTO> orderDiscountList;
    /**
     * 商品信息
     */
    private OrderExtend orderExtend;
    /**
     *收货人名称
     */
    private String buyerFullName;
    /**
     *收货人电话
     */
    private String buyerTelephone;
    /**
     * 收货人手机号
     */
    private String buyerMobile;
    /**
     *收货人地址
     */
    private String buyerFullAddress;
    /**
     *配送门店名称
     */
    private String deliveryStationName;

    /**
     * 订单级别优惠金额(不含单品促销类优惠金额)
     */
    private Long orderDiscountMoney;
}
