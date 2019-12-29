package com.onfresh.cloud.api.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfig {
    @Autowired
    private WxProperties properties;

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId(properties.getAppId());
        config.setSecret(properties.getAppSecret());
        config.setAesKey(properties.getAesKey());
        config.setToken(properties.getToken());
        return config;
    }

    @Bean
    public WxMpService wxMpService(WxMpConfigStorage wxMpConfigStorage){
        WxMpService service = new WxMpServiceImpl();
        service.setWxMpConfigStorage(wxMpConfigStorage);
        return service;
    }
}
