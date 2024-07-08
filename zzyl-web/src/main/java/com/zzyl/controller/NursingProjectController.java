package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.NursingProjectDto;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nursing_project")
@Api(tags = "护理项目管理")
public class NursingProjectController extends BaseController {
    @Autowired
    private NursingProjectService nursingProjectService;

    /**
     * 分页查询护理项目列表
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    @GetMapping
    @ApiOperation("分页查询护理项目列表")
    public ResponseResult<PageResponse<NursingProjectVo>> getByPage(@ApiParam("护理项目名称") String name,
                                                                    @ApiParam("当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                    @ApiParam("每页显示条数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                    @ApiParam("状态：0-禁用，1-启用") Integer status) {

        PageResponse<NursingProjectVo> pageResponse = nursingProjectService.getByPage(name, pageNum, pageSize, status);
        return success(pageResponse);
    }

    /**
     * 新增护理项目
     *
     * @param nursingProjectDto
     * @return
     */
    @PostMapping
    @ApiOperation("新增护理项目")
    public ResponseResult add(@ApiParam(value = "护理项目数据传输对象", required = true)
                              @RequestBody NursingProjectDto nursingProjectDto) {
        nursingProjectService.add(nursingProjectDto);
        return success();
    }

    /**
     * 根据id查询护理项目
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询护理项目")
    public ResponseResult<NursingProjectVo> getById(@ApiParam(value = "护理项目id", required = true)
                                                    @PathVariable Long id) {
        NursingProjectVo nursingProjectVo = nursingProjectService.getById(id);
        return success(nursingProjectVo);
    }


    /**
     * 修改护理项目
     * @param nursingProjectDto
     * @return
     */
    @PutMapping
    @ApiOperation("修改护理项目")
    public ResponseResult update(
            @ApiParam(value = "护理项目数据传输对象", required = true)
            @RequestBody NursingProjectDto nursingProjectDto){
        nursingProjectService.update(nursingProjectDto);
        return success();
    }


}
