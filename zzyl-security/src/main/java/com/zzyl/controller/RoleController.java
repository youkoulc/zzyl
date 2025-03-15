package com.zzyl.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.RoleDto;
import com.zzyl.service.RoleService;
import com.zzyl.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @Description RoleController
 * @Author HeFeng
 * @Date 2024-07-15
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 角色分页
     * @param roleDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/page/{pageNum}/{pageSize}")
    @ApiOperation(value = "角色分页",notes = "角色分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleDto",value = "角色DTO对象",required = true,dataType = "roleDto"),
            @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
            @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    public ResponseResult<PageResponse<RoleVo>> getByPage(@RequestBody RoleDto roleDto, @PathVariable Integer pageNum, @PathVariable Integer pageSize){
        PageResponse<RoleVo> pageResponse=roleService.getByPage(roleDto,pageNum,pageSize);
        return ResponseResult.success(pageResponse);
    }

    @PutMapping
    @ApiOperation(value = "角色添加",notes = "角色添加")
    @ApiImplicitParam(name = "roleDto",value = "角色DTO对象",required = true,dataType = "roleDto")
    @ApiOperationSupport(includeParameters = {"roleDto.roleName","roleDto.dataState"})
    public ResponseResult createRole(@RequestBody RoleDto roleDto) {
        roleService.createRole(roleDto);
        return ResponseResult.success();
    }

    /**
     * 根据角色查询选中的资源数据
     * @param roleId
     * @return
     */
    @ApiOperation(value = "根据角色查询选中的资源数据")
    @GetMapping("/find-checked-resources/{roleId}")
    public ResponseResult<Set<String>> findCheckedResources(@PathVariable("roleId") Long roleId){
        Set<String> resources= roleService.findCheckedResources(roleId);
        return ResponseResult.success(resources);
    }

    /**
     * 角色修改
     * @param roleDto
     * @return
     */
    @PatchMapping
    @ApiOperation("角色修改")
    public ResponseResult updateRole(@RequestBody RoleDto roleDto){
        roleService.updateRole(roleDto);
        return ResponseResult.success();
    }

    /**
     * 删除角色
     */
    @ApiOperation("删除角色")
    @DeleteMapping("/{roleId}")
    public ResponseResult remove(@PathVariable("roleId") Long roleId) {
        return ResponseResult.success(roleService.deleteRoleById(roleId));
    }

    @PostMapping("/init-roles")
    @ApiOperation("角色下拉框")
    public ResponseResult<List<RoleVo>> getInitRoles( ){
        List<RoleVo> list=roleService.getInitRoles();
        return ResponseResult.success(list);
    }
}
