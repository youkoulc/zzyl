package com.zzyl.service;

import com.zzyl.dto.ResourceDto;
import com.zzyl.vo.MenuVo;
import com.zzyl.vo.ResourceVo;
import com.zzyl.vo.TreeVo;

import java.util.List;

/**
 * @Description ResourceService
 * @Author HeFeng
 * @Date 2024-07-14
 */
public interface ResourceService {
    /**
     * 获取资源列表
     * @param resourceDto
     * @return
     */
    List<ResourceVo> getResourceList(ResourceDto resourceDto);

    /**
     * 资源树形
     * @param resourceDto
     * @return
     */
    TreeVo resourceTreeVo(ResourceDto resourceDto);

    /**
     * 添加资源：新增菜单和新增按钮
     * @param resourceDto
     * @return
     */
    void createResource(ResourceDto resourceDto);

    /**
     * 资源修改
     * @param resourceDto
     * @return
     */
    void updateResource(ResourceDto resourceDto);

    /**
     * 资源启用禁用
     * @param resourceDto
     * @return
     */
    void updateDateState(ResourceDto resourceDto);

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    void delete(Long menuId);

    /**
     * 动态菜单
     * @return
     */
    List<MenuVo> menus();
}
