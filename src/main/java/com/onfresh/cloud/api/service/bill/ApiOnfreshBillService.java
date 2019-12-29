package com.onfresh.cloud.api.service.bill;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onfresh.cloud.api.constants.ApiConstants;
import com.onfresh.cloud.api.domain.OrderBussiDiscountMoney;
import com.onfresh.cloud.api.domain.OrderDiscountDTO;
import com.onfresh.cloud.api.domain.OrderInfoDTO;
import com.onfresh.cloud.api.util.ApiRequestUtil;
import com.onfresh.cloud.api.vo.SingleResponseVo;
import com.onfresh.cloud.api.wx.domain.WxOrderAdviceTemplateData;
import com.onfresh.cloud.api.wx.service.WxService;
import o2o.openplatform.sdk.dto.WebResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 京东到家财务账单类服务
 */
@Service
public class ApiOnfreshBillService {
    private static Logger logger = LoggerFactory.getLogger(ApiOnfreshBillService.class);

    private static Gson gson = new Gson();

    @Autowired
    private ApiRequestUtil apiRequestUtil;

    @Autowired
    private WxService wxService;

    /**
     * 进行金额拆分接口完成监听 处理逻辑
     *
     * @param orderId
     */
    public WxOrderAdviceTemplateData doOrderInfoSplitQuery(Long orderId) {
        Map<String, Long> param = new HashMap<>();
        param.put("orderId", orderId);
        Optional<String> responseStr = apiRequestUtil.baseRequest(ApiConstants.BILL_ORDER_SPLIT_QUERY_URL, gson.toJson(param));
        WxOrderAdviceTemplateData build = null;
        if (responseStr.isPresent()) {
            WebResponseDTO<String> webResponseDTO = gson.fromJson(responseStr.get(), new TypeToken<WebResponseDTO<String>>() {
            }.getType());
            if ("0".equals(webResponseDTO.getCode())) {
                SingleResponseVo<OrderInfoDTO> orderInfoDTO = gson.fromJson(webResponseDTO.getData(), new TypeToken<SingleResponseVo<OrderInfoDTO>>() {
                }.getType());
                if (orderInfoDTO.getCode() == 0L) {
                    OrderInfoDTO orderInfo = orderInfoDTO.getResult();
                    String deliveryStationNo = orderInfo.getOrderMain().getDeliveryStationNo();
                    String buyerFullName = orderInfo.getOrderMain().getBuyerFullName();
                    String buyerFullAddress = orderInfo.getOrderMain().getBuyerFullAddress();
                    String buyerMobile = orderInfo.getOrderMain().getBuyerMobile();
                    String buyerTelephone = orderInfo.getOrderMain().getBuyerTelephone();
                    String deliveryStationName = orderInfo.getOrderMain().getDeliveryStationName();
                    //用户实付货款
                    Long orderTotalMoney = orderInfo.getOrderMain().getOrderTotalMoney();
                    //平台补贴
                    Long costMoney = orderInfo.getOassBussinessSkus().stream().mapToLong(y -> y.getDiscountlist().stream().mapToLong(OrderBussiDiscountMoney::getCostMoney).sum()).sum();
                    //运费小费
                    Long tips = orderInfo.getOrderMain().getOrderExtend().getTips();
                    //运费优惠
                    Long discount = orderInfo.getOrderMain().getOrderDiscountList().stream().filter(y -> y.getDiscountType() == 8).mapToLong(OrderDiscountDTO::getDiscountPrice).sum();
                    Long orderDiscountMoney = orderInfo.getOrderMain().getOrderDiscountMoney();
                    //Long business = (orderTotalMoney + costMoney - orderDiscountMoney) * 0.8 - discount - tips;
                    Long businessMoney = (new BigDecimal(orderTotalMoney).add(new BigDecimal(costMoney)).subtract(new BigDecimal(orderDiscountMoney))).multiply(new BigDecimal(0.8)).subtract(new BigDecimal(discount)).subtract(new BigDecimal(tips)).longValue();
                    Long onfreshMoney = (new BigDecimal(orderTotalMoney).add(new BigDecimal(costMoney)).subtract(new BigDecimal(orderDiscountMoney))).multiply(new BigDecimal(0.08)).longValue();
                    logger.info("商家编码：{}, 商品总价:{}, 平台补贴:{}, 优惠金额:{}, 运费小费:{}, 运费优惠:{}, 商家结算金额:{}, 章鱼利润:{}", deliveryStationNo, orderTotalMoney, costMoney, orderDiscountMoney, tips, discount, businessMoney, onfreshMoney);
                    build = WxOrderAdviceTemplateData.builder()
                            .billId(orderId)
                            .buyerMobile(buyerMobile)
                            .buyerTelephone(buyerTelephone)
                            .buyerFullName(buyerFullName)
                            .buyerFullAddress(buyerFullAddress)
                            .deliveryStationName(deliveryStationName)
                            .businessMoney(businessMoney)
                            .onfreshMoney(onfreshMoney)
                            .deliveryStationNo(deliveryStationNo)
                            .orderTotalMoney(orderTotalMoney)
                            .costMoney(costMoney)
                            .tips(tips)
                            .discount(discount)
                            .orderDiscountMoney(orderDiscountMoney)
                            .build();

                }
            }
        }
        return build;
    }

    /**
     * 订单金额拆分完成后发送微信消息
     *
     * @param orderId
     */
    public void sendOrderInfoSplitWxMsg(Long orderId) {
        try {
            logger.info("do order info split query begin, order id:{}", orderId);
            WxOrderAdviceTemplateData data = doOrderInfoSplitQuery(orderId);
            logger.info("do order info split query end, info:{}", data);
            if (data != null) {
                //发送微信模板消息
                wxService.sendMsg(data);
            }
        } catch (Exception e){
            logger.info("订单金额拆分完成后发送微信消息异常！，exception msg:{}", e.getLocalizedMessage());
        }
    }
}


