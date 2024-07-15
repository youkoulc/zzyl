package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.constant.SuperConstant;
import com.zzyl.dto.RoleDto;
import com.zzyl.entity.Role;
import com.zzyl.entity.RoleResource;
import com.zzyl.entity.UserRole;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.mapper.RoleMapper;
import com.zzyl.mapper.RoleResourceMapper;
import com.zzyl.mapper.UserRoleMapper;
import com.zzyl.service.RoleService;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description RoleServiceImpl
 * @Author HeFeng
 * @Date 2024-07-15
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

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
        PageHelper.startPage(pageNum, pageSize);
        Page<Role> page = roleMapper.selectByPage(roleDto);
        return PageResponse.of(page, RoleVo.class);
    }

    @Override
    @Transactional
    public void createRole(RoleDto roleDto) {
        // 转换RoleVo为Role
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

    /**
     * 角色修改
     *
     * @param roleDto
     * @return
     */
    @Override
    public void updateRole(RoleDto roleDto) {
        // 1.将roleDto转换role
        Role role = BeanUtil.toBean(roleDto, Role.class);

        // TODO 角色有关联的用户，不允许禁用
        // 根据角色id查找有无关联用户
        Long userId = userRoleMapper.selectByRoleId(role.getId());
        if (Integer.parseInt(role.getDataState())==1&&ObjectUtil.isNotEmpty(userId)) {
            throw new BaseException(BasicEnum.ROLE_BINDING_USER);
        }

        // 3.角色信息直接调用mapper修改
        roleMapper.updateByPrimaryKeySelective(role);
        // 4.关联资源编号数组数据是否有效
        String[] checkedResourceNos = roleDto.getCheckedResourceNos();
        if (ObjectUtil.isNotEmpty(checkedResourceNos)) {
            // 4.1 有,先根据角色id删除角色资源关联数据
            roleResourceMapper.deleteRoleResourceByRoleId(role.getId());
            // 4.2 遍历资源编号数组数据，构建List<RoleResource>
            List<String> checkedResourceNosList = Arrays.asList(checkedResourceNos);
            List<RoleResource> roleResourceList = checkedResourceNosList.stream().map(resourceNo -> {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(role.getId());
                roleResource.setResourceNo(resourceNo);
                roleResource.setDataState(SuperConstant.DATA_STATE_0);
                return roleResource;
            }).collect(Collectors.toList());
            // 4.3 批量插入List<RoleResource>集合数据
            roleResourceMapper.batchInsert(roleResourceList);
        }
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @Transactional
    @Override
    public int deleteRoleById(Long roleId) {
        // 删除角色与菜单关联
        roleResourceMapper.deleteRoleResourceByRoleId(roleId);
        // 再删除角色数据
        return roleMapper.deleteByPrimaryKey(roleId);
    }
}
