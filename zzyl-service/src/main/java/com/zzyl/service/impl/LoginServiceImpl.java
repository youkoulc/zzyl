package com.zzyl.service.impl;

import com.zzyl.dto.LoginDto;
import com.zzyl.mapper.LoginMapper;
import com.zzyl.service.LoginService;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description LoginServiceImpl
 * @Author HeFeng
 * @Date 2024-07-09
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public UserVo login(LoginDto loginDto) {
        // 验证账户和密码
       UserVo userVo= loginMapper.selectLogin(loginDto);
        if (userVo != null) {
            Map<String,Object> map = new HashMap<>();

            map.put("username",loginDto.getUsername());
            map.put("userId",userVo.getId());

            userVo.setUserToken(JwtUtil.createJWT("itheima",600000,map));

            UserThreadLocal.set(userVo.getId());
            System.out.println("擦擦擦擦擦擦"+UserThreadLocal.getUserId());
            return userVo;
        }else {

            return null;
        }

    }
}
