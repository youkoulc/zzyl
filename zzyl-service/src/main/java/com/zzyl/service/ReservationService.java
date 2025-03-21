package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.ReservationDto;
import com.zzyl.vo.ReservationVo;
import com.zzyl.vo.TimeCountVo;

import java.util.List;

/**
 * @Description ReservationService
 * @Author HeFeng
 * @Date 2024-07-12
 */
public interface ReservationService {
    /**
     * 查询当天取消预约数量
     * @return
     */
    Integer getCancelledCount(Long userId);

    /**
     * 查询每个时间段剩余预约次数
     */
    List<TimeCountVo> getCountByTime(Long time);

    /**
     * 新增预约
     * @param reservationDto
     * @return
     */
    void add(ReservationDto reservationDto);

    /**
     * 分页查询预约记录
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    PageResponse<ReservationVo> getByPage(Integer pageNum, Integer pageSize, Integer status);

    /**
     * 取消预约
     * @param id
     * @return
     */
    void cancel(Long id);

}
