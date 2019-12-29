package com.onfresh.cloud.api.wx.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WxOrderAdviceTemplateData {
    /**
     * 订单编号
     */
    private Long billId;
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
     * 用户实付货款
     */
    private Long orderTotalMoney;
    /**
     * 平台补贴
     */
    private Long costMoney;
    /**
     * 运费小费
     */
    private Long tips;
    /**
     * 运费优惠
     */
    private Long discount;
    /**
     * 计算金额, 商家营收
     */
    private Long businessMoney;
    /**
     * 计算金额, 章鱼利润
     */
    private Long onfreshMoney;
    /**
     *配送门店编号
     */
    private String deliveryStationNo;

    private String produceStationNo;

    /**
     * 订单级别优惠金额(不含单品促销类优惠金额)
     */
    private Long orderDiscountMoney;
}
