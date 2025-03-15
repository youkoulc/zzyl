package com.zzyl.intercept;

import cn.hutool.core.util.StrUtil;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.UserThreadLocal;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description AdminIntercept
 * @Author HeFeng
 * @Date 2024-07-21
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 非控制器方法的处理器放行
        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        String token = request.getHeader("Authorization");
        if (StrUtil.isEmpty(token)){
            throw new BaseException(BasicEnum.LOGIN_LOSE_EFFICACY);
        }

        Claims claims = JwtUtil.parseJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), token);
        String currentUserJson = claims.get("currentUser").toString();

        // 保存用户信息
        UserThreadLocal.setSubject(currentUserJson);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        UserThreadLocal.removeSubject();
    }
}
