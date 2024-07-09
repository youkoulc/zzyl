package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.service.NursingPlanService;
import com.zzyl.vo.NursingPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/nursing/plan")
@Api(tags = "护理计划")
@Slf4j
public class NursingPlanController extends BaseController {
    @Autowired
    private NursingPlanService nursingPlanService;

    /**
     * 获取所有护理计划
     *
     * @return
     */
    @GetMapping
    @ApiOperation("获取所有护理计划 ")
    public ResponseResult<List<NursingPlanVo>> getNursingPlan() {
        return success(nursingPlanService.getNursingPlan());

    }

    /**
     * 获取护理计划
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    @GetMapping("/search")
    @ApiOperation("分页获取护理计划")
    @ApiImplicitParams({@ApiImplicitParam(value = "护理项目名称", name = "name", required = true),
            @ApiImplicitParam(value = "页码", name = "pageNum", required = true),
            @ApiImplicitParam(value = "每页条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "状态", name = "status", required = true)})
    public ResponseResult<PageResponse<NursingPlanVo>> getByPage(String name, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Integer status) {
       log.info("分页获取护理计划 {} {} {} {}", name, pageNum, pageSize, status);
        PageResponse<NursingPlanVo> pageResponse = nursingPlanService.getByPage(name, pageNum, pageSize, status);
        return success(pageResponse);
    }
}
