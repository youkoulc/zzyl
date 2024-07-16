package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.BaseVo;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.UserDto;
import com.zzyl.entity.User;
import com.zzyl.mapper.RoleMapper;
import com.zzyl.mapper.UserMapper;
import com.zzyl.service.UserService;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.vo.RoleVo;
import com.zzyl.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description UserServiceImpl
 * @Author HeFeng
 * @Date 2024-07-16
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 用户分页
     *
     * @param userDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResponse<UserVo> getByPage(UserDto userDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        Page<User> page=userMapper.getByPage(userDto);
        PageResponse<UserVo> userVoPageResponse = PageResponse.of(page, UserVo.class);
        if (ObjectUtil.isNotEmpty(userVoPageResponse.getRecords())){
            List<Long> userIdList = userVoPageResponse.getRecords().stream().map(UserVo::getId).collect(Collectors.toList());
            // 查询用户角色
            List<RoleVo> roleVoList = roleMapper.selectRoleByUserId(userIdList);
            // 封装用户角色Ids，用户角色标识
            userVoPageResponse.getRecords().forEach(userVo -> {
                Set<String> roleVoIds = new HashSet<>();
                Set<String> roleLabels = new HashSet<>();
                roleVoList.forEach(roleVo -> {
                    if (userVo.getId().equals(roleVo.getUserId())){
                        roleVoIds.add(String.valueOf(roleVo.getId()));
                        roleLabels.add(roleVo.getRoleName());
                    }
                });
                userVo.setRoleVoIds(roleVoIds);
                userVo.setRoleLabels(roleLabels);
            })  ;
        }
        return userVoPageResponse;
    }
}
