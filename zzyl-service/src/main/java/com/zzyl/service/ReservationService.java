package com.zzyl.service;

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
}
