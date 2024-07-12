package com.zzyl.controller.customer;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.controller.BaseController;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Description CustomerProjectController
 * @Author HeFeng
 * @Date 2024-07-12
 */
@RestController
@RequestMapping("/customer/orders/project")
@RequiredArgsConstructor
public class CustomerNursingProjectController extends BaseController {
    private final NursingProjectService nursingProjectService;

    @GetMapping("/page")
    @ApiOperation("分页查询护理项目列表")
    public ResponseResult<PageResponse<NursingProjectVo>> getByPage(@ApiParam("护理项目名称") String name,
                                                                    @ApiParam("当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                    @ApiParam("每页显示条数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                    @ApiParam("状态：0-禁用，1-启用") Integer status) {

        PageResponse<NursingProjectVo> pageResponse = nursingProjectService.getByPage(name, pageNum, pageSize, status);
        return success(pageResponse);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询护理项目")
    public ResponseResult<NursingProjectVo> getById(@ApiParam(value = "护理项目id", required = true) @PathVariable Long id) {
        NursingProjectVo nursingProjectVo = nursingProjectService.getById(id);
        return success(nursingProjectVo);
    }
}
