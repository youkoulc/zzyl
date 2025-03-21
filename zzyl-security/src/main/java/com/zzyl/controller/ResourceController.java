package com.zzyl.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.ResourceDto;
import com.zzyl.service.ResourceService;
import com.zzyl.vo.MenuVo;
import com.zzyl.vo.ResourceVo;
import com.zzyl.vo.TreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description ResourceController
 * @Author HeFeng
 * @Date 2024-07-14
 */
@RestController
@RequestMapping("/resource")
@Api(tags = "资源管理")
@Slf4j
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;

    /**
     * 获取资源列表
     *
     * @param resourceDto
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("资源列表")
    @ApiOperationSupport(includeParameters = {"resourceDto.parentResourceNo", "resourceDto.resourceType"})
    public ResponseResult getResourceList(@ApiParam(name = "resourceDto", value = "资源Dto对象", required = true) @RequestBody ResourceDto resourceDto) {
        List<ResourceVo> resourceVoList = resourceService.getResourceList(resourceDto);
        return ResponseResult.success(resourceVoList);
    }


    /**
     * 资源树形
     *
     * @param resourceDto
     * @return
     */
    @PostMapping("/tree")
    @ApiOperation("资源树形")
    @ApiImplicitParam(name = "resourceDto", value = "资源Dto对象", required = true, dataType = "ResourceDto")
    @ApiOperationSupport(includeParameters = {"resourceDto.label"})
    public ResponseResult<TreeVo> resourceTreeVo(@RequestBody ResourceDto resourceDto) {
        TreeVo treeVo = resourceService.resourceTreeVo(resourceDto);
        return ResponseResult.success(treeVo);
    }

    /**
     * 添加资源：新增菜单和新增按钮
     *
     * @param resourceDto
     * @return
     */
    @PutMapping
    @ApiOperation(value = "资源添加", notes = "资源添加")
    @ApiImplicitParam(name = "resourceDto", value = "资源DTO对象", required = true, dataType = "ResourceDto")
    @ApiOperationSupport(includeParameters = {"resourceDto.dataState"
            , "resourceDto.icon"
            , "resourceDto.parentResourceNo"
            , "resourceDto.requestPath"
            , "resourceDto.resourceName"
            , "resourceDto.resourceType"
            , "resourceDto.sortNo"})
    public ResponseResult<ResourceVo> createResource(@RequestBody ResourceDto resourceDto) {
        resourceService.createResource(resourceDto);
        return ResponseResult.success();
    }

    /**
     * 资源修改
     *
     * @param resourceDto
     * @return
     */
    @PatchMapping
    @ApiOperation("资源修改")
    public ResponseResult updateResource(@RequestBody ResourceDto resourceDto) {
        resourceService.updateResource(resourceDto);
        return ResponseResult.success();
    }

    /**
     * 资源启用禁用
     *
     * @param resourceDto
     * @return
     */
    @PostMapping("/enable")
    @ApiOperation("资源启用禁用")
    public ResponseResult updateDateState(@RequestBody ResourceDto resourceDto) {
        resourceService.updateDateState(resourceDto);
        return ResponseResult.success();
    }

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    @DeleteMapping("/{menuId}")
    @ApiOperation("删除菜单")
    public ResponseResult delete(@PathVariable Long menuId) {
        resourceService.delete(menuId);
        return ResponseResult.success();
    }

    /**
     * 动态菜单
     * @return
     */
    @GetMapping("/menus")
    @ApiOperation(value = "左侧菜单", notes = "左侧菜单")
    public ResponseResult<List<MenuVo>> menus() {
        List<MenuVo> menuVoList=resourceService.menus();
        return ResponseResult.success(menuVoList);
    }

}
