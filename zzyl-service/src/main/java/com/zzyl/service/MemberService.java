package com.zzyl.service;

import com.zzyl.dto.UserLoginRequestDto;
import com.zzyl.vo.LoginVo;

/**
 * @Description MemberService
 * @Author HeFeng
 * @Date 2024-07-11
 */
public interface MemberService {
    /**
     * 小程序微信登录
     * @param userLoginRequestDto
     * @return
     */
    LoginVo login(UserLoginRequestDto userLoginRequestDto);
}
