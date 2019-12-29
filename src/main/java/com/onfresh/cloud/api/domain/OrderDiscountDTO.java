package com.onfresh.cloud.api.domain;

import lombok.Data;

/**
 * 包含需要查询订单优惠List列表
 */
@Data
public class OrderDiscountDTO {
    /**
     * 优惠类型(1:优惠码;3:优惠劵;4:满减;5:满折;6:首单优惠;7:VIP免运费;8:商家满免运费;10:满件减;11:满件折;12:首单地推满免运费;15:运费券)
     */
    private Integer discountType;
    /**
     * 小优惠类型(优惠码(1:满减;2:立减;3:满折);优惠券(1:满减;2:立减;3:免运费劵;4:运费优惠N元;5:满折);满件减(1206:满件减);满件折(1207:满件折))
     */
    private Integer discountDetailType;
    /**
     * 优惠金额
     */
    private Long discountPrice;
}
