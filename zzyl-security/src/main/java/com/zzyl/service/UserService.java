package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.UserDto;
import com.zzyl.vo.UserVo;

import java.util.List;

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

    /**
     * 添加用户
     * @param userDto
     * @return
     */
    void createUser(UserDto userDto);

    /**
     * 用户修改
     * @param userDto
     * @return
     */
    void updateUser(UserDto userDto);

    /**
     * 删除用户
     * @param userIds
     * @return
     */
    void delete(Long userIds);

    /**
     * 启用禁用
     * @param id
     * @param status
     * @return
     */
    void updateStatus(Long id, String status);

    List<UserVo> findUserList(UserDto userDto);
}
