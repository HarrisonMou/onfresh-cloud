package com.onfresh.cloud.api.wx.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class WxAccessToken {
    private static Logger logger = LoggerFactory.getLogger(WxAccessToken.class);

    @Autowired
    private RestTemplate restTemplate;

    private static String accessToken;
    private static Long expiresIn;


}
