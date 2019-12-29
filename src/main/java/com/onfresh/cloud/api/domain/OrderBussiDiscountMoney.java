package com.onfresh.cloud.api.domain;

import lombok.Data;

/**
 * 订单级别促销优惠
 */
@Data
public class OrderBussiDiscountMoney {
    /**
     * 优惠类型(1:优惠码;3:优惠劵;4:满减;5:满折;6:首单优惠;10:满件减;11:满件折)
     */
    private Integer promotionType;
    /**
     * 小优惠类型(优惠码(1:满减;2:立减;3:满折);优惠券(1:满减;2:立减;5:满折);满件减(1206:满件减);满件折(1207:满件折))
     */
    private Integer promotionDetailType;
    /**
     * 优惠金额
     */
    private Long skuDiscountMoney;
    /**
     * 商家承担比例
     */
    private Integer saleRadio;
    /**
     * 平台承担金额
     */
    private Long costMoney;
    /**
     * 商家承担金额
     */
    private Long saleMoney;
}
