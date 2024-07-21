package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zzyl.constant.SuperConstant;
import com.zzyl.entity.User;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.mapper.UserMapper;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.vo.UserAuth;
import com.zzyl.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Description UserDetailsServiceImpl
 * @Author HeFeng
 * @Date 2024-07-21
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUserForLogin(username);

        // user为空时（密码不匹配或未创建）、被禁用时，提示异常
        if (ObjectUtil.isEmpty(user)||user.getDataState().equals(SuperConstant.DATA_STATE_1)){
            throw new BaseException(BasicEnum.LOGIN_FAIL);
        }
        return BeanUtil.toBean(user, UserAuth.class);
    }
}
