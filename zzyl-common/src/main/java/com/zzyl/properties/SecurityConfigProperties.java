package com.zzyl.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 *  忽略配置：这些忽略的url不需要登录也不需要鉴权，不会走授权管理器的逻辑
 *  资源白名单
 *
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "zzyl.framework.security")
@Configuration
public class SecurityConfigProperties {

    // List<String> publicAccessUrls = new ArrayList<>();
    public List<String> publicAccessUrls;
    public List<String> ignoreUrl;

}