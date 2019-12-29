package com.onfresh.cloud.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.onfresh.cloud.api.config.JDDJBaseConfig;
import com.onfresh.cloud.api.config.JDDJTokenConfig;
import com.onfresh.cloud.api.exception.BaseException;
import com.onfresh.cloud.api.vo.BaseResponseVo;
import o2o.openplatform.sdk.dto.WebRequestDTO;
import o2o.openplatform.sdk.util.HttpUtil;
import o2o.openplatform.sdk.util.SignUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ApiRequestUtil extends JDDJBaseConfig {

    private static Logger logger = LoggerFactory.getLogger(ApiRequestUtil.class);

    public Optional<String> baseRequest(String requestUrl, String paramJson){
        String timestamp = LocalDateTime.now().toString();
        WebRequestDTO webReqeustDTO = new WebRequestDTO();
        webReqeustDTO.setApp_key(appKey);
        webReqeustDTO.setFormat(format);
        webReqeustDTO.setTimestamp(timestamp);
        webReqeustDTO.setToken(JDDJTokenConfig.token);
        webReqeustDTO.setV(v);
        webReqeustDTO.setJd_param_json(paramJson);
        String sign = null;
        try {
            sign = SignUtils.getSignByMD5(webReqeustDTO, appSecret);
        } catch (Exception e){
            logger.error("jddj api request sign create fail");
        }
        Map<String, Object> param = new HashMap();
        param.put("token", JDDJTokenConfig.token);
        param.put("app_key", appKey);
        param.put("timestamp", timestamp);
        param.put("sign", sign);
        param.put("format", format);
        param.put("v", v);
        param.put("jd_param_json", paramJson);
        try {
            return Optional.ofNullable(HttpUtil.sendSimplePostRequest(requestUrl, param));
        } catch (Exception e){
            throw new BaseException(e.getMessage());
        }
    }

    public BaseResponseVo<String> parseBaseResponseVo(String requestUrl, String paramJson){
        return JSON.parseObject(baseRequest(requestUrl, paramJson).get(), new TypeReference<BaseResponseVo<String>>(){});
    }

    public static void main(String[] args) throws Exception{
        // 应用授权信息
        String appKey = "7fd1c34598924181b3ba295b41c63507";
        String appSecret = "a7182e7f06274e4ebcbb0c64213fcfa7";
        String token = "2f3da4db-a0d4-40a8-bf4e-22007b5603d5";
        String format = "json";
        String v = "1.0";
        String timestamp = "2016-08-08 12:00:00";
        String jd_param_json = "{\"pageNo\":\"1\",\"pageSize\":\"20\",\"orderStartTime_begin\":\"2016-01-01 00:00:00\",\"orderStartTime_end\":\"2016-08-08 00:00:00\", \"orderStatus\":\"90000\"}";
        String url = "https://openo2o.jd.com/djapi/order/es/query";

        // 计算签名实体
        WebRequestDTO webReqeustDTO = new WebRequestDTO();
        webReqeustDTO.setApp_key(appKey);
        webReqeustDTO.setFormat(format);
        webReqeustDTO.setJd_param_json(jd_param_json);
        webReqeustDTO.setTimestamp(timestamp);
        webReqeustDTO.setToken(token);
        webReqeustDTO.setV(v);

        String sign = SignUtils.getSignByMD5(webReqeustDTO, appSecret);
        System.out.println("md5 sign:" + sign);

        // 请求参数实体
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("token", token);
        param.put("app_key", appKey);
        param.put("timestamp", timestamp);
        param.put("sign", sign);
        param.put("format", format);
        param.put("v", v);
        param.put("jd_param_json", jd_param_json);

        String result = HttpUtil.sendSimplePostRequest(url, param);
        System.out.println("result:" + result);
    }

}
