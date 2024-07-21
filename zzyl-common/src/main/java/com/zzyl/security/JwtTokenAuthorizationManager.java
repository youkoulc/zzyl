package com.zzyl.security;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.zzyl.constant.Constants;
import com.zzyl.constant.UserCacheConstant;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.vo.UserAuth;
import com.zzyl.vo.UserVo;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.util.List;
import java.util.function.Supplier;

/**
 * @Description JwtTokenAuthorizationManager
 * @Author HeFeng
 * @Date 2024-07-21
 */
@Component
@RequiredArgsConstructor
public class JwtTokenAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final JwtTokenManagerProperties jwtTokenManagerProperties;
    private final StringRedisTemplate redisTemplate;
    private AntPathMatcher antPathMatcher =new AntPathMatcher();

    /**
     * Determines if access is granted for a specific authentication and object.
     *
     * @param authentication the {@link Supplier} of the {@link Authentication} to check
     * @param requestAuthorizationContext        the {@link RequestAuthorizationContext} object to check
     * @return an {@link AuthorizationDecision} or null if no decision could be made
     */
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        // 获取token
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        String token = request.getHeader(Constants.ADMIN_TOKEN);
        if (StrUtil.isEmpty(token)) {
            return new AuthorizationDecision(false);
        }

        // 解析token
        Claims claims = JwtUtil.parseJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), token);
        if (ObjectUtil.isEmpty(claims)) {
            return new AuthorizationDecision(false);
        }
        // 获取用户数据
        UserVo userVo = JSONUtil.toBean(claims.get("currentUser").toString(), UserVo.class);
        // 封装成UserAuth，存入Spring Security上下文，代表已认证
        UserAuth userAuth = BeanUtil.toBean(userVo, UserAuth.class);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userAuth, userAuth.getPassword(), userAuth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        // 鉴权
        // 从redis获取当前用户可访问的资源列表
        String key = UserCacheConstant.ACCESS_URLS_CACHE + userVo.getId();
        String accessurlJson = redisTemplate.opsForValue().get(key);
        if (StrUtil.isEmpty(accessurlJson)) {
            return new AuthorizationDecision(false);
        }
        List<String> resourceList = JSONUtil.toList(accessurlJson, String.class);

        // 当前请求路径
        String path = request.getMethod() + request.getRequestURI();
        // 遍历资源列表，检查是否与当前路径匹配，匹配放行
        for (String url : resourceList) {
            if (antPathMatcher.match(url,path)){
                return new AuthorizationDecision(true);
            }
        }

        return new AuthorizationDecision(false);

    }
}
