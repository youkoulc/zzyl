package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.service.NursingLevelService;
import com.zzyl.vo.NursingLevelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description NursingLevelController
 * @Author HeFeng
 * @Date 2024-07-09
 */
@RestController
@RequestMapping("/nursingLevel")
@Api(tags = "护理等级管理")
public class NursingLevelController extends BaseController {
    @Autowired
    private NursingLevelService nursingLevelService;

    /**
     * 查询所有护理等级
     *
     * @return
     */
    @GetMapping("/listAll")
    @ApiOperation("查询所有护理等级")
    public ResponseResult<List<NursingLevelVo>> getAll() {
        List<NursingLevelVo> list = nursingLevelService.getAll();
        return success(list);
    }


    /**
     * 护理等级条件分页查询
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    @GetMapping("/listByPage")
    @ApiOperation("护理等级条件分页查询")
    public ResponseResult<PageResponse<NursingLevelVo>> getByPage(@ApiParam("护理等级名称") String name, @ApiParam("当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @ApiParam("每页显示条数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @ApiParam("状态：0-禁用，1-启用") Integer status) {
        PageResponse<NursingLevelVo> pageResponse = nursingLevelService.getByPage(name, pageNum, pageSize, status);
        return success(pageResponse);
    }
}
