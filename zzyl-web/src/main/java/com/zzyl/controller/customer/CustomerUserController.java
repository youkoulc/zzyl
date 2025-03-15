package com.zzyl.controller.customer;

import com.zzyl.base.ResponseResult;
import com.zzyl.controller.BaseController;
import com.zzyl.dto.UserLoginRequestDto;
import com.zzyl.service.MemberService;
import com.zzyl.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description CustomerUserController
 * @Author HeFeng
 * @Date 2024-07-11
 */
@RestController
@Slf4j
@RequestMapping("/customer/user")
@RequiredArgsConstructor
@Api(tags = "客户管理")
public class CustomerUserController extends BaseController {
    private final MemberService memberService;

    /**
     * 小程序微信登录
     * @param userLoginRequestDto
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("微信登录")
    public ResponseResult<LoginVo> login(
            @ApiParam("用户登录信息")
            @RequestBody UserLoginRequestDto userLoginRequestDto){
        LoginVo loginVo=memberService.login(userLoginRequestDto);
        return success(loginVo);
    } 
}
