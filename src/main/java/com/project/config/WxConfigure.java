package com.project.config;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfigure {


    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;
    @Value("${wx.token}")
    private String token;
    @Bean
    public  WxMpServiceImpl wxMpService(){
        WxMpInMemoryConfigStorage storage =new WxMpInMemoryConfigStorage();
        storage.setAppId(appid);
        storage.setSecret(secret);
        storage.setToken(token);
        WxMpServiceImpl wxMpService =new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(storage);
        return wxMpService;
    }


}
