package com.zzyl.controller;

import com.zzyl.base.ResponseResult;
import com.zzyl.service.NursingLevelService;
import com.zzyl.vo.NursingLevelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * @return
     */
    @GetMapping("/listAll")
    @ApiOperation("查询所有护理等级")
    public ResponseResult<List<NursingLevelVo>> getAll( ){
        List<NursingLevelVo> list=nursingLevelService.getAll();
        return success(list);
    } 
}
