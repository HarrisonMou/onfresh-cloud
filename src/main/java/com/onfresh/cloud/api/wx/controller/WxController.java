package com.onfresh.cloud.api.wx.controller;

import com.onfresh.cloud.api.vo.ResponseVo;
import com.onfresh.cloud.api.wx.handler.SubscribeHandler;
import com.onfresh.cloud.api.wx.service.WxService;
import io.swagger.annotations.Api;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/wx")
@Api(value = "微信用户")
public class WxController {
    private static Logger logger = LoggerFactory.getLogger(WxController.class);

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxService wxService;

    @Autowired
    private SubscribeHandler subscribeHandler;

    @Autowired
    private WxMpConfigStorage wxMpConfigStorage;

    @GetMapping("/token")
    public ResponseVo<String> getToken() {
        String accessToken = null;
        try {
            accessToken = wxMpService.getAccessToken();
        } catch (WxErrorException e) {
            return ResponseVo.failed(e.getError().getErrorMsg());
        }
        return ResponseVo.success(accessToken);
    }

    @GetMapping("/user/info")
    public ResponseVo<WxMpUser> getUserInfo(@RequestParam String openId){
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = wxMpService.getUserService().userInfo(openId);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResponseVo.success(wxMpUser);
    }

    @ResponseBody
    @GetMapping(value = "/subscribe", produces = "text/plain;charset=utf-8")
    public String authGet(@RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {
        this.logger.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (this.wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }

    @ResponseBody
    @PostMapping(value = "/subscribe", produces = "application/xml; charset=UTF-8")
    public String post(@RequestBody String requestBody, @RequestParam("signature") String signature,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature,
                       @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce) {
        this.logger.info("\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!this.wxService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.wxService.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toXml();
        } else if ("aes".equals(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody,
                    wxMpConfigStorage, timestamp, nonce, msgSignature);
            this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = this.wxService.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toEncryptedXml(this.wxService.getWxMpConfigStorage());
        }

        this.logger.debug("\n组装回复信息：{}", out);

        return out;
    }


    @GetMapping("/user/list")
    public ResponseVo<?> getUserList() {
        WxMpUserList userList = null;
        try {
            userList = wxMpService.getUserService().userList(null);
            for(String openId : userList.getOpenids()){
                WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openId);
                logger.info("save user info from wx, userInfo:{}", wxMpUser);
            }
        } catch (WxErrorException e) {
            return ResponseVo.failed(e.getError().getErrorMsg());
        }
        return ResponseVo.success(userList);
    }

    @GetMapping("/menu")
    public ResponseVo<WxMpMenu> getMenu(){
        WxMpMenu wxMpMenu = null;
        try {
            wxMpMenu = wxMpService.getMenuService().menuGet();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResponseVo.success(wxMpMenu);
    }

    @PutMapping("/menu")
    public ResponseVo<WxMpMenu> createMenu(){
        WxMpMenu wxMpMenu = null;
        try {
            WxMenu wxMenu = new WxMenu();
            wxMpService.getMenuService().menuCreate(wxMenu);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResponseVo.success(wxMpMenu);
    }

    @GetMapping("/msg/send")
    public ResponseVo<?> send() {
        WxMpMassSendResult massResult = null;
        List<String> ids = Arrays.asList("ooYiD1pwtgurm0qx7CMITHdWtYTg", "ooYiD1mCoCuSADUfATznqf-Vy9nw", "ooYiD1tkf7f0Gb-xn28U8fpESrfk", "ooYiD1lVjr-Zuin8moF9uBX_ZFgU");
        ids.forEach(x -> {
            WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder().templateId("qcO70b4X_hVr4UHsP29NQFKHUoJp1ZKmsKjfihB49FA").toUser(x).build();
            wxMpTemplateMessage.addData(new WxMpTemplateData("first", "哈哈哈 测试一个模板", "#173177"))
                    .addData(new WxMpTemplateData("keyword1", "20190705232413", "#173177"))
                    .addData(new WxMpTemplateData("keyword2", "藕榄桥菜市场", "#173177"))
                    .addData(new WxMpTemplateData("keyword3", "测试顾客001", "#173177"))
                    .addData(new WxMpTemplateData("keyword4", "测试地址", "#173177"))
                    .addData(new WxMpTemplateData("keyword5", "￥50.01元", "#173177"))
                    .addData(new WxMpTemplateData("remark", "这是模板的结束语！", "#173177"));
            try {
                wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
            } catch (WxErrorException e) {
                logger.info("oppenId:{}, error msg:{}", x, e.getError().getErrorMsg());
            }
        });
        return ResponseVo.success(massResult);
    }

    @GetMapping(value = "/test/wx/user")
    public ResponseVo<String> getWxUser(String venderId){
        return ResponseVo.success(wxService.getWxUserByVenderId(venderId));
    }
}
