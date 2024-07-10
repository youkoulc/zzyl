package com.zzyl.intercept;

import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.UserThreadLocal;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description TokenInterceptor
 * @Author HeFeng
 * @Date 2024-07-10
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取Token
        String token = request.getHeader("Authorization");

        if (token == null) {
            response.setStatus(401);
            return false;
        }

        Claims claims;
        try {
             claims = JwtUtil.parseJWT("itheima", token);
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }

        UserThreadLocal.set((Long) claims.get("userId"));
        return true;

    }
}
