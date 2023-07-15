package com.siukatech.poc.react.backend.parent.global.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "app")
//@ConfigurationProperties
public class ParentAppConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Bean(name = "securityRestTemplate")
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    private String hostName;
    //    private App app;
    private Api api;

    public String getMyUserInfoUrl() {
        String myUserInfoUrl = null;
        if ( StringUtils.isNotEmpty(this.getHostName())
                && (this.getApi() != null && StringUtils.isNotEmpty(this.getApi().getMyUserInfo())) ) {
            myUserInfoUrl = this.getHostName() + this.getApi().getMyUserInfo();
        }
        logger.debug("getMyUserInfoUrl - getHostName: [{}], getMyUserInfo: [{}], myUserInfoUrl: [{}]"
                , this.getHostName()
                , (this.getApi() == null ? "NULL" : this.getApi().getMyUserInfo())
                , myUserInfoUrl
        );
        return myUserInfoUrl;
//        return null;
    }

    public String getMyKeyInfoUrl() {
        String myKeyInfoUrl = null;
        if ( StringUtils.isNotEmpty(this.getHostName())
                && (this.getApi() != null && StringUtils.isNotEmpty(this.getApi().getMyKeyInfo())) ) {
            myKeyInfoUrl = this.getHostName() + this.getApi().getMyKeyInfo();
        }
        logger.debug("getMyKeyInfoUrl - getHostName: [{}], getMyUserInfo: [{}], myKeyInfoUrl: [{}]"
                , this.getHostName()
                , (this.getApi() == null ? "NULL" : this.getApi().getMyKeyInfo())
                , myKeyInfoUrl
        );
        return myKeyInfoUrl;
    }

//    @Data
//    public static class App {
//        private String hostName;
//        private Api api;
//    }

    @Data
    public static class Api {
        private String myUserInfo;
        private String myKeyInfo;
    }
}
