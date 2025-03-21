package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.zzyl.constant.CacheConstant;
import com.zzyl.constant.SuperConstant;
import com.zzyl.dto.ResourceDto;
import com.zzyl.entity.Resource;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.mapper.ResourceMapper;
import com.zzyl.mapper.RoleResourceMapper;
import com.zzyl.service.ResourceService;
import com.zzyl.utils.NoProcessing;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.MenuVo;
import com.zzyl.vo.ResourceVo;
import com.zzyl.vo.TreeItemVo;
import com.zzyl.vo.TreeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @Description ResourceServiceImpl
 * @Author HeFeng
 * @Date 2024-07-14
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceMapper resourceMapper;
    private final RoleResourceMapper roleResourceMapper;

    /**
     * 获取资源列表
     *
     * @param resourceDto
     * @return
     */
    @Cacheable(value = CacheConstant.RESOURCE_LIST,key = "#resourceDto.hashCode()",unless = "#result.size() == 0")
    @Override
    public List<ResourceVo> getResourceList(ResourceDto resourceDto) {
        List<Resource> resourceList = resourceMapper.selectResourceList(BeanUtil.toBean(resourceDto, Resource.class));

        return BeanUtil.copyToList(resourceList, ResourceVo.class);
    }

    /**
     * 资源树形
     *
     * @param resourceDto
     * @return
     */
    @Cacheable(value = CacheConstant.RESOURCE_TREE,key = "#resourceDto.hashCode()",unless = "#result == null")
    @Override
    public TreeVo resourceTreeVo(ResourceDto resourceDto) {
        // 1.构建查询条件：去掉结尾0的父节点编号、数据状态为正常0、资源类型为 m
        ResourceDto resourceDto1 = ResourceDto.builder()
                .parentResourceNo(NoProcessing.processString(SuperConstant.ROOT_PARENT_ID))
                .dataState(SuperConstant.DATA_STATE_0)
                .resourceType(SuperConstant.MENU).build();
        // 2.调用mapper获取所有符合条件的资源菜单列表数据
        List<Resource> resourceList = resourceMapper.selectResourceList(BeanUtil.toBean(resourceDto1, Resource.class));
        if (ObjectUtil.isEmpty(resourceList)) {
            throw new RuntimeException("没有菜单数据");
        }
        // 3.定义接收树形数据集合List<TreeItemVo>对象items
        List<TreeItemVo> items = new ArrayList<>();
        // 4.使用Hutool工具实现树形集合获取List<Tree<String>>
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setNameKey("label");
        // TreeUtil.build方法根据资源列表、根节点ID、TreeNodeConfig和节点转换器函数构建树形结构。
        // 节点转换器函数用于将资源对象转换为树节点对象，设置节点的名称、ID和父ID。
        List<Tree<String>> treeList = TreeUtil.build(resourceList, SuperConstant.ROOT_PARENT_ID, treeNodeConfig, (resource, treeNode) -> {
            treeNode.setName(resource.getResourceName());
            treeNode.setId(resource.getResourceNo());
            treeNode.setParentId(resource.getParentResourceNo());
        });
        // hutool工具默认构建的每个节点对象{id:节点编号,name:节点名称，parentId：父节点编号,children:子节点列表}
        // 目标需要的对象：{id:节点编号,label:节点名称，parentId：父节点编号,children:子节点列表}
        //    解决方案：创建TreeNodeConfig对象配置别名，给name名字改为label

        // 5.将List<Tree<String>>转换为List<TreeItemVo>的智慧养老院的children
        List<TreeItemVo> children = BeanUtil.copyToList(treeList, TreeItemVo.class);
        // 6.自定义智慧养老院TreeItemVO对象，封装children集合
        TreeItemVo treeItemVo = TreeItemVo.builder()
                .id(SuperConstant.ROOT_PARENT_ID)
                .label("智慧养老院")
                .children(children).build();
        // 7.items添加智慧养老院对象
        items.add(treeItemVo);
        // 8.创建TreeVO封装items,返回TreeVO对象
        return TreeVo.builder().items(items).build();
    }

    /**
     * 添加资源：新增菜单和新增按钮
     *
     * @param resourceDto
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.RESOURCE_TREE,allEntries = true),
            @CacheEvict(value = CacheConstant.RESOURCE_LIST,allEntries = true)
    })
    @Override
    public void createResource(ResourceDto resourceDto) {
        // 1.将resourceDto转换为resource实体类对象
        Resource resource = BeanUtil.toBean(resourceDto, Resource.class);

        // 2.同步父资源状态
        // 根据父资源编号查询资源对象
        Resource parentResource = resourceMapper.selectByParentResourceNo(resource.getParentResourceNo());
        // 同步状态
        resource.setDataState(parentResource.getDataState());

        // 3.生成子资源编号
        String resourceNo = createResourceNo(resource);
        resource.setResourceNo(resourceNo);

        // 4.插入数据库
        resourceMapper.insertSelective(resource);
    }

    /**
     * 资源修改
     *
     * @param resourceDto
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.RESOURCE_TREE,allEntries = true),
            @CacheEvict(value = CacheConstant.RESOURCE_LIST,allEntries = true)
    })
    @Override
    public void updateResource(ResourceDto resourceDto) {

        // 判断传入的父节点相对数据库是否有改变
        Resource resource = BeanUtil.toBean(resourceDto, Resource.class);
        Resource Originresource = resourceMapper.selectByPrimaryKey(resource.getId());

        List<Resource> resourceSonList =new ArrayList<>();
        if (resource.getResourceType().equals(SuperConstant.MENU) &&
                !resource.getParentResourceNo().equals(Originresource.getParentResourceNo())) {
            // 不相等，即已修改，根据当前资源编号查找子资源列表
            ResourceDto resourceDto1 = ResourceDto.builder().parentResourceNo(NoProcessing.processString(resource.getResourceNo())).build();
           resourceSonList = resourceMapper.selectResourceList(BeanUtil.toBean(resourceDto1, Resource.class));

            // 如果子资源层级+父资源层级>=5，抛出异常
            Optional<Long> OMax = resourceSonList.stream().filter(resource1 -> resource1.getResourceType().equals(SuperConstant.MENU)).map(resource1 -> Long.valueOf(resource1.getResourceNo()))
                    .max(Comparator.comparing(Long::longValue));
            if (OMax.isPresent()) {
                System.out.println(String.valueOf(OMax.get()).substring(NoProcessing.processString(resource.getResourceNo()).length()));
                if ((NoProcessing.processString(resource.getParentResourceNo()) + NoProcessing.processString(String.valueOf(OMax.get()).substring(resource.getResourceNo().length()))).length() / 3 >= 5) {
                    throw new RuntimeException("该菜单绑定到指定上级菜单后，菜单层级将超出限制");
                }
            }

        }
        // 2.同步父资源状态
        // 根据父资源编号查询资源对象
        Resource parentResource = resourceMapper.selectByParentResourceNo(resource.getParentResourceNo());
        // 同步状态
        resource.setDataState(parentResource.getDataState());
        // 相等，直接修改
        String resourceNo = createResourceNo(resource);
        resource.setResourceNo(resourceNo);
        resourceMapper.updateByPrimaryKeySelective(resource);



        // TODO 子资源编号生成

        // TODO 只提取了一级菜单
        resourceSonList.forEach(resource1 -> {
            // 如果为下一级
            if (resource1.getParentResourceNo().equals(Originresource.getResourceNo())) {

                resource1.setParentResourceNo(resourceNo);

                String resource1No = createResourceNo(resource1);
                resource1.setResourceNo(resource1No);
                resourceMapper.updateByPrimaryKeySelective(resource1);
            } else {

            }

        });
    }


    /**
     * 生成资源编号
     *
     * @param resource
     * @return
     */
    private String createResourceNo(Resource resource) {
        // 1.如果是菜单添加，判断3个数字为一个层级，上级菜单不可以超过4，大于4报错
        if (resource.getResourceType().equals(SuperConstant.MENU)
                && NoProcessing.processString(resource.getParentResourceNo()).length() / 3 > 4) {
            throw new BaseException(BasicEnum.RESOURCE_DEPTH_UPPER_LIMIT);
        }
        // 2.根据父节点编号查找下一级子节点列表
        // 构建查询条件
        ResourceDto resourceDto = ResourceDto.builder().parentResourceNo(resource.getParentResourceNo()).build();
        List<Resource> resourceList = resourceMapper.selectResourceList(BeanUtil.toBean(resourceDto, Resource.class));
        // 3.判断子节点列表是否为空
        String resourceNo = "";
        if (ObjectUtil.isNotEmpty(resourceList)) {
            // 3.1 有
            // 获取最大子节点编号
            Long maxNo = resourceList.stream().map(resource1 -> Long.valueOf(resource1.getResourceNo()))
                    .max(Comparator.comparing(Long::longValue)).get();
            // 调用工具类子节点编号+1的算法
            resourceNo = NoProcessing.createNo(maxNo.toString(), true);
        } else {
            // 3.2 没有
            // 调用工具类生成第一个子节点，拼接001
            resourceNo = NoProcessing.createNo(resource.getParentResourceNo(), false);
        }
        // 4.返回编号
        return resourceNo;
    }

    /**
     * 资源启用禁用
     *
     * @param resourceDto
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.RESOURCE_TREE,allEntries = true),
            @CacheEvict(value = CacheConstant.RESOURCE_LIST,allEntries = true)
    })
    @Override
    public void updateDateState(ResourceDto resourceDto) {
        // 启用时，如果父资源禁用，子资源不允许启用
        if (resourceDto.getDataState().equals(SuperConstant.DATA_STATE_0)) {
            Resource parentResource = resourceMapper.selectByParentResourceNo(resourceDto.getParentResourceNo());
            if (ObjectUtil.isNotEmpty(parentResource) && parentResource.getDataState().equals(SuperConstant.DATA_STATE_1)){
                throw new BaseException(BasicEnum.PARENT_MENU_DISABLE);
            }
        }

        // 根据当前资源编号查找子资源列表
        Resource resource = BeanUtil.toBean(resourceDto, Resource.class);
        ResourceDto resourceDto1 = ResourceDto.builder().parentResourceNo(NoProcessing.processString(resource.getResourceNo())).build();
        List<Resource> resourceSonList = resourceMapper.selectResourceList(BeanUtil.toBean(resourceDto1, Resource.class));

        // 遍历子菜单，修改子资源状态
        resourceSonList.forEach(resource1 -> {
            resource1.setDataState(resourceDto.getDataState());
            resourceMapper.updateByPrimaryKeySelective(resource1);
        });

        // 返回当前资源对象，修改状态
        Resource resource0 = resourceMapper.selectByParentResourceNo(resourceDto.getResourceNo());
        resource0.setDataState(resourceDto.getDataState());
        resourceMapper.updateByPrimaryKeySelective(resource0);
    }

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.RESOURCE_TREE,allEntries = true),
            @CacheEvict(value = CacheConstant.RESOURCE_LIST,allEntries = true)
    })
    @Override
    public void delete(Long menuId) {

        Resource resource = resourceMapper.selectByResourceNo(menuId.toString());
        if (resource.getDataState().equals(SuperConstant.DATA_STATE_0)){
            throw new RuntimeException("菜单启用状态,不能删除");
        }
        // 根据当前资源编号查找子资源列表
        ResourceDto resourceDto1 = ResourceDto.builder().parentResourceNo(NoProcessing.processString(resource.getResourceNo())).build();
        List<Resource> resourceSonList = resourceMapper.selectResourceList(BeanUtil.toBean(resourceDto1, Resource.class));
        if (ObjectUtil.isNotEmpty(resourceSonList)){
            throw new RuntimeException("存在子菜单,不允许删除");
        }

        // 如果角色已经关联，不允许删除
        int checkedMenuExistRole = roleResourceMapper.checkMenuExistRole(resource.getResourceNo());
        if (checkedMenuExistRole>0){
            throw new RuntimeException("菜单已分配,不允许删除");
        }


        resourceMapper.deleteByPrimaryKey(resource.getId());
    }

    /**
     * 动态菜单
     *
     * @return
     */
    @Override
    public List<MenuVo> menus() {
        Long userId = UserThreadLocal.getMgtUserId();
        List<MenuVo> menuVoList = resourceMapper.findListByUserId(userId);

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        List<Tree<String>> treeList = TreeUtil.build(menuVoList, SuperConstant.ROOT_PARENT_ID, treeNodeConfig, (menuVo, treeNode) -> {
            treeNode.setId(menuVo.getResourceNo());
            treeNode.setParentId(menuVo.getParentResourceNo());
            treeNode.putExtra("path", menuVo.getPath());
            treeNode.putExtra("redirect", menuVo.getName());
            treeNode.putExtra("meta", menuVo.getMeta());
        });
        List<MenuVo> menuVos = BeanUtil.copyToList(treeList, MenuVo.class);

        return menuVos;
    }
}
