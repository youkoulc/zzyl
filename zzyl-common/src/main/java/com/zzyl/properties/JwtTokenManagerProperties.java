package com.zzyl.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @Description JwtTokenManagerProperties
 * @Author HeFeng
 * @Date 2024-07-11
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "zzyl.framework.jwt")
public class JwtTokenManagerProperties implements Serializable {
    // 签名秘匙
    private String base64EncodedSecretKey;
    // 有效期
    private Integer ttl;
}
