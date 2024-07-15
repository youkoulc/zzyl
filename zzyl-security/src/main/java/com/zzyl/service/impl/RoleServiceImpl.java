package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.RoleDto;
import com.zzyl.entity.Role;
import com.zzyl.mapper.RoleMapper;
import com.zzyl.service.RoleService;
import com.zzyl.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @Description RoleServiceImpl
 * @Author HeFeng
 * @Date 2024-07-15
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 角色分页
     *
     * @param roleDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResponse<RoleVo> getByPage(RoleDto roleDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<Role> page=roleMapper.selectByPage(roleDto);
        return PageResponse.of(page,RoleVo.class);
    }
    @Override
    @Transactional
    public void createRole(RoleDto roleDto) {
        //转换RoleVo为Role
        Role role = BeanUtil.toBean(roleDto, Role.class);
        roleMapper.insert(role);
    }

    /**
     * 根据角色查询选中的资源数据
     *
     * @param roleId
     * @return
     */
    @Override
    public Set<String> findCheckedResources(Long roleId) {
        return roleMapper.selectResourceNoByRoleId(roleId);
    }
}
