package com.zzyl.controller;

import com.zzyl.base.ResponseResult;
import com.zzyl.service.NursingPlanService;
import com.zzyl.vo.NursingPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(":/nursing/plan")
@Api(tags = "护理计划")
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
}
