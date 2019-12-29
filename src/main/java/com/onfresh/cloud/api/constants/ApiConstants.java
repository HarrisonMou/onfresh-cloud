package com.onfresh.cloud.api.constants;


/**
 * 调用京东到家API的常量类
 */
public class ApiConstants {
    /**
     * 订单金额拆分完成查询接口
     */
    public static final String BILL_ORDER_SPLIT_QUERY_URL = "https://openapi.jddj.com/djapi/orderInfo/query";

    /**
     * 订单列表查询接口
     */
    public static final String ORDER_LIST_QUERY_URL = "https://openapi.jddj.com/djapi/order/es/query";

    /**
     * 拣货完成且众包配送接口
     */
    public static final String ORDER_JDZB_DELIVERY_URL = "https://openapi.jddj.com/djapi/bm/open/api/order/OrderJDZBDelivery";
}
