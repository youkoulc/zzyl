package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.json.JSONUtil;
import com.zzyl.constant.UserCacheConstant;
import com.zzyl.dto.LoginDto;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.mapper.ResourceMapper;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.properties.SecurityConfigProperties;
import com.zzyl.service.LoginService;
import com.zzyl.utils.JwtUtil;
import com.zzyl.vo.UserAuth;
import com.zzyl.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description LoginServiceImpl
 * @Author HeFeng
 * @Date 2024-07-21
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenManagerProperties jwtTokenManagerProperties;
    private final SecurityConfigProperties securityConfigProperties;
    private final ResourceMapper resourceMapper;
    private final StringRedisTemplate redisTemplate;

    /**
     * 后台用户登录
     *
     * @param loginDto
     * @return
     */
    @Override
    public UserVo login(LoginDto loginDto) {

        // 激活认证流程
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // 认证失败，返回异常
        if (!authenticate.isAuthenticated()){
            throw new BaseException(BasicEnum.LOGIN_FAIL);
        }

        // 认证成功，获取认证对象
        UserAuth userAuth = (UserAuth) authenticate.getPrincipal();
        UserVo userVo = BeanUtil.toBean(userAuth, UserVo.class);

        // TODO 敏感数据处理
        userVo.setPassword("");

        // 定义载荷存储登录数据
        Map<String, Object> clamis = new HashMap<>();
        clamis.put("currentUser", JSONUtil.toJsonStr(userVo));
        // 封装载荷，生成token
        String token = JwtUtil.createJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), jwtTokenManagerProperties.getTtl(), clamis);

        userVo.setUserToken(token);

        // 获取用户的资源路径列表
        List<String> resourcePathList = resourceMapper.findResourcePathListByUserId(userAuth.getId());
        // 获取白名单
        List<String> publicAccessUrls = securityConfigProperties.getPublicAccessUrls();

        // 合并两者，作为用户可访问的完整资源列表
        resourcePathList.addAll(publicAccessUrls);

        // 存入redis，过期时间设为token的
        String key= UserCacheConstant.ACCESS_URLS_CACHE+userAuth.getId();
        redisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(resourcePathList),jwtTokenManagerProperties.getTtl(), TimeUnit.MILLISECONDS);



        return userVo;
    }
}
