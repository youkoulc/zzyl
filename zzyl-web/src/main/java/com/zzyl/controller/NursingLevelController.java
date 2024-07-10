package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.NursingLevelDto;
import com.zzyl.entity.NursingLevel;
import com.zzyl.service.NursingLevelService;
import com.zzyl.vo.NursingLevelVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 新增护理等级
     *
     * @param nursingLevelDto
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation("新增护理等级")
    public ResponseResult add(
            @ApiParam(value = "护理等级数据传输对象", required = true)
            @RequestBody NursingLevelDto nursingLevelDto) {
        nursingLevelService.add(nursingLevelDto);
        return success();
    }

    /**
     * 根据id查询护理等级
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询护理等级")
    public ResponseResult<NursingLevelVo> getById(@ApiParam("护理等级id") @PathVariable Long id) {
        NursingLevelVo nursingLevelVo = nursingLevelService.getById(id);
        return success(nursingLevelVo);
    }

    /**
     * 修改护理等级
     * @param nursingLevelDto
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("修改护理等级")
    public ResponseResult update(@RequestBody NursingLevelDto nursingLevelDto){
        nursingLevelService.update(nursingLevelDto);
        return success();
    }


    /**
     * 删除护理等级
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除护理等级")
    public ResponseResult delete(@ApiParam("护理等级id") @PathVariable Long id){
        nursingLevelService.delete(id);
        return success();
    }

    /**
     * 切换状态
     * @param id
     * @param status
     * @return
     */
    @PutMapping("/{id}/status/{status}")
    @ApiOperation("启用禁用")
    @ApiImplicitParams({@ApiImplicitParam(value = "护理等级id", name = "id", required = true),
            @ApiImplicitParam(value = "护理等级状态", name = "status", required = true)})
    public ResponseResult switchStatus( @PathVariable Long id, @PathVariable Integer status){
        nursingLevelService.switchStatus(id,status);
        return success();
    }
}
