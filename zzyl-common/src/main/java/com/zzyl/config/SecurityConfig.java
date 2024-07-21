package com.zzyl.config;

import com.zzyl.properties.SecurityConfigProperties;
import com.zzyl.security.JwtTokenAuthorizationManager;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/**
 * @Description 权限核心配置类
 * @Author HeFeng
 * @Date 2024-07-21
 */
@Configuration
@EnableConfigurationProperties(SecurityConfigProperties.class)
public class SecurityConfig {
    @Autowired
    private JwtTokenAuthorizationManager jwtTokenAuthorizationManager;
    @Autowired
    private SecurityConfigProperties securityConfigProperties;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        List<String> ignoreUrl = securityConfigProperties.getIgnoreUrl();
        String[] ignoreUrlArray = ignoreUrl.toArray(new String[ignoreUrl.size()]);

        httpSecurity.authorizeHttpRequests()
                .antMatchers(ignoreUrlArray)
                .permitAll()
                .anyRequest().access(jwtTokenAuthorizationManager)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 关闭session
                .and().headers().cacheControl().disable()// 关闭缓存
                .and().csrf().disable();

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
