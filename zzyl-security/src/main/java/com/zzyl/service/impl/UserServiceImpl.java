package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.BaseVo;
import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.constant.SuperConstant;
import com.zzyl.dto.UserDto;
import com.zzyl.entity.Role;
import com.zzyl.entity.User;
import com.zzyl.entity.UserRole;
import com.zzyl.mapper.RoleMapper;
import com.zzyl.mapper.UserMapper;
import com.zzyl.mapper.UserRoleMapper;
import com.zzyl.service.UserService;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.vo.RoleVo;
import com.zzyl.vo.UserVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @Autowired
    private UserRoleMapper userRoleMapper;

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

    /**
     * 添加用户
     *
     * @param userDto
     * @return
     */
    @Override
    @Transactional
    public void createUser(UserDto userDto) {
        User user = BeanUtil.toBean(userDto, User.class);
        user.setUsername(user.getEmail());
        user.setNickName(user.getRealName());
        user.setDataState(SuperConstant.DATA_STATE_0);
        //TODO 需要设置默认
        user.setPassword("123456");
        userMapper.insertSelective(user);

        // 添加用户角色关联
        batchInsertUseRole(userDto, user);
    }

    /**
     * 用户修改
     *
     * @param userDto
     * @return
     */
    @Override
    public void updateUser(UserDto userDto) {
        User user = BeanUtil.toBean(userDto, User.class);
        user.setUsername(userDto.getEmail());
        userMapper.updateByPrimaryKeySelective(user);

        // 根据userId删除关联内容
        userRoleMapper.deleteByUserId(user.getId());

        batchInsertUseRole(userDto, user);

    }


    /**
     * 批量添加用户角色关联
     * @param userDto
     * @param user
     */
    private void batchInsertUseRole(UserDto userDto, User user) {
        List<UserRole> userRoleList = userDto.getRoleVoIds().stream().map(roleId -> {
            UserRole userRole = UserRole.builder()
                    .dataState(userDto.getDataState())
                    .roleId(Long.valueOf(roleId))
                    .userId(user.getId()).build();
            return userRole;
        }).collect(Collectors.toList());
        userRoleMapper.batchInsert(userRoleList);
    }

    /**
     * 删除用户
     *
     * @param userIds
     * @return
     */
    @Override
    public void delete(Long userIds) {
        userMapper.deleteByPrimaryKey(userIds);
        userRoleMapper.deleteByUserId(userIds);
    }

    /**
     * 启用禁用
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public void updateStatus(Long id, String status) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setDataState(status);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<UserVo> findUserList(UserDto userDto) {
        List<User> userList =  userMapper.selectList();
        return BeanUtil.copyToList(userList,UserVo.class);
    }
}
