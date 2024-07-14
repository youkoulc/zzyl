package com.zzyl.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.ResourceDto;
import com.zzyl.service.ResourceService;
import com.zzyl.vo.ResourceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param resourceDto
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("资源列表")
    @ApiOperationSupport(includeParameters = {"resourceDto.parentResourceNo","resourceDto.resourceType"})
    public ResponseResult getResourceList(@ApiParam(value = "资源Dto对象",required = true) @RequestBody ResourceDto resourceDto){
        List<ResourceVo> resourceVoList=resourceService.getResourceList(resourceDto);
        return ResponseResult.success(resourceVoList);
    } 
}
