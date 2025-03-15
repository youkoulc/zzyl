package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.NursingPlanDto;
import com.zzyl.dto.NursingProjectPlanDto;
import com.zzyl.service.NursingPlanService;
import com.zzyl.vo.NursingPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Properties;

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

    /**
     * 新增护理计划
     *
     * @param nursingPlanDto
     * @return
     */

    @PostMapping
    @ApiOperation("新增护理计划")
    @ApiImplicitParams({@ApiImplicitParam(value = "护理计划", name = "nursingPlanDto", required = true, dataType = "NursingPlanDto")})
    public ResponseResult addNursingPlan(@RequestBody NursingPlanDto nursingPlanDto) {
        nursingPlanService.addNursingPlan(nursingPlanDto);
        return success();
    }

    /**
     * 根据id删除护理计划
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除护理计划")
    @ApiImplicitParams({@ApiImplicitParam(value = "护理计划id", name = "id", required = true)})
    public ResponseResult deleteNursingPlan(@PathVariable("id") Integer id) {
        nursingPlanService.deleteNursingPlan(id);
        return success();
    }

    /**
     * 根据id获取护理计划
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id获取护理计划")
    @ApiImplicitParams({@ApiImplicitParam(value = "护理计划id", name = "id", required = true)})
    public ResponseResult<NursingPlanVo> getById(@PathVariable("id") Integer id) {
        NursingPlanVo nursingPlanVos = nursingPlanService.getById(id);
        return success(nursingPlanVos);
    }

    /**
     * 更新护理计划
     *
     * @param nursingPlanDto
     * @return
     */
    @PutMapping("/{id}")
    @ApiOperation("更新护理计划")
    @ApiImplicitParams({@ApiImplicitParam(value = "护理计划", name = "nursingPlanDto", required = true, dataType = "NursingPlanDto")})
    public ResponseResult updateNursingPlan(@RequestBody NursingPlanDto nursingPlanDto) {
        nursingPlanService.updateNursingPlan(nursingPlanDto);
        return success();
    }

    /**
     * 更新护理计划状态
     *
     * @param id
     * @param status
     * @return
     */
    @PutMapping("/{id}/status/{status}")
    @ApiOperation("更新护理计划状态")
    @ApiImplicitParams({@ApiImplicitParam(value = "护理计划id", name = "id", required = true),
            @ApiImplicitParam(value = "状态", name = "status", required = true)})
    public ResponseResult statusChange(@PathVariable("id") Integer id, @PathVariable("status") Integer status) {
        nursingPlanService.updateStatus(id, status);
        return success();
    }
}
