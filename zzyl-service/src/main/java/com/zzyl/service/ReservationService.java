package com.zzyl.service;

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
}
