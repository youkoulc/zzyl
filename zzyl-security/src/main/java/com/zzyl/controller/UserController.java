package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.UserDto;
import com.zzyl.mapper.UserMapper;
import com.zzyl.service.UserService;
import com.zzyl.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description UserController
 * @Author HeFeng
 * @Date 2024-07-15
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户分页
     *
     * @param userDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/page/{pageNum}/{pageSize}")
    @ApiOperation("用户分页")
    public ResponseResult<PageResponse<UserVo>> getByPage(@RequestBody UserDto userDto,
                                                          @PathVariable Integer pageNum,
                                                          @PathVariable Integer pageSize) {
        PageResponse<UserVo> pageResponse = userService.getByPage(userDto, pageNum, pageSize);
        return ResponseResult.success(pageResponse);
    }

    /**
     * 添加用户
     * @param userDto
     * @return
     */
    @PutMapping
    @ApiOperation("添加用户")
    public ResponseResult createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return ResponseResult.success();
    }

    /**
     * 用户修改
     * @param userDto
     * @return
     */
    @PatchMapping
    @ApiOperation("用户修改")
    public ResponseResult updateUser(@RequestBody UserDto userDto){
        userService.updateUser(userDto);
        return ResponseResult.success();
    }
}
