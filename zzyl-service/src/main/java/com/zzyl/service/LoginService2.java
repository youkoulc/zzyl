package com.zzyl.service;

import com.zzyl.dto.LoginDto;
import com.zzyl.vo.UserVo;

/**
 * @Description LoginService
 * @Author HeFeng
 * @Date 2024-07-09
 */
public interface LoginService2 {
    UserVo login(LoginDto loginDto);
}
