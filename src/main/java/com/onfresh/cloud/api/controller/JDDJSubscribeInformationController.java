package com.onfresh.cloud.api.controller;

import com.google.gson.Gson;
import com.onfresh.cloud.api.domain.JDDJSubscribeInformationData;
import com.onfresh.cloud.api.domain.JDDJSubscribeVenderCreateData;
import com.onfresh.cloud.api.service.bill.ApiOnfreshBillService;
import com.onfresh.cloud.api.service.order.ApiOnfreshOrderService;
import com.onfresh.cloud.api.service.store.ApiOnfreshStoreService;
import o2o.openplatform.sdk.dto.WebRequestDTO;
import o2o.openplatform.sdk.dto.WebResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 京东到家消息回调类
 */
@RestController
@RequestMapping("/api/subscribe/info")
public class JDDJSubscribeInformationController {

    private static Logger logger = LoggerFactory.getLogger(JDDJSubscribeInformationController.class);

    private static Gson gson = new Gson();

    @Autowired
    private ApiOnfreshBillService apiOnfreshBillService;

    @Autowired
    private ApiOnfreshStoreService apiOnfreshStoreService;

    @Autowired
    private ApiOnfreshOrderService apiOnfreshOrderService;

    /**
     * 订单金额拆分完成通知接口
     * @param dto
     * @param request
     * @return
     */
    @PostMapping("/djsw/endOrderFinance")
    public WebResponseDTO<String> subscribeOrderAmountSplit(WebRequestDTO dto, HttpServletRequest request){
        logger.info("request subscribeOrderAmountSplit controller, info:{}, sign:{}", dto, request.getParameter("sign"));
        JDDJSubscribeInformationData subscribeInformationData = gson.fromJson(dto.getJd_param_json(), JDDJSubscribeInformationData.class);
        apiOnfreshBillService.sendOrderInfoSplitWxMsg(subscribeInformationData.getBillId());
        return WebResponseDTO.success("");
    }

    /**
     * 门店创建或修改
     * @param dto
     * @param request
     * @return
     */
    @PostMapping("/djsw/storeCrud")
    public WebResponseDTO<String> subscribeStoreCreate(WebRequestDTO dto, HttpServletRequest request){
        logger.info("request subscribeStore controller, info:{}, sign:{}", dto, request.getParameter("sign"));
        JDDJSubscribeVenderCreateData subscribeVenderCreateData = gson.fromJson(dto.getJd_param_json(), JDDJSubscribeVenderCreateData.class);
//        apiOnfreshStoreService.doVenderCreateOrUpdateSubcribe(subscribeVenderCreateData);
        return WebResponseDTO.success("");
    }

    /**
     * 订单创建
     * @param dto
     * @param request
     * @return
     */
    @PostMapping("/djsw/newOrder")
    public WebResponseDTO<String> subscribeOrderCreate(WebRequestDTO dto, HttpServletRequest request){
        logger.info("request subscribeOrderCreate controller, new order info:{}, sign:{}", dto, request.getParameter("sign"));
        JDDJSubscribeInformationData subscribeInformationData = gson.fromJson(dto.getJd_param_json(), JDDJSubscribeInformationData.class);
        apiOnfreshOrderService.saveNewOrderFromJDDJ(subscribeInformationData.getBillId());
        return WebResponseDTO.success("");
    }

    /**
     * 订单等待出库消息 商家接单后，推送订单等待出库消息。
     * @param dto
     * @param request
     * @return
     */
    @PostMapping(value = "/djsw/orderWaitOutStore")
    public WebResponseDTO<String> subscribeOrderWaitOutStore(WebRequestDTO dto, HttpServletRequest request){
        logger.info("request subscribeOrderWaitOutStore controller, request info:{}", dto);
        JDDJSubscribeInformationData subscribeInformationData = gson.fromJson(dto.getJd_param_json(), JDDJSubscribeInformationData.class);
        apiOnfreshOrderService.orderAutoOutStore(subscribeInformationData.getBillId());
        return WebResponseDTO.success("");
    }

    //---------------------------------------测试请求--------------

    @PostMapping(value = "/test/djsw/endOrderFinance")
    public WebResponseDTO<String> test(Long orderId){
        return WebResponseDTO.success("");
    }

    @PostMapping(value = "/test")
    public WebResponseDTO<String> testWx(Long orderId){
        apiOnfreshBillService.sendOrderInfoSplitWxMsg(922442815000221L);
        return WebResponseDTO.success("");
    }
}
