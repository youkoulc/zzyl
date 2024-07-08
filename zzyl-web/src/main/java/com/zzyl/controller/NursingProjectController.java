package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "护理项目管理")
public class NursingProjectController extends BaseController {
    @Autowired
    private NursingProjectService nursingProjectService;

    /**
     * 分页查询护理项目列表
     * @param name
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    @GetMapping("/nursing_project")
    @ApiOperation("分页查询护理项目列表")
    public ResponseResult<PageResponse<NursingProjectVo>> getByPage(@ApiParam("护理项目名称") String name,
                                                                    @ApiParam("当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                    @ApiParam("每页显示条数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                    @ApiParam("状态：0-禁用，1-启用") Integer status) {

        PageResponse<NursingProjectVo> pageResponse = nursingProjectService.getByPage(name,pageNum,pageSize,status);
        return success(pageResponse);
    }
}
