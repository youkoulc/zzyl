package com.zzyl.controller;

import com.zzyl.base.ResponseResult;
import com.zzyl.dto.BedDto;
import com.zzyl.service.BedService;
import com.zzyl.vo.BedVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bed")
@Api(tags = "床位管理相关接口")
public class BedController extends BaseController {

    @Autowired
    private BedService bedService;

    @GetMapping("/read/room/{roomId}")
    @ApiOperation(value = "根据房间id查询床位", notes = "传入房间id")
    public ResponseResult<List<BedVo>> readBedByRoomId(
            @ApiParam(value = "房间ID", required = true) @PathVariable("roomId") Long roomId) {
        List<BedVo> beds = bedService.getBedsByRoomId(roomId);
        return success(beds);
    }

    /**
     * 创建床位
     *
     * @param bedDto
     * @return
     */
    @PostMapping("/create")
    @ApiOperation(value = "创建床位")
    public ResponseResult creatBed(@ApiParam(value = "床位bto", required = true) @RequestBody BedDto bedDto) {

        return toAjax(bedService.creatBed(bedDto));
    }

    /**
     * 根据id查询床位
     *
     * @param id
     * @return
     */
    @GetMapping("/read/{id}")
    @ApiOperation("根据床位id查询床位")
    @ApiImplicitParam(value = "床位id", required = true, name = "id")
    public ResponseResult<BedVo> readBed(@PathVariable Integer id) {
        BedVo bedVo = bedService.getBedById(id);
        return success(bedVo);
    }

    /**
     * 更新床位
     *
     * @param bedDto
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("更新床位")
    @ApiImplicitParam(value = "床位dto", name = "bedDto", required = true)
    public ResponseResult updateBed(@RequestBody BedDto bedDto) {

        bedService.updateBed(bedDto);
        return success();
    }

    /**
     * 删除床位
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除床位")
    public ResponseResult deleteBed(@ApiParam(value = "床位id", required = true) @PathVariable Integer id) {
        bedService.deleteBed(id);
        return success();
    }
}
