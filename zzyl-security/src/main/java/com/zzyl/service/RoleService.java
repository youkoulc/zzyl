package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.RoleDto;
import com.zzyl.vo.RoleVo;

import java.util.Set;

/**
 * @Description RoleService
 * @Author HeFeng
 * @Date 2024-07-15
 */
public interface RoleService {
    /**
     * 角色分页
     * @param roleDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<RoleVo> getByPage(RoleDto roleDto, Integer pageNum, Integer pageSize);

    /**
     *  创建角色
     * @param roleDto 对象信息
     */
    void createRole(RoleDto roleDto);

    /**
     * 根据角色查询选中的资源数据
     * @param roleId
     * @return
     */
    Set<String> findCheckedResources(Long roleId);
}
