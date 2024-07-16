package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.UserDto;
import com.zzyl.vo.UserVo;

/**
 * @Description UserService
 * @Author HeFeng
 * @Date 2024-07-16
 */
public interface UserService {
    /**
     * 用户分页
     * @param userDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<UserVo> getByPage(UserDto userDto, Integer pageNum, Integer pageSize);
}
