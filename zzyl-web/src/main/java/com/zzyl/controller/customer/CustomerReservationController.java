package com.zzyl.controller.customer;

import com.zzyl.base.ResponseResult;
import com.zzyl.controller.BaseController;
import com.zzyl.service.ReservationService;
import com.zzyl.utils.UserThreadLocal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description CustomerReservationController
 * @Author HeFeng
 * @Date 2024-07-12
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/reservation")
@Api(tags = "参观预约")
public class CustomerReservationController extends BaseController {
    private final ReservationService reservationService;

    /**
     * 查询取消预约数量
     * @return
     */
    @GetMapping("/cancelled-count")
    @ApiOperation("查询取消预约数量")
    public ResponseResult<Integer> getCancelledCount( ){
        Long userId = UserThreadLocal.getUserId();
        Integer count=reservationService.getCancelledCount(userId);
        return success(count);
    } 
}
