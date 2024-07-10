package com.zzyl.mapper;

import com.zzyl.dto.LoginDto;
import com.zzyl.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description LoginMapper
 * @Author HeFeng
 * @Date 2024-07-09
 */
@Mapper
public interface LoginMapper {

    UserVo selectLogin(LoginDto loginDto);
}
