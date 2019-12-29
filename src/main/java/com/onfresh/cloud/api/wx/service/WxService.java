package com.onfresh.cloud.api.wx.service;

import com.onfresh.cloud.api.vo.ResponseVo;
import com.onfresh.cloud.api.wx.constants.WxTemplateIdEnum;
import com.onfresh.cloud.api.wx.domain.WxOrderAdviceTemplateData;
import com.onfresh.cloud.api.wx.handler.SubscribeHandler;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;

@Service
public class WxService extends WxMpServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(WxService.class);

    private final String GET_WX_USER_APPID_URL = "http://www.onfresh.cn/v1/onfresh/wx/getWxUserAppIdByStoreId/{storeId}";

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private SubscribeHandler subscribeHandler;

    private WxMpMessageRouter router;

    @Autowired
    private WxMpConfigStorage wxMpConfigStorage;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        super.setWxMpConfigStorage(wxMpConfigStorage);
        this.refreshRouter();
    }

    private void refreshRouter() {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(this);
        // 关注事件
        newRouter.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.subscribeHandler).end();

        this.router = newRouter;
    }

    public void sendMsg(WxOrderAdviceTemplateData data){
        List<String> openIds = new ArrayList<>();
        //需要获取openIds
//        ResponseEntity<ResponseVo<String>> response = restTemplate.exchange(GET_WX_USER_APPID_URL, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseVo<String>>() {
//        }, data.getDeliveryStationNo());
//        if(HttpStatus.OK.equals(response.getStatusCode())){
//            ResponseVo<String> body = response.getBody();
//            if(StringUtils.isNotEmpty(body.getData()))
//                openIds.add(body.getData());
//        }
        openIds.add("ooYiD1lhVgBnwV34E5JwtAC7NWk8");
        sendMsg(data, openIds);
    }

    public String getWxUserByVenderId(String venderId){
        ResponseEntity<ResponseVo<String>> response = restTemplate.exchange(GET_WX_USER_APPID_URL, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseVo<String>>() {
        }, venderId);
        if(HttpStatus.OK.equals(response.getStatusCode())){
            ResponseVo<String> body = response.getBody();
            if(StringUtils.isNotEmpty(body.getData()))
                return body.getData();
        }
        return null;
    }

    public void sendMsg(WxOrderAdviceTemplateData data, List<String> openIds){
        openIds.forEach(x -> {
            WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder()
                    .templateId(WxTemplateIdEnum.ORDER_COST_MONEY_SPLIT_ADVICE.getTemplateId())
                    .toUser(x)
                    .build();
            String buyerInfo = String.format("%s(%s)", data.getBuyerFullName(), data.getBuyerMobile().split(",")[0]);
            wxMpTemplateMessage.addData(new WxMpTemplateData("first", "章鱼买菜来新订单啦~", "#173177"))
                    .addData(new WxMpTemplateData("keyword1", String.valueOf(data.getBillId()), "#173177"))
                    .addData(new WxMpTemplateData("keyword2", data.getDeliveryStationName(), "#173177"))
                    .addData(new WxMpTemplateData("keyword3", buyerInfo, "#173177"))
                    .addData(new WxMpTemplateData("keyword4", data.getBuyerFullAddress(), "#173177"))
                    .addData(new WxMpTemplateData("keyword5", String.format("￥%s元", BigDecimal.valueOf(data.getBusinessMoney()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)), "#173177"))
                    .addData(new WxMpTemplateData("remark", "15分钟内完成配货，可获得章鱼捡货激励哦，请您尽快完成捡货~", "#173177"));
            try {
                wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
                logger.info("send wx msg success, openid:[{}], info:[{}]", x, wxMpTemplateMessage);
            } catch (WxErrorException e) {
                logger.info("oppenId:{}, error msg:{}", x, e.getError().getErrorMsg());
            }
        });
    }

    public WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return this.router.route(message);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return null;
    }

}
