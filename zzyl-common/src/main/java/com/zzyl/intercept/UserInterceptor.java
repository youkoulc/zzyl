package com.zzyl.intercept;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.zzyl.constant.Constants;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.utils.UserThreadLocal;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description UserInterceptor
 * @Author HeFeng
 * @Date 2024-07-12
 */
@Component
@Slf4j
public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法就放行，比如跨域验证请求、静态资源等不需要身份校验的请求
        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        String token = request.getHeader(Constants.USER_TOKEN);
        log.info("开始解析 customer user token:{}",token);

        if (ObjectUtil.isEmpty(token)){
            throw new BaseException(BasicEnum.LOGIN_LOSE_EFFICACY);
        }

        Claims claims = JwtUtil.parseJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), token);
        if (ObjectUtil.isEmpty(claims)){
            throw new BaseException(BasicEnum.LOGIN_LOSE_EFFICACY);
        }

        Long userId = MapUtil.get(claims, Constants.JWT_USERID, Long.class);
        if (ObjectUtil.isEmpty(userId)){
            throw new BaseException(BasicEnum.LOGIN_LOSE_EFFICACY);
        }

        UserThreadLocal.set(userId);

        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 响应结束，清空，防止数据泄露
        if (UserThreadLocal.get()!=null){
            UserThreadLocal.remove();
        }
    }
}
